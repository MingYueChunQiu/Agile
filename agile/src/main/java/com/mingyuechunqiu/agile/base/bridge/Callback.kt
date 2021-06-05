package com.mingyuechunqiu.agile.base.bridge

import com.mingyuechunqiu.agile.data.bean.ErrorInfo

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/6/2 11:17 下午
 *      Desc:       请求回调接口
 *      Version:    1.0
 * </pre>
 */
interface Callback<T> {

    fun onFailure(info: ErrorInfo)

    fun onSuccess(response: Response<T>)
}