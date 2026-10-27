package functor.sublimation.android

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.HorizontalScrollView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import android.content.res.ColorStateList
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.slider.Slider
import org.json.JSONArray
import org.json.JSONObject
/**
 * JS-KT 通信桥（WebView JavascriptInterface 注入为 __dshAndroid，prompt 通道兜底）。
 *
 * 桥能力（android doc/安卓标准库.md）：
 *   - 基础：log/toast/layout/7 个生命周期/androidRecompose（增量重组）；
 *   - 意图（intent.subl）：openUrl/shareText/dial/sms/openSettings/launchApp；
 *   - 平台能力（platform.subl）：clipboard/storage（持久化）/keepScreenOn/vibrate/
 *     screenW/H/D/installedApps/pickFile（异步：系统选择器 → __dshFn 回调）；
 *   - 值返回型内建（Nat/Bool/List[Str]）返回 JSON 字符串（JS 端 JSON.parse），
 *     Str 型返回原文（prompt 与 JavascriptInterface 通道一致）。
 *
 * 增量重组：layout / androidRecompose 都按「新旧 JSON 树深比较」只更新变化的子树，
 * 相等的视图原样保留（不重建——其状态、监听器、布局参数全部保持）。
 */
class DshBridge(private val activity: MainActivity) {
    private val tag = "Sublimation"
    private val lifecycleCallbacks = mutableSetOf<String>()
    private val firedEvents = mutableSetOf<String>()
    // lazy：DshBridge 在 Activity 字段初始化期构造（此时 base context 尚未 attach），故延后取
    private val prefs by lazy { activity.getSharedPreferences("dsh-storage", 0) }

    @android.webkit.JavascriptInterface
    fun dispatch(kind: String, argsJson: String): String {
        return try { handle(kind, argsJson) } catch (t: Throwable) {
            Log.e(tag, "bridge error [" + kind + "]: " + t.message); "null"
        }
    }

