package com.mingyuechunqiu.agileproject.feature.main;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;
import com.mingyuechunqiu.agile.ui.bottomsheetdialogfragment.BasePresenterBSDialogFragment;
import com.mingyuechunqiu.agile.util.ToastUtils;

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
    protected int getInflateLayoutId() {
        return 0;
    }

    @Override
    protected void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {

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
    public void showToast(@NonNull ToastUtils.ToastConfig config) {

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
