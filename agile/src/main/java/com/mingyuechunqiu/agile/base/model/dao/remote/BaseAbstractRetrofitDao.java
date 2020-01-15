package com.mingyuechunqiu.agile.base.model.dao.remote;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.base.model.dao.framework.callback.DaoRetrofitCallback;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : Retrofit网络Dao抽象基类
 *              继承自BaseAbstractNetworkDao
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractRetrofitDao<C extends DaoRetrofitCallback<?>> extends BaseAbstractNetworkDao<C> {

    public BaseAbstractRetrofitDao() {
    }

    public BaseAbstractRetrofitDao(@NonNull C callback) {
        super(callback);
    }
}
