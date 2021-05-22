package com.mingyuechunqiu.agile.feature.statusview.function

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewConfigure
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants.StatusViewType
import com.mingyuechunqiu.agile.feature.statusview.ui.view.IStatusView

/**
 * <pre>
 *      author : xyj
 *      Github : https://github.com/MingYueChunQiu
 *      e-mail : yujie.xi@ehailuo.com
 *      time   : 2019/1/24
 *      desc   : 状态视图管理器
 *               实现IStatusViewManager
 *      version: 1.0
 * </pre>
 */
internal class StatusViewManager(private val mHelper: IStatusViewHelper) : IStatusViewManager {

    override fun applyStatusViewConfigure(configure: StatusViewConfigure) {
        mHelper.applyStatusViewConfigure(configure)
    }

    override fun getStatusViewConfigure(): StatusViewConfigure? {
        return mHelper.getStatusViewConfigure()
    }

    override fun getStatusView(): IStatusView? {
        return mHelper.getStatusView()
    }

    override fun showStatusView(
        type: StatusViewType,
        container: ViewGroup,
        option: StatusViewOption?
    ) {
        mHelper.showStatusView(type, container, option)
    }

    override fun showStatusView(
        type: StatusViewType,
        manager: FragmentManager,
        option: StatusViewOption?
    ) {
        mHelper.showStatusView(type, manager, option)
    }

    override fun dismissStatusView(allowStateLoss: Boolean) {
        mHelper.dismissStatusView(allowStateLoss)
    }

    override fun getStatusViewMode(): StatusViewConstants.StatusViewMode {
        return mHelper.getStatusViewMode()
    }

    override fun getStatusViewType(): StatusViewType {
        return mHelper.getStatusViewType()
    }

    override fun saveStatueViewInstanceState(outState: Bundle, manager: FragmentManager?) {
        mHelper.saveStatueViewInstanceState(outState, manager)
    }

    override fun restoreStatueViewInstanceState(
        savedInstanceState: Bundle?,
        manager: FragmentManager?
    ) {
        mHelper.restoreStatueViewInstanceState(savedInstanceState, manager)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (source.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            mHelper.dismissStatusView()
        }
    }
}