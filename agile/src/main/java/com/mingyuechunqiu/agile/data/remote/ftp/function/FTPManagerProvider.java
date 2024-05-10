package com.mingyuechunqiu.agile.data.remote.ftp.function;

import androidx.annotation.NonNull;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/10/24 16:49
 *       Desc:       FTP管理器提供者
 *       Version:    1.0
 * </pre>
 */
public final class FTPManagerProvider {

    private FTPManagerProvider() {
    }

    @NonNull
    public static IFTPManager getInstance() {
        return FTPManagerProviderHolder.sInstance;
    }

    private static class FTPManagerProviderHolder {

        private static IFTPManager sInstance = new FTPManager();
    }
}
