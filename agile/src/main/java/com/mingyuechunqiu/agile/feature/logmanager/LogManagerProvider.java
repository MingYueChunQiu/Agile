package com.mingyuechunqiu.agile.feature.logmanager;

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

    private static volatile LogManagerable sInstance;

    private LogManagerProvider() {
    }

    public static void setLogManagerable(LogManagerable logManagerable) {
        sInstance = logManagerable;
    }

    public static LogManagerable getLogManagerable() {
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

    public static void v(String tag, String msg) {
        checkOrCreateLogManager();
        sInstance.v(tag, msg);
    }

    public static void saveVerboseToLocal(String tag, String msg, String title, String filePath) {
        saveVerboseToLocal(tag, msg, title, filePath, false);
    }

    public static void saveVerboseToLocal(String tag, String msg, String title, String filePath, boolean ignoreLogSwitch) {
        checkOrCreateLogManager();
        sInstance.saveVerboseToLocal(tag, msg, title, filePath, ignoreLogSwitch);
    }

    public static void d(String tag, String msg) {
        checkOrCreateLogManager();
        sInstance.d(tag, msg);
    }

    public static void saveDebugToLocal(String tag, String msg, String title, String filePath) {
        saveDebugToLocal(tag, msg, title, filePath, false);
    }

    public static void saveDebugToLocal(String tag, String msg, String title, String filePath, boolean ignoreLogSwitch) {
        checkOrCreateLogManager();
        sInstance.saveDebugToLocal(tag, msg, title, filePath, ignoreLogSwitch);
    }

    public static void i(String tag, String msg) {
        checkOrCreateLogManager();
        sInstance.i(tag, msg);
    }

    public static void saveInfoToLocal(String tag, String msg, String title, String filePath) {
        saveInfoToLocal(tag, msg, title, filePath, false);
    }

    public static void saveInfoToLocal(String tag, String msg, String title, String filePath, boolean ignoreLogSwitch) {
        checkOrCreateLogManager();
        sInstance.saveInfoToLocal(tag, msg, title, filePath, ignoreLogSwitch);
    }

    public static void w(String tag, String msg) {
        checkOrCreateLogManager();
        sInstance.w(tag, msg);
    }

    public static void saveWarnToLocal(String tag, String msg, String title, String filePath) {
        saveWarnToLocal(tag, msg, title, filePath, false);
    }

    public static void saveWarnToLocal(String tag, String msg, String title, String filePath, boolean ignoreLogSwitch) {
        checkOrCreateLogManager();
        sInstance.saveWarnToLocal(tag, msg, title, filePath, ignoreLogSwitch);
    }

    public static void e(String tag, String msg) {
        checkOrCreateLogManager();
        sInstance.e(tag, msg);
    }

    public static void saveErrorToLocal(String tag, String msg, String title, String filePath) {
        saveErrorToLocal(tag, msg, title, filePath, false);
    }

    public static void saveErrorToLocal(String tag, String msg, String title, String filePath, boolean ignoreLogSwitch) {
        checkOrCreateLogManager();
        sInstance.saveErrorToLocal(tag, msg, title, filePath, ignoreLogSwitch);
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
