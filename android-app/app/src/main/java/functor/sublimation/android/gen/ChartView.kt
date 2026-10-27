package functor.sublimation.android.gen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * `@view Chart` 的多序列实现（android doc/Compose代码生成契约.md §5「Chart」）。
 *
 * 语言侧 `Chart(series = List[SublSeries])` 生成调用：
 *   - `sub`  = 各药分量线（每序列一条，带颜色 + 图例名）
 *   - `main` = 总浓度主线（渐变填充 + 粗线）
 *   - `labels` = X 轴刻度（`["0","12","24","36","48"]`）
 *   - `hours`  = X 轴总跨度（48），决定采样点在横向的归一化位置
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
    labels: List<String> = emptyList(),
    hours: Float = 48f,
    series: List<SublSeries> = emptyList(),
    mainPoints: List<Float> = emptyList(),
    mainColor: Color = Palette.primary,
    showLegend: Boolean = true,
    badge: String = "估算",
) {
    val density = LocalDensity.current
    val labelPx = with(density) { 11.dp.toPx() }
    val gridColor = MaterialTheme.colorScheme.outlineVariant
    val axisTextColor = MaterialTheme.colorScheme.onSurfaceVariant
    val maxConc = maxOf(
        (mainPoints.maxOrNull() ?: 0f),
        (series.maxOfOrNull { s -> s.points.maxOrNull() ?: 0f } ?: 0f),
    ).let { raw -> if (raw <= 0f) 1f else raw * 1.15f }

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
        Canvas(modifier = Modifier.fillMaxSize()) {
            val leftPad = 44.dp.toPx()
            val rightPad = 12.dp.toPx()
            val topPad = 14.dp.toPx()
            val bottomPad = 26.dp.toPx()
            val plotW = size.width - leftPad - rightPad
            val plotH = size.height - topPad - bottomPad
            if (plotW <= 0f || plotH <= 0f) return@Canvas

            // 网格：X 每 12 小时；Y 4 条
            var gx = 0f
            while (gx <= hours + 0.001f) {
                val px = leftPad + (gx / hours) * plotW
                drawLine(gridColor, Offset(px, topPad), Offset(px, topPad + plotH), 1f)
                gx += 12f
            }
            val ySteps = 4
            for (i in 0..ySteps) {
                val py = topPad + plotH - (i / ySteps.toFloat()) * plotH
                drawLine(gridColor, Offset(leftPad, py), Offset(leftPad + plotW, py), 1f)
            }
            val yPaint = android.graphics.Paint().apply {
                isAntiAlias = true
                textSize = labelPx
                color = axisTextColor.toArgb()
                textAlign = android.graphics.Paint.Align.RIGHT
            }
            for (i in 0..ySteps) {
                val value = maxConc * i / ySteps
                val py = topPad + plotH - (i / ySteps.toFloat()) * plotH
                drawIntoCanvas { c ->
                    c.nativeCanvas.drawText(formatConc(value), leftPad - 6.dp.toPx(), py + labelPx / 3f, yPaint)
                }
            }
            if (labels.isNotEmpty()) {
                val xPaint = android.graphics.Paint().apply {
                    isAntiAlias = true
                    textSize = labelPx
                    color = axisTextColor.toArgb()
                    textAlign = android.graphics.Paint.Align.CENTER
                }
                for ((i, lb) in labels.withIndex()) {
                    val px = leftPad + (i.toFloat() / maxOf(1, labels.size - 1)) * plotW
                    drawIntoCanvas { c -> c.nativeCanvas.drawText(lb, px, topPad + plotH + 18.dp.toPx(), xPaint) }
                }
            }
            // 轴线
            drawLine(axisTextColor, Offset(leftPad, topPad), Offset(leftPad, topPad + plotH), 1.5f)
            drawLine(axisTextColor, Offset(leftPad, topPad + plotH), Offset(leftPad + plotW, topPad + plotH), 1.5f)

            // 各药分量线（药物颜色，半透明）
            for (s in series) {
                val path = subPath(s.points, hours, leftPad, topPad, plotW, plotH, maxConc)
                drawPath(path, s.color.copy(alpha = 0.6f), style = Stroke(width = 2.dp.toPx()))
            }
            if (mainPoints.isEmpty()) return@Canvas
            // 总浓度线 + 垂直渐变填充
            val line = subPath(mainPoints, hours, leftPad, topPad, plotW, plotH, maxConc)
            val area = Path().apply {
                addPath(line)
                lineTo(leftPad + plotW, topPad + plotH)
                lineTo(leftPad, topPad + plotH)
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

private fun subPath(
    samples: List<Float>, hours: Float,
    leftPad: Float, topPad: Float, plotW: Float, plotH: Float, maxConc: Float,
): Path = Path().apply {
    samples.forEachIndexed { i, c ->
        val t = if (samples.size > 1) i.toFloat() / (samples.size - 1) * hours else 0f
        val px = leftPad + (t / hours) * plotW
        val py = topPad + plotH - (c / maxConc).coerceIn(0f, 1f) * plotH
        if (i == 0) moveTo(px, py) else lineTo(px, py)
    }
}

private fun formatConc(v: Float): String =
    if (v < 10f) String.format("%.1f", v) else v.toInt().toString()
