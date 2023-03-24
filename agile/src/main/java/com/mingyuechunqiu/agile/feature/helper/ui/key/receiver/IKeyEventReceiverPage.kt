package com.mingyuechunqiu.agile.feature.helper.ui.key.receiver

import com.mingyuechunqiu.agile.feature.helper.ui.key.dispatcher.IKeyEventDispatcherPage
import com.mingyuechunqiu.agile.frame.ui.IAgilePage
import com.mingyuechunqiu.agile.frame.ui.fragment.IFragmentOwnerPage

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/15 1:24 下午
 *      Desc:       按键事件接收者界面
 *                  继承自IAgilePage, IFragmentOwnerPage
 *      Version:    1.0
 * </pre>
 */
interface IKeyEventReceiverPage : IAgilePage, IFragmentOwnerPage {

    fun getKeyEventReceiverHelper(): IKeyEventReceiverHelper

    fun getBelongToKeyEventDispatcherPage(): IKeyEventDispatcherPage?
}