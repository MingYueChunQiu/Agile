package com.mingyuechunqiu.agile.base.model.dao.operation.local;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 本地操作抽象基类
 *              实现IBaseLocalDaoOperation
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractLocalDaoOperation<T> implements IBaseLocalDaoOperation<T> {

    protected T mOperation;

    public BaseAbstractLocalDaoOperation(@NonNull T operation) {
        mOperation = operation;
    }

    @Override
    public void releaseOnDetach() {
        mOperation = null;
    }
}
