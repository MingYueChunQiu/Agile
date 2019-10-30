package com.mingyuechunqiu.agile.data.remote.socket.constants;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/9/25 13:28
 *       Desc:       Socket相关常量类
 *       Version:    1.0
 * </pre>
 */
public class SocketConstants {

    public static final int SOCKET_TIME_OUT = 20 * 1000;//连接超时时间（单位秒）

    public static final int SOCKET_RETRY_COUNT = 2;//失败重连默认次数

    public static final int SOCKET_SILENT_DURATION = 60 * 1000;//默认无数据传递时持续时间（单位毫秒）

    public static final String SOCKET_IP = "192.168.69.180";

    public static final int SOCKET_PORT = 8080;

    public static final String SOCKET_HEART_BEAT = "心跳测试";

    //Socket报文发送时固定的前后缀字符串
    public static final String SOCKET_FIXED_SPLIT_STRING = "GEPOSAPPBEGIN";
}
