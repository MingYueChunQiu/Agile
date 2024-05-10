package com.mingyuechunqiu.agile.data.remote.ftp.function;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.data.remote.ftp.bean.FTPConfigure;
import com.mingyuechunqiu.agile.data.remote.ftp.exception.FTPConnectException;
import com.mingyuechunqiu.agile.data.remote.ftp.framework.FTPConnectListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/10/24 17:05
 *       Desc:       FTP管理器类
 *                   实现IFTPManager
 *       Version:    1.0
 * </pre>
 */
class FTPManager implements IFTPManager {

    private Map<String, IFTPHandler> mCacheMap = new ConcurrentHashMap<>();

    @Override
    public void openConnect(@NonNull FTPConfigure info, @NonNull FTPConnectListener listener) {
        if (TextUtils.isEmpty(info.getHostName())) {
            listener.onConnectFailure(new FTPConnectException("HostName can not be null"));
            return;
        }
        IFTPHandler handler = mCacheMap.get(info.getHostName());
        if (handler == null) {
            handler = new FTPHandler();
            mCacheMap.put(info.getHostName(), handler);
        }
        handler.openConnect(info, listener);
    }

    @Override
    public void releaseConnect(@NonNull String hostName) {
        if (TextUtils.isEmpty(hostName) || mCacheMap == null) {
            return;
        }
        IFTPHandler handler = mCacheMap.get(hostName);
        if (handler != null) {
            handler.releaseConnect();
            mCacheMap.remove(hostName);
        }
    }

    @Override
    public void release() {
        if (mCacheMap != null) {
            for (IFTPHandler handler : mCacheMap.values()) {
                handler.releaseConnect();
            }
            mCacheMap.clear();
            mCacheMap = null;
        }
    }
}
