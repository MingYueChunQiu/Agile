package com.mingyuechunqiu.agile.feature.helper.ui.key.dispatcher

import android.view.KeyEvent
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.mingyuechunqiu.agile.data.local.UUIDHelper
import com.mingyuechunqiu.agile.feature.helper.ui.key.*
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider
import com.mingyuechunqiu.agile.frame.ui.IAgilePage
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
class KeyEventDispatcherHelper(private val mPage: IKeyEventDispatcherPage) :
    IKeyEventDispatcherHelper,
    LifecycleEventObserver {

    private val mTag = KeyEventDispatcherHelper::class.java.simpleName
    private val mKeyEventListenerMap: MutableMap<String, MutableList<KeyEventObserver>> by lazy { ConcurrentHashMap() }
    private val mBackPressedObserverMap: MutableMap<String, MutableList<BackPressedObserver>> by lazy { ConcurrentHashMap() }

    init {
        mPage.lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            mKeyEventListenerMap.clear()
            mBackPressedObserverMap.clear()
        }
    }

    override fun addOnKeyEventListener(listener: OnKeyEventListener): String {
        return addOnKeyEventListener(mPage.getPageTag(), listener)
    }

    /**
     * 添加Tag的按键监听器
     *
     * @param tag 标签
     * @param listener Tag按键监听器
     * @return 返回按键观察者Id
     */
    override fun addOnKeyEventListener(tag: String, listener: OnKeyEventListener): String {
        val observer = KeyEventObserver(UUIDHelper.getUUID(), listener)
        val key = createKeyEventRealKey(tag)
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
            if (iterator.next().key === createKeyEventRealKey(tag)) {
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
     * @return 如果成功处理返回true，否则返回false
     */
    override fun dispatchOnKeyEventListener(keyCode: Int, event: KeyEvent?): Boolean {
        LogManagerProvider.d(mTag, "dispatchOnKeyEventListener enter")
        return event?.let {
            mKeyEventListenerMap.values.forEach {
                it.forEach { observer ->
                    if (observer.listener.onKeyEvent(keyCode, event)) {
                        return@let true
                    }
                }
            }
            false
        } ?: false
    }

    override fun addBackPressedObserver(
        page: IAgilePage,
        isEnabled: Boolean,
        operation: () -> Unit
    ): BackPressedObserver? {
        return addBackPressedObserver(page.getPageTag(), page, isEnabled, operation)
    }

    override fun addBackPressedObserver(
        tag: String,
        page: IAgilePage,
        isEnabled: Boolean,
        operation: () -> Unit
    ): BackPressedObserver? {
        LogManagerProvider.d(mTag, "addBackPressedObserver: ${page.getPageTag()} $isEnabled")
        return mPage.getOwnedActivity()?.onBackPressedDispatcher?.let {
            val observer = BackPressedObserver(UUIDHelper.getUUID(), object :
                OnBackPressedCallback(isEnabled) {
                override fun handleOnBackPressed() {
                    LogManagerProvider.d(mTag, "handleOnBackPressed enter")
                    operation()
                }
            })
            val key = createBackPressedRealKey(tag)
            var list = mBackPressedObserverMap[key]
            if (list == null) {
                list = ArrayList()
                list.add(observer)
                mBackPressedObserverMap[key] = list
            } else {
                list.add(observer)
            }
            it.addCallback(page, observer.callback)
            observer
        }
    }

    override fun removeBackPressedObserver(observerId: String): Boolean {
        mBackPressedObserverMap.values.forEach {
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

    override fun removeBackPressedObserversWithTag(): Boolean {
        return removeBackPressedObserversWithTag(mPage.getPageTag())
    }

    override fun removeBackPressedObserversWithTag(tag: String): Boolean {
        val iterator = mBackPressedObserverMap.entries.iterator()
        while (iterator.hasNext()) {
            if (iterator.next().key === createBackPressedRealKey(tag)) {
                iterator.remove()
                return true
            }
        }
        return false
    }

    override fun setEnableBackPressedCallback(observerId: String, isEnabled: Boolean) {
        mBackPressedObserverMap.values.forEach { list ->
            list.find { it.id == observerId }?.callback?.isEnabled = isEnabled
        }
    }

    override fun setEnableBackPressedCallbacksWithTag(isEnabled: Boolean) {
        setEnableBackPressedCallbacksWithTag(mPage.getPageTag(), isEnabled)
    }

    override fun setEnableBackPressedCallbacksWithTag(tag: String, isEnabled: Boolean) {
        mBackPressedObserverMap.filterKeys { it == createBackPressedRealKey(tag) }.values.forEach { list ->
            list.forEach {
                it.callback.isEnabled = isEnabled
            }
        }
    }
}