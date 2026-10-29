package functor.sublimation.android.gen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * `@view Chart` 的多序列实现（android doc/Compose代码生成契约.md §5「Chart」）。
 *
 * 窗口语义：t=0 为「当前时间」，startHour..startHour+hours 为可见全窗口（缺省 -12h..+36h）。
 * 宿主以 stepHour 粒度（缺省 1h）采样，分量/主线在此窗口内绘制。
 *
 * 交互（SfA 增强）：
 *   - 捏合缩放 X 轴（viewSpan 6h..全窗）+ 单指拖动平移；
 *   - 双击在「全窗口 / 以当前时间为中心的 12h」两档间切换；
 *   - 「当前时间」竖虚线（onSurfaceVariant + 「现在」标签），位置随视口重算；
 *   - Y 轴顶部题头「mg/L」，X 轴刻度按视口自适应步长并带「h」单位。
 *
 * 与参考实现 `ConcentrationChart.kt` 同构：图例（色点+药名）+ 右端「估算」徽标 + 网格 + 双轴刻度。
 */
data class SublSeries(
    val label: String,
    val color: Color,
    val points: List<Float>,
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Subl_ConcentrationChart(
    modifier: Modifier = Modifier,
    kind: String = "line",
    labels: List<String> = emptyList(),      // 兼容旧入参；X 刻度按视口自适应计算（带单位）
    hours: Float = 48f,                       // 全窗口跨度（= 缩放上限）
    series: List<SublSeries> = emptyList(),
    mainPoints: List<Float> = emptyList(),
    mainColor: Color = Palette.primary,
    showLegend: Boolean = true,
    badge: String = "估算",
    startHour: Float = -12f,                  // 窗口起点：t=0 为当前时间
    stepHour: Float = 1f,                     // 宿主采样步长
) {
    val density = LocalDensity.current
    val labelPx = with(density) { 11.dp.toPx() }
    val leftPadPx = with(density) { 44.dp.toPx() }
    val rightPadPx = with(density) { 12.dp.toPx() }
    val gridColor = MaterialTheme.colorScheme.outlineVariant
    val axisTextColor = MaterialTheme.colorScheme.onSurfaceVariant
    val maxConc = maxOf(
        (mainPoints.maxOrNull() ?: 0f),
        (series.maxOfOrNull { s -> s.points.maxOrNull() ?: 0f } ?: 0f),
    ).let { raw -> if (raw <= 0f) 1f else raw * 1.15f }

    // 视口状态（rememberSaveable：跨重组/配置变化保持缩放与平移）
    var viewStart by rememberSaveable { mutableFloatStateOf(startHour) }
    var viewSpan by rememberSaveable { mutableFloatStateOf(hours) }
    var plotWpx by remember { mutableFloatStateOf(0f) }

    fun clampViewport() {
        viewSpan = viewSpan.coerceIn(6f, hours)
        viewStart = viewStart.coerceIn(startHour, startHour + hours - viewSpan)
    }

    Column(modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp)) {
        if (showLegend) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    for (s in series) Subl_LegendItem(s.label, s.color)
                }
                if (badge.isNotEmpty()) {
                    Text(
                        text = badge,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { plotWpx = (it.width - leftPadPx - rightPadPx).toFloat() }
                // 捏合缩放 + 拖动平移
                .pointerInput(Unit) {
                    detectTransformGestures { centroid, pan, zoom, _ ->
                        val w = plotWpx.coerceAtLeast(1f)
                        val at = (centroid.x - leftPadPx).coerceIn(0f, w) / w  // 手势中心在当前视口的比例
                        val hAtFocus = viewStart + at * viewSpan
                        viewSpan = (viewSpan / zoom).coerceIn(6f, hours)
                        viewStart = hAtFocus - at * viewSpan
                        viewStart -= pan.x * (viewSpan / w)   // 像素 → 小时
                        clampViewport()
                    }
                }
                // 双击：全窗口 ↔ 以当前时间为中心的 12h（置于平移之后，避免吞掉按下事件）
                .pointerInput(Unit) {
                    detectTapGestures(onDoubleTap = {
                        if (viewSpan > hours / 2f) {
                            viewSpan = 12f
                            viewStart = -6f
                        } else {
                            viewSpan = hours
                            viewStart = startHour
                        }
                        clampViewport()
                    })
                },
        ) {
            val leftPad = leftPadPx
            val rightPad = rightPadPx
            val topPad = 22.dp.toPx()
            val bottomPad = 26.dp.toPx()
            val plotW = size.width - leftPad - rightPad
            val plotH = size.height - topPad - bottomPad
            if (plotW <= 0f || plotH <= 0f) return@Canvas

            val viewEnd = viewStart + viewSpan
            fun hourToX(h: Float): Float = leftPad + ((h - viewStart) / viewSpan) * plotW
            fun concY(c: Float): Float = topPad + plotH - (c / maxConc).coerceIn(0f, 1f) * plotH

            // X 刻度步长随视口自适应（>=36h→12h；>=18h→6h；>=9h→3h；否则 1h）
            val tickStep = when {
                viewSpan >= 36f -> 12f
                viewSpan >= 18f -> 6f
                viewSpan >= 9f -> 3f
                else -> 1f
            }
            val xPaint = android.graphics.Paint().apply {
                isAntiAlias = true
                textSize = labelPx
                color = axisTextColor.toArgb()
                textAlign = android.graphics.Paint.Align.CENTER
            }
            var gx = (Math.ceil(viewStart / tickStep.toDouble()) * tickStep).toFloat()
            while (gx <= viewEnd + 0.001f) {
                val px = hourToX(gx)
                drawLine(gridColor, Offset(px, topPad), Offset(px, topPad + plotH), 1f)
                drawIntoCanvas { c ->
                    val hLabel = gx.toInt().toString() + "h"
                    c.nativeCanvas.drawText(hLabel, px, topPad + plotH + 18.dp.toPx(), xPaint)
                }
                gx += tickStep
            }
            // 网格：Y 4 条 + 刻度
            val ySteps = 4
            val yPaint = android.graphics.Paint().apply {
                isAntiAlias = true
                textSize = labelPx
                color = axisTextColor.toArgb()
                textAlign = android.graphics.Paint.Align.RIGHT
            }
            for (i in 0..ySteps) {
                val py = topPad + plotH - (i / ySteps.toFloat()) * plotH
                drawLine(gridColor, Offset(leftPad, py), Offset(leftPad + plotW, py), 1f)
                val value = maxConc * i / ySteps
                drawIntoCanvas { c ->
                    c.nativeCanvas.drawText(formatConc(value), leftPad - 6.dp.toPx(), py + labelPx / 3f, yPaint)
                }
            }
            // Y 轴单位题头
            val yTitlePaint = android.graphics.Paint().apply {
                isAntiAlias = true
                textSize = labelPx
                color = axisTextColor.toArgb()
                textAlign = android.graphics.Paint.Align.LEFT
            }
            drawIntoCanvas { c ->
                c.nativeCanvas.drawText("mg/L", leftPad, topPad - 6.dp.toPx(), yTitlePaint)
            }
            // 轴线
            drawLine(axisTextColor, Offset(leftPad, topPad), Offset(leftPad, topPad + plotH), 1.5f)
            drawLine(axisTextColor, Offset(leftPad, topPad + plotH), Offset(leftPad + plotW, topPad + plotH), 1.5f)

            // 「当前时间」竖虚线（t=0；不在视口内则跳过）
            if (0f >= viewStart && 0f <= viewEnd) {
                val nx = hourToX(0f)
                val dash = PathEffect.dashPathEffect(floatArrayOf(8.dp.toPx(), 6.dp.toPx()))
                drawLine(
                    axisTextColor, Offset(nx, topPad), Offset(nx, topPad + plotH),
                    strokeWidth = 1.5f, pathEffect = dash,
                )
                drawIntoCanvas { c ->
                    c.nativeCanvas.drawText("现在", nx, topPad - 6.dp.toPx(), xPaint.apply {
                        textAlign = android.graphics.Paint.Align.CENTER
                    })
                }
            }

            // 各药分量线（药物颜色，半透明；仅绘制视口内采样段）
            for (s in series) {
                val path = subPath(s.points, startHour, stepHour, viewStart, viewEnd, { h -> hourToX(h) }, { c -> concY(c) })
                if (path != null) drawPath(path, s.color.copy(alpha = 0.6f), style = Stroke(width = 2.dp.toPx()))
            }
            if (mainPoints.isNotEmpty()) {
                val line = subPath(mainPoints, startHour, stepHour, viewStart, viewEnd, { h -> hourToX(h) }, { c -> concY(c) })
                if (line != null) {
                    val area = Path().apply {
                        addPath(line)
                        lineTo(hourToX(viewEnd.coerceIn(startHour, startHour + hours)), topPad + plotH)
                        lineTo(hourToX(viewStart.coerceIn(startHour, startHour + hours)), topPad + plotH)
                        close()
                    }
                    drawPath(
                        area,
                        brush = Brush.verticalGradient(
                            colors = listOf(mainColor.copy(alpha = 0.28f), Color.Transparent),
                            startY = topPad,
                            endY = topPad + plotH,
                        ),
                    )
                    drawPath(line, color = mainColor, style = Stroke(width = 3.dp.toPx()))
                }
            }
        }
    }
}

