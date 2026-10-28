package functor.sublimation.android

import android.content.Context
import android.view.View
import android.view.ViewGroup

/**
 * 流式布局（视图协议 v2：Box(wrap = true) ≈ Compose FlowRow）。
 *
 * 自实现、零第三方依赖（不引入 FlexboxLayout）：按测量宽度把子视图逐个排进行，
 * 一行放不下就换行；[horizontalSpacing]/[verticalSpacing] 为子视图间距（dp 已由
 * DshBridge 换算成 px 注入）。高度按行数累加，宽度取最宽行。
 */
class FlowLayout(ctx: Context) : ViewGroup(ctx) {
    var horizontalSpacing = 0
    var verticalSpacing = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        var maxLineWidth = 0
        var totalHeight = 0
        var lineWidth = 0
        var lineHeight = 0
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility == GONE) continue
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val lp = child.layoutParams as MarginLayoutParams
            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin
            if (lineWidth + childWidth > widthSize && lineWidth > 0) {
                maxLineWidth = maxOf(maxLineWidth, lineWidth - horizontalSpacing)
                totalHeight += lineHeight - verticalSpacing
                lineWidth = 0
                lineHeight = 0
            }
            lineWidth += childWidth + horizontalSpacing
            lineHeight = maxOf(lineHeight, childHeight)
        }
        maxLineWidth = maxOf(maxLineWidth, lineWidth - horizontalSpacing)
        totalHeight += lineHeight - verticalSpacing
        val finalWidth = if (widthMode == MeasureSpec.EXACTLY) widthSize
            else maxLineWidth + paddingLeft + paddingRight
        val finalHeight = if (heightMode == MeasureSpec.EXACTLY) heightSize
            else totalHeight + paddingTop + paddingBottom
        setMeasuredDimension(
            resolveSize(finalWidth, widthMeasureSpec),
            resolveSize(finalHeight, heightMeasureSpec),
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val width = r - l
        var x = paddingLeft
        var y = paddingTop
        var lineHeight = 0
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility == GONE) continue
            val lp = child.layoutParams as MarginLayoutParams
            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin
            if (x + childWidth > width && x > paddingLeft) {
                x = paddingLeft
                y += lineHeight - verticalSpacing
                lineHeight = 0
            }
            val childLeft = x + lp.leftMargin
            val childTop = y + lp.topMargin
            child.layout(childLeft, childTop, childLeft + child.measuredWidth, childTop + child.measuredHeight)
            x += childWidth + horizontalSpacing
            lineHeight = maxOf(lineHeight, childHeight)
        }
    }

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams): LayoutParams =
        MarginLayoutParams(lp)

    override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean = p is MarginLayoutParams
}
