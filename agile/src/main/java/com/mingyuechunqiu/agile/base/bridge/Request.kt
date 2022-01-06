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
class Request<I : Request.IParamsInfo> @JvmOverloads constructor(
    val requestCategory: RequestCategory = RequestCategory.CATEGORY_OFFLINE,
    val requestTag: String = Request.Tag.TAG_DEFAULT_REQUEST,
    val arguments: Bundle? = null,
    val paramsInfo: I? = null
) {

    enum class RequestCategory {

        //未设置，离线，网络
        CATEGORY_NOT_SET, CATEGORY_OFFLINE, CATEGORY_NETWORK
    }

    /**
     * 参数对象接口
     */
    interface IParamsInfo

    object Tag {

        //未设置标签
        const val TAG_NOT_SET = "tag_not_set"

        //默认请求Tag
        const val TAG_DEFAULT_REQUEST = "tag_default_request"
    }
}