package com.mingyuechunqiu.agilemvpframe.ui.bottomSheetDialogFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mingyuechunqiu.agilemvpframe.base.presenter.BaseNetPresenter;
import com.mingyuechunqiu.agilemvpframe.base.view.BaseNetView;

/**
 * <pre>
 *     author : 明月春秋
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/10/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BasePresenterBSDialogFragment<V extends BaseNetView<P>, P extends BaseNetPresenter> extends BaseBSDialogFragment {

    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        attachPresenter();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.start();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    /**
     * 绑定Presenter层
     */
    protected void attachPresenter() {
        ((V) this).setPresenter(initPresenter());
        mPresenter.attachView((V) this);
    }

    protected abstract P initPresenter();
}
