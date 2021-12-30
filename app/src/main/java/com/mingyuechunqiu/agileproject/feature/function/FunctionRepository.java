package com.mingyuechunqiu.agileproject.feature.function;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.base.bridge.call.Call;
import com.mingyuechunqiu.agile.base.model.repository.remote.BaseAbstractRetrofitRepository;
import com.mingyuechunqiu.agile.base.model.framework.callback.remote.DaoRetrofitCallback;

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
public class FunctionRepository extends BaseAbstractRetrofitRepository<DaoRetrofitCallback> {

    @Override
    protected void release() {
    }

    @Override
    public <T> boolean executeCall(@NonNull Call<T> call) {
        call.getCallback().
        return false;
    }
}
