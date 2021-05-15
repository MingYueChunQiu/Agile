package com.mingyuechunqiu.agile.feature.statusview.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.FragmentManager
import com.mingyuechunqiu.agile.R
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption
import com.mingyuechunqiu.agile.framework.ui.IFragmentInflateLayoutViewCreator
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
internal class StatusViewDialogFragment : BaseDialogFragment() {

    var option: StatusViewOption? = null
    private val mDelegate: IStatusViewDelegate by lazy { StatusViewDelegate(option) }

    override fun onStart() {
        super.onStart()
        setDialogWindow { window ->
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

    override fun generateInflateLayoutViewCreator(): IFragmentInflateLayoutViewCreator {
        return object :
            IFragmentInflateLayoutViewCreator.FragmentInflateLayoutViewCreatorAdapter() {
            override fun getInflateLayoutId(): Int {
                val id = option?.statusViewContainer?.customLayoutId
                return if (id == null || id == 0) R.layout.agile_dialog_fragment_status_view else id
            }
        }
    }

    override fun releaseOnDestroyView() {
    }

    override fun releaseOnDestroy() {
        option?.onStatusViewDialogListener?.onDismissListener(this)
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        mDelegate.applyOption(view)
        applyDialogConfigure()
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
    }

    fun showStatusView(manager: FragmentManager) {
        //状态已经保存了就不显示，否则DialogFragment默认的show方法调用的是commit，在保存状态后调用会崩溃报异常
        if (isAdded || isVisible || manager.isStateSaved) {
            return
        }
        showSafely(manager, AGILE_TAG_STATUS_VIEW)
    }

    /**
     * 应用对话框配置
     */
    private fun applyDialogConfigure() {
        dialog?.let {
            it.setCanceledOnTouchOutside(option?.isCancelWithOutside ?: false)
            it.setOnKeyListener { dialog, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    //拦截返回键事件，是否要做额外处理
                    option?.onStatusViewDialogListener?.let { listener ->
                        return@setOnKeyListener listener.onClickKeyBack(dialog)
                    }
                }
                false
            }
        }
    }

    companion object {

        //状态视图TAG
        const val AGILE_TAG_STATUS_VIEW = "TAG_AGILE_STATUS_VIEW"

        fun newInstance(option: StatusViewOption): StatusViewDialogFragment {
            return StatusViewDialogFragment().apply {
                this.option = option
            }
        }
    }
}
