package com.mingyuechunqiu.agile.base.bridge.call

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
internal class RequestCall<T> constructor(private val mRequest: Request, private val mCallback: Request.Callback<T>) : Call<T> {

    override fun getRequest(): Request {
        return mRequest
    }

    override fun getCallback(): Request.Callback<T> {
        return mCallback
    }
}