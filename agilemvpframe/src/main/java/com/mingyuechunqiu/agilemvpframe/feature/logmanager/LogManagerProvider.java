package com.mingyuechunqiu.agilemvpframe.feature.logmanager;

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
public class LogManagerProvider {

    //日志存储所在文件夹
    public static final String DIRECTORY_LOG_NAME = "Log";
    public static final String SUFFIX_LOG = ".log";

    public static final int VERBOSE = 4;
    public static final int DEBUG = 3;
    public static final int INFO = 2;
    public static final int WARN = 1;
    public static final int ERROR = 0;
    public static final int HIDDEN = -1;//隐藏日志级别

    private static volatile LogManagerable sLogManagerable;

    public static void setLogManagerable(LogManagerable logManagerable) {
        sLogManagerable = logManagerable;
    }

    public static LogManagerable getLogManagerable() {
        return sLogManagerable;
    }

    public static int getCurrentLogLevel() {
        checkOrCreateLogManager();
        return sLogManagerable.getCurrentLogLevel();
    }

    public static void setCurrentLogLevel(int currentLogLevel) {
        checkOrCreateLogManager();
        sLogManagerable.setCurrentLogLevel(currentLogLevel);
    }

    public static synchronized void showLog(boolean showLog) {
        checkOrCreateLogManager();
        sLogManagerable.showLog(showLog);
    }

    public static void v(String tag, String msg) {
        checkOrCreateLogManager();
        sLogManagerable.v(tag, msg);
    }

    public static void saveVerboseToLocal(String tag, String msg, String title, String filePath) {
        checkOrCreateLogManager();
        sLogManagerable.saveVerboseToLocal(tag, msg, title, filePath);
    }

    public static void d(String tag, String msg) {
        checkOrCreateLogManager();
        sLogManagerable.d(tag, msg);
    }

    public static void saveDebugToLocal(String tag, String msg, String title, String filePath) {
        checkOrCreateLogManager();
        sLogManagerable.saveDebugToLocal(tag, msg, title, filePath);
    }

    public static void i(String tag, String msg) {
        checkOrCreateLogManager();
        sLogManagerable.i(tag, msg);
    }

    public static void saveInfoToLocal(String tag, String msg, String title, String filePath) {
        checkOrCreateLogManager();
        sLogManagerable.saveInfoToLocal(tag, msg, title, filePath);
    }

    public static void w(String tag, String msg) {
        checkOrCreateLogManager();
        sLogManagerable.w(tag, msg);
    }

    public static void saveWarnToLocal(String tag, String msg, String title, String filePath) {
        checkOrCreateLogManager();
        sLogManagerable.saveWarnToLocal(tag, msg, title, filePath);
    }

    public static void e(String tag, String msg) {
        checkOrCreateLogManager();
        sLogManagerable.e(tag, msg);
    }

    public static void saveErrorToLocal(String tag, String msg, String title, String filePath) {
        checkOrCreateLogManager();
        sLogManagerable.saveErrorToLocal(tag, msg, title, filePath);
    }

    /**
     * 检查或者创建日志管理实例
     */
    private static void checkOrCreateLogManager() {
        if (sLogManagerable == null) {
            synchronized (LogManagerProvider.class) {
                if (sLogManagerable == null) {
                    sLogManagerable = new LogManager();
                }
            }
        }
    }
}
