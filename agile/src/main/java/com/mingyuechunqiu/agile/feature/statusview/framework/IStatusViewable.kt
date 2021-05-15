package com.mingyuechunqiu.agile.feature.statusview.framework

import androidx.annotation.IdRes

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/7 11:37 下午
 *      Desc:       状态视图能力接口
 *      Version:    1.0
 * </pre>
 */
interface IStatusViewable {

    /**
     * 显示加载对话框
     *
     * @param hint       提示文本
     * @param cancelable 是否可以取消
     */
    fun showLoadingStatusView(hint: String?, cancelable: Boolean)

    /**
     * 显示加载状态视图
     *
     * @param containerId 状态视图添加布局ID
     */
    fun showLoadingStatusView(@IdRes containerId: Int)
}