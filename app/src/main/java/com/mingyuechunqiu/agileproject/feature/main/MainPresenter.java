package com.mingyuechunqiu.agileproject.feature.main;

import androidx.annotation.Nullable;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/11/8
 *     desc   : 主界面MVP中P层
 *              继承自MainContract.Presenter
 *     version: 1.0
 * </pre>
 */
class MainPresenter extends MainContract.Presenter<MainContract.View, MainContract.Model> {

    @Override
    protected void disconnectNetwork() {

    }

    @Override
    protected void release() {

    }

    @Nullable
    @Override
    public MainContract.Model initModel() {
        return new MainModel();
    }

    @Override
    public void initBusinessEngines() {

    }
}
