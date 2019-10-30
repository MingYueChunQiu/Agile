package com.mingyuechunqiu.agile.data.remote.socket.manager.framework.data;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.data.remote.socket.bean.SocketReceiveData;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/9/25 9:46
 *       Desc:       Socket发送数据回调接口
 *                   继承自BaseSocketDataCallback
 *       Version:    1.0
 * </pre>
 */
public interface SocketDataCallback extends BaseSocketDataCallback {

    /**
     * 是否是心跳包
     *
     * @param data 接收数据
     * @return 如果是心跳则返回true，否则返回false
     */
    boolean isHeartBeat(String data);

    /**
     * 当获取心跳响应时回调
     */
    void onGetHeartBeatSuccess();

    /**
     * 当获取数据成功时回调
     *
     * @param data 返回接收到的数据
     */
    void onGetDataSuccess(@NonNull SocketReceiveData data);
}
