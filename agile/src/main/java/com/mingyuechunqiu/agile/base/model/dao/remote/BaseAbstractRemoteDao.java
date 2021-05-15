package com.mingyuechunqiu.agile.base.model.dao.remote;

import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.model.dao.BaseAbstractDao;
import com.mingyuechunqiu.agile.base.model.dao.operation.remote.IBaseRemoteDaoOperation;
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
 *     desc   : 远程Dao层抽象基类
 *              实现IBaseRemoteDao
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractRemoteDao<C extends DaoRemoteCallback> extends BaseAbstractDao<C> implements IBaseRemoteDao {

    @Nullable
    private List<IBaseRemoteDaoOperation<?>> mRemoteDaoOperationList;

    /**
     * 添加远程操作
     *
     * @param operation 远程操作
     */
    protected <T> void addRemoteOperation(@Nullable IBaseRemoteDaoOperation<T> operation) {
        if (operation == null || operation.isCanceled()) {
            return;
        }
        if (mRemoteDaoOperationList == null) {
            mRemoteDaoOperationList = new ArrayList<>();
        }
        //移除已经失效了的操作
        if (mRemoteDaoOperationList.size() > 0) {
            Iterator<IBaseRemoteDaoOperation<?>> iterator = mRemoteDaoOperationList.iterator();
            while (iterator.hasNext()) {
                IBaseRemoteDaoOperation<?> o = iterator.next();
                if (o != null && o.isCanceled()) {
                    o.releaseOnDetach();
                    iterator.remove();
                }
            }
        }
        if (!mRemoteDaoOperationList.contains(operation)) {
            mRemoteDaoOperationList.add(operation);
        }
    }

    /**
     * 从远程操作集合中移除回调
     *
     * @param operation 远程操作
     */
    protected <T> void removeRemoteOperation(@Nullable IBaseRemoteDaoOperation<T> operation) {
        if (operation == null || mRemoteDaoOperationList == null || mRemoteDaoOperationList.size() == 0) {
            return;
        }
        if (!operation.isCanceled()) {
            operation.cancel();
        }
        operation.releaseOnDetach();
        mRemoteDaoOperationList.remove(operation);
    }

    @Override
    protected void preRelease() {
        super.preRelease();
        if (mRemoteDaoOperationList == null) {
            return;
        }
        for (IBaseRemoteDaoOperation<?> operation : mRemoteDaoOperationList) {
            if (operation != null) {
                if (!operation.isCanceled()) {
                    operation.cancel();
                }
                operation.releaseOnDetach();
            }
        }
        mRemoteDaoOperationList.clear();
        mRemoteDaoOperationList = null;
    }
}
