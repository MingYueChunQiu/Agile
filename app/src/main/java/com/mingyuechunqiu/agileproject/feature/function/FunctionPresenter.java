package com.mingyuechunqiu.agileproject.feature.function;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.bridge.Callback;
import com.mingyuechunqiu.agile.base.bridge.Request;
import com.mingyuechunqiu.agile.base.bridge.Response;
import com.mingyuechunqiu.agile.base.bridge.call.Call;
import com.mingyuechunqiu.agile.data.bean.ErrorInfo;

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
public class FunctionPresenter extends FunctionContract.Presenter<FunctionContract.View, FunctionContract.Model> {

    @Override
    protected void disconnectNetwork() {
    }

    @Override
    protected void release() {
        this.dispatchCall(Call.Companion.newCall(new Request<FunctionParamsInfo>(), new Callback<String>() {
            @Override
            public void onFailure(@NonNull ErrorInfo info) {

            }

            @Override
            public void onSuccess(@NonNull Response<String> response) {

            }
        }));
    }

    @Nullable
    @Override
    public FunctionContract.Model initModel() {
        FunctionContract.View view = getView();
        if (view != null) {
            view.doSomeTest();
        }
        return null;
    }
}
