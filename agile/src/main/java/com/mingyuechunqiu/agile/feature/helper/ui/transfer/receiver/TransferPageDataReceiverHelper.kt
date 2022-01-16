package com.mingyuechunqiu.agile.feature.helper.ui.transfer.receiver

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.dispatcher.ITransferPageDataDispatcher
import com.mingyuechunqiu.agile.data.local.UUIDHelper
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2022/1/6 9:11 下午
 *      Desc:       传递数据接收者辅助类
 *                  实现ITransferDataReceiverHelper, LifecycleEventObserver
 *      Version:    1.0
 * </pre>
 */
internal class TransferPageDataReceiverHelper(page: ITransferPageDataReceiverPage) :
    ITransferPageDataReceiverHelper, LifecycleEventObserver {

    private val mReceiverObserverMap: MutableMap<String, MutableList<ITransferPageDataReceiver.TransferPageDataReceiverObserver>> by lazy { ConcurrentHashMap() }

    init {
        page.lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            mReceiverObserverMap.clear()
        }
    }

    override fun onReceiveTransferPageData(
        dataOwner: ITransferPageDataDispatcher.TransferPageDataOwner,
        data: ITransferPageDataDispatcher.TransferPageData?
    ) {
        mReceiverObserverMap.forEach { map ->
            map.value.forEach {
                it.listener.onReceiveTransferPageData(dataOwner, data)
            }
        }
    }

    override fun addTransferDataReceiverListener(listener: ITransferPageDataReceiver.TransferPageDataReceiverListener): String {
        return addTransferDataReceiverListener(TAG_DEFAULT, listener)
    }

    override fun addTransferDataReceiverListener(
        tag: String,
        listener: ITransferPageDataReceiver.TransferPageDataReceiverListener
    ): String {
        val observer = ITransferPageDataReceiver.TransferPageDataReceiverObserver(
            UUIDHelper.getUUID(),
            listener
        )
        val key = createRealKey(tag)
        var list = mReceiverObserverMap[key]
        if (list == null) {
            list = ArrayList()
            list.add(observer)
            mReceiverObserverMap[key] = list
        } else {
            list.add(observer)
        }
        return observer.id
    }

    override fun removeTransferPageDataReceiverListener(observerId: String): Boolean {
        mReceiverObserverMap.values.forEach {
            val iterator = it.iterator()
            while (iterator.hasNext()) {
                if (iterator.next().id == observerId) {
                    iterator.remove()
                    return true
                }
            }
        }
        return false
    }

    override fun removeTransferPageDataReceiverListenerWithTag(tag: String): Boolean {
        val iterator = mReceiverObserverMap.entries.iterator()
        while (iterator.hasNext()) {
            if (iterator.next().key === createRealKey(tag)) {
                iterator.remove()
                return true
            }
        }
        return false
    }

    /**
     * 根据传入tag创建真正的键
     *
     * @param tag 键标签
     */
    private fun createRealKey(tag: String): String {
        return KEY_PREFIX + tag
    }

    companion object {

        //键前缀
        private const val KEY_PREFIX = "Agile_Key_Activity_Key_Event_"

        //默认标签
        private const val TAG_DEFAULT = "default"
    }
}