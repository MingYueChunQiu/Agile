package com.mingyuechunqiu.agile.base.model.part.dao.local;

import com.mingyuechunqiu.agile.base.model.part.dao.operation.local.IBaseLocalOperation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 本地Dao抽象基类
 *              实现ILocalDao
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractLocalDao implements IBaseLocalDao {

    protected List<IBaseLocalOperation> mLocalOperationList;

    /**
     * 添加本地数据操作
     *
     * @param operation 本地数据操作
     */
    protected void addLocalOperation(IBaseLocalOperation operation) {
        if (operation == null || operation.isInvalid()) {
            return;
        }
        if (mLocalOperationList == null) {
            mLocalOperationList = new ArrayList<>();
        }
        //移除已经失效了的操作
        if (mLocalOperationList.size() > 0) {
            Iterator<IBaseLocalOperation> iterator = mLocalOperationList.iterator();
            while (iterator.hasNext()) {
                IBaseLocalOperation o = iterator.next();
                if (o != null && o.isInvalid()) {
                    iterator.remove();
                }
            }
        }
        if (!mLocalOperationList.contains(operation)) {
            mLocalOperationList.add(operation);
        }
    }

    /**
     * 从本地数据操作集合中移除回调
     *
     * @param operation 本地数据操作
     */
    protected void removeLocalOperation(IBaseLocalOperation operation) {
        if (operation == null || mLocalOperationList == null || mLocalOperationList.size() == 0) {
            return;
        }
        if (!operation.isInvalid()) {
            operation.release();
        }
        mLocalOperationList.remove(operation);
    }

    @Override
    public void release() {
        if (mLocalOperationList == null) {
            return;
        }
        for (IBaseLocalOperation operation : mLocalOperationList) {
            if (operation != null && !operation.isInvalid()) {
                operation.release();
            }
        }
        mLocalOperationList.clear();
        mLocalOperationList = null;
    }
}
