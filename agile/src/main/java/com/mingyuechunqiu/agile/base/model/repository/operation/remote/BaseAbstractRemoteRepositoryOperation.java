package com.mingyuechunqiu.agile.base.model.repository.operation.remote;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author : xyj
 *     Github : <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 所有远程调用操作抽象父基类
 *              实现IBaseRemoteRepositoryOperation
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractRemoteRepositoryOperation<T> implements IBaseRemoteRepositoryOperation<T> {

    @NonNull
    protected final T mWork;

    public BaseAbstractRemoteRepositoryOperation(@NonNull T work) {
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
