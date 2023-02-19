package com.mingyuechunqiu.agile.base.viewmodel

import com.mingyuechunqiu.agile.base.bridge.Request
import com.mingyuechunqiu.agile.base.bridge.call.Call
import com.mingyuechunqiu.agile.base.model.IBaseModel

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2023/2/18 21:11
 *      Desc:       不依赖于数据层的孤立的ViewModel，适用于纯粹视图逻辑模块
 *                  继承自BaseAbstractViewModel<IBaseModel>
 *      Version:    1.0
 * </pre>
 */
abstract class BaseIsolatedViewModel : BaseAbstractViewModel<IBaseModel>() {

    override fun initModel(): IBaseModel? {
        return null
    }

    override fun <I : Request.IParamsInfo, T> dispatchCallWithModel(call: Call<I, T>): Boolean {
        return false
    }
}