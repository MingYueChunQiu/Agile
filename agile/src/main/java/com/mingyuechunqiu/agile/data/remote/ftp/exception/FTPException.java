package com.mingyuechunqiu.agile.data.remote.ftp.exception;

import androidx.annotation.NonNull;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :     https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/10/25 9:54
 *       Desc:       FTP异常
 *                   继承自Exception
 *       Version:    1.0
 * </pre>
 */
public class FTPException extends Exception {

    public FTPException(@NonNull String msg){
        super(msg);
    }
}
