package com.mingyuechunqiu.agile.data.remote.socket.function.framework.handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.data.remote.socket.bean.SocketIpInfo;
import com.mingyuechunqiu.agile.data.remote.socket.bean.SocketReceiveData;
import com.mingyuechunqiu.agile.data.remote.socket.function.framework.data.SocketDataCallback;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/9/24 15:18
 *       Desc:       Socket处理器回调接口
 *       Version:    1.0
 * </pre>
 */
public interface SocketHandlerCallback {

    void executeTask(@NonNull Runnable runnable);

    /**
     * 处理心跳成功结果
     *
     * @param callback 网络请求回调
     */
    void handleHeartBeatSuccessResult(@NonNull SocketDataCallback callback);

    /**
     * 处理数据响应成功结果
     *
     * @param callback 网络请求回调
     * @param data     响应接收数据
     */
    void handleDataSuccessResult(@NonNull SocketDataCallback callback, @NonNull SocketReceiveData data);

    /**
     * 处理网络请求失败结果
     *
     * @param callback  网络请求回调
     * @param errorType 错误类型
     * @param msg       错误信息
     */
    void handleFailureResult(@NonNull SocketDataCallback callback, int errorType, @Nullable String msg);

    /**
     * 释放当前IP的Socket
     *
     * @param info 连接的IP地址信息
     */
    void releaseConnect(@Nullable SocketIpInfo info);
}
