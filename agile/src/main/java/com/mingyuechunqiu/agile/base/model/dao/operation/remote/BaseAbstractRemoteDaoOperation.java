package com.mingyuechunqiu.agile.base.model.dao.operation.remote;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 网络调用操作抽象基类
 *              实现IBaseRemoteDaoOperation
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractRemoteDaoOperation<T> implements IBaseRemoteDaoOperation<T> {

    protected T mOperation;

    public BaseAbstractRemoteDaoOperation(@NonNull T operation) {
        mOperation = operation;
    }

    @Override
    public void releaseOnDetach() {
        mOperation = null;
    }

    /**
     * 释放资源
     */
    protected abstract void release();
}
