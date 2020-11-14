package com.mingyuechunqiu.agile.frame.lifecycle.dialog

import com.mingyuechunqiu.agile.ui.dialog.BaseDialog

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       11/14/20 3:53 PM
 *      Desc:       对话框生命周期回调接口
 *      Version:    1.0
 * </pre>
 */
interface DialogLifecycleCallback {

    fun onCreate(dialog: BaseDialog)

    fun onStart(dialog: BaseDialog)

    fun onStop(dialog: BaseDialog)

    fun onDismiss(dialog: BaseDialog)
}