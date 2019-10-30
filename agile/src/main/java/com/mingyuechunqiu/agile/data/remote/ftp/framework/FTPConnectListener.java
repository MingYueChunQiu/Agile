package com.mingyuechunqiu.agile.data.remote.ftp.framework;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.data.remote.ftp.exception.FTPConnectException;
import com.mingyuechunqiu.agile.data.remote.ftp.manager.IFTPHandler;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/10/24 17:43
 *       Desc:       FTP连接监听器接口
 *       Version:    1.0
 * </pre>
 */
public interface FTPConnectListener {

    void onConnectSuccess(@NonNull IFTPHandler handler);

    void onConnectFailure(@NonNull FTPConnectException e);
}
