package com.mingyuechunqiu.agile.feature.helper.ui.key.dispatcher

import android.view.KeyEvent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.mingyuechunqiu.agile.feature.helper.ui.key.receiver.IKeyEventReceiver
import com.mingyuechunqiu.agile.util.UUIDUtils
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/10/21 2:01 PM
 *      Desc:       按键事件分发者辅助类
 *                  实现IKeyEventDispatcher, LifecycleEventObserver
 *      Version:    1.0
 * </pre>
 */
class KeyEventDispatcherHelper(page: IKeyEventDispatcherPage) : IKeyEventDispatcherHelper,
    LifecycleEventObserver {

    private val mKeyEventListenerMap: MutableMap<String, MutableList<IKeyEventReceiver.KeyEventObserver>> by lazy { ConcurrentHashMap() }

    init {
        page.lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            mKeyEventListenerMap.clear()
        }
    }

    override fun addOnKeyEventListener(listener: IKeyEventReceiver.OnKeyEventListener): String {
        return addOnKeyEventListener(createRealKey(TAG_DEFAULT), listener)
    }

    /**
     * 添加Tag的按键监听器
     *
     * @param tag 标签
     * @param listener Tag按键监听器
     * @return 返回按键观察者Id
     */
    override fun addOnKeyEventListener(
        tag: String,
        listener: IKeyEventReceiver.OnKeyEventListener
    ): String {
        val observer = IKeyEventReceiver.KeyEventObserver(UUIDUtils.getUUID(), listener)
        val key = createRealKey(tag)
        var list = mKeyEventListenerMap[key]
        if (list == null) {
            list = ArrayList()
            list.add(observer)
            mKeyEventListenerMap[key] = list
        } else {
            list.add(observer)
        }
        return observer.id
    }

    /**
     * 删除Tag的按键监听器
     *
     * @param observerId 按键观察者Id
     * @return 如果删除成功返回true，否则返回false
     */
    override fun removeOnKeyEventListener(observerId: String): Boolean {
        mKeyEventListenerMap.values.forEach {
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

    /**
     * 移除与Tag关联的所有按键监听器
     *
     * @param tag 与按键监听器相关联的标签
     * @return 如果删除成功返回true，否则返回false
     */
    override fun removeOnKeyEventListenersWithTag(tag: String): Boolean {
        val iterator = mKeyEventListenerMap.entries.iterator()
        while (iterator.hasNext()) {
            if (iterator.next().key === createRealKey(tag)) {
                iterator.remove()
                return true
            }
        }
        return false
    }

    /**
     * 分发按键事件
     *
     * @param keyCode 键值
     * @param event 按键事件
     * @return 如果删除成功返回true，否则返回false
     */
    override fun dispatchOnKeyEventListener(keyCode: Int, event: KeyEvent?): Boolean {
        return event?.let {
            mKeyEventListenerMap.values.forEach {
                it.forEach { observer ->
                    if (observer.listener.onKeyEvent(keyCode, event)) {
                        return true
                    }
                }
            }
            false
        } ?: false
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
        private const val KEY_PREFIX = "Key_Activity_Key_Event_"

        //默认标签
        private const val TAG_DEFAULT = "default"
    }
}