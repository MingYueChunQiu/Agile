package com.mingyuechunqiu.agilemvpframe.agile;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mingyuechunqiu.agilemvpframe.feature.logmanager.LogManagerProvider;
import com.mingyuechunqiu.agilemvpframe.framework.engine.IImageEngine;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/11/7
 *     desc   : 框架主类
 *     version: 1.0
 * </pre>
 */
public class AgileMVPFrame {

    private static final AgileMVPFrame INSTANCE;//单例

    private Context mContext;//上下文对象
    private AgileMVPFrameConfigure mConfigure;//配置信息对象
    private boolean mDebug;//标记是否处于debug模式

    static {
        INSTANCE = new AgileMVPFrame();
    }

    private AgileMVPFrame() {
        mConfigure = new AgileMVPFrameConfigure.Builder().build();
    }

    /**
     * 进行框架初始化，需要在application中进行初始化
     *
     * @param context 传入上下文
     */
    public static void init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context can not be null!");
        }
        INSTANCE.mContext = context;
        debug(false);
    }

    /**
     * 获取主应用的全局context
     *
     * @return 返回全局上下文
     */
    public static Context getAppContext() {
        if (INSTANCE.mContext == null) {
            throw new IllegalArgumentException("Context has not been initialized!");
        }
        return INSTANCE.mContext;
    }

    /**
     * 设置框架配置
     *
     * @param configure 配置对象
     */
    public static void setConfigure(AgileMVPFrameConfigure configure) {
        if (configure == null) {
            return;
        }
        INSTANCE.mConfigure = configure;
    }

    /**
     * 获取框架配置
     *
     * @return 返回配置对象
     */
    @NonNull
    public static AgileMVPFrameConfigure getConfigure() {
        return INSTANCE.mConfigure;
    }

    /**
     * 设置调试模式（在init方法后调用，否则会被init里的debug方法调用覆盖）
     *
     * @param debug 是否开启调试模式
     */
    public static void debug(boolean debug) {
        INSTANCE.mDebug = debug;
        LogManagerProvider.showLog(debug);
    }

    public static boolean isDebug() {
        return INSTANCE.mDebug;
    }

    public static IImageEngine getImageEngine() {
        return INSTANCE.mConfigure.getImageEngine();
    }
}
