package com.mingyuechunqiu.agile.ui.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.mingyuechunqiu.agile.base.model.BaseAbstractModel;
import com.mingyuechunqiu.agile.base.presenter.AgilePresenterViewModel;
import com.mingyuechunqiu.agile.base.presenter.IBasePresenter;
import com.mingyuechunqiu.agile.base.view.IBaseView;
import com.mingyuechunqiu.agile.base.view.IViewAttachPresenter;

import org.jetbrains.annotations.NotNull;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/9 2:08 下午
 *      Desc:       所有具有P层功能的Activity层基类
 *                  继承自BaseFullImmerseScreenActivity
 *      Version:    1.0
 * </pre>
 */
public abstract class BasePresenterActivity<V extends IBaseView, P extends IBasePresenter<V, ? extends BaseAbstractModel>> extends BaseFullImmerseScreenActivity implements IViewAttachPresenter<P> {

    //要在Activity附加到Application后赋值
    @Nullable
    private AgilePresenterViewModel<P> mAgilePresenterViewModel = null;

    @Override
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {
        attachPresenter();
        super.initOnCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        P presenter = getAgilePresenterViewModel().getPresenter();
        if (presenter != null) {
            getLifecycle().removeObserver(presenter);
            getAgilePresenterViewModel().setPresenter(null);
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
            throw new IllegalStateException("Current Activity must implements IBaseView or it subclass");
        }
        setPresenter(presenter);
        P finalPresenter = getAgilePresenterViewModel().getPresenter();
        if (finalPresenter != null) {
            finalPresenter.attachView((V) this);
            getLifecycle().addObserver(finalPresenter);
        }
    }

    @Override
    public void setPresenter(@NonNull @NotNull P presenter) {
        getAgilePresenterViewModel().setPresenter(presenter);
    }

    @Nullable
    @Override
    public P getPresenter() {
        return getAgilePresenterViewModel().getPresenter();
    }

    @SuppressWarnings("unchecked")
    private AgilePresenterViewModel<P> getAgilePresenterViewModel() {
        if (mAgilePresenterViewModel == null) {
            mAgilePresenterViewModel = new ViewModelProvider(this).get(AgilePresenterViewModel.class);
        }
        return mAgilePresenterViewModel;
    }
}
