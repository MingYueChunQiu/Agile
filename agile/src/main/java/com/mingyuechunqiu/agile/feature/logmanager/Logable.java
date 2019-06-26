package com.mingyuechunqiu.agile.feature.logmanager;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/3/1
 *     desc   : 日志功能接口
 *     version: 1.0
 * </pre>
 */
public interface Logable {

    int getCurrentLogLevel();

    /**
     * 设置当前日志等级
     *
     * @param currentLogLevel 当前日志等级
     */
    void setCurrentLogLevel(int currentLogLevel);

    /**
     * 显示日志信息（正式上线时设置隐藏日志信息）
     *
     * @param showLog 是否显示日志信息
     */
    void showLog(boolean showLog);

    void v(String tag, String msg);

    /**
     * 将日志写入到指定文件中，是否启动根据是否处于调试模式决定
     *
     * @param tag      标签
     * @param msg      信息
     * @param title    日志标题
     * @param filePath 日志存储文件
     */
    void saveVerboseToLocal(String tag, String msg, String title, String filePath);

    void saveVerboseToLocal(String tag, String msg, String title, String filePath, boolean ignoreLogSwitch);

    void d(String tag, String msg);

    void saveDebugToLocal(String tag, String msg, String title, String filePath);

    void saveDebugToLocal(String tag, String msg, String title, String filePath, boolean ignoreLogSwitch);

    void i(String tag, String msg);

    void saveInfoToLocal(String tag, String msg, String title, String filePath);

    void saveInfoToLocal(String tag, String msg, String title, String filePath, boolean ignoreLogSwitch);

    void w(String tag, String msg);

    void saveWarnToLocal(String tag, String msg, String title, String filePath);

    void saveWarnToLocal(String tag, String msg, String title, String filePath, boolean ignoreLogSwitch);

    void e(String tag, String msg);

    void saveErrorToLocal(String tag, String msg, String title, String filePath);

    void saveErrorToLocal(String tag, String msg, String title, String filePath, boolean ignoreLogSwitch);
}
