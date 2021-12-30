package com.mingyuechunqiu.agile.ui.fragment;

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
 *      Time:       2021/5/9 7:10 下午
 *      Desc:       P层功能的Fragment的基类
 *                  继承自BaseFragment
 *      Version:    1.0
 * </pre>
 */
public abstract class BasePresenterFragment<V extends IBaseView, P extends IBasePresenter<V, ? extends BaseAbstractModel>> extends BaseFragment implements IViewAttachPresenter<P> {

    @Nullable
    private P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            getLifecycle().removeObserver(mPresenter);
            //不能放在onDestroyView中执行，因为像输入框失去焦点这种事件会在onDestroyView之后才被调用
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
        setPresenter(presenter);
        if (!(this instanceof IBaseView)) {
            throw new IllegalStateException("Current Fragment must implements IBaseView or it subclass");
        }
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
