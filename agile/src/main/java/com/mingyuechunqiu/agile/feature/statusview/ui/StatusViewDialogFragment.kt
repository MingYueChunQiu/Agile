package com.mingyuechunqiu.agile.feature.statusview.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.mingyuechunqiu.agile.R
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants.TAG_AGILE_STATUS_VIEW
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

    private var ivIcon: ImageView? = null
    private var tvContent: TextView? = null
    private var tvReload: TextView? = null

    private var mDelegate: IStatusViewDelegate? = null
    private var mManager: FragmentManager? = null

    override fun onStart() {
        super.onStart()
        setDialogWindow { window ->
            val option = mDelegate?.statusViewOption
            option?.let {
                if (it.dialogWidth > 0 || it.dialogHeight > 0) {
                    val width = if (it.dialogWidth <= 0) (window.attributes?.width
                            ?: 0) else it.dialogWidth
                    val height = if (it.dialogHeight <= 0) (window.attributes?.height
                            ?: 0) else it.dialogHeight
                    if (width > 0 && height > 0) {
                        window.setLayout(width, height)
                    }
                }
                window.setDimAmount(it.dialogDimAmount)
                if (it.dialogAnimationResId != 0) {
                    window.setWindowAnimations(it.dialogAnimationResId)
                }
            }
        }
    }

    override fun generateInflateLayoutViewCreator(): IInflateLayoutViewCreator {
        return object : IInflateLayoutViewCreator {
            override fun getInflateLayoutId(): Int {
                val id = mDelegate?.statusViewOption?.statusViewContainer?.customLayoutId
                return if (id == null || id == 0) R.layout.agile_dialog_fragment_status_view else id
            }

            override fun getInflateLayoutView(inflater: LayoutInflater, container: ViewGroup?): View? {
                return null
            }
        }
    }

    override fun releaseOnDestroyView() {
    }

    override fun releaseOnDestroy() {
        mDelegate?.statusViewOption?.onStatusViewDialogListener?.onDismissListener(this)
        mDelegate = null
        mManager = null
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        var vContainer: View? = null
        var vProgress: View? = null
        mDelegate?.statusViewOption?.let {
            if (it.statusViewContainer?.customLayoutId != null) {
                vContainer = view.findViewById(it.statusViewContainer.containerId)
                vProgress = view.findViewById(it.statusViewContainer.progressViewId)
                ivIcon = view.findViewById(it.statusViewContainer.iconViewId)
                tvContent = view.findViewById(it.statusViewContainer.contentViewId)
                tvReload = view.findViewById(it.statusViewContainer.reloadViewId)
            } else {
                vContainer = view.findViewById(R.id.ll_agile_dfg_status_view_container)

                val progressViewId = when (mDelegate?.statusViewOption?.progressOption?.progressStyle) {
                    StatusViewConstants.ProgressStyle.STYLE_SYSTEM -> R.id.pb_agile_dfg_status_view_progress
                    StatusViewConstants.ProgressStyle.STYLE_DAISY -> R.id.dlv_agile_dfg_status_view_progress
                    else -> R.id.pb_agile_dfg_status_view_progress
                }
                if (progressViewId != 0) {
                    vProgress = view.findViewById(progressViewId)
                    vProgress?.visibility = View.VISIBLE
                }

                ivIcon = view.findViewById(R.id.iv_agile_dfg_status_view_icon)
                tvContent = view.findViewById(R.id.tv_agile_dfg_status_view_content)
                tvReload = view.findViewById(R.id.tv_agile_dfg_status_view_reload)
            }
            tvReload?.setOnClickListener { _ ->
                it.onStatusViewButtonListener?.onClickReload()
            }
        }

        applyDialogConfigure()
        mDelegate?.applyOption(vContainer, vProgress, ivIcon, tvContent, tvReload)
    }

    override fun showStatusView(type: StatusViewConstants.StatusType, manager: FragmentManager) {
        //状态已经保存了就不显示，否则DialogFragment默认的show方法调用的是commit，在保存状态后调用会崩溃报异常
        if (isAdded || isVisible || manager.isStateSaved) {
            return
        }
        mDelegate?.modeType = StatusViewConstants.ModeType.TYPE_DIALOG
        mDelegate?.statusType = type
        mManager = manager
        showSafely(manager, TAG_AGILE_STATUS_VIEW)
    }

    override fun showStatusView(type: StatusViewConstants.StatusType, manager: FragmentManager, containerId: Int) {
        //状态已经保存了就不显示，否则DialogFragment默认的show方法调用的是commit，在保存状态后调用会崩溃报异常
        if (isAdded || isVisible || manager.isStateSaved) {
            return
        }
        mDelegate?.modeType = StatusViewConstants.ModeType.TYPE_FRAGMENT
        mManager = manager
        if (manager.findFragmentByTag(TAG_AGILE_STATUS_VIEW) != null) {
            return
        }
        manager.beginTransaction().add(containerId, this, TAG_AGILE_STATUS_VIEW).commitAllowingStateLoss()
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

    /**
     * 应用对话框配置
     */
    private fun applyDialogConfigure() {
        dialog?.let {
            it.setCanceledOnTouchOutside(mDelegate?.statusViewOption?.isCancelWithOutside ?: false)
            it.setOnKeyListener { dialog, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mDelegate == null) {
                        return@setOnKeyListener false
                    }
                    //拦截返回键事件，是否要做额外处理
                    mDelegate?.statusViewOption?.onStatusViewDialogListener?.let { listener ->
                        return@setOnKeyListener listener.onClickKeyBack(dialog)
                    }
                }
                false
            }
        }
    }

    companion object {

        fun newInstance(option: StatusViewOption): StatusViewDialogFragment {
            val fragment = StatusViewDialogFragment()
            fragment.mDelegate = StatusViewDelegate(option)
            return fragment
        }
    }
}
