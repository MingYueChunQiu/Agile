package com.mingyuechunqiu.agile.data.remote.socket.function;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.data.remote.socket.bean.SocketSendData;
import com.mingyuechunqiu.agile.data.remote.socket.function.framework.data.SocketDataCallback;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/9/24 14:09
 *       Desc:       所有socket处理的父接口
 *       Version:    1.0
 * </pre>
 */
public interface ISocketHandler {

    void sendUdpHeartBeat(@NonNull SocketSendData data, @NonNull SocketDataCallback callback);

    void sendTcpData(@NonNull SocketSendData data, @NonNull SocketDataCallback callback);

    void release();
}
