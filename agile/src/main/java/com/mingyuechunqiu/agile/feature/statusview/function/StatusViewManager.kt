package com.mingyuechunqiu.agile.feature.statusview.function

import androidx.fragment.app.FragmentManager
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants.StatusType

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

    override fun showStatusView(type: StatusType, manager: FragmentManager, option: StatusViewOption?) {
        mHelper.showStatusView(type, manager, option)
    }

    override fun showStatusView(type: StatusType, manager: FragmentManager, containerId: Int, option: StatusViewOption?) {
        mHelper.showStatusView(type, manager, containerId, option)
    }

    override fun dismissStatusView(allowStateLoss: Boolean) {
        mHelper.dismissStatusView(allowStateLoss)
    }

    override fun getModeType(): StatusViewConstants.ModeType {
        return mHelper.getModeType()
    }

    override fun getStatusMode(): StatusType {
        return mHelper.getStatusMode()
    }
}