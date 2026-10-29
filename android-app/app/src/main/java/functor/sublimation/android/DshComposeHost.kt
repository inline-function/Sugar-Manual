package functor.sublimation.android

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import functor.sublimation.android.gen.SublSeries
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.json.JSONObject

/**
 * 逻辑 ↔ 布局 的桥（android doc/Compose代码生成契约.md §4）。
 *
 * 分工：subl 的 `@compose` 视图树在**编译期**展开成 Kotlin Compose 函数，函数体（可变状态、
 * 校验、持久化）仍编译成 JS 跑在 WebView 里。两边只交换**数据**，不交换视图：
 *
 *   - JS → KT（读）：编译器在 `@compose` 函数执行末尾发送 `pushState("<模块>.<函数>", {变量: 值})`；
 *     本类按槽位 `<模块>.<函数>.<变量>` 缓存 JSON，并把解析后的值写进 Compose 的 [MutableState]，
 *     从而触发真重组（不需要任何「JSON 树 → View」运行时映射）。
 *   - KT → JS（写）：交互只调用 JS 里已注册的回调槽位 `__dshFn_<key>`（协议 v2 的回调实例键）。
 *
 * 线程：`pushState` 从 WebView 的 JavascriptInterface 线程进来，统一 post 到主线程再改状态。
 */
object DshComposeHost {
    private const val tag = "Sublimation.Compose"

    /** 槽位 → Compose 状态（`status[key].value` 是解析后的 Kotlin 值）。 */
    private val status = LinkedHashMap<String, MutableState<Any?>>()

    /** 槽位 → JSON 值 → Kotlin 值（生成器的 stateOf 注册）。 */
    private val parsers = LinkedHashMap<String, (Any?) -> Any?>()

    @Volatile
    private var web: android.webkit.WebView? = null

    @Volatile
    private var poster: ((() -> Unit) -> Unit)? = null

    fun attach(webView: android.webkit.WebView, uiPost: (() -> Unit) -> Unit) {
        web = webView
        poster = uiPost
    }

    private fun ui(f: () -> Unit) {
        val p = poster
        if (p != null) p(f) else f()
    }

    /**
     * Compose 侧读一个状态槽：首次组合注册（用 [default] 渲染，等 JS 推送后替换），之后的推送
     * 经 [pushState] 写进同一个 [MutableState]，组合自动重跑。
     *
     * @param key     槽位键（`<模块>.<函数>.<变量名>`）
     * @param default 未推送前的缺省值（= 语言侧字段默认值，编译期已知）
     * @param parse   JSON 值 → Kotlin 值（生成器按值映射表 §4.1 填）
     */
    @Composable
    fun <T : Any> stateOf(key: String, default: T, parse: (JSONObject) -> T): MutableState<T> {
        val holder = remember(key) {
            @Suppress("UNCHECKED_CAST")
            val existing = status[key] as? MutableState<T>
            if (existing != null) existing else {
                parsers[key] = { raw: Any? -> parse(JSONObject().put("v", raw)) }
                val st = mutableStateOf(default)
                status[key] = st as MutableState<Any?>
                st
            }
        }
        return holder
    }

