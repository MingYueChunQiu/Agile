package com.mingyuechunqiu.agileproject.feature.function;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;
import com.mingyuechunqiu.agile.ui.activity.BaseNetPresenterActivity;
import com.mingyuechunqiu.agile.util.ToastUtils;

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
public class FunctionActivity extends BaseNetPresenterActivity<FunctionContract.View<FunctionContract.Presenter<?, ?>>, FunctionContract.Presenter<?, ?>>
        implements FunctionContract.View<FunctionContract.Presenter<?, ?>> {

    @Override
    public void setPresenter(@NonNull FunctionContract.Presenter<?, ?> presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showToast(@NonNull ToastUtils.ToastConfig config) {

    }

    @Nullable
    @Override
    public Context getCurrentContext() {
        return null;
    }

    @Nullable
    @Override
    public FunctionContract.Presenter<?, ?> initPresenter() {
        return new FunctionPresenter();
    }

    @Override
    public IStatusViewManager getStatusViewManager() {
        return null;
    }

    @Override
    protected void release() {

    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void dismissStatusView() {

    }

    @Override
    public void doSomeTest() {

    }
}
