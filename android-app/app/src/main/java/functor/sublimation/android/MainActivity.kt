package functor.sublimation.android

import android.os.Bundle
import android.util.Log
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
/**
 * Sublimation 安卓壳：应用仅由一个活动构成。
 *
 * 使用 AndroidX 兼容活动（AppCompatActivity）——承载 Material 组件与 Activity Result API。
 * JS 端（编译产物 out.js）跑在 WebView 里，经 [DshBridge]（注入为全局 __dshAndroid）
 * 与 KT 端通信：log/toast/layout/生命周期回调、意图、平台能力、recomposion 视图更新通知。
 * 布局由 JS 端的 @view 结构体树描述——KT 端动态构建原生视图，不使用 Compose。
 */
class MainActivity : AppCompatActivity() {
    private val tag = "Sublimation"
    internal lateinit var web: WebView
    internal val bridge = DshBridge(this)

    /** 文件选取（pickFile）的挂起 JS 回调键；系统选择器返回后回调 JS 闭包。 */
    internal var pendingPickKey: String? = null
    internal lateinit var pickLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 文件选取：ActivityResultContracts.GetContent——mime 由 JS 端传入。
        pickLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            val key = pendingPickKey
            pendingPickKey = null
            val js = if (key.isNullOrEmpty()) "" else {
                val arg = if (uri == null) "null" else JSONObject.quote(uri.toString())
                "try{ globalThis.__dshFn_" + key + "(" + arg + "); }catch(e){ console.log(e); }"
            }
            if (js.isNotEmpty()) web.evaluateJavascript(js, null)
        }

        web = WebView(this)
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.addJavascriptInterface(bridge, "__dshAndroid")
        web.setWebChromeClient(object : WebChromeClient() {
            // prompt("dsh:<kind>:<argsJson>") 传输：所有 WebView 通用的兜底通道
            // （华为 hardened WebView 可能剥掉 addJavascriptInterface）
            override fun onJsPrompt(view: WebView?, url: String?, message: String,
                                    defaultValue: String?, result: JsPromptResult): Boolean {
                if (message.startsWith("dsh:")) {
                    val body = message.substring(4)
                    val sep = body.indexOf(':')
                    val kind = if (sep > 0) body.substring(0, sep) else ""
                    val argsJson = if (sep > 0) body.substring(sep + 1) else "[]"
                    result.confirm(bridge.dispatch(kind, argsJson))
                    return true
                }
                return super.onJsPrompt(view, url, message, defaultValue, result)
            }
            override fun onJsAlert(view: WebView?, url: String?, message: String,
                                   result: JsResult): Boolean {
                result.confirm(); return true
            }
        })
        web.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                Log.i(tag, "PAGE-FINISHED " + url)
                view.evaluateJavascript("(typeof __dshAndroid)") { r ->
                    Log.i(tag, "DSH-TYPEOF=" + r)
                }
            }
        }
        setContentView(web)

        web.loadUrl("file:///android_asset/app.js.html")

        // 载入编译器产物（assets/app.js —— 由注解处理器 postCompile 阶段拷入）
        bridge.dispatchLifecycle("onCreate")
    }

    override fun onStart() { super.onStart(); bridge.dispatchLifecycle("onStart") }
    override fun onResume() { super.onResume(); bridge.dispatchLifecycle("onResume") }
    override fun onPause() { super.onPause(); bridge.dispatchLifecycle("onPause") }
    override fun onStop() { super.onStop(); bridge.dispatchLifecycle("onStop") }
    override fun onDestroy() { super.onDestroy(); bridge.dispatchLifecycle("onDestroy") }
    override fun onRestart() { super.onRestart(); bridge.dispatchLifecycle("onRestart") }
}
