package com.mingyuechunqiu.agileproject.feature.function;

import androidx.annotation.Nullable;

/**
 * <pre>
 *      Project:    Agile
 *      Github :    https://github.com/MingYueChunQiu
 *      Author:     XiYuJie
 *      Email:      xiyujie@jinying.com
 *      Time:       2020/1/15 15:02
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
public class FunctionPresenter extends FunctionContract.Presenter<FunctionContract.View<?>, FunctionContract.Model<FunctionContract.Listener>> {

    @Override
    protected void disconnectNet() {
    }

    @Override
    protected void release() {

    }

    @Nullable
    @Override
    public FunctionContract.Model<FunctionContract.Listener> initModel() {
        mViewRef.get().doSomeTest();
        return null;
    }
}
