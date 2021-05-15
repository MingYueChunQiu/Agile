package com.mingyuechunqiu.agile.feature.helper.ui.transfer

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/14 11:29 下午
 *      Desc:       传递界面数据分发者
 *                  继承自ITransferPageDataDispatcherHelper
 *      Version:    1.0
 * </pre>
 */
interface ITransferDataDispatcher : ITransferPageDataDispatcherHelper {

    fun getTransferPageDataHelper(): ITransferPageDataDispatcherHelper
}