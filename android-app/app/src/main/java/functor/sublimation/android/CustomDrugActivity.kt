package functor.sublimation.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.lightColorScheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import functor.sublimation.android.gen.Palette

/**
 * t83 自定义药物/效应包管理页（单开 Activity）。
 * 用户口径：「进入自定义药物界面后就退不出来了」——原实现是全屏 DshSheet，
 * 内容过高遮住了遮罩点击区，且与主页抽屉混在一个 Activity 里。
 * 单开 Activity 后：系统返回键即退出，与主页生命周期解耦。
 */
@OptIn(ExperimentalMaterial3Api::class)
class CustomDrugActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DshComposeHost.attachEnvIfMissing(this)
        setContent {
            MaterialTheme(colorScheme = lightColorScheme(
                    primary = Palette.primary,
                    onPrimary = Palette.onPrimary,
                    primaryContainer = Palette.primaryContainer,
                    onPrimaryContainer = Palette.onPrimaryContainer,
                    secondary = Palette.secondary,
                    onSecondary = Palette.onSecondary,
                    surface = Palette.surface,
                    onSurface = Palette.onSurface,
                    surfaceVariant = Palette.surfaceVariant,
                    onSurfaceVariant = Palette.onSurfaceVariant,
                    background = Palette.surface,
                    onBackground = Palette.onSurface,
                    error = Palette.error,
                    onError = Palette.onError,
                )) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text = "自定义药物 · 效应包") },
                                navigationIcon = {
                                    IconButton(onClick = { finish() }) {
                                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                                    }
                                }
                            )
                        }
                    ) { inner ->
                        DshComposeHost.Subl_CustomDrugMgr(modifier = Modifier.padding(inner))
                    }
                }
            }
        }
    }
}
