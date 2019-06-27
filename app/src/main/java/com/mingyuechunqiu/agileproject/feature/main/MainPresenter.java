package com.mingyuechunqiu.agileproject.feature.main;

import com.mingyuechunqiu.agile.data.bean.BaseParamsInfo;

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
    protected void requestModel(BaseParamsInfo info) {

    }

    @Override
    protected void disconnectNet() {

    }

    @Override
    public MainContract.Model initModel() {
        return null;
    }

    @Override
    public void release() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        showToast("附件为蜂窝");
    }
}
