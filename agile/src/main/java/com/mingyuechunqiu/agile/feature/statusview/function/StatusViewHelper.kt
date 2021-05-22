package com.mingyuechunqiu.agile.feature.statusview.function

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewConfigure
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewStateInfo
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants
import com.mingyuechunqiu.agile.feature.statusview.ui.StatusViewDialogFragment
import com.mingyuechunqiu.agile.feature.statusview.ui.view.DialogStatusView
import com.mingyuechunqiu.agile.feature.statusview.ui.view.IStatusView
import com.mingyuechunqiu.agile.feature.statusview.ui.view.WidgetStatusView
import com.mingyuechunqiu.agile.frame.ui.IAgilePage

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-11-30 15:27
 *      Desc:       状态视图辅助接口
 *                  实现IStatusViewHelper
 *      Version:    1.0
 * </pre>
 */
internal class StatusViewHelper(private val page: IAgilePage) : IStatusViewHelper {

    private var mConfigure: StatusViewConfigure? = null
    private var mStatusView: IStatusView? = null

    override fun applyStatusViewConfigure(configure: StatusViewConfigure) {
        mConfigure = configure
    }

    override fun getStatusViewConfigure(): StatusViewConfigure? {
        return mConfigure
    }

    override fun getStatusView(): IStatusView? {
        return mStatusView
    }

    override fun showStatusView(
        type: StatusViewConstants.StatusViewType,
        container: ViewGroup,
        option: StatusViewOption?
    ) {
        checkOrCreateWidgetStatusView(type, option).showStatusView(
            type,
            container,
            checkOrCreateStatusViewOption(type, option)
        )
    }

    override fun showStatusView(
        type: StatusViewConstants.StatusViewType,
        manager: FragmentManager,
        option: StatusViewOption?
    ) {
        checkOrCreateDialogStatusView(type, option).showStatusView(
            type,
            manager,
            checkOrCreateStatusViewOption(type, option)
        )
    }

    override fun dismissStatusView(allowStateLoss: Boolean) {
        mStatusView?.dismissStatusView(allowStateLoss)
        mStatusView = null
    }

    override fun saveStatueViewInstanceState(outState: Bundle, manager: FragmentManager?) {
        mStatusView?.let {
            StatusViewManagerProvider.saveInstanceStateInfo(
                page,
                StatusViewStateInfo(
                    it.getStatusViewMode(),
                    it.getStatusViewType(),
                    it.getStatusViewOption()
                )
            )
        }
    }

    override fun restoreStatueViewInstanceState(
        savedInstanceState: Bundle?,
        manager: FragmentManager?
    ) {
        (manager?.findFragmentByTag(StatusViewDialogFragment.AGILE_TAG_STATUS_VIEW) as? StatusViewDialogFragment)?.let {
            val stateInfo = StatusViewManagerProvider.getInstanceStateInfo(page)
            initDialogStatusView(
                stateInfo?.type ?: StatusViewConstants.StatusViewType.TYPE_LOADING,
                stateInfo?.option
            )
        }
        mStatusView?.restoreStatueViewInstanceState(savedInstanceState, manager)
    }

    override fun getStatusViewMode(): StatusViewConstants.StatusViewMode {
        return mStatusView?.getStatusViewMode() ?: StatusViewConstants.StatusViewMode.MODE_INVALID
    }

    override fun getStatusViewType(): StatusViewConstants.StatusViewType {
        return mStatusView?.getStatusViewType() ?: StatusViewConstants.StatusViewType.TYPE_LOADING
    }

    @Synchronized
    private fun checkOrCreateWidgetStatusView(
        type: StatusViewConstants.StatusViewType,
        option: StatusViewOption?
    ): IStatusView {
        mStatusView?.let {
            if (it.getStatusViewMode() == StatusViewConstants.StatusViewMode.MODE_WIDGET) {
                dismissStatusView()
            }
        }
        return mStatusView ?: run {
            val view = WidgetStatusView(checkOrCreateStatusViewOption(type, option))
            mStatusView = view
            view
        }
    }

    @Synchronized
    private fun checkOrCreateDialogStatusView(
        type: StatusViewConstants.StatusViewType,
        option: StatusViewOption?
    ): IStatusView {
        mStatusView?.let {
            if (it.getStatusViewMode() == StatusViewConstants.StatusViewMode.MODE_DIALOG) {
                dismissStatusView()
            }
        }
        return mStatusView ?: run {
            val view = initDialogStatusView(type, option)
            view
        }
    }

    private fun initDialogStatusView(
        type: StatusViewConstants.StatusViewType,
        option: StatusViewOption?
    ): DialogStatusView {
        val view = DialogStatusView(checkOrCreateStatusViewOption(type, option))
        mStatusView = view
        return view
    }

    private fun checkOrCreateStatusViewOption(
        type: StatusViewConstants.StatusViewType,
        option: StatusViewOption?
    ): StatusViewOption {
        return option ?: StatusViewHandler.getStatusViewOptionByType(mConfigure, type, false)
        ?: StatusViewHandler.getGlobalStatusViewOptionByType(type)
    }
}