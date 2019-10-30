package com.mingyuechunqiu.agile.data.remote.socket.constants;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/9/25 13:25
 *       Desc:       Socket连接状态常量类
 *       Version:    1.0
 * </pre>
 */
public final class SocketConnectState {

    public static final int STATE_IDLE = -1;//空闲状态
    public static final int STATE_SUCCESS = 0;//连接成功
    public static final int STATE_TIME_OUT = 1;//连接超时
    public static final int STATE_ERROR = 2;//连接失败
    public static final int STATE_UNKNOWN = 3;//连接未知
}
