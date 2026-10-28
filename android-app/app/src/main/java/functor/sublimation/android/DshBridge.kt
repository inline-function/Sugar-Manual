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
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputLayout
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
    // 协议 v2 §4：弹出层（AlertDialog/BottomSheet）按 tag+__ord 注册，visible 数据字段驱显隐。
    private val dialogs = mutableMapOf<String, Dialog>()
    /** 图表多序列默认配色（与 gen/Palette.kt 同源）。 */
    private val SERIES_COLORS = intArrayOf(
        0xFF00695C.toInt(), 0xFFFFC107.toInt(), 0xFF7C4DFF.toInt(),
        0xFFD32F2F.toInt(), 0xFF1976D2.toInt(), 0xFF388E3C.toInt(),
    )
    // 定案（严格解耦）：@compose 通路开时 Compose 持屏——JS 的 layout/androidRecompose 只更新视图树缓存，
    // 不重建/替换原生视图（否则 setContentView 会把 Compose 首页顶掉）；由 MainActivity 按 BuildConfig 置位。
    var composeMode: Boolean = false
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
                if (composeMode) {
                    Log.d(tag, "layout: compose 通路开，视图树仅记账")
                    return "null"
                }
                Log.d(tag, "layout: setRootView -> native tree (composeMode=" + composeMode + ")")
                activity.runOnUiThread {
                    if (old == null) setRootView(buildView(root.toString()))
                    else applyRootDiff(old, root)
                }
            }
            "androidRecompose" -> {
                if (composeMode) return "null"
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
    // KT → JS：hardened WebView 上 web.post{} 的任务挂起不派发（暂停态/某些设备上 evaluateJavascript 永远不执行），
    // 且 prompt 与原生接口的返回值都被剥成 "null"。唯一可靠通道是从调用现场
    // （WebView 回调、点击监听均为 UI 线程）直接同步 evaluateJavascript——实测 EV-IFACE-ALIVE/LOADURL-ALIVE 全部落到页面世界。
    private fun evalJs(expr: String) {
        // 原生接口调用跑在 JavaBridge 线程；WebView 方法必须在 UI 线程。
        // 暂停态下 post 会积压不派发，所以在 UI 线程上直执（实测可靠），其余线程才转回 UI。
        try {
            if (android.os.Looper.myLooper() == activity.web.context.mainLooper) {
                activity.web.evaluateJavascript(expr, null)
            } else {
                activity.runOnUiThread { activity.web.evaluateJavascript(expr, null) }
            }
        } catch (t: Throwable) { Log.w(tag, "evalJs failed: " + t.message) }
    }

    private fun jsCallback(key: String, argJs: String) {
        evalJs("try{ globalThis.__dshFn_" + key + "(" + argJs + "); }catch(e){ console.log(e); }")
    }

    fun dispatchLifecycle(event: String) {
        if (event in lifecycleCallbacks) dispatchLater(event)
        else firedEvents += event
    }

    private fun dispatchLater(event: String) {
        evalJs("try{ __dshLifecycle_" + event + "(); }catch(e){ console.log(e); }")
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
            "Card" -> buildCard(node, children)
            "TextButton" -> buildTextButton(node, children)
            "Icon" -> buildIcon(node)
            "AlertDialog" -> buildAlertDialog(node, children)
            "BottomSheet" -> buildBottomSheet(node, children)
            else -> buildText(node)
        }
    }

    private fun buildBox(node: JSONObject, children: JSONArray?): View {
        val vertical = node.optString("orientation", "vertical") != "horizontal"
        // 协议 v2 §3：wrap=true → FlowLayout 流式换行（≈ Compose FlowRow）；否则线性容器。
        val ctx: ViewGroup = if (node.optBoolean("wrap", false)) {
            FlowLayout(activity).apply {
                horizontalSpacing = dp(node.optInt("spacing", 0))
                verticalSpacing = dp(node.optInt("spacing", 0))
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        } else {
            LinearLayout(activity).apply {
                orientation = if (vertical) LinearLayout.VERTICAL else LinearLayout.HORIZONTAL
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        }
        val align = node.optJSONObject("contentAlignment")?.optString("tag", "Center") ?: "Center"
        var gravity = when (align) {
            "Center" -> Gravity.CENTER
            "TopStart" -> Gravity.TOP or Gravity.START
            "BottomEnd" -> Gravity.BOTTOM or Gravity.END
            "CenterStart" -> Gravity.CENTER_VERTICAL or Gravity.START
            "CenterEnd" -> Gravity.CENTER_VERTICAL or Gravity.END
            "Top" -> Gravity.TOP
            "Bottom" -> Gravity.BOTTOM
            else -> Gravity.CENTER
        }
        val spacing = node.optInt("spacing", 0)
        val arrangement = node.optString("arrangement", "Start")
        // 主轴分布（arrangement）覆盖 contentAlignment 的主轴分量：
        // Start（默认）/ Center / End 走 gravity；SpaceBetween/SpaceAround/SpaceEvenly 走权重分配条。
        val horizontal = node.optString("orientation", "vertical") == "horizontal"
        val distributed = arrangement == "SpaceBetween" || arrangement == "SpaceAround" || arrangement == "SpaceEvenly"
        gravity = removeMainAxis(gravity, horizontal) or when (arrangement) {
            "Center" -> if (horizontal) Gravity.CENTER_HORIZONTAL else Gravity.CENTER_VERTICAL
            "End" -> if (horizontal) Gravity.END else Gravity.BOTTOM
            else -> if (horizontal) Gravity.START else Gravity.TOP
        }
        if (ctx is LinearLayout) ctx.gravity = gravity
        val bg = node.optString("backgroundColor", "")
        if (bg.isNotEmpty()) {
            val c = parseColor(bg)
            if (c != 0) ctx.setBackgroundColor(c)
        }
        // 容器可点（协议 v2 §3：Box.onClick ≈ Compose Surface(onClick)）
        bindOnClick(ctx, node.optString("onClick", ""))
        applyModifiers(ctx, node)
        // 悬浮按钮（FloatingButton）子视图从线性流中抽出，父 Box 改用 FrameLayout，
        // FAB 按底部居右重力叠放（MD FAB 定位）；其余子视图照常竖排。
        var hasFab = false
        if (children != null) {
            var added = 0
            for (i in 0 until children.length()) {
                val child = children.optJSONObject(i) ?: continue
                val tag = child.optString("tag", "")
                if (tag == "FloatingButton") { hasFab = true; continue }
                // 分配条（SpaceBetween/SpaceAround/SpaceEvenly 的主轴间隙，按权重分配剩余空间）：
                // SpaceBetween 只在内部（weight 1）；SpaceAround 内 2 / 首尾 1；SpaceEvenly 全 2。
                if (ctx is LinearLayout && distributed && added > 0) {
                    ctx.addView(spacer(if (arrangement == "SpaceBetween") 1f else 2f, horizontal))
                }
                val v = buildView(child.toString())
                if (added > 0 && spacing > 0 && ctx is LinearLayout) {
                    val lp0 = v.layoutParams
                    val lp = if (lp0 is LinearLayout.LayoutParams) lp0
                        else LinearLayout.LayoutParams(lp0?.width ?: ViewGroup.LayoutParams.WRAP_CONTENT,
                            lp0?.height ?: ViewGroup.LayoutParams.WRAP_CONTENT)
                    if (vertical) lp.topMargin = dp(spacing) else lp.leftMargin = dp(spacing)
                    ctx.addView(v, lp)
                } else ctx.addView(v)
                added++
            }
            if (ctx is LinearLayout && distributed && added > 0 && arrangement != "SpaceBetween") {
                ctx.addView(spacer(if (arrangement == "SpaceEvenly") 2f else 1f, horizontal), 0)
                ctx.addView(spacer(if (arrangement == "SpaceEvenly") 2f else 1f, horizontal))
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
            val scroller = if (horizontal)
                HorizontalScrollView(activity) else ScrollView(activity)
            ctx.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            scroller.addView(ctx)
            return scroller
        }
        return ctx
    }

    /** 主轴 gravity 位清零（保留十字轴分量）。 */
    private fun removeMainAxis(gravity: Int, horizontal: Boolean): Int =
        if (horizontal) gravity and (Gravity.VERTICAL_GRAVITY_MASK).inv()
        else gravity and (Gravity.HORIZONTAL_GRAVITY_MASK).inv()

    /** 权重分配条（SpaceBetween/SpaceAround/SpaceEvenly 的主轴间隙）。 */
    private fun spacer(weight: Float, horizontal: Boolean): View =
        View(activity).apply {
            layoutParams = LinearLayout.LayoutParams(
                if (horizontal) 0 else ViewGroup.LayoutParams.WRAP_CONTENT,
                if (horizontal) ViewGroup.LayoutParams.WRAP_CONTENT else 0,
                weight)
        }

    /** 点击绑定：key 非空 → 点击回调 JS 侧 __dshFn_<key>（协议 v2 实例键）。 */
    private fun bindOnClick(view: View, key: String) {
        if (key.isEmpty()) return
        view.isClickable = true
        view.isFocusable = true
        view.setOnClickListener { jsCallback(key, "") }
    }

    /** dp → px。 */
    private fun dp(n: Int): Int = (n * activity.resources.displayMetrics.density).toInt()

    /** 主题色 primary（按钮等容器底色兜底）。 */
    private fun resolveColorPrimary(): Int {
        val tv = TypedValue()
        return if (activity.theme.resolveAttribute(android.R.attr.colorPrimary, tv, true)) tv.data
            else 0xFF6750A4.toInt()
    }

    /** 涟漪（可点击容器的前景）。 */
    private fun rippleDrawable(): Drawable? {
        val tv = TypedValue()
        return if (activity.theme.resolveAttribute(android.R.attr.selectableItemBackground, tv, true))
            activity.getDrawable(tv.resourceId) else null
    }

    /**
     * 按钮视图（协议 v2 §3）：**容器**——body 的全部子视图装进按钮内部（纵向居中），
     * 不再"只取首个 Text 作文案"。
     * - body 为空 / 全是 Text → MaterialButton（文案 = 各 Text 换行拼接 / label）；
     * - body 含其它视图 → MaterialCardView 容器（filled/text/outlined 三种形态），
     *   子视图全部渲染进去。
     * style："filled"（默认）/ "text"（≈ TextButton）/ "outlined"。
     */
    private fun buildButton(node: JSONObject, children: JSONArray?, styleOverride: String? = null): View {
        val style = styleOverride ?: node.optString("style", "filled")
        val label = node.optString("label", "")
        val texts = extractAllText(children)
        if (texts != null || !hasNonTextChild(children)) {
            // 纯文案按钮：MaterialButton（含 style 变体）
            val ctx = when (style) {
                "text" -> android.view.ContextThemeWrapper(activity,
                    com.google.android.material.R.style.Widget_Material3_Button_TextButton)
                "outlined" -> android.view.ContextThemeWrapper(activity,
                    com.google.android.material.R.style.Widget_Material3_Button_OutlinedButton)
                else -> activity
            }
            val btn = MaterialButton(ctx)
            btn.text = texts ?: label
            btn.isEnabled = node.optBoolean("enabled", true)
            bindOnClick(btn, node.optString("onClick", ""))
            applyModifiers(btn, node)
            return btn
        }
        // 容器按钮：MaterialCardView 装全部子视图
        val card = MaterialCardView(activity)
        card.radius = 20f * activity.resources.displayMetrics.density
        card.cardElevation = 0f
        card.isClickable = true
        card.minimumHeight = dp(36)
        when (style) {
            "text" -> card.setCardBackgroundColor(Color.TRANSPARENT)
            "outlined" -> {
                card.setCardBackgroundColor(Color.TRANSPARENT)
                card.strokeColor = parseColor("outline")
                card.strokeWidth = dp(1)
            }
            else -> card.setCardBackgroundColor(resolveColorPrimary())
        }
        card.foreground = rippleDrawable()
        bindOnClick(card, node.optString("onClick", ""))
        val inner = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(dp(12), dp(6), dp(12), dp(6))
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        if (children != null) {
            for (i in 0 until children.length()) {
                val child = children.optJSONObject(i) ?: continue
                inner.addView(buildView(child.toString()))
            }
        }
        card.addView(inner)
        applyModifiers(card, node)
        return card
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
        // 协议 v2 §6 动画最小集：flash=true → 无限透明度闪烁（1.0 ↔ 0.25，650ms 反向）
        if (node.optBoolean("flash", false)) flashAnimation(tv)
        applyModifiers(tv, node)
        return tv
    }

    /** 无限透明度闪烁（≈ Compose 的 Modifier.flash()，协议 v2 动画最小集）。 */
    private fun flashAnimation(view: View) {
        val a = AlphaAnimation(1f, 0.25f).apply {
            duration = 650
            repeatMode = Animation.REVERSE
            repeatCount = Animation.INFINITE
            interpolator = AccelerateDecelerateInterpolator()
        }
        view.startAnimation(a)
    }

    private fun buildTextField(node: JSONObject): View {
        // label/errText ≈ Kotlin OutlinedTextField 的 label 槽 + isError/supportingText：
        // label 非空 → TextInputLayout 常驻标签；errText 非空 → isError 态（红框 + 下置错误文本）。
        val label = node.optString("label", "")
        val errText = node.optString("errText", "")
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
        et.setSingleLine(true)  // ≈ Kotlin OutlinedTextField(singleLine = true)
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
        if (label.isEmpty() && errText.isEmpty()) {
            applyModifiers(et, node)
            return et
        }
        // 常驻标签 / 错误态走 TextInputLayout 包裹（Kotlin OutlinedTextField 的 label + isError/supportingText）。
        val til = TextInputLayout(activity).apply {
            if (label.isNotEmpty()) {
                hint = label
                setHintEnabled(true)
            } else {
                setHintEnabled(false)
            }
            isErrorEnabled = errText.isNotEmpty()
            if (errText.isNotEmpty()) error = errText
            endIconMode = TextInputLayout.END_ICON_NONE
        }
        til.addView(et, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        applyModifiers(til, node)
        return til
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

    /**
     * 图表视图（@view Chart）：折线/柱状自绘。
     * 协议 v2 §6：多序列——series 里每条序列各自颜色 + 图例名 + 采样点，
     * points 是总浓度主线（垂直渐变填充），legend/badge 控制图例与徽标。
     */
    private fun buildChart(node: JSONObject): View {
        val cv = ChartView(activity)
        val kind = node.optString("kind", "line")
        val colorHex = node.optString("color", "")
        val mainArr = node.optJSONArray("points")
        val pts = FloatArray(mainArr?.length() ?: 0) { i -> mainArr!!.optDouble(i).toFloat() }
        val labelArr = node.optJSONArray("labels")
        val labels = Array(labelArr?.length() ?: 0) { i -> labelArr!!.optString(i) }
        val density = (activity.resources.displayMetrics.densityDpi / 160f)
        cv.layoutParams = ChartView.densityScaled(density)
        // 兑现已声明的 modifier 字段（不新增协议字段）：Height(n) / FillMaxSize 等。
        applyModifiers(cv, node)
        val seriesArr = node.optJSONArray("series")
        if (seriesArr != null && seriesArr.length() > 0) {
            val list = ArrayList<ChartView.Series>()
            for (i in 0 until seriesArr.length()) {
                val s = seriesArr.optJSONObject(i) ?: continue
                val pArr = s.optJSONArray("points")
                val sPts = FloatArray(pArr?.length() ?: 0) { j -> pArr!!.optDouble(j).toFloat() }
                val col = parseColor(s.optString("color", ""))
                    .let { if (it != 0) it else SERIES_COLORS[i % SERIES_COLORS.size] }
                list += ChartView.Series(s.optString("label", ""), col, sPts)
            }
            val mainColor = parseColor(colorHex).let { if (it != 0) it else 0xFF00695C.toInt() }
            cv.setMultiSeries(pts, list, labels, kind, mainColor,
                node.optBoolean("legend", true), node.optString("badge", ""))
        } else {
            if (colorHex.isNotEmpty()) {
                val c = parseColor(colorHex)
                if (c != 0) cv.setData(pts, labels, kind, c, (c and 0x00FFFFFF) or 0x33000000)
                else cv.setData(pts, labels, kind, 0xFF7C4DFF.toInt(), 0x337C4DFF)
            } else cv.setData(pts, labels, kind, 0xFF7C4DFF.toInt(), 0x337C4DFF)
        }
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

    /**
     * 悬浮圆角按钮（@view FloatingButton）：MaterialButton + 圆角 + 主色 tint。
     * 协议 v2：icon 为内置图标名（Icon.subl 同表；空串 = 无图标），text 为文字；
     * icon + text = ExtendedFAB（MaterialButton 自动图标 + 文字布局）。
     */
    private fun buildFloatingButton(node: JSONObject, children: JSONArray?): View {
        val btn = MaterialButton(activity)
        btn.text = node.optString("text", if (node.optString("label", "").isNotEmpty())
            node.optString("label", "") else extractText(children) ?: "")
        btn.cornerRadius = node.optInt("cornerRadius", 28)
        val colorHex = node.optString("color", "")
        if (colorHex.isNotEmpty()) {
            val c = parseColor(colorHex)
            if (c != 0) btn.backgroundTintList = ColorStateList.valueOf(c)
        }
        val iconName = node.optString("icon", "")
        if (iconName.isNotEmpty()) {
            val resId = resolveIcon(iconName)
            if (resId != 0) btn.setIconResource(resId)
        }
        bindOnClick(btn, node.optString("onClick", ""))
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

    /**
     * 颜色解析（协议 v2 §5）：主题色名（primary/onPrimary/…，见 Theme.subl / gen/Palette.kt）
     * | "#AARRGGBB" | "#RRGGBB"（Color.parseColor 原生支持 alpha 通道）。
     */
    private fun parseColor(spec: String): Int {
        if (spec.isEmpty()) return 0
        themeColor(spec)?.let { return it }
        val s = if (spec.startsWith("#")) spec else "#" + spec
        return try { Color.parseColor(s) } catch (t: Throwable) {
            Log.w(tag, "无效颜色: " + spec); 0
        }
    }

    /** 主题色表（与 @compose 生成物 gen/Palette.kt 一致；换主题双侧同改）。 */
    private fun themeColor(name: String): Int? = when (name) {
        "primary" -> 0xFF00695C.toInt()
        "onPrimary" -> 0xFFFFFFFF.toInt()
        "primaryContainer" -> 0xFFB2DFDB.toInt()
        "onPrimaryContainer" -> 0xFF00211E.toInt()
        "secondary" -> 0xFF4D6357.toInt()
        "onSecondary" -> 0xFFFFFFFF.toInt()
        "secondaryContainer" -> 0xFFCFE8D9.toInt()
        "onSecondaryContainer" -> 0xFF0A1F16.toInt()
        "surface" -> 0xFFFFFBFE.toInt()
        "onSurface" -> 0xFF1D1B20.toInt()
        "surfaceVariant" -> 0xFFE7E0EC.toInt()
        "onSurfaceVariant" -> 0xFF49454E.toInt()
        "outline" -> 0xFF79747E.toInt()
        "outlineVariant" -> 0xFFCAC4D0.toInt()
        "error" -> 0xFFB3261E.toInt()
        "onError" -> 0xFFFFFFFF.toInt()
        "errorContainer" -> 0x33D32F2F.toInt()
        "warning" -> 0xFFE65100.toInt()
        "warningContainer" -> 0x33F9A825.toInt()
        "info" -> 0xFF2E7D32.toInt()
        "infoContainer" -> 0x334CAF50.toInt()
        else -> null
    }

    /** body 全是 Text 时返回拼接文案（换行分隔），否则 null（调用方改走容器渲染）。 */
    private fun extractAllText(children: JSONArray?): String? {
        if (children == null) return null
        val sb = StringBuilder()
        var any = false
        for (i in 0 until children.length()) {
            val c = children.optJSONObject(i) ?: continue
            if (c.optString("tag") != "Text") return null
            if (any) sb.append("\n")
            sb.append(c.optString("text", ""))
            any = true
        }
        return if (any) sb.toString() else null
    }

    /** body 里存在非 Text 子视图 → 容器化渲染。 */
    private fun hasNonTextChild(children: JSONArray?): Boolean {
        if (children == null) return false
        for (i in 0 until children.length()) {
            val c = children.optJSONObject(i) ?: continue
            if (c.optString("tag") != "Text") return true
        }
        return false
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
            "Width" -> lp.width = dp(m.optInt("0"))
            "Height" -> lp.height = dp(m.optInt("0"))
            "Size" -> {
                lp.width = dp(m.optInt("0"))
                lp.height = dp(m.optInt("1"))
            }
            "Padding" -> {
                val p = dp(m.optInt("0"))
                view.setPadding(p, p, p, p)
            }
            "Padding4" -> view.setPadding(dp(m.optInt("0")), dp(m.optInt("1")),
                dp(m.optInt("2")), dp(m.optInt("3")))
            "PaddingH" -> {
                val h = dp(m.optInt("0"))
                view.setPadding(h, view.paddingTop, h, view.paddingBottom)
            }
            "PaddingV" -> {
                val v = dp(m.optInt("0"))
                view.setPadding(view.paddingLeft, v, view.paddingRight, v)
            }
            "FillWidth" -> lp.width = ViewGroup.LayoutParams.MATCH_PARENT
            "FillHeight" -> {
                val f = m.optDouble("0", 1.0)
                lp.height = if (f >= 1.0) ViewGroup.LayoutParams.MATCH_PARENT
                    else (f * activity.resources.displayMetrics.heightPixels).toInt().coerceAtLeast(1)
            }
            "FractionWidth" -> {
                val f = m.optDouble("0", 1.0)
                lp.width = if (f >= 1.0) ViewGroup.LayoutParams.MATCH_PARENT
                    else (f * activity.resources.displayMetrics.widthPixels).toInt().coerceAtLeast(1)
            }
            "FractionHeight" -> {
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
            // 协议 v2 §5/§6 外观与动画
            "Background" -> {
                val c = parseColor(m.optString("0", ""))
                if (c != 0) view.setBackgroundColor(c)
            }
            "Clip" -> {
                val r = dp(m.optInt("0"))
                view.background = GradientDrawable().apply { cornerRadius = r.toFloat() }
            }
            "Alpha" -> view.alpha = m.optDouble("0", 1.0).toFloat()
            "Flash" -> flashAnimation(view)
        }
        view.layoutParams = lp
    }

    // ========== 协议 v2 §4 新组件 ==========

    private fun buildCard(node: JSONObject, children: JSONArray?): View {
        val card = CardView(activity)
        card.radius = dp(node.optInt("cornerRadius", 12)).toFloat()
        card.cardElevation = dp(node.optInt("elevation", 1)).toFloat()
        card.setCardBackgroundColor(parseColor(node.optString("backgroundColor", "")).let {
            if (it != 0) it else 0xFFE7E0EC.toInt()
        })
        bindOnClick(card, node.optString("onClick", ""))
        val inner = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        if (children != null) {
            for (i in 0 until children.length()) {
                val child = children.optJSONObject(i) ?: continue
                inner.addView(buildView(child.toString()))
            }
        }
        card.addView(inner)
        applyModifiers(card, node)
        return card
    }

    /** TextButton：Button 的 "text" 形态（无边框无填充，≈ Compose TextButton）。 */
    private fun buildTextButton(node: JSONObject, children: JSONArray?): View =
        buildButton(node, children, "text")

    /** Icon（@view Icon）：内置矢量图标名 → 系统 drawable 兜底。 */
    private fun buildIcon(node: JSONObject): View {
        val iv = ImageView(activity)
        val resId = resolveIcon(node.optString("name", "add"))
        if (resId != 0) iv.setImageResource(resId) else iv.setImageResource(android.R.drawable.ic_menu_info_details)
        val size = node.optInt("size", 24)
        iv.layoutParams = ViewGroup.LayoutParams(dp(size), dp(size))
        iv.scaleType = ImageView.ScaleType.FIT_CENTER
        val tint = node.optString("tint", "")
        if (tint.isNotEmpty()) {
            val c = parseColor(tint)
            if (c != 0) iv.setColorFilter(c)
        }
        applyModifiers(iv, node)
        return iv
    }

    /**
     * AlertDialog（@view AlertDialog）：visible 是**数据字段**——按数据显隐，
     * 不靠视图树增删（协议 v2 §4）。遮罩 + 点外部取消 = onDismiss。
     */
    private fun buildAlertDialog(node: JSONObject, children: JSONArray?): View {
        val key = dialogKey(node)
        if (node.optBoolean("visible", false)) {
            if (dialogs[key] == null) {
                val builder = AlertDialog.Builder(activity)
                val title = node.optString("title", "")
                if (title.isNotEmpty()) builder.setTitle(title)
                val body = ArrayList<View>()
                if (children != null) {
                    for (i in 0 until children.length()) {
                        val c = children.optJSONObject(i) ?: continue
                        body += buildView(c.toString())
                    }
                }
                if (body.isNotEmpty()) {
                    val inner = LinearLayout(activity).apply {
                        orientation = LinearLayout.VERTICAL
                        setPadding(dp(24), dp(8), dp(24), dp(8))
                    }
                    body.forEach { inner.addView(it) }
                    builder.setView(inner)
                }
                val confirm = node.optString("confirmText", "")
                val dismiss = node.optString("dismissText", "")
                if (confirm.isNotEmpty()) builder.setPositiveButton(confirm) { _, _ ->
                    jsCallback(node.optString("onConfirm", ""), ""); dismissDialog(key)
                }
                if (dismiss.isNotEmpty()) builder.setNegativeButton(dismiss) { _, _ ->
                    jsCallback(node.optString("onDismiss", ""), ""); dismissDialog(key)
                }
                builder.setCancelable(true)
                builder.setOnCancelListener {
                    jsCallback(node.optString("onDismiss", ""), ""); dismissDialog(key)
                }
                activity.runOnUiThread {
                    val d = builder.create()
                    dialogs[key] = d
                    d.show()
                }
            }
        } else dismissDialog(key)
        return stubView()
    }

    /** BottomSheet（@view BottomSheet）：底部抽屉（Material BottomSheetDialog），下滑/点遮罩关闭。 */
    private fun buildBottomSheet(node: JSONObject, children: JSONArray?): View {
        val key = dialogKey(node)
        if (node.optBoolean("visible", false)) {
            val inner = LinearLayout(activity).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(dp(24), dp(16), dp(24), dp(16))
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            }
            if (children != null) {
                for (i in 0 until children.length()) {
                    val c = children.optJSONObject(i) ?: continue
                    inner.addView(buildView(c.toString()))
                }
            }
            val existing = dialogs[key] as? BottomSheetDialog
            activity.runOnUiThread {
                if (existing != null) {
                    // 已显隐中的抽屉：整抽屉内容替换（visible=true 的新树直送，不丢回掉已注册键）
                    existing.setContentView(inner)
                } else {
                    val sheet = BottomSheetDialog(activity)
                    sheet.setContentView(inner)
                    sheet.setCancelable(true)
                    sheet.setOnCancelListener {
                        jsCallback(node.optString("onDismiss", ""), ""); dismissDialog(key)
                    }
                    sheet.setOnDismissListener { dismissDialogFromCallback(key) }
                    dialogs[key] = sheet
                    sheet.show()
                }
            }
        } else dismissDialog(key)
        return stubView()
    }

    private fun dialogKey(node: JSONObject): String =
        node.optString("tag", "") + "_" + node.optInt("__ord", 0)

    private fun dismissDialog(key: String) { dialogs.remove(key)?.dismiss() }
    private fun dismissDialogFromCallback(key: String) { dialogs.remove(key) }

    /** 弹出层占位视图（0 尺寸，保持视图树形状不变，真实 UI 在 Dialog 里）。 */
    private fun stubView(): View = View(activity).apply {
        layoutParams = ViewGroup.LayoutParams(0, 0)
    }

    /**
     * 内置矢量图标名（与 Icon.subl 表一致）→ 系统 drawable；未命中再按原名查应用/系统资源。
     * "android:ic_menu_info_details" 直接取系统 drawable。
     */
    private fun resolveIcon(name: String): Int {
        if (name.isEmpty()) return 0
        if (name.startsWith("android:")) {
            return activity.resources.getIdentifier(name.substring(8), "drawable", "android")
        }
        val mapped = when (name) {
            "add" -> "ic_menu_add"
            "close" -> "ic_menu_close_clear_cancel"
            "settings" -> "ic_menu_preferences"
            "search" -> "ic_menu_search"
            "arrowBack" -> "ic_media_previous"
            "arrowForward" -> "ic_media_next"
            "menu" -> "ic_menu_sort_by_size"
            "moreVert" -> "ic_menu_more"
            "favorite" -> "btn_star_big_on"
            "delete" -> "ic_delete"
            "info" -> "ic_menu_info_details"
            "warning" -> "ic_dialog_alert"
            "share" -> "ic_menu_share"
            "edit" -> "ic_menu_edit"
            "check" -> "ic_menu_done"
            else -> name
        }
        val sysId = activity.resources.getIdentifier(mapped, "drawable", "android")
        return if (sysId != 0) sysId else
            activity.resources.getIdentifier(name, "drawable", activity.packageName)
    }

}