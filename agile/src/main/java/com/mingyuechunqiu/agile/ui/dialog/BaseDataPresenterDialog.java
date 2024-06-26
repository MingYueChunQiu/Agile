package com.mingyuechunqiu.agile.ui.dialog;

import android.content.Context;

import com.mingyuechunqiu.agile.base.model.BaseAbstractDataModel;
import com.mingyuechunqiu.agile.base.presenter.BaseAbstractDataPresenter;
import com.mingyuechunqiu.agile.base.view.IBaseDataView;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *      Email:      xiyujieit@163.com
 *      Time:       2020/4/7 9:31 PM
 *      Desc:       所有MVP层具有网络功能的对话框的基类
 *                  继承自BaseAbstractPresenterDialog
 *      Version:    1.0
 * </pre>
 */
public abstract class BaseDataPresenterDialog<V extends IBaseDataView, P extends BaseAbstractDataPresenter<V, ? extends BaseAbstractDataModel>> extends BasePresenterDialog<V, P> {

    public BaseDataPresenterDialog(Context context) {
        super(context);
    }

    public BaseDataPresenterDialog(Context context, int theme) {
        super(context, theme);
    }

    protected BaseDataPresenterDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
