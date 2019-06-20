package com.mingyuechunqiu.agilemvpframe.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mingyuechunqiu.agilemvpframe.base.presenter.BaseDialogPresenter;
import com.mingyuechunqiu.agilemvpframe.base.view.IBaseDialogView;
import com.mingyuechunqiu.agilemvpframe.base.view.IViewAttachPresenter;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/12
 *     desc   : 所有MVP层界面的基类
 *              继承自BaseFullScreenActivity
 *     version: 1.0
 * </pre>
 */
public abstract class BasePresenterActivity<V extends IBaseDialogView<P>, P extends BaseDialogPresenter> extends BaseFullImmerseScreenActivity
        implements IViewAttachPresenter<P> {

    protected P mPresenter;

    @Override
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {
        attachPresenter();
        super.initOnCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            getLifecycle().removeObserver(mPresenter);
            mPresenter = null;
        }
    }

    /**
     * 是否单独使用View，不绑定Presenter（默认返回false）
     *
     * @return 返回true表示只用View不绑定Presenter，否则返回false
     */
    @Override
    public boolean aloneView() {
        return false;
    }

    /**
     * 添加Present相关
     */
    @SuppressWarnings("unchecked")
    @Override
    public void attachPresenter() {
        if (aloneView()) {
            return;
        }
        ((V) this).setPresenter(initPresenter());
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
            getLifecycle().addObserver(mPresenter);
        }
    }
}
