package com.mingyuechunqiu.agile.base.model.dao.operation.remote;

import androidx.annotation.NonNull;

import retrofit2.Call;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : Retrofit网络调用操作
 *              继承自BaseAbstractRemoteDaoOperation
 *     version: 1.0
 * </pre>
 */
public class RetrofitCallDaoOperation<T> extends BaseAbstractRemoteDaoOperation<Call<T>> {

    public RetrofitCallDaoOperation(@NonNull Call<T> operation) {
        super(operation);
    }

    @Override
    public boolean isCanceled() {
        return mOperation != null && mOperation.isCanceled();
    }

    @Override
    public void cancel() {
        if (mOperation != null) {
            mOperation.cancel();
        }
    }

    @Override
    protected void release() {
    }
}
