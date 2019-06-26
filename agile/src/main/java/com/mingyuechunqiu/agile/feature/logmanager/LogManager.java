package com.mingyuechunqiu.agile.feature.logmanager;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/3/1
 *     desc   : 日志管理类
 *              实现LogManagerable
 *     version: 1.0
 * </pre>
 */
class LogManager implements LogManagerable {

    private Logable mLogable;

    LogManager() {
        mLogable = new LogUtils();
    }

    @Override
    public int getCurrentLogLevel() {
        return mLogable.getCurrentLogLevel();
    }

    @Override
    public void setCurrentLogLevel(int currentLogLevel) {
        mLogable.setCurrentLogLevel(currentLogLevel);
    }

    @Override
    public void showLog(boolean showLog) {
        mLogable.showLog(showLog);
    }

    @Override
    public void v(String tag, String msg) {
        mLogable.v(tag, msg);
    }

    @Override
    public void saveVerboseToLocal(String tag, String msg, String title, String filePath) {
        mLogable.saveVerboseToLocal(tag, msg, title, filePath);
    }

    @Override
    public void saveVerboseToLocal(String tag, String msg, String title, String filePath, boolean ignoreLogSwitch) {
        mLogable.saveVerboseToLocal(tag, msg, title, filePath, ignoreLogSwitch);
    }

    @Override
    public void d(String tag, String msg) {
        mLogable.d(tag, msg);
    }

    @Override
    public void saveDebugToLocal(String tag, String msg, String title, String filePath) {
        mLogable.saveDebugToLocal(tag, msg, title, filePath);
    }

    @Override
    public void saveDebugToLocal(String tag, String msg, String title, String filePath, boolean ignoreLogSwitch) {
        mLogable.saveDebugToLocal(tag, msg, title, filePath, ignoreLogSwitch);
    }

    @Override
    public void i(String tag, String msg) {
        mLogable.i(tag, msg);
    }

    @Override
    public void saveInfoToLocal(String tag, String msg, String title, String filePath) {
        mLogable.saveInfoToLocal(tag, msg, title, filePath);
    }

    @Override
    public void saveInfoToLocal(String tag, String msg, String title, String filePath, boolean ignoreLogSwitch) {
        mLogable.saveInfoToLocal(tag, msg, title, filePath, ignoreLogSwitch);
    }

    @Override
    public void w(String tag, String msg) {
        mLogable.w(tag, msg);
    }

    @Override
    public void saveWarnToLocal(String tag, String msg, String title, String filePath) {
        mLogable.saveWarnToLocal(tag, msg, title, filePath);
    }

    @Override
    public void saveWarnToLocal(String tag, String msg, String title, String filePath, boolean ignoreLogSwitch) {
        mLogable.saveWarnToLocal(tag, msg, title, filePath, ignoreLogSwitch);
    }

    @Override
    public void e(String tag, String msg) {
        mLogable.e(tag, msg);
    }

    @Override
    public void saveErrorToLocal(String tag, String msg, String title, String filePath) {
        mLogable.saveErrorToLocal(tag, msg, title, filePath);
    }

    @Override
    public void saveErrorToLocal(String tag, String msg, String title, String filePath, boolean ignoreLogSwitch) {
        mLogable.saveErrorToLocal(tag, msg, title, filePath, ignoreLogSwitch);
    }
}
