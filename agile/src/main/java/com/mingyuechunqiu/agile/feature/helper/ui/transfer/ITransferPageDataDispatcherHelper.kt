package com.mingyuechunqiu.agile.feature.helper.ui.transfer

import android.os.Bundle

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/10/21 1:17 PM
 *      Desc:       传递界面数据能力接口
 *      Version:    1.0
 * </pre>
 */
interface ITransferPageDataDispatcherHelper {

    fun transferDataToPage(
        targetPage: TransferPageDataCallback,
        data: TransferPageData?
    ): Boolean

    fun transferDataToActivity(data: TransferPageData?): Boolean

    fun returnToPreviousPageWithActivity(interceptor: TransferPageDataInterceptor?): Boolean

    fun transferDataToParentFragment(data: TransferPageData?): Boolean

    fun transferDataToTargetFragment(data: TransferPageData?): Boolean

    fun returnToPreviousPageWithParentFragment(interceptor: TransferPageDataInterceptor?): Boolean

    fun returnToPreviousPageWithTargetFragment(interceptor: TransferPageDataInterceptor?): Boolean

    interface TransferPageDataCallback {

        fun onReceiveTransferPageData(dataOwner: TransferPageDataOwner, data: TransferPageData?)
    }

    /**
     * 跳转界面源头
     *
     * @param tag 标签
     */
    data class TransferPageDataOwner(val tag: String)

    /**
     * 跳转界面数据
     *
     * @param bundle 键值对数据
     * @param obj 数据对象
     */
    data class TransferPageData(val bundle: Bundle, val obj: Any? = null)

    interface TransferPageDataInterceptor {

        /**
         * 传递Bundle数据
         *
         * @param bundle 传递数据
         * @return 返回数据Bundle对象
         */
        fun interceptTransferData(bundle: Bundle): Bundle
    }
}