package com.mingyuechunqiu.agile.ui.dialog;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.model.BaseAbstractModel;
import com.mingyuechunqiu.agile.base.presenter.IBasePresenter;
import com.mingyuechunqiu.agile.base.view.IBaseView;
import com.mingyuechunqiu.agile.base.view.IViewAttachPresenter;

import org.jetbrains.annotations.NotNull;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/13 12:16 上午
 *      Desc:       P层功能的Fragment的基类
 *                  继承自BaseDialog
 *      Version:    1.0
 * </pre>
 */
public abstract class BasePresenterDialog<V extends IBaseView, P extends IBasePresenter<V, ? extends BaseAbstractModel>> extends BaseDialog implements IViewAttachPresenter<P> {

    @Nullable
    private P mPresenter;

    public BasePresenterDialog(Context context) {
        super(context);
    }

    public BasePresenterDialog(Context context, int theme) {
        super(context, theme);
    }

    protected BasePresenterDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();
    }

    @Override
    protected void releaseOnDetach() {
        super.releaseOnDetach();
        if (mPresenter != null) {
            getLifecycle().removeObserver(mPresenter);
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
        if (!(this instanceof IBaseView)) {
            throw new IllegalStateException("Current Dialog must implements IBaseView or it subclass");
        }
        setPresenter(presenter);
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
            getLifecycle().addObserver(mPresenter);
        }
    }

    @Override
    public void setPresenter(@NonNull @NotNull P presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public P getPresenter() {
        return mPresenter;
    }
}
