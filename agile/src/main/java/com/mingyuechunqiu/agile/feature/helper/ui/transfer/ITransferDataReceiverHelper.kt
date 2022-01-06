package com.mingyuechunqiu.agile.feature.helper.ui.transfer

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2022/1/6 9:10 下午
 *      Desc:       传递数据接收者辅助类接口
 *      Version:    1.0
 * </pre>
 */
interface ITransferDataReceiverHelper {

    /**
     * 添加Tag的数据监听器
     *
     * @param tag 标签
     * @param listener Tag按键监听器
     * @return 返回数据观察者Id
     */
    fun addTransferDataReceiverListener(
        tag: String,
        listener: TransferPageDataReceiverListener
    ): String

    /**
     * 删除Tag的数据监听器
     *
     * @param observerId 数据观察者Id
     * @return 如果删除成功返回true，否则返回false
     */
    fun removeTransferPageDataReceiverListener(observerId: String): Boolean

    /**
     * 移除与Tag关联的所有数据监听器
     *
     * @param tag 与数据监听器相关联的标签
     * @return 如果删除成功返回true，否则返回false
     */
    fun removeTransferPageDataReceiverListenerWithTag(tag: String): Boolean

    /**
     * 传递数据接收者接口
     */
    interface TransferPageDataReceiverListener {

        /**
         * 当接收传递界面数据时回调
         *
         * @param data 传递的数据
         */
        fun onReceiveTransferPageData(data: ITransferPageDataDispatcherHelper.TransferPageData?)
    }

    data class TransferPageDataReceiverObserver(
        val id: String,
        val listener: TransferPageDataReceiverListener
    )
}