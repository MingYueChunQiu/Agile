package com.mingyuechunqiu.agileproject.feature.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.data.bean.ErrorInfo;

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
class MainPresenter extends MainContract.Presenter<MainContract.View<?>, MainContract.Model<?>> {

    @Override
    protected void disconnectNet() {

    }

    @Override
    protected void release() {

    }

    @Nullable
    @Override
    public MainContract.Model<?> initModel() {
        return new MainModel(new Igeg() {
            @Override
            public void onewe() {

            }

            @Override
            public void onFailure(@NonNull ErrorInfo info) {

            }
        });
    }

    public interface Igeg extends MainContract.Listener {

        void onewe();
    }
}
