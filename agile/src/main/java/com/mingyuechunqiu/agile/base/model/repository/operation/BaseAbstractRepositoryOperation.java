package com.mingyuechunqiu.agile.base.model.repository.operation;

import androidx.annotation.NonNull;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/12/29 10:58 下午
 *      Desc:       所有调用操作抽象父基类
 *                  实现IBaseRepositoryOperation
 *      Version:    1.0
 * </pre>
 */
public abstract class BaseAbstractRepositoryOperation<T> implements IBaseRepositoryOperation<T> {

    @NonNull
    protected final T mWork;

    public BaseAbstractRepositoryOperation(@NonNull T work) {
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
