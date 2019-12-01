package com.mingyuechunqiu.agile.feature.statusview.function

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants.StatusType

/**
 * <pre>
 * Project:    Agile
 *
 * Author:     xiyujie
 * Github:     https://github.com/MingYueChunQiu
 * Email:      xiyujieit@163.com
 * Time:       2019-11-27 22:37
 * Desc:
 * Version:    1.0
</pre> *
 */
interface IStatusViewHelper {

    fun showStatusView(type: StatusType, manager: FragmentManager, option: StatusViewOption?)

    fun showStatusView(type: StatusType, manager: FragmentManager, @IdRes containerId: Int, option: StatusViewOption?)

    fun dismissStatusView(allowStateLoss: Boolean = true)

    fun getModeType(): StatusViewConstants.ModeType

    fun getStatusMode(): StatusType
}