package com.mingyuechunqiu.agile.data.remote.socket.function.framework.data;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.data.remote.socket.exception.SocketHandlerException;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/9/24 16:53
 *       Desc:       所有Socket数据回调父接口
 *       Version:    1.0
 * </pre>
 */
public interface BaseSocketDataCallback {

    void onGetDataFailed(@NonNull SocketHandlerException e);
}
