package com.mingyuechunqiu.agileproject.feature.function;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.base.model.dao.framework.callback.remote.DaoRetrofitCallback;
import com.mingyuechunqiu.agile.base.model.dao.remote.BaseAbstractRetrofitDao;

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
public class FunctionDao extends BaseAbstractRetrofitDao<DaoRetrofitCallback<? extends IFunctionListener>> {

    public FunctionDao(@NonNull DaoRetrofitCallback<? extends IFunctionListener> callback) {
        super(callback);
    }

    @Override
    protected void release() {

    }
}
