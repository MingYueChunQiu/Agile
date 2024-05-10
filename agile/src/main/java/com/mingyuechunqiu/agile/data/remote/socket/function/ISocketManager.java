package com.mingyuechunqiu.agile.data.remote.socket.function;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    <a href="https://github.com/MingYueChunQiu">仓库地址</a>
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
