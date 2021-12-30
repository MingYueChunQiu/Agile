package com.mingyuechunqiu.agile.ui.dialog

import android.content.Context
import android.content.DialogInterface

/**
 * <pre>
 *     author : MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/16
 *     desc   : 所有具有数据ViewModel层功能的基类
 *              继承自BaseAbstractViewModelDialog
 *     version: 1.0
 * </pre>
 */
abstract class BaseDataViewModelDialog : BaseViewModelDialog {

    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    protected constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)
}