package com.mingyuechunqiu.agile.feature.helper.ui.transfer.receiver

import com.mingyuechunqiu.agile.frame.ui.IAgilePage

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2022/1/6 10:33 下午
 *      Desc:       传递界面数据接收者界面
 *                  继承自IAgilePage
 *      Version:    1.0
 * </pre>
 */
interface ITransferPageDataReceiverPage : IAgilePage {

    fun getTransferPageDataReceiverHelper(): ITransferPageDataReceiverHelper
}