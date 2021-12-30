package com.mingyuechunqiu.agileproject.feature.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.helper.ui.hint.ToastHelper;
import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;
import com.mingyuechunqiu.agile.framework.ui.IFragmentInflateLayoutViewCreator;
import com.mingyuechunqiu.agile.ui.diaglogfragment.BasePresenterDialogFragment;
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
public class TestDialogFragment extends BasePresenterDialogFragment<MainContract.View, MainContract.Presenter<MainContract.View, ?>>
        implements MainContract.View {

    @Override
    public void onStart() {
        super.onStart();
    }

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
    public void showToast(@NonNull ToastHelper.ToastConfig config) {

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

    @Nullable
    @Override
    protected IFragmentInflateLayoutViewCreator generateInflateLayoutViewCreator() {
        return new IFragmentInflateLayoutViewCreator.FragmentInflateLayoutViewCreatorAdapter() {
            @Override
            public int getInflateLayoutId() {
                return R.layout.activity_test;
            }

            @Nullable
            @Override
            public View getInflateLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
                return null;
            }
        };
    }

    @Override
    protected void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setWindowAnimations(R.style.PopupAnimation);
        }
    }

    @Override
    protected void initData(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Nullable
    @Override
    public MainContract.Presenter<MainContract.View, ?> initPresenter() {
        return new MainPresenter();
    }
}
