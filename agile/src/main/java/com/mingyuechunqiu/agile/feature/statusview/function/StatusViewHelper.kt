package com.mingyuechunqiu.agile.feature.statusview.function

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewConfigure
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants
import com.mingyuechunqiu.agile.feature.statusview.ui.view.DialogStatusView
import com.mingyuechunqiu.agile.feature.statusview.ui.view.IStatusView
import com.mingyuechunqiu.agile.feature.statusview.ui.view.WidgetStatusView

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
internal class StatusViewHelper : IStatusViewHelper {

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

    override fun showStatusView(type: StatusViewConstants.StatusType, container: ViewGroup, option: StatusViewOption?) {
        checkOrCreateWidgetStatusView(type, option).showStatusView(type, container, checkOrCreateStatusViewOption(type, option))
    }

    override fun showStatusView(type: StatusViewConstants.StatusType, manager: FragmentManager, option: StatusViewOption?) {
        checkOrCreateDialogStatusView(type, option).showStatusView(type, manager, checkOrCreateStatusViewOption(type, option))
    }

    override fun dismissStatusView(allowStateLoss: Boolean) {
        mStatusView?.dismissStatusView(allowStateLoss)
        mStatusView = null
    }

    override fun getModeType(): StatusViewConstants.StatusMode {
        return mStatusView?.getModeType() ?: StatusViewConstants.StatusMode.MODE_INVALID
    }

    override fun getStatusMode(): StatusViewConstants.StatusType {
        return mStatusView?.getStatusType() ?: StatusViewConstants.StatusType.TYPE_LOADING
    }

    override fun restoreStatueView(savedInstanceState: Bundle?, manager: FragmentManager) {
        (mStatusView as? DialogStatusView)?.restoreStatueView(savedInstanceState, manager)
    }

    private fun checkOrCreateWidgetStatusView(type: StatusViewConstants.StatusType, option: StatusViewOption?): IStatusView {
        mStatusView?.let {
            if (it.getModeType() == StatusViewConstants.StatusMode.MODE_WIDGET) {
                dismissStatusView()
            }
        }
        return mStatusView ?: run {
            synchronized(this) {
                val view = WidgetStatusView(checkOrCreateStatusViewOption(type, option))
                mStatusView = view
                view
            }
        }
    }

    private fun checkOrCreateDialogStatusView(type: StatusViewConstants.StatusType, option: StatusViewOption?): IStatusView {
        mStatusView?.let {
            if (it.getModeType() == StatusViewConstants.StatusMode.MODE_DIALOG) {
                dismissStatusView()
            }
        }
        return mStatusView ?: run {
            synchronized(this) {
                val view = DialogStatusView(checkOrCreateStatusViewOption(type, option))
                mStatusView = view
                view
            }
        }
    }

    private fun checkOrCreateStatusViewOption(type: StatusViewConstants.StatusType, option: StatusViewOption?): StatusViewOption {
        return option ?: StatusViewHandler.getStatusViewOptionByType(mConfigure, type, false)
        ?: StatusViewHandler.getGlobalStatusViewOptionByType(type)
    }
}