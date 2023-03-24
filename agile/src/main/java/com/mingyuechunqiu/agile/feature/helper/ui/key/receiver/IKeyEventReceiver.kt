package com.mingyuechunqiu.agile.feature.helper.ui.key.receiver

import com.mingyuechunqiu.agile.feature.helper.ui.key.BackPressedObserver
import com.mingyuechunqiu.agile.feature.helper.ui.key.OnKeyEventListener
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.dispatcher.ITransferPageDataDispatcher
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.dispatcher.ITransferPageDataDispatcherHelper

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/10/21 1:13 PM
 *      Desc:       按键事件接收者接口
 *      Version:    1.0
 * </pre>
 */
interface IKeyEventReceiver {

    //禁止返回Activity
    var isForbidBackToActivity: Boolean

    //禁止返回Fragment
    var isForbidBackToFragment: Boolean

    /**
     * 添加按键监听器
     *
     * @param listener 按键监听器
     * @return 返回按键观察者Id
     */
    fun addOnKeyEventListener(listener: OnKeyEventListener): String?

    /**
     * 移除按键监听器
     *
     * @param observerId 按键观察者Id
     * @return 移除成功返回true，否则返回false
     */
    fun removeOnKeyEventListener(observerId: String): Boolean

    /**
     * 清除当前界面所有按键监听器
     */
    fun clearAllOnKeyEventListeners()

    /**
     * 添加返回键监听器
     *
     * @param isEnabled 是否启用
     * @param operation 返回操作
     * @return 返回返回键监听器，可能为空
     */
    fun addBackPressedObserver(isEnabled: Boolean, operation: (() -> Unit)): BackPressedObserver?

    /**
     * 添加返回键监听器
     *
     * @param tag 事件监听器标签
     * @param isEnabled 是否启用
     * @param operation 返回操作
     * @return 返回返回键监听器，可能为空
     */
    fun addBackPressedObserver(
        tag: String,
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

    /**
     * 添加按返回键通过Activity返回上一个界面
     *
     * @param helper 传递界面数据辅助类
     * @param interceptor 跳转参数拦截设置器
     * @return 添加监听成功返回true, 否则返回false
     */
    fun listenBackKeyToPreviousPageWithActivity(
        helper: ITransferPageDataDispatcherHelper,
        interceptor: ITransferPageDataDispatcher.TransferPageDataInterceptor?
    ): Boolean

    /**
     * 添加按返回键通过父Fragment返回上一个界面
     *
     * @param helper 传递界面数据辅助类
     * @param interceptor 跳转参数拦截设置器
     * @return 添加监听成功返回true, 否则返回false
     */
    fun listenBackKeyToPreviousPageWithParentFragment(
        helper: ITransferPageDataDispatcherHelper,
        interceptor: ITransferPageDataDispatcher.TransferPageDataInterceptor?
    ): Boolean

    /**
     * 添加按返回键通过父Fragment返回上一个界面
     *
     * @param helper 传递界面数据辅助类
     * @param interceptor 跳转参数拦截设置器
     * @return 添加监听成功返回true, 否则返回false
     */
    fun listenBackKeyToPreviousPageWithTargetFragment(
        helper: ITransferPageDataDispatcherHelper,
        interceptor: ITransferPageDataDispatcher.TransferPageDataInterceptor?
    ): Boolean
}