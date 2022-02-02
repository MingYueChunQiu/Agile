package com.mingyuechunqiu.agileproject.feature.main;

import com.mingyuechunqiu.agileproject.feature.function.FunctionRepository;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/11/8
 *     desc   : 主界面模型层
 *              继承自MainContract.Model
 *     version: 1.0
 * </pre>
 */
class MainModel extends MainContract.Model {

    @Override
    protected void release() {

    }

    @Override
    public void initModelParts() {

    }

    @Override
    public void initRepositories() {
        addRepository(new FunctionRepository());
    }
}
