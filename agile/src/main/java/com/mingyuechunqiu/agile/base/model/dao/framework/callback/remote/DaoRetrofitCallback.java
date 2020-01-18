package com.mingyuechunqiu.agile.base.model.dao.framework.callback.remote;

import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.framework.IBaseListener;

import retrofit2.Response;

/**
 * <pre>
 *      Project:    Agile
 *      Github :    https://github.com/MingYueChunQiu
 *      Author:     XiYuJie
 *      Email:      xiyujie@jinying.com
 *      Time:       2020/1/15 13:56
 *      Desc:       使用Retrofit的网络功能Dao层回调
 *                  继承自DaoNetCallback
 *      Version:    1.0
 * </pre>
 */
public interface DaoRetrofitCallback<I extends IBaseListener> extends DaoNetworkCallback<I> {

    /**
     * 检测Retrofit的网络响应是否为空
     *
     * @param response 网络响应
     * @return 如果网络响应为空返回true，否则返回false
     */
    boolean checkRetrofitResponseIsNull(@Nullable Response response);
}
