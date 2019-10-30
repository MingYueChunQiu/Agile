package com.mingyuechunqiu.agile.data.remote.socket.manager;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/10/8 18:02
 *       Desc:       Socket管理器提供类
 *       Version:    1.0
 * </pre>
 */
public final class SocketManagerProvider {

    private SocketManagerProvider() {
    }

    public static ISocketManager getInstance() {
        return SocketManagerProviderHolder.sInstance;
    }

    private static final class SocketManagerProviderHolder {

        private static ISocketManager sInstance = new SocketManager();
    }
}
