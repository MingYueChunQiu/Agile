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
    protected void requestModel(BaseInfo info) {

    }

    @Override
    protected void disconnectNet() {

    }

    @Override
    public void start() {

    }

    @Override
    public MainContract.Model initModel() {
        return null;
    }

    @Override
    public void release() {

    }
}
