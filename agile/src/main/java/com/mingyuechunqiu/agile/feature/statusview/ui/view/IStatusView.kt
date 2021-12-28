package com.mingyuechunqiu.agile.feature.statusview.ui.view

import android.view.ViewGroup
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants.StatusViewType

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-11-29 23:19
 *      Desc:       状态视图界面接口
 *      Version:    1.0
 * </pre>
 */
interface IStatusView {

    fun showStatusView(type: StatusViewType, container: ViewGroup, option: StatusViewOption)

    fun dismissStatusView()

    fun getStatusViewType(): StatusViewType

    fun getStatusViewOption(): StatusViewOption
}