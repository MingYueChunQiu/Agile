package com.mingyuechunqiu.agile.data.remote.ftp.function;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.data.remote.ftp.bean.FTPConfigure;
import com.mingyuechunqiu.agile.data.remote.ftp.exception.FTPConnectException;
import com.mingyuechunqiu.agile.data.remote.ftp.exception.FTPDownloadException;
import com.mingyuechunqiu.agile.data.remote.ftp.exception.FTPException;
import com.mingyuechunqiu.agile.data.remote.ftp.framework.FTPConnectListener;
import com.mingyuechunqiu.agile.data.remote.ftp.framework.FTPResponseCallback;
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;
import com.mingyuechunqiu.agile.io.IOUtils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mingyuechunqiu.agile.data.remote.ftp.constants.FTPConstants.PATH_CURRENT_DIRECTORY;
import static com.mingyuechunqiu.agile.data.remote.ftp.constants.FTPConstants.PATH_PREVIOUS_DIRECTORY;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/10/28 8:40
 *       Desc:       FTP具体处理类
 *                   实现IFTPHandler
 *       Version:    1.0
 * </pre>
 */
class FTPHandler implements IFTPHandler {

    private final FTPClient mClient;
    private FTPResponseCallback mCallback;

    FTPHandler() {
        mClient = new FTPClient();
    }

    @Override
    public void openConnect(@NonNull FTPConfigure configure, @NonNull FTPConnectListener listener) {
        mClient.setControlEncoding(configure.getControlEncoding());
        if (configure.getConnectTimeout() > 0) {
            mClient.setConnectTimeout(configure.getConnectTimeout());
        }
        if (configure.getDataTimeout() > 0) {
            mClient.setDataTimeout(configure.getDataTimeout());
        }

        try {
            if (!mClient.isConnected()) {
                mClient.connect(configure.getHostName());
            }
            if (!FTPReply.isPositiveCompletion(mClient.getReplyCode())) {
                mClient.disconnect();
                listener.onConnectFailure(new FTPConnectException("连接失败"));
            }
            mClient.login(configure.getUsername(), configure.getPassword());
            if (!FTPReply.isPositiveCompletion(mClient.getReplyCode())) {
                mClient.disconnect();
                listener.onConnectFailure(new FTPConnectException("登录失败"));
            } else {
                FTPClientConfig config = new FTPClientConfig(mClient.getSystemType().split(" ")[0]);
                config.setServerLanguageCode("zh");
                mClient.configure(config);
                // 使用被动模式设为默认
                mClient.enterLocalPassiveMode();
                // 二进制文件支持
                mClient.setFileType(FTP.BINARY_FILE_TYPE);
            }
            listener.onConnectSuccess(this);
        } catch (IOException e) {
            listener.onConnectFailure(new FTPConnectException(e.getMessage() == null ? "IO异常" : e.getMessage()));
            LogManagerProvider.e("FTPHandler：openConnect", e.getMessage());
        }
    }

    @Override
    public void releaseConnect() {
        if (checkFTPClientIsError()) {
            return;
        }
        try {
            mClient.abort();
            mClient.disconnect();
        } catch (IOException e) {
            LogManagerProvider.e("FTPHandler：releaseOnDetach", e.getMessage());
        }
    }

    @Override
    public void downloadFile(@NonNull String remotePath, @NonNull String downloadFileName, @NonNull String savePath,
                             @NonNull FTPResponseCallback callback) {
        mCallback = callback;

        boolean success = false;//是否下载成功标志
        try {
            success = downloadWithMultiFiles(remotePath, downloadFileName, savePath);
        } catch (FTPDownloadException e) {
            success = true;
            LogManagerProvider.e("FTPHandler：download single file end", e.getMessage());
        } catch (IOException e) {
            callback.onResponseFailure(new FTPException(e.getMessage() == null ? "IO异常" : e.getMessage()));
            LogManagerProvider.e("FTPHandler：downloadFile", e.getMessage());
        }

        if (success) {
            File file = new File(savePath + File.separator + downloadFileName);
            if (!file.exists()) {
                callback.onResponseFailure(new FTPException("localFile has not existed"));
                return;
            }
            callback.onDownloadFileSuccess(file);
        } else {
            callback.onResponseFailure(new FTPException("download failure"));
        }
    }

