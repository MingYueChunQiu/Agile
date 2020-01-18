package com.mingyuechunqiu.agile.data.remote.ftp.function;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.data.remote.ftp.bean.FTPConfigure;
import com.mingyuechunqiu.agile.data.remote.ftp.framework.FTPConnectListener;
import com.mingyuechunqiu.agile.data.remote.ftp.framework.FTPResponseCallback;

import org.apache.commons.net.ftp.FTPFile;

import java.util.List;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/10/28 8:40
 *       Desc:       FTP处理接口
 *       Version:    1.0
 * </pre>
 */
public interface IFTPHandler {

    void openConnect(@NonNull FTPConfigure configure, @NonNull FTPConnectListener listener);

    void releaseConnect();

    List<FTPFile> listFiles(@NonNull String remotePath);

    void downloadFile(@NonNull String remotePath, @NonNull String downloadFileName, @NonNull String savePath,
                      @NonNull FTPResponseCallback callback);

    void uploadFile(@NonNull String filePath, @NonNull FTPResponseCallback callback);
}
