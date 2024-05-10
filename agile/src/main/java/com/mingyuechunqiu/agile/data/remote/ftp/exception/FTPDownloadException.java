package com.mingyuechunqiu.agile.data.remote.ftp.exception;

import androidx.annotation.NonNull;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/10/28 14:20
 *       Desc:       FTP下载异常
 *                   继承自Exception
 *       Version:    1.0
 * </pre>
 */
public class FTPDownloadException extends Exception {

    public FTPDownloadException(@NonNull String msg) {
        super(msg);
    }
}
