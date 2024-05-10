package com.mingyuechunqiu.agile.data.remote.ftp.function;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.data.remote.ftp.bean.FTPConfigure;
import com.mingyuechunqiu.agile.data.remote.ftp.framework.FTPConnectListener;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/10/24 16:54
 *       Desc:       FTP管理器接口
 *       Version:    1.0
 * </pre>
 */
public interface IFTPManager {

    void openConnect(@NonNull FTPConfigure info, @NonNull FTPConnectListener listener);

    void releaseConnect(@NonNull String hostName);

    void release();
}
