package com.mingyuechunqiu.agile.base.model.part.operation.remote;

import android.support.annotation.NonNull;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 网络调用操作
 *              实现INetworkOperation
 *     version: 1.0
 * </pre>
 */
public abstract class NetworkOperation<T> implements INetworkOperation {

    protected T mOperation;

    public NetworkOperation(@NonNull T operation) {
        mOperation = operation;
    }

}
