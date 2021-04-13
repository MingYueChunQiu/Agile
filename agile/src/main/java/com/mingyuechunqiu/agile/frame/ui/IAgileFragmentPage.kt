package com.mingyuechunqiu.agile.frame.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.mingyuechunqiu.agile.feature.helper.ui.key.IKeyEventReceiver
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.ITransferDataPage

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/8/21 11:11 PM
 *      Desc:       所有框架Fragment界面父接口
 *                  继承自IAgilePage, ITransferDataPage, IKeyEventReceiver
 *      Version:    1.0
 * </pre>
 */
interface IAgileFragmentPage : IAgilePage, ITransferDataPage, IKeyEventReceiver {

    fun getOwnedActivity(): FragmentActivity?

    fun getOwnedParentFragment(): Fragment?

    fun getOwnedTargetFragment(): Fragment?
}