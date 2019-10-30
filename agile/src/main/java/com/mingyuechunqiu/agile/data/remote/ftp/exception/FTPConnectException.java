package com.mingyuechunqiu.agile.data.remote.ftp.exception;

import androidx.annotation.NonNull;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/10/28 8:52
 *       Desc:       FTP连接异常
 *                   继承自Exception
 *       Version:    1.0
 * </pre>
 */
public class FTPConnectException extends Exception {

    public FTPConnectException(@NonNull String msg) {
        super(msg);
    }
}
