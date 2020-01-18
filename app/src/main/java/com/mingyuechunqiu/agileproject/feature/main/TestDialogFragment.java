package com.mingyuechunqiu.agileproject.feature.main;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;
import com.mingyuechunqiu.agile.ui.diaglogfragment.BaseStatusViewPresenterDialogFragment;
import com.mingyuechunqiu.agile.util.ToastUtils;
import com.mingyuechunqiu.agileproject.R;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/5/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class TestDialogFragment extends BaseStatusViewPresenterDialogFragment<MainContract.View<MainContract.Presenter<?, ?>>, MainContract.Presenter<?, ?>>
        implements MainContract.View<MainContract.Presenter<?, ?>> {

    @Override
    public void showLoadingStatusView(@Nullable String hint, boolean cancelable) {

    }

    @Override
    protected void releaseOnDestroyView() {

    }

    @Override
    protected void releaseOnDestroy() {

    }

    @Override
    public void setPresenter(@NonNull MainContract.Presenter<?, ?> presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showToast(@NonNull ToastUtils.ToastConfig config) {

    }

    @Override
    public Context getCurrentContext() {
        return null;
    }

    @NonNull
    @Override
    public IStatusViewManager getStatusViewManager() {
        return super.getStatusViewManager();
    }

    @Override
    protected int getInflateLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setWindowAnimations(R.style.PopupAnimation);
        }
    }

    @Override
    public void dismissStatusView() {

    }

    @Nullable
    @Override
    public MainContract.Presenter<?, ?> initPresenter() {
        return new MainPresenter();
    }
}
