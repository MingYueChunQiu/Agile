package com.mingyuechunqiu.agile.base.model.repository.local;

import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.model.repository.BaseAbstractRepository;
import com.mingyuechunqiu.agile.base.model.framework.callback.local.DaoLocalCallback;
import com.mingyuechunqiu.agile.base.model.repository.operation.local.BaseAbstractLocalRepositoryOperation;
import com.mingyuechunqiu.agile.base.model.repository.operation.local.IBaseLocalRepositoryOperationAbility;

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
public abstract class BaseAbstractLocalRepository<C extends DaoLocalCallback> extends BaseAbstractRepository<C> implements IBaseLocalRepositoryAbility {

    @Nullable
    private List<IBaseLocalRepositoryOperationAbility<?>> mLocalDaoOperationList;

    /**
     * 添加本地数据操作
     *
     * @param operation 本地数据操作
     */
    protected <T> void addLocalOperation(@Nullable IBaseLocalRepositoryOperationAbility<T> operation) {
        if (operation == null || operation.isCanceled()) {
            return;
        }
        if (mLocalDaoOperationList == null) {
            mLocalDaoOperationList = new ArrayList<>();
        }
        //移除已经失效了的操作
        if (mLocalDaoOperationList.size() > 0) {
            Iterator<IBaseLocalRepositoryOperationAbility<?>> iterator = mLocalDaoOperationList.iterator();
            while (iterator.hasNext()) {
                IBaseLocalRepositoryOperationAbility<?> o = iterator.next();
                if (o != null && o.isCanceled()) {
                    o.releaseOnDetach();
                    iterator.remove();
                }
            }
        }
        if (!mLocalDaoOperationList.contains(operation)) {
            mLocalDaoOperationList.add(operation);
        }
    }

    /**
     * 从本地数据操作集合中移除回调
     *
     * @param operation 本地数据操作
     */
    protected <T> void removeLocalOperation(@Nullable IBaseLocalRepositoryOperationAbility<T> operation) {
        if (operation == null || mLocalDaoOperationList == null || mLocalDaoOperationList.size() == 0) {
            return;
        }
        if (!operation.isCanceled()) {
            operation.cancel();
        }
        operation.releaseOnDetach();
        mLocalDaoOperationList.remove(operation);
    }

    @Override
    protected void preRelease() {
        super.preRelease();
        if (mLocalDaoOperationList == null) {
            return;
        }
        for (IBaseLocalRepositoryOperationAbility<?> operation : mLocalDaoOperationList) {
            if (operation != null) {
                if (!operation.isCanceled()) {
                    operation.cancel();
                }
                operation.releaseOnDetach();
            }
        }
        mLocalDaoOperationList.clear();
        mLocalDaoOperationList = null;
    }
}
