package com.mingyuechunqiu.agilemvpframeproject.feature.main;

import java.util.Map;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/11/8
 *     desc   : 主界面模型层
 *              继承自MainContract.Model
 *     version: 1.0
 * </pre>
 */
class MainModel extends MainContract.Model<MainContract.Listener> {

    public MainModel(MainContract.Listener listener) {
        super(listener);
    }

    @Override
    protected void reRequest(Map<String, String> paramMap) {

    }

    @Override
    protected void getRequest() {

    }

    @Override
    public void destroy() {

    }
}
