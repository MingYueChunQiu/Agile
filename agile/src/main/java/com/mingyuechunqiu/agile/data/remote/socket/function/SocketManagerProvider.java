package com.mingyuechunqiu.agile.data.remote.socket.function;

import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketConstants.SOCKET_HEART_BEAT;
import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketConstants.SOCKET_RETRY_COUNT;
import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketConstants.SOCKET_SILENT_DURATION;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.data.remote.socket.bean.SocketSendData;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/10/8 18:02
 *       Desc:       Socket管理器提供类
 *       Version:    1.0
 * </pre>
 */
public final class SocketManagerProvider {

    private static SocketConfigure mGlobalConfigure;

    private SocketManagerProvider() {
    }

    public static ISocketManager getInstance() {
        return SocketManagerProviderHolder.sInstance;
    }

    public static void setGlobalSocketConfigure(@NonNull SocketConfigure configure) {
        mGlobalConfigure = configure;
    }

    @NonNull
    public static SocketConfigure getGlobalSocketConfigure() {
        if (mGlobalConfigure == null) {
            synchronized (SocketManagerProvider.class) {
                if (mGlobalConfigure == null) {
                    SocketSendData heartBeat = new SocketSendData();
                    heartBeat.setData(SOCKET_HEART_BEAT);
                    mGlobalConfigure = new SocketConfigure.Builder()
                            .setRetryCount(SOCKET_RETRY_COUNT)
                            .setSilentDuration(SOCKET_SILENT_DURATION)
                            .setHeartBeat(heartBeat)
                            .build();
                }
            }
        }
        return mGlobalConfigure;
    }

    private static final class SocketManagerProviderHolder {

        private static final ISocketManager sInstance = new SocketManager();
    }
}
