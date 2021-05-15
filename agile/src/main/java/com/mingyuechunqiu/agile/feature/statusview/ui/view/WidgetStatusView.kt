package com.mingyuechunqiu.agile.feature.statusview.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
internal class WidgetStatusView(private val mOption: StatusViewOption) : IStatusView {

    private var mStatusType: StatusViewConstants.StatusType =
        StatusViewConstants.StatusType.TYPE_LOADING
    private val mDelegate: IStatusViewDelegate by lazy { StatusViewDelegate(mOption) }
    private var mView: View? = null

    override fun showStatusView(
        type: StatusViewConstants.StatusType,
        container: ViewGroup,
        option: StatusViewOption
    ) {
        mStatusType = type
        val id = mOption.statusViewContainer?.customLayoutId
        val layoutId = if (id == null || id == 0) R.layout.agile_dialog_fragment_status_view else id
        mView =
            LayoutInflater.from(Agile.getAppContext()).inflate(layoutId, container, false).apply {
                mDelegate.applyOption(this)
            }
    }

    override fun showStatusView(
        type: StatusViewConstants.StatusType,
        manager: FragmentManager,
        option: StatusViewOption
    ) {
        LogManagerProvider.i("WidgetStatusView", "showStatusView : not correct mode")
    }

    override fun dismissStatusView(allowStateLoss: Boolean) {
        mView?.let {
            (it.parent as? ViewGroup)?.removeView(it)
        }
        mView = null
    }

    override fun getModeType(): StatusViewConstants.StatusMode {
        return StatusViewConstants.StatusMode.MODE_WIDGET
    }

    override fun getStatusType(): StatusViewConstants.StatusType {
        return mStatusType
    }
}