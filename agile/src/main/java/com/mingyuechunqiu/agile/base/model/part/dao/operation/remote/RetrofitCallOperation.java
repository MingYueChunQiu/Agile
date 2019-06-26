package com.mingyuechunqiu.agile.base.model.part.dao.operation.remote;

import android.support.annotation.NonNull;

import retrofit2.Call;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : Retrofit网络调用操作
 *              继承自NetworkOperation
 *     version: 1.0
 * </pre>
 */
public class RetrofitCallOperation extends AbstractNetworkOperation<Call> {

    public RetrofitCallOperation(@NonNull Call operation) {
        super(operation);
    }

    @Override
    public boolean isCanceled() {
        return mOperation.isCanceled();
    }

    @Override
    public void cancel() {
        mOperation.cancel();
    }
}
