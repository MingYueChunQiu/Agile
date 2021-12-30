package com.mingyuechunqiu.agile.base.model.repository.operation.local;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 本地操作抽象基类
 *              实现IBaseLocalRepositoryOperation
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractLocalRepositoryOperation<T> implements IBaseLocalRepositoryOperation<T> {

    @NonNull
    protected final T mWork;

    public BaseAbstractLocalRepositoryOperation(@NonNull T work) {
        mWork = work;
    }

    @Override
    public void releaseOnDetach() {
        cancel();
        release();
    }

    @NonNull
    @Override
    public T getWork() {
        return mWork;
    }

    protected abstract void release();
}
