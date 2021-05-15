package com.mingyuechunqiu.agile.frame.ui.activity

import com.mingyuechunqiu.agile.feature.helper.ui.key.IKeyEventDispatcher
import com.mingyuechunqiu.agile.feature.helper.ui.key.IKeyEventDispatcherPage
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.ITransferDataReceiver
import com.mingyuechunqiu.agile.frame.ui.IAgilePage

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/11/21 8:16 PM
 *      Desc:       所有框架Activity界面父接口
 *                  继承自IAgilePage, ITransferDataReceiver, IKeyEventDispatcher, IKeyEventDispatcherPage
 *      Version:    1.0
 * </pre>
 */
interface IAgileActivityPage : IAgilePage, ITransferDataReceiver, IKeyEventDispatcher,
    IKeyEventDispatcherPage