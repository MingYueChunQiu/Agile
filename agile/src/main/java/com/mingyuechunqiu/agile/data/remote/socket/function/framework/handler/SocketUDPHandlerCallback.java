package com.mingyuechunqiu.agile.data.remote.socket.function.framework.handler;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.data.remote.socket.bean.SocketSendData;
import com.mingyuechunqiu.agile.data.remote.socket.function.framework.data.SocketDataCallback;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/9/24 14:49
 *       Desc:       Socket处理UDP器回调接口
 *                   继承自ISocketHandlerCallback
 *       Version:    1.0
 * </pre>
 */
public interface SocketUDPHandlerCallback extends SocketHandlerCallback {

    void onReceiveUdpHeartBeatSuccess(@NonNull SocketSendData data, @NonNull SocketDataCallback callback);
}
