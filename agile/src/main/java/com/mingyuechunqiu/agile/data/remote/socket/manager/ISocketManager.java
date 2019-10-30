package com.mingyuechunqiu.agile.data.remote.socket.manager;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/9/24 14:09
 *       Desc:       socket管理器类
 *                   继承自ISocketHandler
 *       Version:    1.0
 * </pre>
 */
public interface ISocketManager extends ISocketHandler {

    void release();
}
