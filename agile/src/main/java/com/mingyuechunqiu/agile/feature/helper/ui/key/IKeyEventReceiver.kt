package com.mingyuechunqiu.agile.feature.helper.ui.key

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/10/21 1:13 PM
 *      Desc:       按键事件接收者接口
 *                  继承自IKeyEventReceiverHelper
 *      Version:    1.0
 * </pre>
 */
interface IKeyEventReceiver : IKeyEventReceiverHelper {

    fun getKeyEventReceiverHelper(): IKeyEventReceiverHelper
}