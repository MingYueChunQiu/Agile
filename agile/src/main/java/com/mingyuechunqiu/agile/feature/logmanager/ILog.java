package com.mingyuechunqiu.agile.feature.logmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
public interface ILog {

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
    void saveVerboseToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath);

    void saveVerboseToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch);

    void d(@NonNull String tag, @NonNull String msg);

    void saveDebugToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath);

    void saveDebugToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch);

    void i(@NonNull String tag, @NonNull String msg);

    void saveInfoToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath);

    void saveInfoToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch);

    void w(@NonNull String tag, @NonNull String msg);

    void saveWarnToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath);

    void saveWarnToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch);

    void e(@NonNull String tag, String msg);

    void saveErrorToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath);

    void saveErrorToLocal(@NonNull String tag, @NonNull String msg, @NonNull String title, @Nullable String filePath, boolean ignoreLogSwitch);
}
