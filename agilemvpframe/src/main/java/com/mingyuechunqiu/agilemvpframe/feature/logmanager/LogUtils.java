package com.mingyuechunqiu.agilemvpframe.feature.logmanager;

import android.Manifest;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.mingyuechunqiu.agilemvpframe.agile.AgileMVPFrame;
import com.mingyuechunqiu.agilemvpframe.io.IOUtils;

import java.io.File;

import pub.devrel.easypermissions.EasyPermissions;

import static com.mingyuechunqiu.agilemvpframe.feature.logmanager.LogManagerProvider.DEBUG;
import static com.mingyuechunqiu.agilemvpframe.feature.logmanager.LogManagerProvider.DIRECTORY_LOG_NAME;
import static com.mingyuechunqiu.agilemvpframe.feature.logmanager.LogManagerProvider.ERROR;
import static com.mingyuechunqiu.agilemvpframe.feature.logmanager.LogManagerProvider.HIDDEN;
import static com.mingyuechunqiu.agilemvpframe.feature.logmanager.LogManagerProvider.INFO;
import static com.mingyuechunqiu.agilemvpframe.feature.logmanager.LogManagerProvider.SUFFIX_LOG;
import static com.mingyuechunqiu.agilemvpframe.feature.logmanager.LogManagerProvider.VERBOSE;
import static com.mingyuechunqiu.agilemvpframe.feature.logmanager.LogManagerProvider.WARN;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/3/1
 *     desc   : 日志工具类
 *              实现Logable
 *     version: 1.0
 * </pre>
 */
class LogUtils implements Logable {

    //申请权限
    private static final String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private int current = VERBOSE;//记录当前日志记录级别，当正式上线时，将日志屏蔽

    @Override
    public int getCurrentLogLevel() {
        return current;
    }

    @Override
    public void setCurrentLogLevel(int currentLogLevel) {
        current = currentLogLevel;
    }

    @Override
    public void showLog(boolean showLog) {
        current = showLog ? VERBOSE : HIDDEN;
    }

    @Override
    public void v(String tag, String msg) {
        if (checkLogIsInvalid(tag, msg, VERBOSE)) {
            return;
        }
        Log.v(tag, msg);
    }

    @Override
    public void saveVerboseToLocal(String tag, String msg, String title, String filePath) {
        if (checkLogIsInvalid(tag, msg, VERBOSE)) {
            return;
        }
        saveLogToLocalFile(msg, title, filePath);
    }

    @Override
    public void d(String tag, String msg) {
        if (checkLogIsInvalid(tag, msg, DEBUG)) {
            return;
        }
        Log.d(tag, msg);
    }

    @Override
    public void saveDebugToLocal(String tag, String msg, String title, String filePath) {
        if (checkLogIsInvalid(tag, msg, DEBUG)) {
            return;
        }
        saveLogToLocalFile(msg, title, filePath);
    }

    @Override
    public void i(String tag, String msg) {
        if (checkLogIsInvalid(tag, msg, INFO)) {
            return;
        }
        Log.i(tag, msg);
    }

    @Override
    public void saveInfoToLocal(String tag, String msg, String title, String filePath) {
        if (checkLogIsInvalid(tag, msg, INFO)) {
            return;
        }
        saveLogToLocalFile(msg, title, filePath);
    }

    @Override
    public void w(String tag, String msg) {
        if (current < WARN) {
            return;
        }
        if (TextUtils.isEmpty(msg)) {
            Log.w(tag, "传入日志内容为空");
            return;
        }
        Log.v(tag, msg);
    }

    @Override
    public void saveWarnToLocal(String tag, String msg, String title, String filePath) {
        if (checkLogIsInvalid(tag, msg, WARN)) {
            return;
        }
        saveLogToLocalFile(msg, title, filePath);
    }

    @Override
    public void e(String tag, String msg) {
        if (checkLogIsInvalid(tag, msg, ERROR)) {
            return;
        }
        Log.e(tag, msg);
    }

    @Override
    public void saveErrorToLocal(String tag, String msg, String title, String filePath) {
        if (checkLogIsInvalid(tag, msg, ERROR)) {
            return;
        }
        saveLogToLocalFile(msg, title, filePath);
    }

    /**
     * 检查日志是否无效
     *
     * @param tag      标签
     * @param msg      日志信息
     * @param logLevel 日志等级
     * @return 如果无效返回true，否则返回false
     */
    private boolean checkLogIsInvalid(String tag, String msg, int logLevel) {
        if (current < logLevel) {
            return true;
        }
        if (TextUtils.isEmpty(msg)) {
            w(tag, "传入日志内容为空");
            return true;
        }
        return false;
    }

    /**
     * 获取默认日志文件存储路径
     *
     * @param title 标题
     * @return 返回默认文件存储路径
     */
    @Nullable
    private String getDefaultFilePath(String title) {
        String prefixPath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (!EasyPermissions.hasPermissions(AgileMVPFrame.getAppContext(), permissions)) {
                e("LogUtils", "请申请存储写入权限，以便进行日志写入");
            }
            File file = AgileMVPFrame.getAppContext().getExternalFilesDir(Environment.DIRECTORY_ALARMS);
            if (file != null) {
                prefixPath = file.getAbsolutePath();
            }
        }
        if (TextUtils.isEmpty(prefixPath)) {
            prefixPath = AgileMVPFrame.getAppContext().getFilesDir().getAbsolutePath();
        }
        File dir = new File(prefixPath + File.separator + DIRECTORY_LOG_NAME);
        boolean isMakingDirSuccess = true;
        if (!dir.exists()) {
            isMakingDirSuccess = dir.mkdir();
        }
        return isMakingDirSuccess ? dir.getAbsolutePath() + File.separator + title + "_" + System.currentTimeMillis() + SUFFIX_LOG : null;
    }

    /**
     * 保存日志记录到本地文件
     *
     * @param msg      日志信息
     * @param title    标题
     * @param filePath 文件路径
     */
    private void saveLogToLocalFile(String msg, String title, String filePath) {
        String destPath = filePath;
        if (TextUtils.isEmpty(filePath)) {
            destPath = getDefaultFilePath(title);
        }
        IOUtils.writeStringToLocalFile(title, msg, destPath);
    }
}
