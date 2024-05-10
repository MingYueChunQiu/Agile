package com.mingyuechunqiu.agile.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView

/**
 * <pre>
 *      author : xyj
 *      Github : <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *      e-mail : xiyujieit@163.com
 *      time   : 2019/8/14
 *      desc   : 滚动View，可监听滚动事件
 *               继承自ScrollView
 *      version: 1.0
 * </pre>
 */
class CustomScrollView : ScrollView {
    var onScrollViewScrollChangedListener: OnScrollViewScrollChangedListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onScrollChanged(dx: Int, dy: Int, oldDx: Int, oldDy: Int) {
        super.onScrollChanged(dx, dy, oldDx, oldDy)
        onScrollViewScrollChangedListener?.onScrollChanged(dx, dy, oldDx, oldDy)
    }

    interface OnScrollViewScrollChangedListener {

        /**
         * 滚动时回调
         *
         * @param dx    水平方向距离
         * @param dy    垂直方向距离
         * @param oldDx 旧的水平方向距离
         * @param oldDy 旧的垂直方向距离
         */
        fun onScrollChanged(dx: Int, dy: Int, oldDx: Int, oldDy: Int)
    }
}
