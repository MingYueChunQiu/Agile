package com.mingyuechunqiu.agile.feature.statusview.function

import android.app.Activity
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewConfigure
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants
import com.mingyuechunqiu.agile.feature.statusview.ui.view.IStatusView

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-11-27 22:37
 *      Desc:       状态视图帮助接口
 *      Version:    1.0
 * </pre>
 */
interface IStatusViewHelper {

    fun applyStatusViewConfigure(configure: StatusViewConfigure)

    fun getStatusViewConfigure(): StatusViewConfigure?

    fun getStatusView(): IStatusView?

    fun showStatusView(
        type: StatusViewConstants.StatusViewType,
        activity: Activity,
        option: StatusViewOption?
    )

    fun showStatusView(
        type: StatusViewConstants.StatusViewType,
        fragment: Fragment,
        option: StatusViewOption?
    )

    fun showStatusView(
        type: StatusViewConstants.StatusViewType,
        container: ViewGroup,
        option: StatusViewOption?
    )

    fun dismissStatusView()

    fun getStatusViewType(): StatusViewConstants.StatusViewType

    fun getStatusViewOption(): StatusViewOption?
}