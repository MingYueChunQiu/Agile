package com.mingyuechunqiu.agile.frame.ui.fragment

import com.mingyuechunqiu.agile.feature.helper.ui.key.IKeyEventReceiver
import com.mingyuechunqiu.agile.feature.helper.ui.key.IKeyEventReceiverPage
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.ITransferDataDispatcher
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.ITransferDataDispatcherPage

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/8/21 11:11 PM
 *      Desc:       所有框架Fragment界面父接口
 *                  继承自ITransferDataDispatcher, ITransferDataDispatcherPage, IKeyEventReceiver, IKeyEventReceiverPage
 *      Version:    1.0
 * </pre>
 */
interface IAgileFragmentPage : ITransferDataDispatcher, ITransferDataDispatcherPage,
    IKeyEventReceiver, IKeyEventReceiverPage {

    fun getFragmentViewPage(): FragmentViewPage
}