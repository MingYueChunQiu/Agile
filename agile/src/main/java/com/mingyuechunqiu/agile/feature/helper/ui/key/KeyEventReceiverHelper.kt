package com.mingyuechunqiu.agile.feature.helper.ui.key

import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.ITransferPageDataDispatcherHelper
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
class KeyEventReceiverHelper(private val page: IKeyEventReceiverPage) : IKeyEventReceiverHelper, LifecycleEventObserver {

    private var mBackPressedObserver: IKeyEventReceiverHelper.BackPressedObserver? = null

    init {
        page.lifecycle.addObserver(this)
    }

    override var isForbidBackToActivity: Boolean = false
        set(value) {
            field = value
            setEnableBackPressedCallback(field)
        }
    override var isForbidBackToFragment: Boolean = false
        set(value) {
            field = value
            setEnableBackPressedCallback(field)
        }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            isForbidBackToActivity = false
            isForbidBackToFragment = false
            clearAllOnKeyEventListeners()
        }
    }

    override fun addOnKeyEventListener(listener: IKeyEventReceiverHelper.OnKeyEventListener): String? {
        return (page.getOwnedActivity() as? IKeyEventDispatcher)?.addOnKeyEventListener(page.getPageTag(), listener)
    }

    override fun removeOnKeyEventListener(observerId: String): Boolean {
        return (page.getOwnedActivity() as? IKeyEventDispatcher)?.removeOnKeyEventListener(observerId)
                ?: false
    }

    override fun clearAllOnKeyEventListeners() {
        (page.getOwnedActivity() as? IKeyEventDispatcher)?.removeOnKeyEventListenersWithTag(page.getPageTag())
    }

    override fun getBackPressedObserver(): IKeyEventReceiverHelper.BackPressedObserver? {
        return mBackPressedObserver
    }

    /**
     * 设置返回回调是否生效
     * @param enabled true生效，否则false
     */
    override fun setEnableBackPressedCallback(enabled: Boolean) {
        mBackPressedObserver?.callback?.isEnabled = enabled
    }

    /**
     * 添加按返回键通过Activity返回上一个界面
     *
     * @param helper 传递界面数据辅助类
     * @param interceptor 跳转参数拦截设置器
     * @return 添加监听成功返回true, 否则返回false
     */
    override fun listenBackKeyToPreviousPageWithActivity(helper: ITransferPageDataDispatcherHelper, interceptor: ITransferPageDataDispatcherHelper.TransferPageDataInterceptor?): Boolean {
        return listenBackKeyToPreviousPage {
            if (isForbidBackToActivity) {
                LogManagerProvider.d("KeyEventReceiverHelper", "listenBackKeyToPreviousPageWithTargetFragment: isForbidBackToActivity")
                return@listenBackKeyToPreviousPage
            }
            helper.returnToPreviousPageWithActivity(interceptor)
        }
    }

    /**
     * 添加按返回键通过父Fragment返回上一个界面
     *
     * @param helper 传递界面数据辅助类
     * @param interceptor 跳转参数拦截设置器
     * @return 添加监听成功返回true, 否则返回false
     */
    override fun listenBackKeyToPreviousPageWithParentFragment(helper: ITransferPageDataDispatcherHelper, interceptor: ITransferPageDataDispatcherHelper.TransferPageDataInterceptor?): Boolean {
        return listenBackKeyToPreviousPage {
            if (isForbidBackToFragment) {
                LogManagerProvider.d("KeyEventReceiverHelper", "listenBackKeyToPreviousPageWithTargetFragment: isForbidBackToFragment")
                return@listenBackKeyToPreviousPage
            }
            helper.returnToPreviousPageWithParentFragment(interceptor)
        }
    }

    /**
     * 添加按返回键通过父Fragment返回上一个界面
     *
     * @param helper 传递界面数据辅助类
     * @param interceptor 跳转参数拦截设置器
     * @return 添加监听成功返回true, 否则返回false
     */
    override fun listenBackKeyToPreviousPageWithTargetFragment(helper: ITransferPageDataDispatcherHelper, interceptor: ITransferPageDataDispatcherHelper.TransferPageDataInterceptor?): Boolean {
        return listenBackKeyToPreviousPage {
            if (isForbidBackToFragment) {
                LogManagerProvider.d("KeyEventReceiverHelper", "listenBackKeyToPreviousPageWithTargetFragment: isForbidBackToFragment")
                return@listenBackKeyToPreviousPage
            }
            helper.returnToPreviousPageWithTargetFragment(interceptor)
        }
    }

    /**
     * 添加按返回键通过Activity返回上一个界面
     *
     * @param operation 返回操作
     * @return 添加监听成功返回true, 否则返回false
     */
    private fun listenBackKeyToPreviousPage(operation: (() -> Unit)): Boolean {
        return page.getOwnedActivity()?.onBackPressedDispatcher?.let {
            val observer = IKeyEventReceiverHelper.BackPressedObserver(object : OnBackPressedCallback(isForbidBackToActivity) {
                override fun handleOnBackPressed() {
                    if (checkPageStateIsActive()) {
                        LogManagerProvider.d("KeyEventReceiverHelper", "listenBackKeyToPreviousPage: page state is active")
                        operation()
                    } else {
                        LogManagerProvider.d("KeyEventReceiverHelper", "listenBackKeyToPreviousPage: page state is inactive")
                    }
                }
            })
            mBackPressedObserver = observer
            it.addCallback(observer.callback)
            true
        } ?: false
    }

    private fun checkPageStateIsActive(): Boolean {
        return page.lifecycle.currentState >= Lifecycle.State.RESUMED
    }
}