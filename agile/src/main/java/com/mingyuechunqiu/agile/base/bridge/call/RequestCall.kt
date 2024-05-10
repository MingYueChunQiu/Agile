package com.mingyuechunqiu.agile.base.bridge.call

import com.mingyuechunqiu.agile.base.bridge.Callback
import com.mingyuechunqiu.agile.base.bridge.Request

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/17/21 7:32 PM
 *      Desc:       请求调用类
 *                  实现Call
 *      Version:    1.0
 * </pre>
 */
internal class RequestCall<I : Request.IParamsInfo, T>(
    private val mRequest: Request<I>,
    private val mCallback: Callback<T>
) : Call<I, T> {

    override fun getRequest(): Request<I> {
        return mRequest
    }

    override fun getCallback(): Callback<T> {
        return mCallback
    }
}