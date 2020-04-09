package com.mingyuechunqiu.agileproject.feature.function;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.data.bean.ParamsInfo;

import java.util.Map;

/**
 * <pre>
 *      Project:    Agile
 *      Github :    https://github.com/MingYueChunQiu
 *      Author:     XiYuJie
 *      Email:      xiyujie@jinying.com
 *      Time:       2020/1/15 15:00
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
public class FunctionModel extends FunctionContract.Model<FunctionContract.Listener> {

    private FunctionContract.Dao<?> mDao = null;

    FunctionModel(FunctionContract.Listener listener) {
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
