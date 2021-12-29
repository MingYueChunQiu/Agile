package com.mingyuechunqiu.agile.base.viewmodel

import android.content.Context
import com.mingyuechunqiu.agile.base.bridge.call.ICallExecutor
import com.mingyuechunqiu.agile.feature.helper.ui.hint.IPopHintOwner
import com.mingyuechunqiu.agile.feature.statusview.framework.IStatusViewProcessor

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2021/12/29
 *     desc   : 所有ViewModel层的父接口
 *              继承自ICallExecutor, IPopHintOwner, IStatusViewProcessor
 *     version: 1.0
 * </pre>
 */
interface IBaseViewModel : ICallExecutor, IPopHintOwner, IStatusViewProcessor {

    fun releaseOnDetach()

    fun getAppContext(): Context
}