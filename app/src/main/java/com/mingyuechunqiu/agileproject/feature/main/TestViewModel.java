package com.mingyuechunqiu.agileproject.feature.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.model.BaseAbstractDataModel;
import com.mingyuechunqiu.agile.base.viewmodel.BaseAbstractDataViewModel;
import com.mingyuechunqiu.agile.frame.lifecycle.AgileLifecycle;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2022/3/6 11:03 上午
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
public class TestViewModel extends BaseAbstractDataViewModel<BaseAbstractDataModel> {
    @Override
    protected void disconnectNetwork() {

    }

    @Override
    protected void release() {

    }

    @Nullable
    @Override
    public BaseAbstractDataModel initModel() {
        return null;
    }

    @Override
    public void initBusinessEngines() {

    }

    @NonNull
    @Override
    public AgileLifecycle.LifecycleType getLifecycleType() {
        return AgileLifecycle.LifecycleType.COMPONENT;
    }

    public void testToast() {
        showToast("fwejfoewifjwe");
    }
}