    private fun handle(kind: String, argsJson: String): String {
        val args = if (argsJson.isEmpty() || argsJson == "null") emptyList<Any?>()
            else parseJsonArray(argsJson)
        when (kind) {
            "log" -> {
                val msg = args.getOrNull(0)?.toString() ?: ""
                val level = (args.getOrNull(1) as? Number)?.toInt() ?: 2
                when (level) {
                    0 -> Log.v(tag, msg); 1 -> Log.d(tag, msg); 3 -> Log.w(tag, msg)
                    4 -> Log.e(tag, msg); else -> Log.i(tag, msg)
                }
            }
            "toast" -> {
                val msg = args.getOrNull(0)?.toString() ?: ""
                Log.d(tag, "toast " + msg)
                val long = (args.getOrNull(1) as? Boolean) == true
                activity.runOnUiThread {
                    Toast.makeText(activity, msg,
                        if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
                }
            }
            "registerLifecycle" -> {
                val event = args.getOrNull(0)?.toString() ?: return "null"
                lifecycleCallbacks += event
                if (event in firedEvents) dispatchLater(event)
            }
            "layout" -> {
                val viewJson = args.getOrNull(0)?.toString() ?: return "null"
                val root = JSONObject(viewJson)
                val old = rootJson
                rootJson = root
                activity.runOnUiThread {
                    if (old == null) setRootView(buildView(root.toString()))
                    else applyRootDiff(old, root)
                }
            }
            "androidRecompose" -> {
                val viewJson = args.getOrNull(2)?.toString() ?: return "null"
                val rkind = args.getOrNull(0)?.toString() ?: return "null"
                val ord = (args.getOrNull(1) as? Number)?.toInt() ?: 1
                val rj = rootJson ?: return "null"
                val old = JSONObject(rj.toString())
                if (!replaceNode(rj, rkind, ord, JSONObject(viewJson))) {
                    Log.w(tag, "recompose 定位失败: " + rkind + "#" + ord)
                    return "null"
                }
                if (jsonEquals(old, rj)) return "null"
                activity.runOnUiThread { applyRootDiff(old, rj) }
            }
            // ---------------- 意图（intent.subl） ----------------
            "openUrl" -> {
                val url = args.getOrNull(0)?.toString() ?: return "null"
                ui {
                    try { activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) }
                    catch (t: Throwable) { toastFail("无法打开链接：" + t.message) }
                }
            }
            "shareText" -> {
                val text = args.getOrNull(0)?.toString() ?: return "null"
                ui {
                    try {
                        val it = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"; putExtra(Intent.EXTRA_TEXT, text)
                        }
                        activity.startActivity(Intent.createChooser(it, "分享"))
                    } catch (t: Throwable) { toastFail("无法分享：" + t.message) }
                }
            }
            "dial" -> {
                val phone = args.getOrNull(0)?.toString() ?: return "null"
                ui {
                    try { activity.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone))) }
                    catch (t: Throwable) { toastFail("无法拨号：" + t.message) }
                }
            }
            "sms" -> {
                val phone = args.getOrNull(0)?.toString() ?: return "null"
                ui {
                    try {
                        val it = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone))
                        activity.startActivity(it)
                    } catch (t: Throwable) { toastFail("无法发短信：" + t.message) }
                }
            }
            "openSettings" -> {
                ui {
                    try {
                        val it = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        it.data = Uri.parse("package:" + activity.packageName)
                        activity.startActivity(it)
                    } catch (t: Throwable) { toastFail("无法打开设置：" + t.message) }
                }
            }
            "launchApp" -> {
                val pkg = args.getOrNull(0)?.toString() ?: return "null"
                ui {
                    val it = activity.packageManager.getLaunchIntentForPackage(pkg)
                    if (it != null) {
                        try { activity.startActivity(it) } catch (t: Throwable) { toastFail("无法启动：" + pkg) }
                    } else toastFail("应用未安装：" + pkg)
                }
            }
            // ---------------- 平台能力（platform.subl） ----------------
            "clipboardSet" -> {
                val text = args.getOrNull(0)?.toString() ?: ""
                val cm = activity.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as ClipboardManager
                cm.setPrimaryClip(ClipData.newPlainText("text", text))
            }
            "clipboardGet" -> {
                val cm = activity.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = cm.primaryClip
                return if (clip != null && clip.itemCount > 0) clip.getItemAt(0).text.toString() else ""
            }
            "storagePut" -> {
                val key = args.getOrNull(0)?.toString() ?: return "null"
                val value = args.getOrNull(1)?.toString() ?: ""
                prefs.edit().putString(key, value).apply()
            }
            "storageGet" -> {
                val key = args.getOrNull(0)?.toString() ?: return "null"
                return prefs.getString(key, "") ?: ""
            }
            "keepScreenOn" -> {
                val on = (args.getOrNull(0) as? Boolean) == true
                ui { activity.web.keepScreenOn = on }
            }
            "vibrate" -> {
                val ms = (args.getOrNull(0) as? Number)?.toInt() ?: 30
                val vb = activity.getSystemService(android.content.Context.VIBRATOR_SERVICE) as Vibrator
                try {
                    if (Build.VERSION.SDK_INT >= 26) vb.vibrate(VibrationEffect.createOneShot(ms.toLong(), VibrationEffect.DEFAULT_AMPLITUDE))
                    else @Suppress("DEPRECATION") vb.vibrate(ms.toLong())
                } catch (t: Throwable) { Log.w(tag, "vibrate: " + t.message) }
            }
            "screenWidth" -> return activity.resources.displayMetrics.widthPixels.toString()
            "screenHeight" -> return activity.resources.displayMetrics.heightPixels.toString()
            "screenDensity" -> return activity.resources.displayMetrics.densityDpi.toString()
                        // 时间格式化：java.time → "MM-dd HH:mm"
            "fmtTime" -> {
                val ms = (args.getOrNull(0) as? Number)?.toLong() ?: 0L
                val inst = java.time.Instant.ofEpochMilli(ms)
                val zid = java.time.ZoneId.systemDefault()
                val dt = java.time.LocalDateTime.ofInstant(inst, zid)
                val fmt = java.time.format.DateTimeFormatter.ofPattern("MM-dd HH:mm")
                dt.format(fmt)
            }
            "installedApps" -> {
                val apps = activity.packageManager.getInstalledApplications(0)
                val arr = JSONArray()
                for (i in 0 until apps.size) arr.put(apps[i].packageName)
                return arr.toString()
            }
            "pickFile" -> {
                // 异步：系统选择器返回后经 evaluateJavascript 回调 JS 闭包（key 由编译器分配）
                val mime = args.getOrNull(0)?.toString() ?: "*/*"
                val key = args.getOrNull(1)?.toString() ?: return "null"
                activity.pendingPickKey = key
                ui {
                    try {
                        if (mime == "") activity.pickLauncher.launch("*/*")
                        else activity.pickLauncher.launch(mime)
                    } catch (t: Throwable) {
                        activity.pendingPickKey = null
                        toastFail("无法选取文件：" + t.message)
                    }
                }
            }
            else -> Log.w(tag, "unknown bridge kind: " + kind)
        }
        return "null"
    }

    private fun ui(f: () -> Unit) { activity.runOnUiThread(f) }
    private fun toastFail(msg: String) { Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show() }
    private fun jsCallback(key: String, argJs: String) {
        activity.web.evaluateJavascript(
            "try{ globalThis.__dshFn_" + key + "(" + argJs + "); }catch(e){ console.log(e); }", null)
    }

    fun dispatchLifecycle(event: String) {
        if (event in lifecycleCallbacks) dispatchLater(event)
        else firedEvents += event
    }

    private fun dispatchLater(event: String) {
        activity.web.post {
            activity.web.evaluateJavascript(
                "try{ __dshLifecycle_" + event + "(); }catch(e){ console.log(e); }", null)
        }
    }
    // ---------------- 增量重组（diff） ----------------

    /** 当前视图树的 JSON（layout 写入；recompose 在树上原地替换节点）。 */
    private var rootJson: JSONObject? = null
    /** 当前根原生视图（JSON 树的原生镜像）。 */
    private var rootView: View? = null

    private fun setRootView(v: View) {
        rootView = v
        activity.setContentView(v)
    }

    /**
     * 新旧两棵树的根级 diff。相等 → 什么都不做；
     * 根类型/自身字段变化 → 整树重建；根字段相同且同是 Box（含 children）→ 逐子树增量。
     */
    private fun applyRootDiff(old: JSONObject, nw: JSONObject) {
        val rv = rootView ?: run { setRootView(buildView(nw.toString())); return }
        if (jsonEquals(old, nw)) {
            Log.d(tag, "diff: 无变化，跳过")
            return
        }
        if (old.optString("tag", "") != nw.optString("tag", "") || !fieldsEqual(old, nw)) {
            setRootView(buildView(nw.toString()))
            return
        }
        val oldCh = old.optJSONArray("children")
        val newCh = nw.optJSONArray("children")
        val group = rv as? ViewGroup
        if (oldCh == null || newCh == null || group == null ||
            oldCh.length() != newCh.length() || oldCh.length() != group.childCount) {
            setRootView(buildView(nw.toString()))
            return
        }
        for (i in 0 until newCh.length()) {
            val o = oldCh.optJSONObject(i) ?: continue
            val n = newCh.optJSONObject(i) ?: continue
            applyChildDiff(o, n, group, i)
        }
    }

    private fun applyChildDiff(old: JSONObject, nw: JSONObject, host: ViewGroup, i: Int) {
        if (jsonEquals(old, nw)) return
        val oldTag = old.optString("tag", "")
        val newTag = nw.optString("tag", "")
        val oldV = if (i < host.childCount) host.getChildAt(i) else null
        if (newTag != oldTag || oldV == null) {
            rebuild(host, i, nw)
            return
        }
        if (newTag == "Box" && fieldsEqual(old, nw)) {
            val oldCh = old.optJSONArray("children")
            val newCh = nw.optJSONArray("children")
            val group = oldV as? ViewGroup
            if (oldCh != null && newCh != null && group != null &&
                oldCh.length() == newCh.length() && newCh.length() == group.childCount) {
                for (k in 0 until newCh.length()) {
                    val o = oldCh.optJSONObject(k) ?: continue
                    val n = newCh.optJSONObject(k) ?: continue
                    applyChildDiff(o, n, group, k)
                }
                return
            }
        }
        rebuild(host, i, nw)
    }

    /** 原位重建第 i 个子树（同位置）。 */
    private fun rebuild(host: ViewGroup, i: Int, nw: JSONObject) {
        host.removeViewAt(i)
        host.addView(buildView(nw.toString()), i)
        Log.d(tag, "diff: 重建 " + nw.optString("tag", "?") + " 子树")
    }

    /** 按 tag + __ord 在树中替换节点（recompose 用）。 */
    private fun replaceNode(node: JSONObject, tag: String, ord: Int, replacement: JSONObject): Boolean {
        val children = node.optJSONArray("children") ?: return false
        for (i in 0 until children.length()) {
            val c = children.optJSONObject(i) ?: continue
            if (c.optString("tag") == tag && c.optInt("__ord", -1) == ord) {
                children.put(i, replacement); return true
            }
            if (replaceNode(c, tag, ord, replacement)) return true
        }
        return false
    }

    /** JSON 深相等（String/Int/Bool/Double + 嵌套对象/数组）。 */
    private fun jsonEquals(a: Any?, b: Any?): Boolean = when {
        a is JSONObject && b is JSONObject ->
            a.length() == b.length() && a.keys().asSequence().all { k -> b.has(k) && jsonEquals(a.opt(k), b.opt(k)) }
        a is JSONArray && b is JSONArray ->
            a.length() == b.length() && (0 until a.length()).all { i -> jsonEquals(a.opt(i), b.opt(i)) }
        else -> a == b
    }

    /** 视图自身字段是否相同（排除 children——children 由增量路径处理）。 */
    private fun fieldsEqual(a: JSONObject, b: JSONObject): Boolean {
        val ka = mutableListOf<String>()
        a.keys().forEach { if (it != "children") ka += it }
        val kb = mutableListOf<String>()
        b.keys().forEach { if (it != "children") kb += it }
        if (ka.size != kb.size || !ka.containsAll(kb)) return false
        return ka.all { jsonEquals(a.opt(it), b.opt(it)) }
    }
    // ---------------- 视图构建 ----------------

    private fun buildView(json: String): View {
        val node = JSONObject(json)
        val type = node.optString("tag", "")
        val children = node.optJSONArray("children")
        return when (type) {
            "Box" -> buildBox(node, children)
            "Button" -> buildButton(node, children)
            "TextField" -> buildTextField(node)
            "Switch" -> buildSwitch(node)
            "Checkbox" -> buildCheckbox(node)
            "Slider" -> buildSlider(node)
            "Image" -> buildImage(node)
            "Progress" -> buildProgress(node)
            "Chart" -> buildChart(node)
            "TimelineView" -> buildTimelineView(node)
            "FloatingButton" -> buildFloatingButton(node, children)
            else -> buildText(node)
        }
    }

    private fun buildBox(node: JSONObject, children: JSONArray?): View {
        val ctx = LinearLayout(activity).apply {
            orientation = if (node.optString("orientation", "vertical") == "horizontal")
                LinearLayout.HORIZONTAL else LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        val align = node.optJSONObject("contentAlignment")?.optString("tag", "Center") ?: "Center"
        val gravity = when (align) {
            "Center" -> Gravity.CENTER
            "TopStart" -> Gravity.TOP or Gravity.START
            "BottomEnd" -> Gravity.BOTTOM or Gravity.END
            "CenterStart" -> Gravity.CENTER_VERTICAL or Gravity.START
            "CenterEnd" -> Gravity.CENTER_VERTICAL or Gravity.END
            "Top" -> Gravity.TOP
            "Bottom" -> Gravity.BOTTOM
            else -> Gravity.CENTER
        }
        ctx.gravity = gravity
        val bg = node.optString("backgroundColor", "")
        if (bg.isNotEmpty()) {
            val c = parseColor(bg)
            if (c != 0) ctx.setBackgroundColor(c)
        }
        applyModifiers(ctx, node)
        // 悬浮按钮（FloatingButton）子视图从线性流中抽出，父 Box 改用 FrameLayout，
        // FAB 按底部居右重力叠放（MD FAB 定位）；其余子视图照常竖排。
        var hasFab = false
        if (children != null) {
            for (i in 0 until children.length()) {
                val child = children.optJSONObject(i) ?: continue
                val tag = child.optString("tag", "")
                if (tag == "FloatingButton") { hasFab = true; continue }
                ctx.addView(buildView(child.toString()))
            }
        }
        if (hasFab) {
            val frame = FrameLayout(activity)
            frame.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
            ctx.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            frame.addView(ctx)
            val density = activity.resources.displayMetrics.density
            if (children != null) {
                for (i in 0 until children.length()) {
                    val child = children.optJSONObject(i) ?: continue
                    if (child.optString("tag", "") != "FloatingButton") continue
                    val fab = buildView(child.toString())
                    val lp = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.BOTTOM or Gravity.END)
                    lp.setMargins(0, 0, (16 * density).toInt(), (16 * density).toInt())
                    frame.addView(fab, lp)
                }
            }
            return frame
        }
        // scrollable：内容包进 ScrollView（纵向）/HorizontalScrollView（横向）。
        if (node.optBoolean("scrollable", false)) {
            val scroller = if (node.optString("orientation", "vertical") == "horizontal")
                HorizontalScrollView(activity) else ScrollView(activity)
            ctx.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            scroller.addView(ctx)
            return scroller
        }
        return ctx
    }

    private fun buildButton(node: JSONObject, children: JSONArray?): View {
        val btn = MaterialButton(activity)
        btn.text = extractText(children) ?: node.optString("label", "")
        btn.isEnabled = node.optBoolean("enabled", true)
        val onClickId = node.optString("onClick", "")
        if (onClickId.isNotEmpty()) {
            btn.setOnClickListener {
                activity.web.evaluateJavascript(
                    "try{ globalThis.__dshFn_" + onClickId + "(); }catch(e){ console.log(e); }", null)
            }
        }
        applyModifiers(btn, node)
        return btn
    }

    private fun buildText(node: JSONObject): View {
        val tv = TextView(activity)
        tv.text = node.optString("text", "")
        val color = node.optString("color", "")
        if (color.isNotEmpty()) {
            val c = parseColor(color)
            if (c != 0) tv.setTextColor(c)
        }
        val size = node.optDouble("size", 0.0)
        if (size > 0) tv.textSize = size.toFloat()
        val maxLines = node.optInt("maxLines", 0)
        if (maxLines > 0) tv.maxLines = maxLines
        if (node.optBoolean("bold", false) || node.optBoolean("italic", false)) {
            val style = (if (node.optBoolean("bold", false)) 1 else 0) or
                (if (node.optBoolean("italic", false)) 2 else 0)
            tv.setTypeface(tv.typeface, style)
        }
        applyModifiers(tv, node)
        return tv
    }

    private fun buildTextField(node: JSONObject): View {
        val et = EditText(activity)
        et.hint = node.optString("placeholder", "")
        et.setText(node.optString("text", ""))
        val kb = node.optString("keyboard", "text")
        et.inputType = when (kb) {
            "number" -> InputType.TYPE_CLASS_NUMBER
            "password" -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            "email" -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            else -> InputType.TYPE_CLASS_TEXT
        }
        val onChangeId = node.optString("onChange", "")
        if (onChangeId.isNotEmpty()) {
            et.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // 只回传文本、不重组视图——重组会重建输入框并丢失焦点/光标
                    jsCallback(onChangeId, JSONObject.quote(s?.toString() ?: ""))
                }
            })
        }
        applyModifiers(et, node)
        return et
    }

    private fun buildSwitch(node: JSONObject): View {
        val sw = MaterialSwitch(activity)
        sw.isChecked = node.optBoolean("checked", false)
        sw.isEnabled = node.optBoolean("enabled", true)
        val onToggleId = node.optString("onToggle", "")
        if (onToggleId.isNotEmpty()) {
            sw.setOnCheckedChangeListener { _, b ->
                jsCallback(onToggleId, if (b) "true" else "false")
            }
        }
        applyModifiers(sw, node)
        return sw
    }

    private fun buildCheckbox(node: JSONObject): View {
        val cb = MaterialCheckBox(activity)
        cb.text = node.optString("text", "")
        cb.isChecked = node.optBoolean("checked", false)
        val onToggleId = node.optString("onToggle", "")
        if (onToggleId.isNotEmpty()) {
            cb.setOnCheckedChangeListener { _, b ->
                jsCallback(onToggleId, if (b) "true" else "false")
            }
        }
        applyModifiers(cb, node)
        return cb
    }

    private fun buildSlider(node: JSONObject): View {
        val sl = Slider(activity)
        sl.valueFrom = node.optDouble("from", 0.0).toFloat()
        sl.valueTo = node.optDouble("to", 1.0).toFloat()
        // value 必须在 [from, to] 内，先钳制再赋值（JS 侧越界值会导致 IllegalArgumentException）
        val raw = node.optDouble("value", 0.0).toFloat()
        var v = raw
        if (v < sl.valueFrom) v = sl.valueFrom
        if (v > sl.valueTo) v = sl.valueTo
        sl.value = v
        sl.isEnabled = node.optBoolean("enabled", true)
        val onChangeId = node.optString("onChange", "")
        if (onChangeId.isNotEmpty()) {
            sl.addOnChangeListener { _, value, _ ->
                jsCallback(onChangeId, value.toString())
            }
        }
        applyModifiers(sl, node)
        return sl
    }

    private fun buildImage(node: JSONObject): View {
        val iv = ImageView(activity)
        val id = resolveDrawable(node.optString("name", ""))
        if (id != 0) iv.setImageResource(id)
        applyModifiers(iv, node)
        return iv
    }

    /** 图表视图（@view Chart）：折线/柱状自绘 */
    private fun buildChart(node: JSONObject): View {
        val cv = ChartView(activity)
        val kind = node.optString("kind", "line")
        val colorHex = node.optString("color", "")
        val series = node.optJSONArray("points")
        val pts = FloatArray(series?.length() ?: 0) { i -> series!!.optDouble(i).toFloat() }
        val labelArr = node.optJSONArray("labels")
        val labels = Array(labelArr?.length() ?: 0) { i -> labelArr!!.optString(i) }
        val density = (activity.resources.displayMetrics.densityDpi / 160f)
        cv.layoutParams = ChartView.densityScaled(density)
        // 兑现已声明的 modifier 字段（不新增协议字段）：Height(n) / FillMaxSize 等。
        applyModifiers(cv, node)
        if (colorHex.isNotEmpty()) {
            val c = parseColor(colorHex)
            if (c != 0) cv.setData(pts, labels, kind, c, (c and 0x00FFFFFF) or 0x33000000)
            else cv.setData(pts, labels, kind, 0xFF7C4DFF.toInt(), 0x337C4DFF)
        } else cv.setData(pts, labels, kind, 0xFF7C4DFF.toInt(), 0x337C4DFF)
        return cv
    }

    /** 时间线视图（@view TimelineView）：可滑动的时间点列表（纵向 ScrollView / 横向 HorizontalScrollView） */
    private fun buildTimelineView(node: JSONObject): View {
        val horizontal = node.optString("orientation", "vertical") == "horizontal"
        val scroller = if (horizontal) HorizontalScrollView(activity) else ScrollView(activity)
        val density = activity.resources.displayMetrics.density
        val line = LinearLayout(activity).apply {
            orientation = if (horizontal) LinearLayout.HORIZONTAL else LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        val timesArr = node.optJSONArray("times")
        val labelsArr = node.optJSONArray("labels")
        val colorHex = node.optString("color", "")
        val dotColor = if (colorHex.isNotEmpty()) parseColor(colorHex) else 0xFF7C4DFF.toInt()
        val n = timesArr?.length() ?: 0
        for (i in 0 until n) {
            val t = timesArr!!.optString(i)
            val l = if (labelsArr != null && i < labelsArr.length()) labelsArr.optString(i) else ""
            val row = LinearLayout(activity).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                val pad = (8 * density).toInt()
                setPadding((12 * density).toInt(), pad, (12 * density).toInt(), pad)
            }
            row.addView(TextView(activity).apply {
                text = t
                textSize = 14f
                minWidth = (56 * density).toInt()
            })
            val dot = View(activity).apply {
                val d = (10 * density).toInt()
                setBackgroundColor(dotColor)
                layoutParams = LinearLayout.LayoutParams(d, d).apply {
                    setMargins((8 * density).toInt(), 0, (8 * density).toInt(), 0)
                }
            }
            row.addView(dot)
            if (l.isNotEmpty()) {
                row.addView(TextView(activity).apply {
                    text = l
                    textSize = 14f
                    setTextColor(0xFF666666.toInt())
                })
            }
            line.addView(row)
        }
        scroller.addView(line)
        applyModifiers(scroller, node)
        return scroller
    }

    /** 悬浮圆角按钮（@view FloatingButton）：MaterialButton + 圆角 + 主色 tint。 */
    private fun buildFloatingButton(node: JSONObject, children: JSONArray?): View {
        val btn = MaterialButton(activity)
        btn.text = extractText(children) ?: node.optString("label", "")
        btn.cornerRadius = node.optInt("cornerRadius", 28)
        val colorHex = node.optString("color", "")
        if (colorHex.isNotEmpty()) {
            val c = parseColor(colorHex)
            if (c != 0) btn.backgroundTintList = ColorStateList.valueOf(c)
        }
        val onClickId = node.optString("onClick", "")
        if (onClickId.isNotEmpty()) {
            btn.setOnClickListener {
                activity.web.evaluateJavascript(
                    "try{ globalThis.__dshFn_" + onClickId + "(); }catch(e){ console.log(e); }", null)
            }
        }
        applyModifiers(btn, node)
        return btn
    }

    private fun buildProgress(node: JSONObject): View {
        val indet = node.optBoolean("indeterminate", false)
        val pb = ProgressBar(activity, null,
            if (indet) android.R.attr.progressBarStyleLarge else android.R.attr.progressBarStyleHorizontal)
        if (!indet) {
            pb.max = 100
            pb.progress = node.optInt("value", 0)
        }
        applyModifiers(pb, node)
        return pb
    }

    private fun resolveDrawable(name: String): Int {
        if (name.isEmpty()) return 0
        return if (name.startsWith("android:")) {
            activity.resources.getIdentifier(name.substring(8), "drawable", "android")
        } else {
            activity.resources.getIdentifier(name, "drawable", activity.packageName)
        }
    }

    private fun parseColor(hex: String): Int {
        return try { Color.parseColor(hex) } catch (t: Throwable) {
            Log.w(tag, "无效颜色: " + hex); 0
        }
    }

    private fun extractText(children: JSONArray?): String? {
        if (children == null) return null
        for (i in 0 until children.length()) {
            val c = children.optJSONObject(i) ?: continue
            if (c.optString("tag") == "Text") return c.optString("text", "")
        }
        return null
    }

    /** JSONArray 按下标转 List（API 24 兼容：无 toList()）。 */
    private fun parseJsonArray(json: String): List<Any?> {
        val arr = JSONArray(json)
        val out = ArrayList<Any?>(arr.length())
        for (i in 0 until arr.length()) out += arr.opt(i)
        return out
    }

    /** 视图协议 v2：Modifier 有序链。
     *  node 携带 "modifiers"（有序数组，链式）或 "modifier"（单对象，旧形态兜底），
     *  按链顺序依次应用——Width/Height→layoutParams、Padding 四方向、FillWidth/FillHeight
     *  （含 fraction）、Weight（LinearLayout.weight）、Center。旧单字段枚举（{tag,0}）逐条走 applyModifier。 */
    private fun applyModifiers(view: View, node: JSONObject) {
        val chain = node.optJSONArray("modifiers")
        if (chain != null) {
            for (i in 0 until chain.length()) {
                val m = chain.optJSONObject(i) ?: continue
                applyModifier(view, m)
            }
            return
        }
        val single = node.optJSONObject("modifier") ?: return
        applyModifier(view, single)
    }

    /** 单条 Modifier（enum 构造子 {tag, 0..}）→ 原生视图参数（按序调用即链语义）。 */
    private fun applyModifier(view: View, m: JSONObject) {
        val mtag = m.optString("tag", "Plain")
        if (mtag.isEmpty() || mtag == "Plain") return
        val lp = view.layoutParams ?: ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        when (mtag) {
            "FillMaxSize" -> {
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT
            }
            "WrapContent" -> {
                lp.width = ViewGroup.LayoutParams.WRAP_CONTENT
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            "Width" -> lp.width = m.optInt("0")
            "Height" -> lp.height = m.optInt("0")
            "Size" -> {
                lp.width = m.optInt("0")
                lp.height = m.optInt("1")
            }
            "Padding" -> {
                val p = m.optInt("0")
                view.setPadding(p, p, p, p)
            }
            "Padding4" -> view.setPadding(m.optInt("0"), m.optInt("1"), m.optInt("2"), m.optInt("3"))
            "PaddingH" -> {
                val h = m.optInt("0")
                view.setPadding(h, view.paddingTop, h, view.paddingBottom)
            }
            "PaddingV" -> {
                val v = m.optInt("0")
                view.setPadding(view.paddingLeft, v, view.paddingRight, v)
            }
            "FillWidth" -> lp.width = ViewGroup.LayoutParams.MATCH_PARENT
            "FillHeight" -> {
                val f = m.optDouble("0", 1.0)
                lp.height = if (f >= 1.0) ViewGroup.LayoutParams.MATCH_PARENT
                    else (f * activity.resources.displayMetrics.heightPixels).toInt().coerceAtLeast(1)
            }
            "Weight" -> {
                val w = m.optDouble("0", 1.0).toFloat()
                view.layoutParams = (lp as? LinearLayout.LayoutParams
                    ?: LinearLayout.LayoutParams(lp.width, lp.height)).apply { weight = w }
                return
            }
            "Center" -> if (view is LinearLayout) view.gravity = Gravity.CENTER
        }
        view.layoutParams = lp
    }
}