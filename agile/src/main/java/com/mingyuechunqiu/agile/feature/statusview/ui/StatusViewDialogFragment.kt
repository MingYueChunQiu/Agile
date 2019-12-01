package com.mingyuechunqiu.agile.feature.statusview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.mingyuechunqiu.agile.R
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants
import com.mingyuechunqiu.agile.ui.diaglogfragment.BaseDialogFragment

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-11-24 19:36
 *      Desc:       状态视图界面
 *                  继承自BaseDialogFragment，实现IStatusView
 *      Version:    1.0
 * </pre>
 */
internal class StatusViewDialogFragment : BaseDialogFragment(), IStatusView {

    private var tvContent: TextView? = null
    private var tvReload: TextView? = null

    private var mDelegate: IStatusViewDelegate? = null
    private var mManager: FragmentManager? = null

    override fun releaseOnDestroyView() {
        mDelegate = null
        mManager = null
    }

    override fun releaseOnDestroy() {

    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var vLayout = inflater.inflate(R.layout.agile_dialog_fragment_status_view, container, false)
        var vContainer: View? = null
        var vProgress: View? = null
        mDelegate?.statusViewOption?.let {
            if (it.statusViewContainer?.customLayoutId != null) {
                vLayout = inflater.inflate(it.statusViewContainer.customLayoutId, container, false)
                vContainer = vLayout?.findViewById(it.statusViewContainer.containerId)
                vProgress = vLayout?.findViewById(it.statusViewContainer.progressViewId)
                tvContent = vLayout?.findViewById(it.statusViewContainer.contentViewId)
                tvReload = vLayout?.findViewById(it.statusViewContainer.reloadViewId)
            } else {
                vContainer = vLayout?.findViewById(R.id.ll_agile_dfg_status_view_container)
                vProgress = vLayout?.findViewById(R.id.pb_agile_dfg_status_view_progress)
                tvContent = vLayout?.findViewById(R.id.tv_agile_dfg_status_view_content)
                tvReload = vLayout?.findViewById(R.id.tv_agile_dfg_status_view_reload)
            }
            tvReload?.setOnClickListener { _ ->
                it.onStatusViewButtonListener?.onClickReload()
            }
        }
        mDelegate?.applyOption(vContainer, vProgress, tvContent, tvReload)
        return vLayout
    }

    override fun showStatusView(type: StatusViewConstants.StatusType, manager: FragmentManager) {
        //状态已经保存了就不显示，否则DialogFragment默认的show方法调用的是commit，在保存状态后调用会崩溃报异常
        if (isAdded || isVisible || manager.isStateSaved) {
            return
        }
        mDelegate?.modeType = StatusViewConstants.ModeType.TYPE_DIALOG
        mDelegate?.statusType = type
        mManager = manager
        show(manager, StatusViewDialogFragment::class.java.simpleName)
    }

    override fun showStatusView(type: StatusViewConstants.StatusType, manager: FragmentManager, containerId: Int) {
        //状态已经保存了就不显示，否则DialogFragment默认的show方法调用的是commit，在保存状态后调用会崩溃报异常
        if (isAdded || isVisible || manager.isStateSaved) {
            return
        }
        mDelegate?.modeType = StatusViewConstants.ModeType.TYPE_FRAGMENT
        mManager = manager
        manager.beginTransaction().add(containerId, this).commitAllowingStateLoss()
    }

    override fun dismissStatusView(allowStateLoss: Boolean) {
        /* 在某些情况下调用对话框dismiss时，会出现
         * java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
         * 所以要调用dismissAllowingStateLoss方法*/
        if (allowStateLoss) {
            dismissAllowingStateLoss()
        } else {
            dismiss()
        }
        mManager?.let {
            val transaction = it.beginTransaction().remove(this)
            if (allowStateLoss) {
                transaction.commitAllowingStateLoss()
            } else {
                transaction.commit()
            }
        }
    }

    override fun getModeType(): StatusViewConstants.ModeType {
        return mDelegate?.modeType ?: StatusViewConstants.ModeType.TYPE_INVALID
    }

    override fun getStatusMode(): StatusViewConstants.StatusType {
        return mDelegate?.statusType ?: StatusViewConstants.StatusType.TYPE_LOADING
    }

    companion object {

        fun newInstance(option: StatusViewOption): StatusViewDialogFragment {
            val fragment = StatusViewDialogFragment()
            fragment.mDelegate = StatusViewDelegate(option)
            return fragment
        }
    }
}