    /**
     * 下载多个文件
     *
     * @param remotePath       远程文件路径
     * @param downloadFileName 下载文件名（文件名为“”表示是下载当前目录文件夹所有内容）
     * @param savePath         保存文件路径
     * @return 如果下载成功返回true，否则返回false
     * @throws IOException          文件下载异常
     * @throws FTPDownloadException 单个文件下载完成，为退出递归而特意抛出的异常
     */
    private boolean downloadWithMultiFiles(@NonNull String remotePath, @NonNull String downloadFileName,
                                           @NonNull String savePath)
            throws IOException, FTPDownloadException {

        if (checkFTPClientIsError()) {
            return false;
        }

        mClient.changeWorkingDirectory(remotePath);

        boolean resultFlag = false;//下载结果是否成功标志

        for (FTPFile file : listFiles(remotePath)) {
            if (checkIsInvalidFile(file.getName())) {
                continue;
            }
            if ("".equals(downloadFileName) || downloadFileName.equals(file.getName())) {

                File localFile = getLocalSaveFile(file.getName(), savePath, checkIsAvailableDirectory(file));
                if (localFile == null) {
                    return false;
                }
                if (checkIsAvailableDirectory(file)) {

                    resultFlag = downloadWithMultiFiles(remotePath + file.getName(),
                            "", localFile.getAbsolutePath());
                } else {

                    resultFlag = downloadWithSingleFile(file, localFile);
                    //单个文件下载完成，需要通过抛异常方式退出遍历
                    if (resultFlag && !"".equals(downloadFileName)) {
                        throw new FTPDownloadException("download single file end");
                    }
                }

                //单个文件下载完成直接退出，文件夹下载需要继续遍历
                if (!"".equals(downloadFileName)) {
                    break;
                }
            }

            if (checkIsAvailableDirectory(file)) {
                resultFlag = downloadWithMultiFiles(remotePath + file.getName(),
                        downloadFileName, savePath);
            }
        }
        return resultFlag;
    }

    /**
     * 下载单个文件
     *
     * @param remoteFile 远程下载文件
     * @param localFile  本地存储文件
     * @return 如果下载成功返回true，否则返回false
     */
    private boolean downloadWithSingleFile(@NonNull FTPFile remoteFile, @NonNull File localFile) {
        if (checkFTPClientIsError()) return false;
        OutputStream os = null;

        try {
            os = new FileOutputStream(localFile);
            mClient.retrieveFile(remoteFile.getName(), os);
            return true;
        } catch (IOException e) {
            LogManagerProvider.e("FTPHandler：downloadWithSingleFile", "下载单个文件失败 " + e.getMessage());
        } finally {
            IOUtils.closeStream(os);
        }
        return false;
    }

    @Override
    public void uploadFile(@NonNull String filePath, @NonNull FTPResponseCallback callback) {
        mCallback = callback;

        if (TextUtils.isEmpty(filePath)) {
            callback.onResponseFailure(new FTPException("FTPHandler：filePath error"));
            return;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            callback.onResponseFailure(new FTPException("FTPHandler：localFile do not exist"));
            return;
        }

        if (uploadWithMultiFiles(file)) {
            callback.onDownloadFileSuccess(file);
        } else {
            callback.onResponseFailure(new FTPException("上传文件失败"));
        }
    }

    /**
     * 上传多个文件
     *
     * @param localFile 本地上传文件
     * @return 如果上传成功返回true，否则返回false
     */
    private boolean uploadWithMultiFiles(@NonNull File localFile) {
        boolean resultFlag = false;
        if (localFile.isDirectory()) {
            try {
                mClient.makeDirectory(localFile.getName());
                mClient.changeWorkingDirectory(localFile.getName());
                File[] files = localFile.listFiles();
                if (files == null) {
                    return false;
                }
                for (File f : files) {
                    if (f.isDirectory()) {
                        resultFlag = uploadWithMultiFiles(f);
                    } else {
                        resultFlag = uploadWithSingleFile(localFile);
                    }
                }
            } catch (IOException e) {
                LogManagerProvider.e("FTPHandler：uploadWithMultiFiles", "上传文件失败 " + e.getMessage());
            }
        } else {
            resultFlag = uploadWithSingleFile(localFile);
        }
        return resultFlag;
    }

