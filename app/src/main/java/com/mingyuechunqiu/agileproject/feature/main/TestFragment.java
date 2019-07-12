package com.mingyuechunqiu.agileproject.feature.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.loading.data.LoadingDialogFragmentOption;
import com.mingyuechunqiu.agile.feature.loading.provider.LoadingDfgProviderable;
import com.mingyuechunqiu.agile.ui.fragment.BasePresenterFragment;

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
public class TestFragment extends BasePresenterFragment<MainContract.View<MainContract.Presenter>, MainContract.Presenter>
        implements MainContract.View<MainContract.Presenter> {

    @Override
    public void showLoadingDialog(@Nullable String hint, boolean cancelable) {

    }

    @Override
    public void showLoadingDialog(@Nullable LoadingDialogFragmentOption option) {

    }

    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    public void addOrShowLoadingDialog(int containerId, LoadingDialogFragmentOption option) {

    }

    @Override
    public void hideLoadingDialog() {

    }

    @Override
    public LoadingDfgProviderable getLoadingDialog() {
        return null;
    }

    @Override
    protected void releaseOnDestroyView() {

    }

    @Override
    protected void releaseOnDestroy() {

    }

    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void setPresenter(@NonNull MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showToast(@Nullable String hint) {

    }

    @Override
    public void showToast(int stringResourceId) {

    }

    @Override
    public Context getCurrentContext() {
        return getContext();
    }

    @Override
    public MainContract.Presenter initPresenter() {
        return new MainPresenter();
    }
}
