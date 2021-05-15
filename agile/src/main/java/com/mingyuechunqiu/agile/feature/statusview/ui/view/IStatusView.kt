package com.mingyuechunqiu.agile.feature.statusview.ui.view

import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption
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

    fun showStatusView(type: StatusType, container: ViewGroup, option: StatusViewOption)

    fun showStatusView(type: StatusType, manager: FragmentManager, option: StatusViewOption)

    fun dismissStatusView(allowStateLoss: Boolean = true)

    fun getModeType(): StatusViewConstants.StatusMode

    fun getStatusType(): StatusType
}