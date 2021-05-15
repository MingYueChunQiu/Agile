package com.mingyuechunqiu.agileproject.feature.function;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;
import com.mingyuechunqiu.agile.framework.ui.IActivityInflateLayoutViewCreator;
import com.mingyuechunqiu.agile.ui.activity.BaseDataPresenterActivity;
import com.mingyuechunqiu.agile.feature.helper.ui.hint.ToastHelper;

/**
 * <pre>
 *      Project:    Agile
 *      Github :    https://github.com/MingYueChunQiu
 *      Author:     XiYuJie
 *      Email:      xiyujie@jinying.com
 *      Time:       2020/1/15 15:05
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
public class FunctionActivity extends BaseDataPresenterActivity<FunctionContract.View, FunctionContract.Presenter<FunctionContract.View, ?>>
        implements FunctionContract.View {

    @Override
    public void showToast(@NonNull ToastHelper.ToastConfig config) {

    }

    @Nullable
    @Override
    public Context getCurrentContext() {
        return null;
    }

    @NonNull
    @Override
    public IStatusViewManager getStatusViewManager() {
        return super.getStatusViewManager();
    }

    @NonNull
    @Override
    protected IActivityInflateLayoutViewCreator generateInflateLayoutViewCreator() {
        return new IActivityInflateLayoutViewCreator.ActivityInflateLayoutViewCreatorAdapter() {
            @Override
            public int getInflateLayoutId() {
                return 0;
            }

            @Nullable
            @Override
            public View getInflateLayoutView() {
                return null;
            }
        };
    }

    @Override
    protected void release() {

    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void doSomeTest() {

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public FunctionContract.Presenter<FunctionContract.View, ?> initPresenter() {
        return new FunctionPresenter();
    }
}
