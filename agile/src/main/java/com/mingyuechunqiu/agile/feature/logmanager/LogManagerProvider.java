package com.mingyuechunqiu.agile.feature.logmanager;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/3/1
 *     desc   : 日志管理提供者
 *     version: 1.0
 * </pre>
 */
public final class LogManagerProvider {

    //日志存储所在文件夹
    public static final String DIRECTORY_LOG_NAME = "Log";
    public static final String SUFFIX_LOG = ".log";

    public static final int VERBOSE = 4;
    public static final int DEBUG = 3;
    public static final int INFO = 2;
    public static final int WARN = 1;
    public static final int ERROR = 0;
    public static final int HIDDEN = -1;//隐藏日志级别

    private static volatile ILogManager sInstance;

    private LogManagerProvider() {
    }

    public static void setLogManager(@NonNull ILogManager logManager) {
        sInstance = logManager;
    }

    @NonNull
    public static ILogManager getLogManager() {
        checkOrCreateLogManager();
        return sInstance;
    }

    public static int getCurrentLogLevel() {
        checkOrCreateLogManager();
        return sInstance.getCurrentLogLevel();
    }

    public static void setCurrentLogLevel(int currentLogLevel) {
        checkOrCreateLogManager();
        sInstance.setCurrentLogLevel(currentLogLevel);
    }

    public static synchronized void showLog(boolean showLog) {
        checkOrCreateLogManager();
        sInstance.showLog(showLog);
    }

    public static void v(@NonNull String tag, @Nullable String msg) {
        checkOrCreateLogManager();
        sInstance.v(tag, TextUtils.isEmpty(msg) ? "传入msg为空" : msg);
    }

    public static void saveVerboseToLocal(@NonNull String tag, @Nullable String msg, @NonNull String title, @Nullable String filePath) {
        saveVerboseToLocal(tag, msg, title, filePath, false);
    }

    public static void saveVerboseToLocal(@NonNull String tag, @Nullable String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch) {
        checkOrCreateLogManager();
        sInstance.saveVerboseToLocal(tag, TextUtils.isEmpty(msg) ? "传入msg为空" : msg, title, filePath, ignoreLogSwitch);
    }

    public static void d(@NonNull String tag, @Nullable String msg) {
        checkOrCreateLogManager();
        sInstance.d(tag, TextUtils.isEmpty(msg) ? "传入msg为空" : msg);
    }

    public static void saveDebugToLocal(@NonNull String tag, @Nullable String msg, @NonNull String title, @Nullable String filePath) {
        saveDebugToLocal(tag, msg, title, filePath, false);
    }

    public static void saveDebugToLocal(@NonNull String tag, @Nullable String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch) {
        checkOrCreateLogManager();
        sInstance.saveDebugToLocal(tag, TextUtils.isEmpty(msg) ? "传入msg为空" : msg, title, filePath, ignoreLogSwitch);
    }

    public static void i(@NonNull String tag, @Nullable String msg) {
        checkOrCreateLogManager();
        sInstance.i(tag, TextUtils.isEmpty(msg) ? "传入msg为空" : msg);
    }

    public static void saveInfoToLocal(@NonNull String tag, @Nullable String msg, @NonNull String title, @Nullable String filePath) {
        saveInfoToLocal(tag, msg, title, filePath, false);
    }

    public static void saveInfoToLocal(@NonNull String tag, @Nullable String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch) {
        checkOrCreateLogManager();
        sInstance.saveInfoToLocal(tag, TextUtils.isEmpty(msg) ? "传入msg为空" : msg, title, filePath, ignoreLogSwitch);
    }

    public static void w(@NonNull String tag, @Nullable String msg) {
        checkOrCreateLogManager();
        sInstance.w(tag, TextUtils.isEmpty(msg) ? "传入msg为空" : msg);
    }

    public static void saveWarnToLocal(@NonNull String tag, @Nullable String msg, @NonNull String title, @Nullable String filePath) {
        saveWarnToLocal(tag, msg, title, filePath, false);
    }

    public static void saveWarnToLocal(@NonNull String tag, @Nullable String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch) {
        checkOrCreateLogManager();
        sInstance.saveWarnToLocal(tag, TextUtils.isEmpty(msg) ? "传入msg为空" : msg, title, filePath, ignoreLogSwitch);
    }

    public static void e(@NonNull String tag, @Nullable String msg) {
        checkOrCreateLogManager();
        sInstance.e(tag, TextUtils.isEmpty(msg) ? "传入msg为空" : msg);
    }

    public static void saveErrorToLocal(@NonNull String tag, @Nullable String msg, @NonNull String title, @Nullable String filePath) {
        saveErrorToLocal(tag, msg, title, filePath, false);
    }

    public static void saveErrorToLocal(@NonNull String tag, @Nullable String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch) {
        checkOrCreateLogManager();
        sInstance.saveErrorToLocal(tag, TextUtils.isEmpty(msg) ? "传入msg为空" : msg, title, filePath, ignoreLogSwitch);
    }

    /**
     * 检查或者创建日志管理实例
     */
    private static void checkOrCreateLogManager() {
        if (sInstance == null) {
            synchronized (LogManagerProvider.class) {
                if (sInstance == null) {
                    sInstance = new LogManager();
                }
            }
        }
    }
}
