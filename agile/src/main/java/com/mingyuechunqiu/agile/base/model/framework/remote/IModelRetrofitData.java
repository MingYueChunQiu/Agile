package com.mingyuechunqiu.agile.base.model.framework.remote;

import androidx.annotation.Nullable;

import retrofit2.Response;

/**
 * <pre>
 *      Project:    Agile
 *      Github :    https://github.com/MingYueChunQiu
 *      Author:     XiYuJie
 *      Email:      xiyujie@jinying.com
 *      Time:       2020/1/19 16:53
 *      Desc:       Model层Retrofit功能数据处理接口
 *                  继承自IModelNetworkData
 *      Version:    1.0
 * </pre>
 */
public interface IModelRetrofitData extends IModelNetworkData {

    /**
     * 检测Retrofit的网络响应是否为空
     *
     * @param response 网络响应
     * @return 如果网络响应为空返回true，否则返回false
     */
    boolean checkRetrofitResponseIsNull(@Nullable Response response);
}
