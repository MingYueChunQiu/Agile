package com.mingyuechunqiu.agile.feature.helper.ui.key.dispatcher

import android.view.KeyEvent
import com.mingyuechunqiu.agile.feature.helper.ui.key.BackPressedObserver
import com.mingyuechunqiu.agile.feature.helper.ui.key.OnKeyEventListener
import com.mingyuechunqiu.agile.frame.ui.IAgilePage

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/10/21 1:14 PM
 *      Desc:       按键事件分发者接口
 *      Version:    1.0
 * </pre>
 */
interface IKeyEventDispatcher {

    /**
     * 添加Tag的按键监听器
     *
     * @param listener Tag按键监听器
     * @return 返回按键观察者Id
     */
    fun addOnKeyEventListener(listener: OnKeyEventListener): String

    /**
     * 添加Tag的按键监听器
     *
     * @param tag 标签
     * @param listener Tag按键监听器
     * @return 返回按键观察者Id
     */
    fun addOnKeyEventListener(tag: String, listener: OnKeyEventListener): String

    /**
     * 根据监听器ID移除返回键监听器
     *
     * @param observerId 监听器Id
     * @return 如果移除成功返回true，否则返回false
     */
    fun removeOnKeyEventListener(observerId: String): Boolean

    /**
     * 移除与Tag关联的所有按键监听器
     *
     * @param tag 与按键监听器相关联的标签
     * @return 如果移除成功返回true，否则返回false
     */
    fun removeOnKeyEventListenersWithTag(tag: String): Boolean

    /**
     * 分发按键事件
     *
     * @param keyCode 键值
     * @param event 按键事件
     * @return 如果成功处理返回true，否则返回false
     */
    fun dispatchOnKeyEventListener(keyCode: Int, event: KeyEvent?): Boolean

    /**
     * 添加返回键监听器
     *
     * @param page 需要添加的页面
     * @param isEnabled 是否启用
     * @param operation 返回操作
     * @return 返回返回键监听器，可能为空
     */
    fun addBackPressedObserver(
        page: IAgilePage,
        isEnabled: Boolean,
        operation: (() -> Unit)
    ): BackPressedObserver?

    /**
     * 添加返回键监听器
     *
     * @param tag 事件监听器标签
     * @param page 需要添加的页面
     * @param isEnabled 是否启用
     * @param operation 返回操作
     * @return 返回返回键监听器，可能为空
     */
    fun addBackPressedObserver(
        tag: String,
        page: IAgilePage,
        isEnabled: Boolean,
        operation: (() -> Unit)
    ): BackPressedObserver?

    /**
     * 根据监听器ID移除返回键监听器
     *
     * @param observerId 监听器ID
     * @return 移除成功返回true，否则返回false
     */
    fun removeBackPressedObserver(observerId: String): Boolean

    /**
     * 根据默认监听器关联标签移除返回键监听器
     *
     * @return 如果移除成功返回true，否则返回false
     */
    fun removeBackPressedObserversWithTag(): Boolean

    /**
     * 根据监听器关联标签移除返回键监听器
     *
     * @param tag 与按键监听器相关联的标签
     * @return 如果移除成功返回true，否则返回false
     */
    fun removeBackPressedObserversWithTag(tag: String): Boolean

    /**
     * 根据监听器ID设置返回键监听器是否生效
     *
     * @param observerId 监听器ID
     * @param isEnabled true表示生效，否则false
     */
    fun setEnableBackPressedCallback(observerId: String, isEnabled: Boolean)

    /**
     * 根据默认监听器关联标签设置返回键监听器是否生效
     *
     * @param isEnabled true表示生效，否则false
     */
    fun setEnableBackPressedCallbacksWithTag(isEnabled: Boolean)

    /**
     * 根据监听器关联标签设置返回键监听器是否生效
     *
     * @param tag 监听器关联标签
     * @param isEnabled true表示生效，否则false
     */
    fun setEnableBackPressedCallbacksWithTag(tag: String, isEnabled: Boolean)
}