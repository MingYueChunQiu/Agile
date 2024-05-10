package com.mingyuechunqiu.agile.frame.ui.fragment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/16 11:53 下午
 *      Desc:       Fragment视图页面
 *                  实现IAgileFragmentViewPage
 *      Version:    1.0
 * </pre>
 */
data class FragmentViewPage(
    val owner: LifecycleOwner,
    val tag: String,
    val isEmpty: Boolean
) :
    IAgileFragmentViewPage {

    override fun getPageTag(): String {
        return tag
    }

    override val lifecycle: Lifecycle
        get() = owner.lifecycle

    override fun isEmptyView(): Boolean {
        return isEmpty
    }
}