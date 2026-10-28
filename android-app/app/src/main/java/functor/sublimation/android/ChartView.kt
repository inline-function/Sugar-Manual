package functor.sublimation.android

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Shader
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup

/**
 * 图表视图（自绘 Canvas，零第三方依赖；视图协议 v2 多序列）。
 *
 * - kind="line" 折线图：每条序列各自颜色的分量细线 + 一条总浓度主线（垂直渐变填充），
 *   左上角图例（色点 + 序列名）、右端徽标、网格 + 双轴刻度；
 * - kind="bar"  柱状图（单序列）。
 * 与 @compose 生成物 gen/ChartView.kt 的 Subl_ConcentrationChart 同构
 * （来源：Sugar-Manual 参考实现 ConcentrationChart.kt）。
 */
class ChartView(ctx: Context) : View(ctx) {

    /** 一条序列：图例名 + 颜色 + Y 值点列。 */
    data class Series(val label: String, val color: Int, val points: FloatArray)

    private var pts: FloatArray = FloatArray(0)
    private var series: List<Series> = emptyList()
    private var labels: Array<String> = emptyArray()
    private var kind: String = "line"
    private var lineColor: Int = Color.parseColor("#00695C")
    private var legend: Boolean = true
    private var badge: String = ""
    private val density: Float = (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT).toFloat()

    /** 单序列（v1 兼容）。 */
    fun setData(points: FloatArray, labels: Array<String>, kind: String, color: Int, fill: Int) {
        this.series = emptyList()
        this.pts = points
        this.labels = labels
        this.kind = kind
        this.lineColor = color
        this.legend = false
        this.badge = ""
        invalidate()
    }

    /** 多序列（v2）：[main] 总浓度主线（渐变填充），[series] 逐药分量线。 */
    fun setMultiSeries(
        main: FloatArray, series: List<Series>, labels: Array<String>, kind: String,
        mainColor: Int, legend: Boolean, badge: String,
    ) {
        this.pts = main
        this.series = series
        this.labels = labels
        this.kind = kind
        this.lineColor = mainColor
        this.legend = legend
        this.badge = badge
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desired = (180 * density).toInt()
        val w = resolveSize(desired, widthMeasureSpec)
        val h = resolveSize((160 * density).toInt(), heightMeasureSpec)
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val hasSeries = series.isNotEmpty()
        if (pts.isEmpty() && !hasSeries) return
        val legendH = if (legend && hasSeries) 18 * density else 0f
        val pl = 12 * density
        val pw = width - pl * 2
        val ph = height - pl * 2 - (if (labels.isNotEmpty()) 18 * density else 0f) - legendH
        if (pw <= 0 || ph <= 0) return
        val all = ArrayList<Float>()
        if (pts.isNotEmpty()) all += pts.toList()
        for (s in series) all += s.points.toList()
        val maxV = (all.maxOrNull() ?: 1f).coerceAtLeast(0.0001f)
        val minV = (all.minOrNull() ?: 0f)
        val span = (maxV - minV).coerceAtLeast(0.0001f)
        val xOf: (Int, Int) -> Float = { i, n ->
            pl + (if (n > 1) pw * i / (n - 1).toFloat() else pw / 2f)
        }
        val yOf: (Float) -> Float = { v -> pl + legendH + ph - ((v - minV) / span) * ph }

        // 网格
        val grid = Paint().apply { color = 0x1A888888; strokeWidth = 1f * density }
        var g = 0
        while (g <= 3) {
            val y = pl + legendH + ph * g / 3f
            canvas.drawLine(pl, y, pl + pw, y, grid)
            g++
        }

        // 各药分量线（半透明细线）
        if (kind == "line") {
            for (s in series) {
                if (s.points.isEmpty()) continue
                val path = Path()
                for (i in s.points.indices) {
                    val x = xOf(i, s.points.size)
                    val y = yOf(s.points[i])
                    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }
                canvas.drawPath(path, Paint().apply {
                    color = (s.color and 0x00FFFFFF) or 0x99000000.toInt(); isAntiAlias = true
                    strokeWidth = 2f * density; style = Paint.Style.STROKE
                })
            }
        }

        // 主线 / 主柱
        if (pts.isNotEmpty()) {
            if (kind == "bar") {
                val barW = (pw / pts.size) * 0.62f
                val fillPaint = Paint().apply { color = lineColor; isAntiAlias = true }
                for (i in pts.indices) {
                    val cx = xOf(i, pts.size)
                    val bh = (pts[i] / maxV) * ph
                    canvas.drawRect(cx - barW / 2f, pl + legendH + ph - bh, cx + barW / 2f, pl + legendH + ph, fillPaint)
                }
            } else {
                val path = Path()
                for (i in pts.indices) {
                    val x = xOf(i, pts.size)
                    val y = yOf(pts[i])
                    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }
                // 垂直渐变填充
                val fillPath = Path(path)
                fillPath.lineTo(xOf(pts.size - 1, pts.size), pl + legendH + ph)
                fillPath.lineTo(pl, pl + legendH + ph)
                fillPath.close()
                val fp = Paint().apply {
                    isAntiAlias = true; style = Paint.Style.FILL
                    shader = LinearGradient(0f, pl + legendH, 0f, pl + legendH + ph,
                        (lineColor and 0x00FFFFFF) or 0x47000000, 0x00000000, Shader.TileMode.CLAMP)
                }
                canvas.drawPath(fillPath, fp)
                canvas.drawPath(path, Paint().apply {
                    color = lineColor; isAntiAlias = true; strokeWidth = 3f * density
                    style = Paint.Style.STROKE
                })
                // 数据点
                val dot = Paint().apply { color = lineColor; isAntiAlias = true; style = Paint.Style.FILL }
                for (i in pts.indices) {
                    canvas.drawCircle(xOf(i, pts.size), yOf(pts[i]), 3.5f * density, dot)
                }
            }
        }

        // 图例（色点 + 名）与徽标
        val tp = Paint().apply {
            color = 0xFF666666.toInt(); isAntiAlias = true; textSize = 10f * density
        }
        if (legend && hasSeries) {
            var lx = pl
            val ly = pl + 8f * density
            for (s in series) {
                canvas.drawCircle(lx + 4f * density, ly - 3f * density, 4f * density,
                    Paint().apply { color = s.color; isAntiAlias = true; style = Paint.Style.FILL })
                canvas.drawText(s.label, lx + 11f * density, ly, tp)
                lx += (11f * density + tp.measureText(s.label) + 16f * density)
            }
        }
        if (badge.isNotEmpty()) {
            val bp = Paint().apply { color = lineColor; isAntiAlias = true; textSize = 10f * density; textAlign = Paint.Align.RIGHT }
            canvas.drawText(badge, pl + pw, pl + 8f * density, bp)
        }
        if (labels.isNotEmpty()) {
            tp.textAlign = Paint.Align.CENTER
            val ly = height - 4 * density
            for (i in labels.indices) {
                canvas.drawText(labels[i], xOf(i, labels.size), ly, tp)
            }
        }
    }

    companion object {
        /** 默认布局参数：宽度占满、高度 160dp */
        fun densityScaled(density: Float): ViewGroup.LayoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, (160 * density).toInt(),
        )
    }
}
