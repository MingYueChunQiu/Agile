package com.mingyuechunqiu.agile.base.model.part.dao.remote;

import com.mingyuechunqiu.agile.base.framework.IBaseListener;
import com.mingyuechunqiu.agile.base.model.part.dao.operation.remote.IBaseRemoteDaoOperation;

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
public abstract class BaseAbstractRemoteDao<I extends IBaseListener> implements IBaseRemoteDao {

    protected I mListener;

    protected List<IBaseRemoteDaoOperation> mRemoteDaoOperationList;

    public BaseAbstractRemoteDao(I listener) {
        mListener = listener;
    }

    /**
     * 添加远程操作
     *
     * @param operation 远程操作
     */
    protected void addRemoteOperation(IBaseRemoteDaoOperation operation) {
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
    protected void removeRemoteOperation(IBaseRemoteDaoOperation operation) {
        if (operation == null || mRemoteDaoOperationList == null || mRemoteDaoOperationList.size() == 0) {
            return;
        }
        if (!operation.isCanceled()) {
            operation.cancel();
        }
        mRemoteDaoOperationList.remove(operation);
    }

    @Override
    public void release() {
        destroy();
        superRelease();
    }

    protected void superRelease() {
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
        mListener = null;
    }

    /**
     * 销毁资源
     */
    protected abstract void destroy();
}
