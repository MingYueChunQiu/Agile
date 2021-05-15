package com.mingyuechunqiu.agile.feature.helper.ui.key

import android.view.KeyEvent
import androidx.activity.OnBackPressedCallback
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.ITransferPageDataDispatcherHelper

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/11/21 8:34 PM
 *      Desc:       按键事件接收者辅助类接口
 *      Version:    1.0
 * </pre>
 */
interface IKeyEventReceiverHelper {

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

    fun getBackPressedObserver(): BackPressedObserver?

    /**
     * 设置返回回调是否生效
     * @param enabled true生效，否则false
     */
    fun setEnableBackPressedCallback(enabled: Boolean)

    /**
     * 添加按返回键通过Activity返回上一个界面
     *
     * @param helper 传递界面数据辅助类
     * @param interceptor 跳转参数拦截设置器
     * @return 添加监听成功返回true, 否则返回false
     */
    fun listenBackKeyToPreviousPageWithActivity(helper: ITransferPageDataDispatcherHelper, interceptor: ITransferPageDataDispatcherHelper.TransferPageDataInterceptor?): Boolean

    /**
     * 添加按返回键通过父Fragment返回上一个界面
     *
     * @param helper 传递界面数据辅助类
     * @param interceptor 跳转参数拦截设置器
     * @return 添加监听成功返回true, 否则返回false
     */
    fun listenBackKeyToPreviousPageWithParentFragment(helper: ITransferPageDataDispatcherHelper, interceptor: ITransferPageDataDispatcherHelper.TransferPageDataInterceptor?): Boolean

    /**
     * 添加按返回键通过父Fragment返回上一个界面
     *
     * @param helper 传递界面数据辅助类
     * @param interceptor 跳转参数拦截设置器
     * @return 添加监听成功返回true, 否则返回false
     */
    fun listenBackKeyToPreviousPageWithTargetFragment(helper: ITransferPageDataDispatcherHelper, interceptor: ITransferPageDataDispatcherHelper.TransferPageDataInterceptor?): Boolean

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
    data class BackPressedObserver(val callback: OnBackPressedCallback)
}