package com.mingyuechunqiu.agile.base.model.dao.local;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.model.dao.framework.callback.local.DaoLocalCallback;
import com.mingyuechunqiu.agile.base.model.dao.operation.local.IBaseLocalDaoOperation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 本地Dao层抽象基类
 *              实现IBaseLocalDao
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractLocalDao<C extends DaoLocalCallback<?>> implements IBaseLocalDao<C> {

    @Nullable
    protected C mDaoCallback;
    @Nullable
    private List<IBaseLocalDaoOperation<?>> mLocalDaoOperationList;

    public BaseAbstractLocalDao() {
    }

    public BaseAbstractLocalDao(@NonNull C callback) {
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
     * 添加本地数据操作
     *
     * @param operation 本地数据操作
     */
    protected <T> void addLocalOperation(@Nullable IBaseLocalDaoOperation<T> operation) {
        if (operation == null || operation.isCanceled()) {
            return;
        }
        if (mLocalDaoOperationList == null) {
            mLocalDaoOperationList = new ArrayList<>();
        }
        //移除已经失效了的操作
        if (mLocalDaoOperationList.size() > 0) {
            Iterator<IBaseLocalDaoOperation<?>> iterator = mLocalDaoOperationList.iterator();
            while (iterator.hasNext()) {
                IBaseLocalDaoOperation<?> o = iterator.next();
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
    protected <T> void removeLocalOperation(@Nullable IBaseLocalDaoOperation<T> operation) {
        if (operation == null || mLocalDaoOperationList == null || mLocalDaoOperationList.size() == 0) {
            return;
        }
        if (!operation.isCanceled()) {
            operation.cancel();
        }
        operation.releaseOnDetach();
        mLocalDaoOperationList.remove(operation);
    }

    protected void preRelease() {
        if (mLocalDaoOperationList == null) {
            return;
        }
        for (IBaseLocalDaoOperation<?> operation : mLocalDaoOperationList) {
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

    protected void postRelease() {
        mDaoCallback = null;
    }

    /**
     * 释放资源
     */
    protected abstract void release();
}