/** 图例项：色点 + 药名。 */
@Composable
fun Subl_LegendItem(name: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Spacer(
            Modifier.size(9.dp).clip(CircleShape).background(color),
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

/**
 * 采样点 → 视口内路径：index i 对应时刻 startHour + i*stepHour；仅取落在 [viewStart, viewEnd]
 * 的点，首点 moveTo（视口边缘从前一斜坡截断，视觉上接受）。
 */
private fun subPath(
    points: List<Float>,
    startHour: Float,
    stepHour: Float,
    viewStart: Float,
    viewEnd: Float,
    hourToX: (Float) -> Float,
    concY: (Float) -> Float,
): Path? {
    val path = Path()
    var started = false
    for (i in points.indices) {
        val h = startHour + i * stepHour
        if (h < viewStart || h > viewEnd) continue
        path.moveTo_or_line(hourToX(h), concY(points[i]), started)
        started = true
    }
    return if (started) path else null
}

private fun Path.moveTo_or_line(x: Float, y: Float, started: Boolean) {
    if (started) lineTo(x, y) else moveTo(x, y)
}

/** 浓度数值格式化：<10 保留一位小数，否则取整。 */
fun formatConc(v: Float): String =
    if (v < 10f) String.format("%.1f", v) else v.toInt().toString()
