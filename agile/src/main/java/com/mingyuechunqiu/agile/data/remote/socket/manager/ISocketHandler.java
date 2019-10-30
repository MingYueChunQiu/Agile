package com.mingyuechunqiu.agile.data.remote.socket.manager;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.data.remote.socket.bean.SocketSendData;
import com.mingyuechunqiu.agile.data.remote.socket.manager.framework.data.SocketDataCallback;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
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
