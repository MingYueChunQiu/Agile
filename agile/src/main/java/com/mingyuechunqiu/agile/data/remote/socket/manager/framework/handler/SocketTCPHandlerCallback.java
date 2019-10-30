package com.mingyuechunqiu.agile.data.remote.socket.manager.framework.handler;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.data.remote.socket.bean.SocketSendData;
import com.mingyuechunqiu.agile.data.remote.socket.manager.framework.data.SocketDataCallback;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/9/24 15:20
 *       Desc:       Socket处理TCP器回调接口
 *                   继承自ISocketHandlerCallback
 *       Version:    1.0
 * </pre>
 */
public interface SocketTCPHandlerCallback extends SocketHandlerCallback {

    /**
     * 重新尝试连接
     *
     * @param data     需要发送的数据
     * @param callback 发送数据回调
     */
    void retryReconnect(@NonNull SocketSendData data, @NonNull SocketDataCallback callback);

    /**
     * 当TCP连接成功时回调
     *
     * @param data     需要发送的数据
     * @param callback 发送数据回调
     */
    void connectTcpSuccess(@NonNull SocketSendData data, @NonNull SocketDataCallback callback);
}
