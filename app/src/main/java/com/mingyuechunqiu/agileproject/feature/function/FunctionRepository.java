package com.mingyuechunqiu.agileproject.feature.function;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.base.bridge.Callback;
import com.mingyuechunqiu.agile.base.bridge.Request;
import com.mingyuechunqiu.agile.base.bridge.call.Call;
import com.mingyuechunqiu.agile.base.model.repository.operation.IBaseRepositoryOperation;
import com.mingyuechunqiu.agile.base.model.repository.remote.BaseAbstractRetrofitRepository;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020/4/9 9:52 PM
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
public class FunctionRepository extends BaseAbstractRetrofitRepository {

    @Override
    protected void release() {
    }

    @Override
    public <I extends Request.IParamsInfo, T> boolean dispatchCall(@NonNull Call<I, T> call) {
        executeCall(call, new ExecuteCallCallback<FunctionParamsInfo, String, String>() {

            @Override
            public IBaseRepositoryOperation<String> executeCall(@NonNull Request<FunctionParamsInfo> request, @NonNull Callback<String> callback) {
                return null;
            }
        });
        return true;
    }
}
