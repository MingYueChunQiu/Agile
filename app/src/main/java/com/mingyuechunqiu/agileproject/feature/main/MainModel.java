package com.mingyuechunqiu.agileproject.feature.main;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.data.bean.ParamsInfo;

import java.util.Map;

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
class MainModel extends MainContract.Model<MainContract.Listener> {

    MainModel(MainContract.Listener listener) {
        super(listener);
    }

    @Override
    protected void redoRequest(@NonNull Map<String, String> paramMap) {

    }

    @Override
    protected void doRequest(@NonNull ParamsInfo info) {

    }

    @Override
    protected void release() {

    }
}
