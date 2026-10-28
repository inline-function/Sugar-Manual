@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package functor.sublimation.android

import android.os.Bundle
import android.util.Log
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import functor.sublimation.android.gen.Palette
import functor.sublimation.android.gen.SublApp_content
import functor.sublimation.android.gen.SublRoot
import org.json.JSONObject

/**
 * Sublimation 安卓壳（Compose 版）：**布局 = Kotlin Compose，逻辑 = WebView 里的 subl JS**。
 *
 * 定案（用户）：SfA 把 subl 的 `@compose` 部分编译成 Kotlin Compose 函数；Compose 函数内部继续用
 * WebView 跑 subl 编译出的 JS。严格解耦——Compose 不解释视图 JSON，JS 不构建视图。
 *
 * 页面：
 *   - `BuildConfig.SUBL_COMPOSE_SAMPLE`：挂 **_样例首页_** `gen/GeneratedComposeSample.kt` 的
 *     `SublApp_content()`（= Sugar-Manual `main.subl` 的编译期产物形态，契约见
 *     `android doc/Compose代码生成契约.md`）。渲染它的同时拉起旁边一个 0 尺寸 WebView 跑 JS 逻辑。
 *   - `BuildConfig.SUBL_COMPOSE_GENERATED`：生成器（t57）产物 `gen/GeneratedCompose.kt` 的 `SublRoot()`。
 *   - 否则：**逻辑控制台**（不渲染任何 subl 视图，只显示推送进 Compose 的状态槽 + 运行 JS 逻辑）。
 */
class MainActivity : ComponentActivity() {
    private val tag = "Sublimation"
    internal lateinit var web: WebView
    internal val bridge = DshBridge(this)

    /** 文件选取（pickFile）的挂起 JS 回调键；系统选择器返回后回调 JS 闭包。 */
    internal var pendingPickKey: String? = null
    internal lateinit var pickLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pickLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            val key = pendingPickKey
            pendingPickKey = null
            if (!key.isNullOrEmpty()) {
                val arg = if (uri == null) "null" else JSONObject.quote(uri.toString())
                web.evaluateJavascript(
                    "try{ globalThis.__dshFn_" + key + "(" + arg + "); }catch(e){ console.log(e); }", null)
            }
        }

        // 严格解耦：compose 通路开时视图树归 Compose 侧，JS 只跑逻辑（layout 仅记账）。
        bridge.composeMode = BuildConfig.SUBL_COMPOSE_GENERATED || BuildConfig.SUBL_COMPOSE_SAMPLE

        web = buildWebView()
        // 逻辑 ↔ 布局 的桥：Compose 侧读状态槽、写回调（契约 §4）
        DshComposeHost.attach(web) { f -> runOnUiThread(f) }
        DshComposeHost.attachEnv(applicationContext)
        web.loadUrl("file:///android_asset/app.js.html")

        val scheme = lightColorScheme(
            primary = Palette.primary,
            onPrimary = Palette.onPrimary,
            primaryContainer = Palette.primaryContainer,
            onPrimaryContainer = Palette.onPrimaryContainer,
            surface = Palette.surface,
            onSurface = Palette.onSurface,
            surfaceVariant = Palette.surfaceVariant,
            onSurfaceVariant = Palette.onSurfaceVariant,
            outline = Palette.outline,
            outlineVariant = Palette.outlineVariant,
            error = Palette.error,
        )
        setContent {
            MaterialTheme(colorScheme = scheme) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    when {
                        // @compose 编译期产物 gen/GeneratedCompose.kt 的 SublRoot()（发射器聚合的分派入口）；
                        // 生成支未就位时退回样例首页（gen/GeneratedComposeSample.kt）；都没有就只是逻辑控制台。
                        BuildConfig.SUBL_COMPOSE_GENERATED -> SublRoot()
                        BuildConfig.SUBL_COMPOSE_SAMPLE -> SublApp_content()
                        else -> LogicConsole()
                    }
                }
            }
        }
        bridge.dispatchLifecycle("onCreate")
    }

    private fun buildWebView(): WebView {
        val w = WebView(this)
        w.settings.javaScriptEnabled = true
        w.settings.domStorageEnabled = true
        w.addJavascriptInterface(bridge, "__dshAndroid")
        w.addJavascriptInterface(DshComposeHost, "__dshComposeHost")
        w.setWebChromeClient(object : WebChromeClient() {
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
            override fun onJsAlert(view: WebView?, url: String?, message: String, result: JsResult): Boolean {
                result.confirm(); return true
            }
        })
        w.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                Log.i(tag, "PAGE-FINISHED " + url)
            }
        }
        return w
    }

    /** 逻辑控制台：不渲染 subl 视图，只显示推送进 Compose 的状态槽（验收/调试用）。 */
    @Composable
    private fun LogicConsole() {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Text("Sublimation · 逻辑控制台", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text(
                "本工程未生成 @compose 视图（BuildConfig.SUBL_COMPOSE_GENERATED=false）。\n" +
                    "逻辑在 WebView 中运行；当前 Compose 状态槽：\n" +
                    (DshComposeHost.slotKeys().ifEmpty { listOf("（尚未推送）") }.joinToString("\n")),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }

    override fun onStart() { super.onStart(); bridge.dispatchLifecycle("onStart") }
    override fun onResume() { super.onResume(); bridge.dispatchLifecycle("onResume") }
    override fun onPause() { super.onPause(); bridge.dispatchLifecycle("onPause") }
    override fun onStop() { super.onStop(); bridge.dispatchLifecycle("onStop") }
    override fun onDestroy() { super.onDestroy(); bridge.dispatchLifecycle("onDestroy") }
    override fun onRestart() { super.onRestart(); bridge.dispatchLifecycle("onRestart") }
}