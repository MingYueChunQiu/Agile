package com.mingyuechunqiu.agilemvpframe.ui.bottomsheetdialogfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mingyuechunqiu.agilemvpframe.base.presenter.BaseNetPresenter;
import com.mingyuechunqiu.agilemvpframe.base.view.IBaseNetView;

/**
 * <pre>
 *     author : 明月春秋
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/10/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BasePresenterBSDialogFragment<V extends IBaseNetView<P>, P extends BaseNetPresenter> extends BaseBSDialogFragment {

    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            getLifecycle().removeObserver(mPresenter);
            mPresenter = null;
        }
    }

    /**
     * 绑定Presenter层
     */
    @SuppressWarnings("unchecked")
    protected void attachPresenter() {
        ((V) this).setPresenter(initPresenter());
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
            getLifecycle().addObserver(mPresenter);
        }
    }

    protected abstract P initPresenter();
}
