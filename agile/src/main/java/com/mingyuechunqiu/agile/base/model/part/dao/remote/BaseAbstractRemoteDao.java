package com.mingyuechunqiu.agile.base.model.part.dao.remote;

import com.mingyuechunqiu.agile.base.model.part.dao.operation.remote.IBaseRemoteOperation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 远程Dao抽象基类
 *              实现IRemoteDao
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractRemoteDao implements IBaseRemoteDao {

    protected List<IBaseRemoteOperation> mRemoteOperationList;

    /**
     * 添加远程操作
     *
     * @param operation 远程操作
     */
    protected void addRemoteOperation(IBaseRemoteOperation operation) {
        if (operation == null || operation.isCanceled()) {
            return;
        }
        if (mRemoteOperationList == null) {
            mRemoteOperationList = new ArrayList<>();
        }
        //移除已经失效了的操作
        if (mRemoteOperationList.size() > 0) {
            Iterator<IBaseRemoteOperation> iterator = mRemoteOperationList.iterator();
            while (iterator.hasNext()) {
                IBaseRemoteOperation o = iterator.next();
                if (o != null && o.isCanceled()) {
                    iterator.remove();
                }
            }
        }
        if (!mRemoteOperationList.contains(operation)) {
            mRemoteOperationList.add(operation);
        }
    }

    /**
     * 从远程操作集合中移除回调
     *
     * @param operation 远程操作
     */
    protected void removeRemoteOperation(IBaseRemoteOperation operation) {
        if (operation == null || mRemoteOperationList == null || mRemoteOperationList.size() == 0) {
            return;
        }
        if (!operation.isCanceled()) {
            operation.cancel();
        }
        mRemoteOperationList.remove(operation);
    }

    @Override
    public void release() {
        if (mRemoteOperationList == null) {
            return;
        }
        for (IBaseRemoteOperation operation : mRemoteOperationList) {
            if (operation != null && !operation.isCanceled()) {
                operation.cancel();
            }
        }
        mRemoteOperationList.clear();
        mRemoteOperationList = null;
    }
}
