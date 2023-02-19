package com.mingyuechunqiu.agile.ui.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/8/5
 *     desc   : 颜色分割线Item装饰器
 *              继承自RecyclerView.ItemDecoration
 *     version: 1.0
 * </pre>
 */
class ColorDividerItemDecoration(
    private val dividerSize: Int = 1, @ColorInt val dividerColor: Int = Color.TRANSPARENT,
    private val offset: Int = 0,
    private val orientation: Int = ORIENTATION_HORIZONTAL
) : RecyclerView.ItemDecoration() {

    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        mPaint.color = dividerColor
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) != 0) {
            if (orientation == ORIENTATION_HORIZONTAL) {
                outRect.top = dividerSize
            } else if (orientation == ORIENTATION_VERTICAL) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && View.LAYOUT_DIRECTION_RTL == view.resources.configuration.layoutDirection) {
                    outRect.right = dividerSize
                } else {
                    outRect.left = dividerSize
                }
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        for (i in 0 until parent.childCount) {
            val view = parent.getChildAt(i)
            val index = parent.getChildAdapterPosition(view)
            if (index == 0) {
                continue
            }
            if (orientation == ORIENTATION_HORIZONTAL) {
                drawHorizontalDivider(view, c)
            } else if (orientation == ORIENTATION_VERTICAL) {
                drawVerticalDivider(view, c)
            }
        }
    }

    /**
     * 绘制水平方向分割线
     *
     * @param view 当前view
     * @param c    画布
     */
    private fun drawHorizontalDivider(view: View, c: Canvas) {
        val top = view.top - dividerSize
        c.drawRect(
            view.left.toFloat() + offset, top.toFloat(), view.right.toFloat(),
            view.top.toFloat(), mPaint
        )
    }

    /**
     * 绘制垂直方向分割线
     *
     * @param view 当前view
     * @param c    画布
     */
    private fun drawVerticalDivider(view: View, c: Canvas) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && View.LAYOUT_DIRECTION_RTL == view.resources.configuration.layoutDirection) {
            val right = view.right + dividerSize
            c.drawRect(
                view.right.toFloat(),
                view.top.toFloat() + offset,
                right.toFloat(),
                view.bottom.toFloat(),
                mPaint
            )
        } else {
            val left = view.left - dividerSize
            c.drawRect(
                left.toFloat(),
                view.top.toFloat() + offset,
                view.left.toFloat(),
                view.bottom.toFloat(),
                mPaint
            )
        }
    }

    companion object {

        const val ORIENTATION_HORIZONTAL = 0//水平方向
        const val ORIENTATION_VERTICAL = 1//垂直方向
    }
}