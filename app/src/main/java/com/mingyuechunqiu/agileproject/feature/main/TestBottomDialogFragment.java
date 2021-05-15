package com.mingyuechunqiu.agileproject.feature.main;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.framework.ui.IFragmentInflateLayoutViewCreator;
import com.mingyuechunqiu.agile.ui.diaglogfragment.BaseDataPresenterDialogFragment;

import org.jetbrains.annotations.NotNull;

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
public class TestBottomDialogFragment extends BaseDataPresenterDialogFragment<MainContract.View, MainContract.Presenter<MainContract.View, ?>>
        implements MainContract.View {

    @Nullable
    @Override
    public Context getCurrentContext() {
        return getContext();
    }

    @Override
    public void setPresenter(@NonNull @NotNull MainContract.Presenter<MainContract.View, ?> presenter) {

    }

    @Nullable
    @Override
    public MainContract.Presenter<MainContract.View, ?> initPresenter() {
        return null;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public MainContract.Presenter<MainContract.View, ?> getPresenter() {
        return null;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    protected IFragmentInflateLayoutViewCreator generateInflateLayoutViewCreator() {
        return null;
    }

    @Override
    protected void initView(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void releaseOnDestroyView() {

    }

    @Override
    protected void releaseOnDestroy() {

    }
}
