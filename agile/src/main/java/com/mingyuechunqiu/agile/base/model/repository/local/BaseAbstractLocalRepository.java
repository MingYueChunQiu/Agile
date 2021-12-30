package com.mingyuechunqiu.agile.base.model.repository.local;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.model.repository.BaseAbstractRepository;
import com.mingyuechunqiu.agile.base.model.repository.operation.local.IBaseLocalRepositoryOperation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 本地Repository层抽象基类
 *              实现IBaseLocalRepository
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractLocalRepository extends BaseAbstractRepository implements IBaseLocalRepositoryAbility {

    @NonNull
    protected final String TAG_FAILURE = getClass().getSimpleName() + "_failure";//打印错误日志标签
    @Nullable
    private List<IBaseLocalRepositoryOperation<?>> mLocalRepositoryOperationList;

    /**
     * 添加本地数据操作
     *
     * @param operation 本地数据操作
     */
    protected <T> void addLocalOperation(@Nullable IBaseLocalRepositoryOperation<T> operation) {
        if (operation == null || operation.isCanceled()) {
            return;
        }
        if (mLocalRepositoryOperationList == null) {
            mLocalRepositoryOperationList = new ArrayList<>();
        }
        //移除已经失效了的操作
        if (mLocalRepositoryOperationList.size() > 0) {
            Iterator<IBaseLocalRepositoryOperation<?>> iterator = mLocalRepositoryOperationList.iterator();
            while (iterator.hasNext()) {
                IBaseLocalRepositoryOperation<?> o = iterator.next();
                if (o != null && o.isCanceled()) {
                    o.releaseOnDetach();
                    iterator.remove();
                }
            }
        }
        if (!mLocalRepositoryOperationList.contains(operation)) {
            mLocalRepositoryOperationList.add(operation);
        }
    }

    /**
     * 从本地数据操作集合中移除回调
     *
     * @param operation 本地数据操作
     */
    protected <T> void removeLocalOperation(@Nullable IBaseLocalRepositoryOperation<T> operation) {
        if (operation == null || mLocalRepositoryOperationList == null || mLocalRepositoryOperationList.size() == 0) {
            return;
        }
        if (!operation.isCanceled()) {
            operation.cancel();
        }
        operation.releaseOnDetach();
        mLocalRepositoryOperationList.remove(operation);
    }

    @Override
    protected void preRelease() {
        super.preRelease();
        if (mLocalRepositoryOperationList == null) {
            return;
        }
        for (IBaseLocalRepositoryOperation<?> operation : mLocalRepositoryOperationList) {
            if (operation != null) {
                if (!operation.isCanceled()) {
                    operation.cancel();
                }
                operation.releaseOnDetach();
            }
        }
        mLocalRepositoryOperationList.clear();
        mLocalRepositoryOperationList = null;
    }
}
