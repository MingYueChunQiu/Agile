package com.mingyuechunqiu.agile.base.model.repository.operation.remote;

import androidx.annotation.NonNull;

import retrofit2.Call;

/**
 * <pre>
 *     author : xyj
 *     Github : <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : Retrofit网络调用操作
 *              继承自BaseAbstractRemoteRepositoryOperation
 *     version: 1.0
 * </pre>
 */
public class RetrofitCallRepositoryOperation<T> extends BaseAbstractNetworkRepositoryOperation<Call<T>> {

    public RetrofitCallRepositoryOperation(@NonNull Call<T> work) {
        super(work);
    }

    @Override
    protected void release() {
    }

    @Override
    public boolean isCanceled() {
        return getWork().isCanceled();
    }

    @Override
    public void cancel() {
        getWork().cancel();
    }
}
