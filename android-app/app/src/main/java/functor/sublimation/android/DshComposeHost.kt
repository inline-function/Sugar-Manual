package functor.sublimation.android

import android.util.Log
import androidx.compose.runtime.Composable
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

    /** 调试：当前已注册槽位。 */
    fun slotKeys(): List<String> = status.keys.toList()

    /** 调试：某槽当前值。 */
    fun slotValue(key: String): Any? = status[key]?.value
}
