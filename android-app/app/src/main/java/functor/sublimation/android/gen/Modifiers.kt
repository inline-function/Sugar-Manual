package functor.sublimation.android.gen

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

/**
 * 动画最小集（契约 §5「动画最小集」）：无限透明度循环 1.0 ↔ 0.25，650ms 反向。
 *
 * 对应语言侧 `MFlash()` 链元素 / 视图字段 `flash = true`；JS 运行时侧由 `AlphaAnimation` 实现同效果。
 * **不造框架**：只有一个 `Modifier` 扩展，需要的地方 `.then(Modifier.flash())`。
 */
@Composable
fun Modifier.flash(): Modifier {
    val transition = rememberInfiniteTransition(label = "sublFlash")
    val a by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0.25f,
        animationSpec = infiniteRepeatable(animation = tween(durationMillis = 650), repeatMode = RepeatMode.Reverse),
        label = "sublFlashAlpha",
    )
    return this.alpha(a)
}

/** 圆角裁剪（契约 §3 `MClip(corner)`）。 */
@Composable
fun Modifier.sublClip(corner: Int): Modifier = this.clip(RoundedCornerShape(corner.dp))
