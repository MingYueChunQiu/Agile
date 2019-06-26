package com.mingyuechunqiu.agile.base.model.part.dao.local;

import com.mingyuechunqiu.agile.base.framework.IBaseListener;
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
public abstract class BaseAbstractLocalDao<I extends IBaseListener> implements IBaseLocalDao {

    protected I mListener;

    protected List<IBaseLocalDaoOperation> mLocalDaoOperationList;

    public BaseAbstractLocalDao(I listener) {
        mListener = listener;
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

    @Override
    public void release() {
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
        mListener = null;
    }
}
