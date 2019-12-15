package com.mingyuechunqiu.agile.feature.logmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/3/1
 *     desc   : 日志管理类
 *              实现ILogManager
 *     version: 1.0
 * </pre>
 */
class LogManager implements ILogManager {

    private ILog mLog;

    LogManager() {
        mLog = new LogUtils();
    }

    @Override
    public int getCurrentLogLevel() {
        return mLog.getCurrentLogLevel();
    }

    @Override
    public void setCurrentLogLevel(int currentLogLevel) {
        mLog.setCurrentLogLevel(currentLogLevel);
    }

    @Override
    public void showLog(boolean showLog) {
        mLog.showLog(showLog);
    }

    @Override
    public void v(@NonNull String tag, @NonNull String msg) {
        mLog.v(tag, msg);
    }

    @Override
    public void saveVerboseToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath) {
        mLog.saveVerboseToLocal(tag, msg, title, filePath);
    }

    @Override
    public void saveVerboseToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch) {
        mLog.saveVerboseToLocal(tag, msg, title, filePath, ignoreLogSwitch);
    }

    @Override
    public void d(@NonNull String tag, @NonNull String msg) {
        mLog.d(tag, msg);
    }

    @Override
    public void saveDebugToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath) {
        mLog.saveDebugToLocal(tag, msg, title, filePath);
    }

    @Override
    public void saveDebugToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch) {
        mLog.saveDebugToLocal(tag, msg, title, filePath, ignoreLogSwitch);
    }

    @Override
    public void i(@NonNull String tag, @NonNull String msg) {
        mLog.i(tag, msg);
    }

    @Override
    public void saveInfoToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath) {
        mLog.saveInfoToLocal(tag, msg, title, filePath);
    }

    @Override
    public void saveInfoToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch) {
        mLog.saveInfoToLocal(tag, msg, title, filePath, ignoreLogSwitch);
    }

    @Override
    public void w(@NonNull String tag, @NonNull String msg) {
        mLog.w(tag, msg);
    }

    @Override
    public void saveWarnToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath) {
        mLog.saveWarnToLocal(tag, msg, title, filePath);
    }

    @Override
    public void saveWarnToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch) {
        mLog.saveWarnToLocal(tag, msg, title, filePath, ignoreLogSwitch);
    }

    @Override
    public void e(@NonNull String tag, @NonNull String msg) {
        mLog.e(tag, msg);
    }

    @Override
    public void saveErrorToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath) {
        mLog.saveErrorToLocal(tag, msg, title, filePath);
    }

    @Override
    public void saveErrorToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch) {
        mLog.saveErrorToLocal(tag, msg, title, filePath, ignoreLogSwitch);
    }
}
