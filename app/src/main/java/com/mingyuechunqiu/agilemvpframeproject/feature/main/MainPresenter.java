package com.mingyuechunqiu.agilemvpframeproject.feature.main;

import com.mingyuechunqiu.agilemvpframe.data.bean.BaseInfo;

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
    public void start() {

    }

    @Override
    protected MainContract.Model initModel() {
        return null;
    }

    @Override
    protected <B extends BaseInfo> void requestModel(B info) {

    }

    @Override
    protected void release() {

    }

    @Override
    protected void disconnectNet() {

    }
}
