package functor.sublimation.android

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup

/**
 * vico 风格图表视图（自绘 Canvas，零第三方依赖）：
 * - kind="line" 折线图（带数据点圆点与渐变填充）
 * - kind="bar"  柱状图
 * 数据由 JS 端 @view Chart 结构体序列化而来：points=List[Rat]（y 值序列）。
 */
class ChartView(ctx: Context) : View(ctx) {
    private var pts: FloatArray = FloatArray(0)
    private var labels: Array<String> = emptyArray()
    private var kind: String = "line"
    private var lineColor: Int = Color.parseColor("#7C4DFF")
    private var fillColor: Int = 0x337C4DFF
    private val density: Float = (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT).toFloat()

    fun setData(points: FloatArray, labels: Array<String>, kind: String, color: Int, fill: Int) {
        this.pts = points
        this.labels = labels
        this.kind = kind
        this.lineColor = color
        this.fillColor = fill
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
        if (pts.isEmpty()) return
        val pl = 12 * density   // padding
        val pw = width - pl * 2
        val ph = height - pl * 2 - (if (labels.isNotEmpty()) 18 * density else 0f)
        if (pw <= 0 || ph <= 0) return
        val maxV = (pts.maxOrNull() ?: 1f).coerceAtLeast(0.0001f)
        val minV = pts.minOrNull() ?: 0f
        val span = (maxV - minV).coerceAtLeast(0.0001f)
        val xStep = if (pts.size > 1) pw / (pts.size - 1) else 0f
        val grid = Paint().apply { color = 0x1A888888; strokeWidth = 1f * density }
        var g = 0
        while (g <= 3) {
            val y = pl + ph * g / 3f
            canvas.drawLine(pl, y, pl + pw, y, grid)
            g++
        }
        val linePaint = Paint().apply {
            color = lineColor; isAntiAlias = true; strokeWidth = 3f * density; style = Paint.Style.STROKE
        }
        if (kind == "bar") {
            val barW = (pw / pts.size) * 0.62f
            val fillPaint = Paint().apply { color = lineColor; isAntiAlias = true }
            for (i in pts.indices) {
                val cx = pl + (if (pts.size == 1) pw / 2f else xStep * i)
                val bh = (pts[i] / maxV) * ph
                canvas.drawRect(cx - barW / 2f, pl + ph - bh, cx + barW / 2f, pl + ph, fillPaint)
            }
        } else {
            val path = Path()
            for (i in pts.indices) {
                val x = pl + (if (pts.size == 1) pw / 2f else xStep * i)
                val y = pl + ph - ((pts[i] - minV) / span) * ph
                if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }
            // 填充区
            val fillPath = Path(path)
            fillPath.lineTo(pl + (if (pts.size == 1) pw / 2f else xStep * (pts.size - 1)), pl + ph)
            fillPath.lineTo(pl, pl + ph)
            fillPath.close()
            val fp = Paint().apply { color = fillColor; isAntiAlias = true; style = Paint.Style.FILL }
            canvas.drawPath(fillPath, fp)
            canvas.drawPath(path, linePaint)
            // 数据点
            val dot = Paint().apply { color = lineColor; isAntiAlias = true; style = Paint.Style.FILL }
            for (i in pts.indices) {
                val x = pl + (if (pts.size == 1) pw / 2f else xStep * i)
                val y = pl + ph - ((pts[i] - minV) / span) * ph
                canvas.drawCircle(x, y, 3.5f * density, dot)
            }
        }
        if (labels.isNotEmpty()) {
            val tp = Paint().apply {
                color = 0xFF666666.toInt(); isAntiAlias = true; textSize = 9f * density; textAlign = Paint.Align.CENTER
            }
            val ly = height - 4 * density
            for (i in labels.indices) {
                val x = pl + (if (labels.size == 1) pw / 2f else (pw / (labels.size - 1)) * i)
                canvas.drawText(labels[i], x, ly, tp)
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