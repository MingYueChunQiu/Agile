package com.mingyuechunqiu.agile.data.remote.socket.bean;

import com.mingyuechunqiu.agile.data.remote.socket.manager.framework.data.SocketDataCallback;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/10/10 9:21
 *       Desc:       Socket响应结果信息类
 * Version:    1.0
 * </pre>
 */
public class SocketResultInfo {

    private SocketDataCallback callback;//网络请求回调
    private SocketReceiveData receiveData;//响应接受数据
    private int errorType;//错误类型
    private String errorMessage;//错误信息

    public SocketDataCallback getCallback() {
        return callback;
    }

    public void setCallback(SocketDataCallback callback) {
        this.callback = callback;
    }

    public SocketReceiveData getReceiveData() {
        return receiveData;
    }

    public void setReceiveData(SocketReceiveData receiveData) {
        this.receiveData = receiveData;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
