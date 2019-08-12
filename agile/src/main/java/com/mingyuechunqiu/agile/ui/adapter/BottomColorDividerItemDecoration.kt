package com.mingyuechunqiu.agile.ui.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/8/12
 *     desc   : 底部颜色分割线Item装饰器
 *              继承自RecyclerView.ItemDecoration
 *     version: 1.0
 * </pre>
 */
internal class BottomColorDividerItemDecoration(
        private val dividerSize: Int = 2, @ColorInt private val dividerColor: Int = Color.parseColor("#dbdbdb"),
        private val offset: Int
) : RecyclerView.ItemDecoration() {

    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        mPaint.color = dividerColor
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        for (i in 0 until parent.childCount) {
            val view = parent.getChildAt(i)
            val bottom = view.bottom + dividerSize
            c.drawRect(
                    view.left.toFloat() + offset, view.bottom.toFloat(), view.right.toFloat(),
                    bottom.toFloat(), mPaint
            )
        }
    }
}