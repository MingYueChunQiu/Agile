package com.mingyuechunqiu.agile.feature.logmanager;

import static com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider.DEBUG;
import static com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider.DIRECTORY_LOG_NAME;
import static com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider.ERROR;
import static com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider.HIDDEN;
import static com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider.INFO;
import static com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider.SUFFIX_LOG;
import static com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider.VERBOSE;
import static com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider.WARN;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.frame.Agile;
import com.mingyuechunqiu.agile.io.IOUtils;

import java.io.File;

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
class LogUtils implements ILog {

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
    public void v(@NonNull String tag, @NonNull String msg) {
        if (checkLogIsInvalid(tag, msg, VERBOSE, false)) {
            return;
        }
        Log.v(tag, msg);
    }

    @Override
    public void saveVerboseToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath) {
        saveVerboseToLocal(tag, msg, title, filePath, false);
    }

    @Override
    public void saveVerboseToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch) {
        if (checkLogIsInvalid(tag, msg, VERBOSE, ignoreLogSwitch)) {
            return;
        }
        saveLogToLocalFile(msg, title, filePath);
    }

    @Override
    public void d(@NonNull String tag, @NonNull String msg) {
        if (checkLogIsInvalid(tag, msg, DEBUG, false)) {
            return;
        }
        Log.d(tag, msg);
    }

    @Override
    public void saveDebugToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath) {
        saveDebugToLocal(tag, msg, title, filePath, false);
    }

    @Override
    public void saveDebugToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch) {
        if (checkLogIsInvalid(tag, msg, DEBUG, ignoreLogSwitch)) {
            return;
        }
        saveLogToLocalFile(msg, title, filePath);
    }

    @Override
    public void i(@NonNull String tag, @NonNull String msg) {
        if (checkLogIsInvalid(tag, msg, INFO, false)) {
            return;
        }
        Log.i(tag, msg);
    }

    @Override
    public void saveInfoToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath) {
        saveInfoToLocal(tag, msg, title, filePath, false);
    }

    @Override
    public void saveInfoToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch) {
        if (checkLogIsInvalid(tag, msg, INFO, ignoreLogSwitch)) {
            return;
        }
        saveLogToLocalFile(msg, title, filePath);
    }

    @Override
    public void w(@NonNull String tag, @NonNull String msg) {
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
    public void saveWarnToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath) {
        saveWarnToLocal(tag, msg, title, filePath, false);
    }

    @Override
    public void saveWarnToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch) {
        if (checkLogIsInvalid(tag, msg, WARN, ignoreLogSwitch)) {
            return;
        }
        saveLogToLocalFile(msg, title, filePath);
    }

    @Override
    public void e(@NonNull String tag, @NonNull String msg) {
        if (checkLogIsInvalid(tag, msg, ERROR, false)) {
            return;
        }
        Log.e(tag, msg);
    }

    @Override
    public void saveErrorToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath) {
        saveErrorToLocal(tag, msg, title, filePath, false);
    }

    @Override
    public void saveErrorToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch) {
        if (checkLogIsInvalid(tag, msg, ERROR, ignoreLogSwitch)) {
            return;
        }
        saveLogToLocalFile(msg, title, filePath);
    }

    /**
     * 检查日志是否无效
     *
     * @param tag             标签
     * @param msg             日志信息
     * @param logLevel        日志等级
     * @param ignoreLogSwitch 是否忽略日志开关
     * @return 如果无效返回true，否则返回false
     */
    private boolean checkLogIsInvalid(@NonNull String tag, @NonNull String msg, int logLevel, boolean ignoreLogSwitch) {
        if (!ignoreLogSwitch && current < logLevel) {
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
    private String getDefaultFilePath(@NonNull String title) {
        String prefixPath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = Agile.getAppContext().getExternalCacheDir();
            if (file != null) {
                prefixPath = file.getAbsolutePath();
            }
        }
        if (TextUtils.isEmpty(prefixPath)) {
            prefixPath = Agile.getAppContext().getCacheDir().getAbsolutePath();
        }
        File dir = new File(prefixPath + File.separator + DIRECTORY_LOG_NAME);
        boolean isMakingDirSuccess = true;
        if (!dir.exists()) {
            isMakingDirSuccess = dir.mkdirs();
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
    private void saveLogToLocalFile(@NonNull String msg, @NonNull String title, @Nullable String filePath) {
        String destPath = filePath;
        if (TextUtils.isEmpty(filePath)) {
            destPath = getDefaultFilePath(title);
        }
        if (destPath == null || destPath.isEmpty()) {
            return;
        }
        IOUtils.writeStringToLocalFile(title, msg, destPath);
    }
}
