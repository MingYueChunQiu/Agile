package com.mingyuechunqiu.agile.feature.statusview.function

import android.app.Activity
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewConfigure
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants
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

    override fun showStatusView(
        type: StatusViewConstants.StatusViewType,
        activity: Activity,
        option: StatusViewOption?
    ) {
        (activity.findViewById(android.R.id.content) as? ViewGroup)?.let {
            checkOrCreateWidgetStatusView(type, option).showStatusView(
                type,
                it,
                checkOrCreateStatusViewOption(type, option)
            )
        }
    }

    override fun showStatusView(
        type: StatusViewConstants.StatusViewType,
        fragment: Fragment,
        option: StatusViewOption?
    ) {
        (fragment.view as? ViewGroup)?.let {
            checkOrCreateWidgetStatusView(type, option).showStatusView(
                type,
                it,
                checkOrCreateStatusViewOption(type, option)
            )
        }
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

    override fun dismissStatusView() {
        mStatusView?.dismissStatusView()
        mStatusView = null
    }

    override fun getStatusViewType(): StatusViewConstants.StatusViewType {
        return mStatusView?.getStatusViewType() ?: StatusViewConstants.StatusViewType.TYPE_UNKNOWN
    }

    override fun getStatusViewOption(): StatusViewOption? {
        return mStatusView?.getStatusViewOption()
    }

    @Synchronized
    private fun checkOrCreateWidgetStatusView(
        type: StatusViewConstants.StatusViewType,
        option: StatusViewOption?
    ): IStatusView {
        return mStatusView ?: run {
            val view = WidgetStatusView(checkOrCreateStatusViewOption(type, option))
            mStatusView = view
            view
        }
    }

    private fun checkOrCreateStatusViewOption(
        type: StatusViewConstants.StatusViewType,
        option: StatusViewOption?
    ): StatusViewOption {
        return option ?: StatusViewHandler.getStatusViewOptionByType(mConfigure, type, false)
        ?: StatusViewHandler.getGlobalStatusViewOptionByType(type)
    }
}