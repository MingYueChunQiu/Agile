package com.mingyuechunqiu.agile.feature.statusview.ui

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants.StatusType

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

    fun showStatusView(type: StatusType, manager: FragmentManager)

    fun showStatusView(type: StatusType, manager: FragmentManager, @IdRes containerId: Int)

    fun dismissStatusView(allowStateLoss: Boolean = true)

    fun getModeType(): StatusViewConstants.ModeType

    fun getStatusMode(): StatusType
}