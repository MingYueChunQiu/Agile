package com.mingyuechunqiu.agile.feature.helper.ui.hint

import androidx.annotation.StringRes
import com.mingyuechunqiu.agile.data.bean.ErrorInfo
import com.mingyuechunqiu.agile.feature.helper.ui.hint.ToastHelper.ToastConfig

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/18/21 11:44 PM
 *      Desc:       界面弹出提示拥有者接口
 *      Version:    1.0
 * </pre>
 */
interface IPopHintOwner {

    /**
     * 根据资源id显示提示
     *
     * @param msgResId 文本资源id
     */
    fun showToast(@StringRes msgResId: Int)

    /**
     * 根据文本显示提示
     *
     * @param msg 文本
     */
    fun showToast(msg: String?)

    /**
     * 根据错误信息显示提示
     *
     * @param info 错误信息对象
     */
    fun showToast(info: ErrorInfo)

    /**
     * 根据配置信息显示提示
     *
     * @param config 配置信息对象
     */
    fun showToast(config: ToastConfig)
}