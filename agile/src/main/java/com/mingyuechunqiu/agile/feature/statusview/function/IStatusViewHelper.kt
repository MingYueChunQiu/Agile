package com.mingyuechunqiu.agile.feature.statusview.function

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewConfigure
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants.StatusViewType
import com.mingyuechunqiu.agile.feature.statusview.ui.view.IStatusView

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-11-27 22:37
 *      Desc:       状态视图帮助接口
 *      Version:    1.0
 * </pre>
 */
interface IStatusViewHelper {

    fun applyStatusViewConfigure(configure: StatusViewConfigure)

    fun getStatusViewConfigure(): StatusViewConfigure?

    fun getStatusView(): IStatusView?

    /**
     * 显示状态视图
     *
     * @param type    状态视图类型
     * @param container 视图添加父容器
     * @param option  状态视图配置信息类
     */
    fun showStatusView(type: StatusViewType, container: ViewGroup, option: StatusViewOption?)

    /**
     * 显示状态视图
     *
     * @param type    状态视图类型
     * @param manager Fragment管理器
     * @param option  状态视图配置信息类
     */
    fun showStatusView(type: StatusViewType, manager: FragmentManager, option: StatusViewOption?)

    /**
     * 关闭状态视图
     *
     * @param allowStateLoss true允许丧失状态，否则false
     */
    fun dismissStatusView(allowStateLoss: Boolean = true)

    fun saveStatueViewInstanceState(outState: Bundle, manager: FragmentManager?)

    fun restoreStatueViewInstanceState(savedInstanceState: Bundle?, manager: FragmentManager?)

    fun getStatusViewMode(): StatusViewConstants.StatusViewMode

    fun getStatusViewType(): StatusViewType
}