    /** JS 推送一个 `@compose` 函数的局部状态（`{变量名: 值}`）。 */
    @android.webkit.JavascriptInterface
    fun pushState(fn: String, stateJson: String) {
        val obj = try { JSONObject(stateJson) } catch (t: Throwable) {
            Log.w(tag, "pushState 解析失败 " + fn + ": " + t.message); return
        }
        val keys = obj.keys()
        while (keys.hasNext()) {
            val name = keys.next()
            val key = fn + "." + name
            val raw = obj.opt(name)
            ui { applySlot(key, raw) }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun applySlot(key: String, raw: Any?) {
        val holder = status[key]
        val parse = parsers[key]
        if (holder == null || parse == null) {
            Log.d(tag, "pushState 未注册槽位: " + key); return
        }
        val v = try { parse(raw) } catch (t: Throwable) {
            Log.w(tag, "槽位解析失败 " + key + ": " + t.message); return
        }
        if (holder.value != v) holder.value = v
    }

    /** 交互 → JS 回调槽位（`__dshFn_<key>`，协议 v2 的回调实例键）。 */
    fun callJs(key: String, argsJson: String = "[]") {
        val w = web ?: run { Log.w(tag, "callJs 无 WebView: " + key); return }
        w.post {
            w.evaluateJavascript(
                "try{ var f = globalThis[\"__dshFn_" + key + "\"];" +
                    " if (typeof f === \"function\") f.apply(null, " + argsJson + ");" +
                    " else console.log(\"missing cb " + key + "\"); }catch(e){ console.log(e); }",
                null)
        }
    }


    // ---------- 契约 §4 交互态（发射器直发 Kotlin 侧状态；JS 主逻辑仍跑 WebView，两端 storage 同源） ----------
    // act 路由名 = subl 侧函数名（toggleForm/cancelForm/pickDrugAt/setDose/commitDose/……），
    // 由 ComposeEmitter 的 onClickRoute 从 `unchecked f(args)` lambda 抽出。
    private val formOpen = mutableStateOf(false)
    private val selectedDrug = mutableStateOf(-1)
    private val doseTextState = mutableStateOf("")
    private val doseErrState = mutableStateOf("")
    private val settingsOpen = mutableStateOf(false)
    private val docOpen = mutableStateOf(false)
    // 记录卡（dose_drug/doseAmount/doseTime）：空台账→演示值，commitDose 后由 ledger 头条驱动
    private val doseDrugState = mutableStateOf("氟伏沙明")
    private val doseAmountState = mutableStateOf("100 mg")
    private val doseTimeState = mutableStateOf("给药时间 今日 08:00 · 口服")
    private var doseCardInit = false
    @Volatile private var appCtx: android.content.Context? = null

    fun attachEnv(ctx: android.content.Context) { appCtx = ctx; refreshDoseCard(); recomputeChart() }

    // ---------- 浓度图（与 JS chartSeriesList/PkEngine 同公式：C(t)=Σ D·F·0.5^(t/T½)；窗口 -12h..+36h 步长 1h，t=0=现在） ----------
    private val chartSeriesState = mutableStateOf<List<SublSeries>>(emptyList())
    private val chartTotalState = mutableStateOf<List<Float>>(emptyList())
    private val chartLabelsState = mutableStateOf<List<String>>(emptyList())

    /** 图表三数据槽（发射器把 Chart 的 series/points/labels CallExpr 路由到此处）。 */
    fun chartSeries(): List<SublSeries> = chartSeriesState.value
    fun chartTotal(): List<Float> = chartTotalState.value
    fun chartLabels(): List<String> = chartLabelsState.value

    private fun drugParams(name: String): Pair<Int, Double> = when (name) {
        "氟伏沙明" -> 15 to 0.5      // T½ 15h · F=50%
        "帕罗西汀" -> 24 to 1.0      // T½ 24h · F=100%
        else -> 24 to 1.0           // 同 halfOfName/bioOfName 兜底
    }
    private fun drugColorOf(name: String): Int =
        if (name == "氟伏沙明" || name == "帕罗西汀" || name == "舍曲林" || name == "氟西汀") 0xFFFFC107.toInt()
        else 0xFF00695C.toInt()

    /** 读 ledger 重算全部序列（空台账→演示序列；与 chartSeriesList 同语义）。 */
    private fun recomputeChart() {
        val ctx = appCtx ?: return
        val now = System.currentTimeMillis()
        val recs = ArrayList<Triple<String, Double, Long>>()
        try {
            val arr = org.json.JSONArray(ctx.getSharedPreferences("dsh-storage", 0).getString("ledger", "[]") ?: "[]")
            for (i in 0 until arr.length()) {
                val o = arr.optJSONObject(i) ?: continue
                val amt = ((o.opt("amount") as? String) ?: o.opt("amount")?.toString() ?: "0").toDoubleOrNull() ?: 0.0
                recs.add(Triple(o.opt("drug") as? String ?: "氟伏沙明", amt,
                    (o.opt("time") as? String)?.toLongOrNull() ?: 0L))
            }
        } catch (e: Throwable) { Log.w(tag, "recomputeChart: " + e.message) }

        val series = ArrayList<SublSeries>()
        if (recs.isEmpty()) {
            series.add(SublSeries("氟伏沙明", Color(0xFFFFC107),
                pkSeries(listOf(100.0 to -24L, 100.0 to 0L), 15, 0.5)))  // 演示：24h 前一剂 + 当前一剂
        } else {
            // recentDrugs：台账内首次出现序
            val seen = LinkedHashSet<String>()
            for (r in recs) seen.add(r.first)
            for (name in seen) {
                val (th, f) = drugParams(name)
                val doses = recs.filter { it.first == name }.map { it.second to (it.third - now) / 3600000L }
                series.add(SublSeries(name, Color(drugColorOf(name)), pkSeries(doses, th, f)))
            }
        }
        chartSeriesState.value = series
        chartTotalState.value = if (series.isEmpty()) emptyList()
            else List(series[0].points.size) { i -> series.sumOf { it.points[i].toDouble() }.toFloat() }
        chartLabelsState.value = (-12..36 step 12).map { "${it}h" }
    }

    /** -12h..+36h 每 1h 采样（t=0 为当前时间；供图表视口内裁剪绘制）；浓度取 1 位小数。 */
    private fun pkSeries(doses: List<Pair<Double, Long>>, tHalf: Int, bio: Double): List<Float> =
        (-12..36).map { t ->
            val c = doses.fold(0.0) { acc, d ->
                if (t >= d.second) acc + d.first * bio * Math.pow(0.5, (t - d.second).toDouble() / tHalf) else acc
            }
            Math.round(c * 10.0) / 10.0.toFloat()
        }

    /** 布尔态读：isFormOpen/isSettingsOpen/isDocOpen/isSel(k)。 */
    fun boolArg(name: String, vararg args: Any?): Boolean = when (name) {
        "isFormOpen" -> formOpen.value
        "isSettingsOpen" -> settingsOpen.value
        "isDocOpen" -> docOpen.value
        "isSel" -> (args.getOrNull(0) as? Int) == selectedDrug.value
        else -> { Log.w(tag, "boolArg 未知路由: " + name); false }
    }

    /** 字符串态读：curDose（剂量输入）、doseErr（校验错误）。 */
    fun strArg(name: String): String = when (name) {
        "curDose" -> doseTextState.value
        "doseErr" -> doseErrState.value
        "doseDrug" -> doseDrugState.value
        "doseAmount" -> doseAmountState.value
        "doseTime" -> doseTimeState.value
        else -> { Log.w(tag, "strArg 未知路由: " + name); "" }
    }

    /** TextField onValueChange → actText("setDose", v)。 */
    fun actText(name: String, v: String) {
        if (name == "setDose") {
            doseTextState.value = v
            if (doseErrState.value.isNotEmpty()) doseErrState.value = ""
        } else Log.w(tag, "actText 未知路由: " + name)
    }

    /** 交互动作（onClick → 宿主态机；与 main.subl 的 JS 逻辑同语义，storage 同源）。 */
    fun act(name: String, args: List<Any?> = emptyList()) {
        when (name) {
            "toggleForm" -> {
                formOpen.value = !formOpen.value
                if (formOpen.value) { selectedDrug.value = -1; doseTextState.value = ""; doseErrState.value = "" }
            }
            "cancelForm" -> {
                formOpen.value = false; selectedDrug.value = -1; doseTextState.value = ""; doseErrState.value = ""
            }
            "pickDrugAt" -> {
                selectedDrug.value = (args.getOrNull(0) as? Int) ?: 0
                doseErrState.value = ""
            }
            "commitDose" -> commitDose()
            "noNavigation" -> toast("药物页：当前视图协议无多页导航")
            "openSettingsDialog" -> settingsOpen.value = true
            "closeSettings" -> settingsOpen.value = false
            "openDocDialog" -> docOpen.value = true
            "closeDoc" -> docOpen.value = false
            "clearAll" -> {
                putLedger("[]")
                recomputeChart()
                doseDrugState.value = "氟伏沙明"; doseAmountState.value = "100 mg"
                doseTimeState.value = "给药时间 今日 08:00 · 口服"
                settingsOpen.value = false; toast("已清空记录")
            }
            else -> Log.w(tag, "act 未知路由: " + name)
        }
    }

    private fun commitDose() {
        val pick = selectedDrug.value
        val dt = doseTextState.value.trim()
        val amt = dt.toDoubleOrNull()
        when {
            pick < 0 -> { doseErrState.value = "请先选择药物"; toast("请先选择药物") }
            dt.isEmpty() || amt == null -> { doseErrState.value = "剂量必须为数字"; toast("剂量必须为数字") }
            amt <= 0.0 -> { doseErrState.value = "剂量必须大于 0"; toast("剂量必须大于 0") }
            amt > 10000.0 -> { doseErrState.value = "单次剂量不得超过 10000 mg"; toast("单次剂量不得超过 10000 mg") }
            else -> {
                val drug = if (pick == 0) "氟伏沙明" else "帕罗西汀"
                val time = System.currentTimeMillis().toString()
                val rec = "{\"drug\":" + org.json.JSONObject.quote(drug) +
                    ",\"amount\":" + org.json.JSONObject.quote(dt) +
                    ",\"time\":" + org.json.JSONObject.quote(time) + "}"
                val old = appCtx?.getSharedPreferences("dsh-storage", 0)?.getString("ledger", "[]") ?: "[]"
                val merged = if (old.trim() == "[]") "[$rec]" else "[$rec," + old.trim().trimStart('[').trimEnd(']') + "]"
                putLedger(merged)
                applyDoseCard(drug, dt, time.toLong())
                recomputeChart()
                toast("已记录服药")
                formOpen.value = false; selectedDrug.value = -1; doseTextState.value = ""; doseErrState.value = ""
            }
        }
    }

    /** 记录卡同步：读 prefs "ledger" 头条；空台账保留演示值（与 JS @view 同语义）。 */
    private fun refreshDoseCard() {
        val ctx = appCtx ?: return
        if (doseCardInit) return
        doseCardInit = true
        try {
            val prefs = ctx.getSharedPreferences("dsh-storage", 0)
            val arr = org.json.JSONArray(prefs.getString("ledger", "[]") ?: "[]")
            if (arr.length() > 0) {
                val rec = arr.optJSONObject(0)
                if (rec != null) applyDoseCard(rec.opt("drug") as? String ?: "氟伏沙明",
                    (rec.opt("amount") as? String) ?: rec.opt("amount")?.toString() ?: "100",
                    (rec.opt("time") as? String)?.toLongOrNull() ?: 0L)
            }
        } catch (e: Throwable) { Log.w(tag, "refreshDoseCard: " + e.message) }
    }

    private fun applyDoseCard(drug: String, amount: String, ms: Long) {
        val timeTxt = if (ms > 0) {
            val cal = java.util.Calendar.getInstance().apply { timeInMillis = ms }
            "给药时间 今日 %02d:%02d · 口服".format(cal.get(java.util.Calendar.HOUR_OF_DAY), cal.get(java.util.Calendar.MINUTE))
        } else "给药时间 今日 08:00 · 口服"
        doseDrugState.value = drug
        doseAmountState.value = amount + " mg"
        doseTimeState.value = timeTxt
    }

    private fun putLedger(v: String) {
        appCtx?.getSharedPreferences("dsh-storage", 0)?.edit()?.putString("ledger", v)?.apply()
    }

    private fun toast(msg: String) {
        val ctx = appCtx
        if (ctx != null) ui { android.widget.Toast.makeText(ctx, msg, android.widget.Toast.LENGTH_SHORT).show() }
    }

    /** 调试：当前已注册槽位。 */
    fun slotKeys(): List<String> = status.keys.toList()

    /** 调试：某槽当前值。 */
    fun slotValue(key: String): Any? = status[key]?.value
}
