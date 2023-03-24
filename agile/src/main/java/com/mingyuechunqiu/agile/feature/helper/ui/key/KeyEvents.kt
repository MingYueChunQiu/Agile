package com.mingyuechunqiu.agile.feature.helper.ui.key

import android.view.KeyEvent
import androidx.activity.OnBackPressedCallback

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2023/3/23 22:55
 *      Desc:       按键事件API通用集合
 *      Version:    1.0
 * </pre>
 */
//键前缀
private const val KEY_KEY_EVENT_PREFIX = "Key_key_event_"
private const val KEY_BACK_PRESSED_EVENT_PREFIX = "Key_back_pressed_"

/**
 * 针对通用按键，根据传入tag创建真正的键
 *
 * @param tag 最终键标签
 */
fun createKeyEventRealKey(tag: String): String {
    return KEY_KEY_EVENT_PREFIX + tag
}

/**
 * 针对返回按键，根据传入tag创建真正的键
 *
 * @param tag 最终键标签
 */
fun createBackPressedRealKey(tag: String): String {
    return KEY_BACK_PRESSED_EVENT_PREFIX + tag
}

data class KeyEventObserver(val id: String, val listener: OnKeyEventListener)

interface OnKeyEventListener {

    /**
     * 当触发按键事件时回调
     *
     * @param keyCode 键值
     * @param event   按键事件
     * @return 如果自己处理完成，不需要分发者继续处理返回true，否则返回false
     */
    fun onKeyEvent(keyCode: Int, event: KeyEvent): Boolean
}

/**
 * 回退键观察者类
 *
 * @param callback 回退键回调
 */
data class BackPressedObserver(val id: String, val callback: OnBackPressedCallback)