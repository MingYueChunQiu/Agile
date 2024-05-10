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
 *       Desc:       Socket管理类（单例）
 *                   实现ISocketManager
 *       Version:    1.0
 * </pre>
 */
class SocketManager implements ISocketManager {

    private ISocketHandler mSocketHandler;

    SocketManager() {
        mSocketHandler = new SocketHandler();
    }

    @Override
    public void sendUdpHeartBeat(@NonNull SocketSendData data, @NonNull SocketDataCallback callback) {
        checkOrCreateSocketHandler();
        mSocketHandler.sendUdpHeartBeat(data, callback);
    }

    @Override
    public void sendTcpData(@NonNull SocketSendData data, @NonNull SocketDataCallback callback) {
        checkOrCreateSocketHandler();
        mSocketHandler.sendTcpData(data, callback);
    }

    @Override
    public void release() {
        if (mSocketHandler != null) {
            mSocketHandler.release();
            mSocketHandler = null;
        }
    }

    private void checkOrCreateSocketHandler() {
        if (mSocketHandler == null) {
            mSocketHandler = new SocketHandler();
        }
    }
}
