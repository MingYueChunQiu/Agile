package com.mingyuechunqiu.agile.base.model.dao.remote;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.base.model.dao.framework.callback.remote.DaoNetworkCallback;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 网络Dao层抽象基类
 *              继承自BaseAbstractRemoteDao
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractNetworkDao<C extends DaoNetworkCallback<?>> extends BaseAbstractRemoteDao<C> {

    public BaseAbstractNetworkDao() {
    }

    public BaseAbstractNetworkDao(@NonNull C callback) {
        super(callback);
    }
}
