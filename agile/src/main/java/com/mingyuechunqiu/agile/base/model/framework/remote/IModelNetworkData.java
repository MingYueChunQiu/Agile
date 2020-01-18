package com.mingyuechunqiu.agile.base.model.framework.remote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.mingyuechunqiu.agile.data.bean.ErrorInfo;

import retrofit2.Response;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020-01-17 20:34
 *      Desc:       Model层网络数据处理接口
 *                  继承自IModelRemoteData
 *      Version:    1.0
 * </pre>
 */
public interface IModelNetworkData extends IModelRemoteData {

    /**
     * 检测Retrofit的网络响应是否为空
     *
     * @param response 网络响应
     * @return 如果网络响应为空返回true，否则返回false
     */
    boolean checkRetrofitResponseIsNull(@Nullable Response response);

    /**
     * 处理网络响应失败事件
     *
     * @param t             抛出的异常
     * @param errorMsgResId 错误提示字符串资源ID
     */
    void onNetworkResponseFailed(@Nullable Throwable t, @StringRes int errorMsgResId);

    /**
     * 处理网络响应失败事件
     *
     * @param t    抛出的异常
     * @param info 错误信息对象
     */
    void onNetworkResponseFailed(@Nullable Throwable t, @NonNull ErrorInfo info);

    /**
     * 根据网络响应返回码，进行不同处理
     *
     * @param code     网络响应返回码
     * @param errorMsg 网络请求错误信息
     * @return 返回true表示响应成功，否则返回false失败
     */
    boolean handleNetworkResponseCode(int code, @Nullable String errorMsg);

    /**
     * 释放网络相关资源
     */
    void releaseNetworkResources();
}
