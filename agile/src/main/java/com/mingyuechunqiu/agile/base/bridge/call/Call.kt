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
 *      Time:       4/17/21 7:30 PM
 *      Desc:       请求调用接口
 *      Version:    1.0
 * </pre>
 */
interface Call {

    fun <I : Request.IParamsInfo> getRequest(): Request<I>

    fun <T> getCallback(): Callback<T>

    companion object {

        /**
         * 创建一个请求调用
         *
         * @param request 请求对象
         * @param callback 请求回调
         * @param <T> 响应数据类型
         */
        fun <I : Request.IParamsInfo, T> newCall(request: Request<I>, callback: Callback<T>): Call {
            return RequestCall(request, callback)
        }
    }
}