package com.mingyuechunqiu.agile.frame.ui.dialog

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.dispatcher.ITransferPageDataDispatcherPage

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/10 12:01 上午
 *      Desc:       所有框架对话框界面父接口
 *                  继承自ITransferDataDispatcherPage
 *      Version:    1.0
 * </pre>
 */
interface IAgileDialogPage : ITransferPageDataDispatcherPage {

    @MainThread
    fun getDialogLifecycleOwner(): LifecycleOwner
}