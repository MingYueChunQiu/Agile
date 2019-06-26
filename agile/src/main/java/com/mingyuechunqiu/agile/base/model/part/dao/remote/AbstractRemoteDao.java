package com.mingyuechunqiu.agile.base.model.part.dao.remote;

import com.mingyuechunqiu.agile.base.model.part.operation.remote.INetworkOperation;

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
public abstract class AbstractRemoteDao implements IRemoteDao {

    protected List<INetworkOperation> mNetworkOperationList;

    /**
     * 添加网络操作
     *
     * @param operation 网络操作
     */
    protected void addNetworkOperation(INetworkOperation operation) {
        if (operation == null || operation.isCanceled()) {
            return;
        }
        if (mNetworkOperationList == null) {
            mNetworkOperationList = new ArrayList<>();
        }
        //移除已经失效了的Retrofit回调
        if (mNetworkOperationList.size() > 0) {
            Iterator<INetworkOperation> iterator = mNetworkOperationList.iterator();
            while (iterator.hasNext()) {
                INetworkOperation o = iterator.next();
                if (o != null && o.isCanceled()) {
                    iterator.remove();
                }
            }
        }
        if (!mNetworkOperationList.contains(operation)) {
            mNetworkOperationList.add(operation);
        }
    }

    /**
     * 从网络操作集合中移除回调
     *
     * @param operation 网络操作
     */
    protected void removeNetworkOperation(INetworkOperation operation) {
        if (operation == null || mNetworkOperationList == null || mNetworkOperationList.size() == 0) {
            return;
        }
        if (!operation.isCanceled()) {
            operation.cancel();
        }
        mNetworkOperationList.remove(operation);
    }

    @Override
    public void release() {
        if (mNetworkOperationList == null) {
            return;
        }
        for (INetworkOperation operation : mNetworkOperationList) {
            if (operation != null && !operation.isCanceled()) {
                operation.cancel();
            }
        }
        mNetworkOperationList.clear();
        mNetworkOperationList = null;
    }
}
