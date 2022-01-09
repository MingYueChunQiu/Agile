package com.mingyuechunqiu.agile.frame.ui.fragment

import com.mingyuechunqiu.agile.feature.helper.ui.key.receiver.IKeyEventReceiverPage
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.dispatcher.ITransferPageDataDispatcherPage
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.receiver.ITransferPageDataReceiverPage

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/8/21 11:11 PM
 *      Desc:       所有框架Fragment界面父接口
 *                  继承自ITransferPageDataDispatcherPage, ITransferPageDataReceiverPage, IKeyEventReceiver, IKeyEventReceiverPage
 *      Version:    1.0
 * </pre>
 */
interface IAgileFragmentPage : ITransferPageDataDispatcherPage, ITransferPageDataReceiverPage,
    IKeyEventReceiverPage {

    fun getFragmentViewPage(): FragmentViewPage
}