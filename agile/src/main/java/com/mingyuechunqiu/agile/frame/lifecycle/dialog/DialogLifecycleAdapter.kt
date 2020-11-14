package com.mingyuechunqiu.agile.frame.lifecycle.dialog

import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider
import com.mingyuechunqiu.agile.ui.dialog.BaseDialog

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       11/14/20 3:55 PM
 *      Desc:       对话框生命周期回调适配器（提供默认空方法实现）
 *                  实现DialogLifecycleCallback
 *      Version:    1.0
 * </pre>
 */
open class DialogLifecycleAdapter : DialogLifecycleCallback {

    override fun onCreate(dialog: BaseDialog) {
        LogManagerProvider.i("DialogLifecycleAdapter", "onCreate:${dialog::class.java.simpleName}")
    }

    override fun onStart(dialog: BaseDialog) {
        LogManagerProvider.i("DialogLifecycleAdapter", "onStart:${dialog::class.java.simpleName}")
    }

    override fun onStop(dialog: BaseDialog) {
        LogManagerProvider.i("DialogLifecycleAdapter", "onStop:${dialog::class.java.simpleName}")
    }

    override fun onDismiss(dialog: BaseDialog) {
        LogManagerProvider.i("DialogLifecycleAdapter", "onDismiss:${dialog::class.java.simpleName}")
    }
}