package com.mingyuechunqiu.agile.base.bridge

import android.os.Bundle

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/14/21 10:41 PM
 *      Desc:       数据请求类
 *      Version:    1.0
 * </pre>
 */
class Request @JvmOverloads constructor(
    var requestCategory: RequestCategory = RequestCategory.CATEGORY_NOT_SET,
    var requestTag: String = DEFAULT_KEY_REQUEST_TAG
) {

    var arguments: Bundle? = null
    var paramsInfo: IParamsInfo? = null

    enum class RequestCategory {

        //未设置，离线，网络
        CATEGORY_NOT_SET, CATEGORY_OFFLINE, CATEGORY_NETWORK
    }

    /**
     * 参数对象接口
     */
    interface IParamsInfo

    companion object {

        //默认请求Tag
        const val DEFAULT_KEY_REQUEST_TAG = "DEFAULT_KEY_REQUEST_TAG";
    }
}