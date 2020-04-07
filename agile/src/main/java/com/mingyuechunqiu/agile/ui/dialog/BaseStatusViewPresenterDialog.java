package com.mingyuechunqiu.agile.ui.dialog;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.presenter.BaseAbstractStatusViewPresenter;
import com.mingyuechunqiu.agile.base.view.IBaseStatusView;
import com.mingyuechunqiu.agile.base.view.IViewAttachPresenter;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020/4/7 9:28 PM
 *      Desc:       所有MVP层具有状态视图功能的对话框的基类
 *                  继承自BaseDialog
 *      Version:    1.0
 * </pre>
 */
public abstract class BaseStatusViewPresenterDialog<V extends IBaseStatusView<P>, P extends BaseAbstractStatusViewPresenter> extends BaseDialog
        implements IViewAttachPresenter<P> {

    @Nullable
    protected P mPresenter;

    public BaseStatusViewPresenterDialog(Context context) {
        super(context);
    }

    public BaseStatusViewPresenterDialog(Context context, int theme) {
        super(context, theme);
    }

    protected BaseStatusViewPresenterDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.callOnStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.callOnStop();
        }
    }

    @Override
    protected void releaseOnDetach() {
        super.releaseOnDetach();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    /**
     * 关联添加Present
     */
    @Override
    public void attachPresenter() {
        P presenter = initPresenter();
        if (presenter == null) {
            return;
        }
        bindPresenter(presenter);
    }

    /**
     * 绑定Presenter
     *
     * @param presenter 外部传入的Presenter
     */
    @SuppressWarnings("unchecked")
    @Override
    public void bindPresenter(@NonNull P presenter) {
        ((V) this).setPresenter(presenter);
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }
}
