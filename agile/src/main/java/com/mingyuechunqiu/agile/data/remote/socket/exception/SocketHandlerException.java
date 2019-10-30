package com.mingyuechunqiu.agile.data.remote.socket.exception;

import androidx.annotation.NonNull;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/9/30 9:31
 *       Desc:       Socket响应异常
 *                   继承自Exception
 *       Version:    1.0
 * </pre>
 */
public class SocketHandlerException extends Exception {

    private int errorType;//错误类型

    public SocketHandlerException(int errorType, @NonNull String msg) {
        super(msg);
        this.errorType = errorType;
    }

    public int getErrorType() {
        return errorType;
    }
}
