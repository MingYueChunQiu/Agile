package com.mingyuechunqiu.agile.base.model.repository.remote;

import androidx.annotation.Nullable;

import retrofit2.Response;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : Retrofit网络Repository抽象基类
 *              继承自BaseAbstractNetworkRepository
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractRetrofitRepository extends BaseAbstractNetworkRepository {

    /**
     * 检查响应对象是否为空
     *
     * @param response 响应对象
     * @param <T>      返回值类型
     * @return 如果为空返回true，否则返回false
     */
    protected <T> boolean checkRetrofitResponseIsNull(@Nullable Response<T> response) {
        return response == null || response.body() == null;
    }
}
