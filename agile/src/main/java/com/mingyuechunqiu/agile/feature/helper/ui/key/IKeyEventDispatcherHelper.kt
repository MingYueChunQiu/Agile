package com.mingyuechunqiu.agile.feature.helper.ui.key

import android.view.KeyEvent

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/11/21 8:32 PM
 *      Desc:       按键事件分发者辅助类接口
 *      Version:    1.0
 * </pre>
 */
interface IKeyEventDispatcherHelper {

    /**
     * 添加Tag的按键监听器
     *
     * @param listener Tag按键监听器
     * @return 返回按键观察者Id
     */
    fun addOnKeyEventListener(tag: String, listener: IKeyEventReceiverHelper.OnKeyEventListener): String

    /**
     * 删除Tag的按键监听器
     *
     * @param observerId 按键观察者Id
     * @return 如果删除成功返回true，否则返回false
     */
    fun removeOnKeyEventListener(observerId: String): Boolean

    /**
     * 移除与Tag关联的所有按键监听器
     *
     * @param tag 与按键监听器相关联的标签
     * @return 如果删除成功返回true，否则返回false
     */
    fun removeOnKeyEventListenersWithTag(tag: String): Boolean

    /**
     * 分发按键事件
     *
     * @param keyCode 键值
     * @param event 按键事件
     * @return 如果删除成功返回true，否则返回false
     */
    fun dispatchOnKeyEventListener(keyCode: Int, event: KeyEvent?): Boolean
}