package com.mingyuechunqiu.agile.feature.statusview.ui.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import com.mingyuechunqiu.agile.R
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants
import com.mingyuechunqiu.agile.feature.statusview.ui.IStatusViewDelegate
import com.mingyuechunqiu.agile.feature.statusview.ui.StatusViewDelegate
import com.mingyuechunqiu.agile.frame.Agile

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/22/21 11:26 PM
 *      Desc:       控件模式状态视图
 *                  实现IStatusView
 *      Version:    1.0
 * </pre>
 */
internal class WidgetStatusView(private val mOption: StatusViewOption) : IStatusView {

    private var mStatusType: StatusViewConstants.StatusViewType =
        StatusViewConstants.StatusViewType.TYPE_LOADING
    private val mDelegate: IStatusViewDelegate by lazy { StatusViewDelegate(mOption) }
    private var mView: FrameLayout? = null

    override fun showStatusView(
        type: StatusViewConstants.StatusViewType,
        container: ViewGroup,
        option: StatusViewOption
    ) {
        LogManagerProvider.i("WidgetStatusView", "showStatusView : Widget")
        mStatusType = type
        val id = mOption.statusViewContainer?.customLayoutId
        val layoutId = if (id == null || id == 0) R.layout.agile_dialog_fragment_status_view else id
        mView = FrameLayout(Agile.getAppContext()).apply {
            isFocusable = true
            isFocusableInTouchMode = true
            requestFocus()
            setOnKeyListener { _, keyCode, _ ->
                return@setOnKeyListener if (keyCode == KeyEvent.KEYCODE_BACK) {
                    //拦截返回键事件，是否要做额外处理
                    option.onStatusViewDialogListener?.let { listener ->
                        LogManagerProvider.d(
                            "WidgetStatusView",
                            "onKeyEvent: StatusViewDialogListener enter"
                        )
                        return@let listener.onClickKeyBack(this@WidgetStatusView)
                    } ?: kotlin.run {
                        if (option.isBackWithDismiss) {
                            LogManagerProvider.d(
                                "WidgetStatusView",
                                "onKeyEvent: isBackWithDismiss enter"
                            )
                            dismissStatusView(true)
                        }
                        true
                    }
                } else {
                    false
                }
            }
            container.addView(this)
        }
        LayoutInflater.from(Agile.getAppContext()).inflate(layoutId, mView as FrameLayout, true)
            .apply {
                mDelegate.applyOption(this)
                setOnClickListener {
                    if (option.isCancelWithOutside) {
                        dismissStatusView(true)
                    }
                }
            }
    }

    override fun showStatusView(
        type: StatusViewConstants.StatusViewType,
        manager: FragmentManager,
        option: StatusViewOption
    ) {
        LogManagerProvider.i("WidgetStatusView", "showStatusView : not correct mode")
    }

    override fun dismissStatusView(allowStateLoss: Boolean) {
        mOption.onStatusViewDialogListener?.onDismissListener(this)
        mView?.let {
            (it.parent as? ViewGroup)?.removeView(it)
        }
        mView = null
    }

    override fun saveStatueViewInstanceState(outState: Bundle, manager: FragmentManager?) {
    }

    override fun restoreStatueViewInstanceState(
        savedInstanceState: Bundle?,
        manager: FragmentManager?
    ) {
    }

    override fun getStatusViewMode(): StatusViewConstants.StatusViewMode {
        return StatusViewConstants.StatusViewMode.MODE_WIDGET
    }

    override fun getStatusViewType(): StatusViewConstants.StatusViewType {
        return mStatusType
    }

    override fun getStatusViewOption(): StatusViewOption {
        return mOption
    }
}