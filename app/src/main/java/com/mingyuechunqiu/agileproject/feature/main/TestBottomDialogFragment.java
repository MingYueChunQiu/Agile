package com.mingyuechunqiu.agileproject.feature.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;
import com.mingyuechunqiu.agile.ui.bottomsheetdialogfragment.BasePresenterBSDialogFragment;

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
public class TestBottomDialogFragment extends BasePresenterBSDialogFragment<MainContract.View<MainContract.Presenter>, MainContract.Presenter>
        implements MainContract.View<MainContract.Presenter> {
    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    protected void releaseOnDestroyView() {

    }

    @Override
    protected void releaseOnDestroy() {

    }

    @NonNull
    @Override
    public IStatusViewManager getStatusViewManager() {
        return super.getStatusViewManager();
    }

    @Override
    public void dismissStatusView() {
        super.dismissStatusView();
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
        return null;
    }

    @Override
    public MainContract.Presenter initPresenter() {
        return new MainPresenter();
    }
}
