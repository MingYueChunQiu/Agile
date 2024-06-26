package com.mingyuechunqiu.agile.data.remote.socket.function.udp;

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
 *       Desc:       所有socket处理UDP的父接口
 *       Version:    1.0
 * </pre>
 */
public interface ISocketUDPHandler {

    /**
     * 是否已经连接成功
     *
     * @return 如果已经连接成功返回true，否则返回false
     */
    boolean isConnectSuccess();

    void sendHeartBeat(@NonNull SocketSendData data, @NonNull SocketDataCallback callback);

    void receiveHeartBeat();

    void release();
}
