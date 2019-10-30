package com.mingyuechunqiu.agile.data.remote.socket.manager.tcp;

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
 *       Desc:       所有socket处理TCP的父接口
 *       Version:    1.0
 * </pre>
 */
public interface ISocketTCPHandler {

    /**
     * 是否已经连接成功
     *
     * @return 如果已经连接成功返回true，否则返回false
     */
    boolean isConnectSuccess();

    void sendHeartBeat(@NonNull SocketSendData data, @NonNull SocketDataCallback callback);

    void receiveData();

    void sendData(@NonNull SocketSendData data, @NonNull SocketDataCallback callback);

    void release();
}
