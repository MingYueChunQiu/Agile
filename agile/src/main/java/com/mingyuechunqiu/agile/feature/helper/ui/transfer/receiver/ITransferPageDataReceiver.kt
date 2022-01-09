package com.mingyuechunqiu.agile.feature.helper.ui.transfer.receiver

import com.mingyuechunqiu.agile.feature.helper.ui.transfer.dispatcher.ITransferPageDataDispatcher

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/14 11:13 下午
 *      Desc:       传递界面数据接受者
 *      Version:    1.0
 * </pre>
 */
interface ITransferPageDataReceiver {

    /**
     * 当接收界面数据时回调
     *
     * @param dataOwner 数据拥有者
     * @param data 传递的数据
     */
    fun onReceiveTransferPageData(
        dataOwner: ITransferPageDataDispatcher.TransferPageDataOwner,
        data: ITransferPageDataDispatcher.TransferPageData?
    )

    /**
     * 添加数据监听器（使用默认标签）
     *
     * @param listener Tag数据监听器
     * @return 返回数据观察者Id
     */
    fun addTransferDataReceiverListener(listener: TransferPageDataReceiverListener): String

    /**
     * 添加Tag的数据监听器
     *
     * @param tag 标签
     * @param listener Tag数据监听器
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
         * @param dataOwner 数据拥有者
         * @param data 传递的数据
         */
        fun onReceiveTransferPageData(
            dataOwner: ITransferPageDataDispatcher.TransferPageDataOwner,
            data: ITransferPageDataDispatcher.TransferPageData?
        )
    }

    data class TransferPageDataReceiverObserver(
        val id: String,
        val listener: TransferPageDataReceiverListener
    )
}