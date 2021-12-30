package com.mingyuechunqiu.agile.base.model.repository.remote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.model.repository.BaseAbstractRepository;
import com.mingyuechunqiu.agile.base.model.repository.operation.remote.IBaseRemoteRepositoryOperation;
import com.mingyuechunqiu.agile.base.model.framework.callback.remote.DaoRemoteCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 远程Repository层抽象基类
 *              实现IBaseRemoteRepository
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractRemoteRepository<C extends DaoRemoteCallback> extends BaseAbstractRepository<C> implements IBaseRemoteRepositoryAbility {

    @NonNull
    protected final String TAG_FAILURE = getClass().getSimpleName() + "_failure";//打印错误日志标签
    @Nullable
    private List<IBaseRemoteRepositoryOperation<?>> mRemoteRepositoryOperationList;

    /**
     * 添加远程操作
     *
     * @param operation 远程操作
     */
    protected <T> void addRemoteOperation(@Nullable IBaseRemoteRepositoryOperation<T> operation) {
        if (operation == null || operation.isCanceled()) {
            return;
        }
        if (mRemoteRepositoryOperationList == null) {
            mRemoteRepositoryOperationList = new ArrayList<>();
        }
        //移除已经失效了的操作
        if (mRemoteRepositoryOperationList.size() > 0) {
            Iterator<IBaseRemoteRepositoryOperation<?>> iterator = mRemoteRepositoryOperationList.iterator();
            while (iterator.hasNext()) {
                IBaseRemoteRepositoryOperation<?> o = iterator.next();
                if (o != null && o.isCanceled()) {
                    o.releaseOnDetach();
                    iterator.remove();
                }
            }
        }
        if (!mRemoteRepositoryOperationList.contains(operation)) {
            mRemoteRepositoryOperationList.add(operation);
        }
    }

    /**
     * 从远程操作集合中移除回调
     *
     * @param operation 远程操作
     */
    protected <T> void removeRemoteOperation(@Nullable IBaseRemoteRepositoryOperation<T> operation) {
        if (operation == null || mRemoteRepositoryOperationList == null || mRemoteRepositoryOperationList.size() == 0) {
            return;
        }
        if (!operation.isCanceled()) {
            operation.cancel();
        }
        operation.releaseOnDetach();
        mRemoteRepositoryOperationList.remove(operation);
    }

    @Override
    protected void preRelease() {
        super.preRelease();
        if (mRemoteRepositoryOperationList == null) {
            return;
        }
        for (IBaseRemoteRepositoryOperation<?> operation : mRemoteRepositoryOperationList) {
            if (operation != null) {
                if (!operation.isCanceled()) {
                    operation.cancel();
                }
                operation.releaseOnDetach();
            }
        }
        mRemoteRepositoryOperationList.clear();
        mRemoteRepositoryOperationList = null;
    }
}
