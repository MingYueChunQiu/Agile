package com.mingyuechunqiu.agile.data.remote.ftp.framework;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.data.remote.ftp.exception.FTPException;

import java.io.File;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/10/24 18:05
 *       Desc:       FTP响应回调
 *       Version:    1.0
 * </pre>
 */
public interface FTPResponseCallback {

    /**
     * 当下载文件成功时回调
     *
     * @param localFile 本地存储文件
     */
    void onDownloadFileSuccess(@NonNull File localFile);

    void onResponseFailure(@NonNull FTPException e);
}
