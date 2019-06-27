package com.mingyuechunqiu.agile.base.model.part.dao.local;

import android.support.annotation.NonNull;

import com.mingyuechunqiu.agile.base.model.part.dao.IBaseDao;
import com.mingyuechunqiu.agile.base.model.part.dao.operation.local.IBaseLocalDaoOperation;

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
public abstract class BaseAbstractLocalDao<C extends IBaseDao.ModelDaoCallback> implements IBaseLocalDao<C> {

    protected C mCallback;

    protected List<IBaseLocalDaoOperation> mLocalDaoOperationList;

    public BaseAbstractLocalDao() {
    }

    public BaseAbstractLocalDao(C callback) {
        attachModelDaoCallback(callback);
    }

    @Override
    public void attachModelDaoCallback(@NonNull C callback) {
        mCallback = callback;
        onAttachModelDaoCallback(callback);
    }

    @Override
    public void release() {
        preRelease();
        destroy();
        postRelease();
    }

    /**
     * 当和Model层Dao回调关联时调用
     *
     * @param callback 回调对象
     */
    protected void onAttachModelDaoCallback(@NonNull C callback) {
    }

    /**
     * 添加本地数据操作
     *
     * @param operation 本地数据操作
     */
    protected void addLocalOperation(IBaseLocalDaoOperation operation) {
        if (operation == null || operation.isInvalid()) {
            return;
        }
        if (mLocalDaoOperationList == null) {
            mLocalDaoOperationList = new ArrayList<>();
        }
        //移除已经失效了的操作
        if (mLocalDaoOperationList.size() > 0) {
            Iterator<IBaseLocalDaoOperation> iterator = mLocalDaoOperationList.iterator();
            while (iterator.hasNext()) {
                IBaseLocalDaoOperation o = iterator.next();
                if (o != null && o.isInvalid()) {
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
    protected void removeLocalOperation(IBaseLocalDaoOperation operation) {
        if (operation == null || mLocalDaoOperationList == null || mLocalDaoOperationList.size() == 0) {
            return;
        }
        if (!operation.isInvalid()) {
            operation.release();
        }
        mLocalDaoOperationList.remove(operation);
    }

    protected void preRelease() {
        if (mLocalDaoOperationList == null) {
            return;
        }
        for (IBaseLocalDaoOperation operation : mLocalDaoOperationList) {
            if (operation != null && !operation.isInvalid()) {
                operation.release();
            }
        }
        mLocalDaoOperationList.clear();
        mLocalDaoOperationList = null;
    }

    protected void postRelease() {
        mCallback = null;
    }

    /**
     * 销毁资源
     */
    protected abstract void destroy();
}
