package com.mingyuechunqiu.agileproject.feature.main;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.base.bridge.call.Call;
import com.mingyuechunqiu.agile.base.model.framework.callback.remote.DaoRetrofitCallback;

import org.jetbrains.annotations.NotNull;

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
public class MainDao extends MainContract.Dao<DaoRetrofitCallback> {

    @Override
    protected void release() {

    }

    @Override
    public <T> boolean executeCall(@NonNull @NotNull Call<T> call) {
        return false;
    }
}
