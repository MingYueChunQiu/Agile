package com.mingyuechunqiu.agile.base.model.dao.operation.remote;

import androidx.annotation.NonNull;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020-01-18 21:37
 *      Desc:       所有网络调用操作抽象父基类
 *                  继承自BaseAbstractRemoteDaoOperation
 *      Version:    1.0
 * </pre>
 */
public abstract class BaseAbstractNetworkDaoOperation<T> extends BaseAbstractRemoteDaoOperation<T> {

    public BaseAbstractNetworkDaoOperation(@NonNull T operation) {
        super(operation);
    }
}
