package com.mingyuechunqiu.agile.feature.statusview.ui.view

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants
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

    fun showStatusView(type: StatusViewType, manager: FragmentManager, option: StatusViewOption)

    fun dismissStatusView(allowStateLoss: Boolean = true)

    fun saveStatueViewInstanceState(outState: Bundle, manager: FragmentManager?)

    fun restoreStatueViewInstanceState(savedInstanceState: Bundle?, manager: FragmentManager?)

    fun getStatusViewMode(): StatusViewConstants.StatusViewMode

    fun getStatusViewType(): StatusViewType

    fun getStatusViewOption(): StatusViewOption
}