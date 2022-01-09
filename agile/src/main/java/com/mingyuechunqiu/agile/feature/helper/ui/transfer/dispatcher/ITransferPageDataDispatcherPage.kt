package com.mingyuechunqiu.agile.feature.helper.ui.transfer.dispatcher

import com.mingyuechunqiu.agile.frame.ui.fragment.IFragmentOwnerPage

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/12 11:44 下午
 *      Desc:       传递界面数据分发者界面
 *                  继承自IFragmentOwnerPage
 *      Version:    1.0
 * </pre>
 */
interface ITransferPageDataDispatcherPage : IFragmentOwnerPage {

    fun getTransferPageDataDispatcherHelper(): ITransferPageDataDispatcherHelper
}