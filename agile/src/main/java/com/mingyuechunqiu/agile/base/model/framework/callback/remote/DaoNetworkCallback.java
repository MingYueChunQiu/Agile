package com.mingyuechunqiu.agile.base.model.framework.callback.remote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.bridge.Callback;
import com.mingyuechunqiu.agile.base.bridge.call.Call;
import com.mingyuechunqiu.agile.data.bean.ErrorInfo;

/**
 * <pre>
 *      Project:    Agile
 *      Github :    https://github.com/MingYueChunQiu
 *      Author:     XiYuJie
 *      Email:      xiyujie@jinying.com
 *      Time:       2020/1/15 13:53
 *      Desc:       网络功能Dao层回调接口
 *                  继承自DaoCallback
 *      Version:    1.0
 * </pre>
 */
public interface DaoNetworkCallback extends DaoRemoteCallback {

    /**
     * 根据网络响应返回码，进行不同处理
     *
     * @param call     调用对象
     * @param code     网络响应返回码
     * @param errorMsg 网络请求错误信息
     * @param <T>      响应对象类型
     * @return 返回true表示响应成功，否则返回false失败
     */
    <T> boolean preHandleNetworkResponseFailureWithCode(@NonNull Call<T> call, int code, @Nullable String errorMsg);

    /**
     * 处理网络响应失败事件
     *
     * @param callback 请求回调
     * @param errorMsg 错误信息
     */
    <T> void handleOnNetworkResponseError(@NonNull Callback<T> callback, @Nullable String errorMsg);

    /**
     * 处理网络响应失败事件
     *
     * @param callback 请求回调
     * @param info     错误对象
     */
    <T> void handleOnNetworkResponseError(@NonNull Callback<T> callback, @NonNull ErrorInfo info);
}
