package com.mingyuechunqiu.agile.feature.helper.ui.key.receiver

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.mingyuechunqiu.agile.feature.helper.ui.key.BackPressedObserver
import com.mingyuechunqiu.agile.feature.helper.ui.key.OnKeyEventListener
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.dispatcher.ITransferPageDataDispatcher
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.dispatcher.ITransferPageDataDispatcherHelper
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       3/27/21 8:24 PM
 *      Desc:       按键事件接收者辅助类
 *                  实现IKeyEventReceiver, LifecycleEventObserver
 *      Version:    1.0
 * </pre>
 */
class KeyEventReceiverHelper(private val mPage: IKeyEventReceiverPage) : IKeyEventReceiverHelper,
    LifecycleEventObserver {

    private val mTag = KeyEventReceiverHelper::class.java.simpleName

    init {
        mPage.lifecycle.addObserver(this)
    }

    override var isForbidBackToActivity: Boolean = false
        set(value) {
            field = value
            setEnableBackPressedCallbacksWithTag(field)
        }
    override var isForbidBackToFragment: Boolean = false
        set(value) {
            field = value
            setEnableBackPressedCallbacksWithTag(field)
        }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            isForbidBackToActivity = false
            isForbidBackToFragment = false
            clearAllOnKeyEventListeners()
        }
    }

    override fun addOnKeyEventListener(listener: OnKeyEventListener): String? {
        return mPage.getBelongToKeyEventDispatcherPage()?.getKeyEventDispatcherHelper()
            ?.addOnKeyEventListener(
                mPage.getPageTag(),
                listener
            )
    }

    override fun removeOnKeyEventListener(observerId: String): Boolean {
        return mPage.getBelongToKeyEventDispatcherPage()?.getKeyEventDispatcherHelper()
            ?.removeOnKeyEventListener(
                observerId
            )
            ?: false
    }

    override fun clearAllOnKeyEventListeners() {
        mPage.getBelongToKeyEventDispatcherPage()?.getKeyEventDispatcherHelper()
            ?.removeOnKeyEventListenersWithTag(mPage.getPageTag())
    }

    override fun addBackPressedObserver(
        isEnabled: Boolean,
        operation: () -> Unit
    ): BackPressedObserver? {
        return mPage.getBelongToKeyEventDispatcherPage()?.getKeyEventDispatcherHelper()
            ?.addBackPressedObserver(mPage, isEnabled, operation)
    }

    override fun addBackPressedObserver(
        tag: String,
        isEnabled: Boolean,
        operation: () -> Unit
    ): BackPressedObserver? {
        return mPage.getBelongToKeyEventDispatcherPage()?.getKeyEventDispatcherHelper()
            ?.addBackPressedObserver(tag, mPage, isEnabled, operation)
    }

    override fun removeBackPressedObserver(observerId: String): Boolean {
        return mPage.getBelongToKeyEventDispatcherPage()?.getKeyEventDispatcherHelper()
            ?.removeBackPressedObserver(observerId) ?: false
    }

    override fun removeBackPressedObserversWithTag(): Boolean {
        return removeBackPressedObserversWithTag(mPage.getPageTag())
    }

    override fun removeBackPressedObserversWithTag(tag: String): Boolean {
        return mPage.getBelongToKeyEventDispatcherPage()?.getKeyEventDispatcherHelper()
            ?.removeBackPressedObserversWithTag(tag) ?: false
    }

    override fun setEnableBackPressedCallback(observerId: String, isEnabled: Boolean) {
        mPage.getBelongToKeyEventDispatcherPage()?.getKeyEventDispatcherHelper()
            ?.setEnableBackPressedCallback(observerId, isEnabled)
    }

    override fun setEnableBackPressedCallbacksWithTag(isEnabled: Boolean) {
        setEnableBackPressedCallbacksWithTag(mPage.getPageTag(), isEnabled)
    }

    override fun setEnableBackPressedCallbacksWithTag(tag: String, isEnabled: Boolean) {
        mPage.getBelongToKeyEventDispatcherPage()?.getKeyEventDispatcherHelper()
            ?.setEnableBackPressedCallbacksWithTag(tag, isEnabled)
    }

    /**
     * 添加按返回键通过Activity返回上一个界面
     *
     * @param helper 传递界面数据辅助类
     * @param interceptor 跳转参数拦截设置器
     * @return 添加监听成功返回true, 否则返回false
     */
    override fun listenBackKeyToPreviousPageWithActivity(
        helper: ITransferPageDataDispatcherHelper,
        interceptor: ITransferPageDataDispatcher.TransferPageDataInterceptor?
    ): Boolean {
        return addBackPressedObserver(!isForbidBackToActivity) {
            if (isForbidBackToActivity) {
                LogManagerProvider.d(
                    mTag,
                    "listenBackKeyToPreviousPageWithTargetFragment: isForbidBackToActivity"
                )
                return@addBackPressedObserver
            }
            helper.returnToPreviousPageWithActivity(interceptor)
        } != null
    }

    /**
     * 添加按返回键通过父Fragment返回上一个界面
     *
     * @param helper 传递界面数据辅助类
     * @param interceptor 跳转参数拦截设置器
     * @return 添加监听成功返回true, 否则返回false
     */
    override fun listenBackKeyToPreviousPageWithParentFragment(
        helper: ITransferPageDataDispatcherHelper,
        interceptor: ITransferPageDataDispatcher.TransferPageDataInterceptor?
    ): Boolean {
        return addBackPressedObserver(!isForbidBackToFragment) {
            if (isForbidBackToFragment) {
                LogManagerProvider.d(
                    mTag,
                    "listenBackKeyToPreviousPageWithTargetFragment: isForbidBackToFragment"
                )
                return@addBackPressedObserver
            }
            helper.returnToPreviousPageWithParentFragment(interceptor)
        } != null
    }

    /**
     * 添加按返回键通过父Fragment返回上一个界面
     *
     * @param helper 传递界面数据辅助类
     * @param interceptor 跳转参数拦截设置器
     * @return 添加监听成功返回true, 否则返回false
     */
    override fun listenBackKeyToPreviousPageWithTargetFragment(
        helper: ITransferPageDataDispatcherHelper,
        interceptor: ITransferPageDataDispatcher.TransferPageDataInterceptor?
    ): Boolean {
        return addBackPressedObserver(!isForbidBackToFragment) {
            if (isForbidBackToFragment) {
                LogManagerProvider.d(
                    mTag,
                    "listenBackKeyToPreviousPageWithTargetFragment: isForbidBackToFragment"
                )
                return@addBackPressedObserver
            }
            helper.returnToPreviousPageWithTargetFragment(interceptor)
        } != null
    }
}