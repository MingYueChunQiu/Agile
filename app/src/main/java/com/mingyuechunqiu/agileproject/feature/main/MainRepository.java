package com.mingyuechunqiu.agileproject.feature.main;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.base.bridge.Request;
import com.mingyuechunqiu.agile.base.bridge.call.Call;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020-01-19 21:19
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
public class MainRepository extends MainContract.Repository {

    @Override
    protected void release() {

    }

    @Override
    public <I extends Request.IParamsInfo, T> boolean dispatchCall(@NonNull Call<I, T> call) {
        return false;
    }
}