    /**
     * 上传单个文件
     *
     * @param file 需要上传的文件
     * @return 如果上传成功返回true，否则返回false
     */
    private boolean uploadWithSingleFile(File file) {
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            /* storeFile:将本地文件上传到服务器
                1）如果服务器已经存在此文件，则不会重新覆盖,即不会再重新上传
                2）如果当前连接FTP服务器的用户没有写入的权限，则不会上传成功，但是也不会报错抛异常
            */
            mClient.storeFile(file.getName(), is);
            return true;
        } catch (IOException e) {
            LogManagerProvider.e("FTPHandler：uploadWithSingleFile", "上传单个文件失败 " + e.getMessage());
        } finally {
            IOUtils.closeStream(is);
        }
        return false;
    }

    /**
     * 获取本地保存文件
     *
     * @param downloadFileName 下载文件名
     * @param savePath         保存路径
     * @return 如果获取本地保存文件成功返回file（没有会试图创建），否则返回null
     */
    @Nullable
    private File getLocalSaveFile(@NonNull String downloadFileName, @NonNull String savePath, boolean isDirectory) {
        File directory = new File(savePath);
        if (directory.isDirectory() && !directory.exists()) {
            if (handleFileResultIsError(directory.mkdirs(), "创建文件失败")) {
                return null;
            }
        }
        File localFile = new File(savePath + File.separator + downloadFileName);
        if (localFile.exists()) {
            if (handleFileResultIsError(IOUtils.deleteDirectory(localFile), "删除已有文件失败")) {
                return null;
            }
        }
        try {
            boolean createFlag;
            if (isDirectory) {
                createFlag = localFile.mkdirs();
            } else {
                createFlag = localFile.createNewFile();
            }
            if (handleFileResultIsError(createFlag, "创建文件失败")) {
                return null;
            }
        } catch (IOException e) {
            LogManagerProvider.e("FTPHandler：getLocalSaveFile", "获取本地保存文件失败 " + e.getMessage());
        }
        return localFile;
    }

    @Override
    @NonNull
    public List<FTPFile> listFiles(@NonNull String remotePath) {
        List<FTPFile> list = new ArrayList<>();
        if (checkFTPClientIsError()) {
            return list;
        }
        try {
            FTPFile[] files = mClient.listFiles(remotePath);
            list.addAll(Arrays.asList(files));
        } catch (IOException e) {
            LogManagerProvider.e("FTPHandler：listFiles", e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 处理文件结果
     *
     * @param resultFlag 文件结果标志
     * @param msg        提示信息
     * @return 如果文件结果错误返回true，否则返回false
     */
    private boolean handleFileResultIsError(boolean resultFlag, @NonNull String msg) {
        if (!resultFlag) {
            LogManagerProvider.e("FTPHandler：", msg);
            return true;
        }
        return false;
    }

    /**
     * 检查文件是否是可用的目录
     *
     * @param file 被检查的文件
     * @return 如果文件是可用的目录返回true，否则返回false
     */
    private boolean checkIsAvailableDirectory(@NonNull FTPFile file) {
        return !checkIsInvalidFile(file.getName()) &&
                file.isDirectory();
    }

    /**
     * 检查文件是否是无效的文件
     *
     * @param name 文件名
     * @return 如果文件是无效的返回true，否则返回false
     */
    private boolean checkIsInvalidFile(@NonNull String name) {
        return PATH_CURRENT_DIRECTORY.equals(name) ||
                PATH_PREVIOUS_DIRECTORY.equals(name);
    }

    /**
     * 检查FTPClient是否处于可用状态
     *
     * @return 如果不可用返回true，否则返回false
     */
    private boolean checkFTPClientIsError() {
        if (!mClient.isConnected() || !mClient.isAvailable()) {
            if (mCallback != null) {
                mCallback.onResponseFailure(new FTPException("FTPHandler：FTPClient不可用"));
            }
            return true;
        }
        return false;
    }
}
