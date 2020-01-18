package com.mingyuechunqiu.agile.base.model.dao.remote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.model.dao.framework.callback.remote.DaoRemoteCallback;
import com.mingyuechunqiu.agile.base.model.dao.operation.remote.IBaseRemoteDaoOperation;

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
public abstract class BaseAbstractRemoteDao<C extends DaoRemoteCallback<?>> implements IBaseRemoteDao<C> {

    @Nullable
    protected C mDaoCallback;
    @Nullable
    private List<IBaseRemoteDaoOperation> mRemoteDaoOperationList;

    public BaseAbstractRemoteDao() {
    }

    public BaseAbstractRemoteDao(@NonNull C callback) {
        attachDaoCallback(callback);
    }

    @Override
    public void attachDaoCallback(@NonNull C callback) {
        mDaoCallback = callback;
        onAttachDaoCallback(callback);
    }

    @Override
    public void releaseOnDetach() {
        preRelease();
        release();
        postRelease();
    }

    /**
     * 当和Model层Dao回调关联时调用
     *
     * @param callback 回调对象
     */
    protected void onAttachDaoCallback(@NonNull C callback) {
    }

    /**
     * 添加远程操作
     *
     * @param operation 远程操作
     */
    protected void addRemoteOperation(@Nullable IBaseRemoteDaoOperation operation) {
        if (operation == null || operation.isCanceled()) {
            return;
        }
        if (mRemoteDaoOperationList == null) {
            mRemoteDaoOperationList = new ArrayList<>();
        }
        //移除已经失效了的操作
        if (mRemoteDaoOperationList.size() > 0) {
            Iterator<IBaseRemoteDaoOperation> iterator = mRemoteDaoOperationList.iterator();
            while (iterator.hasNext()) {
                IBaseRemoteDaoOperation o = iterator.next();
                if (o != null && o.isCanceled()) {
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
    protected void removeRemoteOperation(@Nullable IBaseRemoteDaoOperation operation) {
        if (operation == null || mRemoteDaoOperationList == null || mRemoteDaoOperationList.size() == 0) {
            return;
        }
        if (!operation.isCanceled()) {
            operation.cancel();
        }
        mRemoteDaoOperationList.remove(operation);
    }

    protected void preRelease() {
        if (mRemoteDaoOperationList == null) {
            return;
        }
        for (IBaseRemoteDaoOperation operation : mRemoteDaoOperationList) {
            if (operation != null && !operation.isCanceled()) {
                operation.cancel();
            }
        }
        mRemoteDaoOperationList.clear();
        mRemoteDaoOperationList = null;
    }

    protected void postRelease() {
        mDaoCallback = null;
    }

    /**
     * 释放资源
     */
    protected abstract void release();
}
