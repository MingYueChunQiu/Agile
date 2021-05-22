package com.mingyuechunqiu.agile.feature.statusview.ui.view

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants
import com.mingyuechunqiu.agile.feature.statusview.ui.StatusViewDialogFragment

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/21/21 11:22 PM
 *      Desc:       对话框模式状态视图
 *                  实现IStatusView
 *      Version:    1.0
 * </pre>
 */
internal class DialogStatusView(private val mOption: StatusViewOption) : IStatusView {

    private var mFragment: StatusViewDialogFragment? = null
    private var mStatusType: StatusViewConstants.StatusViewType =
        StatusViewConstants.StatusViewType.TYPE_LOADING

    override fun showStatusView(
        type: StatusViewConstants.StatusViewType,
        container: ViewGroup,
        option: StatusViewOption
    ) {
        LogManagerProvider.i("DialogStatusView", "showStatusView : not correct mode")
    }

    override fun showStatusView(
        type: StatusViewConstants.StatusViewType,
        manager: FragmentManager,
        option: StatusViewOption
    ) {
        LogManagerProvider.i("DialogStatusView", "showStatusView : Dialog")
        if (manager.findFragmentByTag(StatusViewDialogFragment.AGILE_TAG_STATUS_VIEW) != null) {
            LogManagerProvider.e("DialogStatusView", "showStatusView: has in the manager")
            return
        }
        mStatusType = type
        dismissStatusView(true)
        mFragment = StatusViewDialogFragment.newInstance(this, mOption).apply {
            showSafely(manager, StatusViewDialogFragment.AGILE_TAG_STATUS_VIEW)
        }
    }

    override fun dismissStatusView(allowStateLoss: Boolean) {
        /* 在某些情况下调用对话框dismiss时，会出现
         * java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
         * 所以要调用dismissAllowingStateLoss方法*/
        mFragment?.let {
            if (allowStateLoss) {
                it.dismissAllowingStateLoss()
            } else {
                it.dismiss()
            }
        }
    }

    override fun saveStatueViewInstanceState(outState: Bundle, manager: FragmentManager?) {
    }

    override fun restoreStatueViewInstanceState(
        savedInstanceState: Bundle?,
        manager: FragmentManager?
    ) {
        if (savedInstanceState == null) {
            return
        }
        //DialogFragment在界面意外销毁后会由系统重新创建
        (manager?.findFragmentByTag(StatusViewDialogFragment.AGILE_TAG_STATUS_VIEW) as? StatusViewDialogFragment)?.let {
            it.statusView = this
            it.option = mOption
            mFragment = it
        }
    }

    override fun getStatusViewMode(): StatusViewConstants.StatusViewMode {
        return StatusViewConstants.StatusViewMode.MODE_DIALOG
    }

    override fun getStatusViewType(): StatusViewConstants.StatusViewType {
        return mStatusType
    }

    override fun getStatusViewOption(): StatusViewOption {
        return mOption
    }
}