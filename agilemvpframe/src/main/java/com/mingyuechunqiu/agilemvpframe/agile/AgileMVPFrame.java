package com.mingyuechunqiu.agilemvpframe.agile;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mingyuechunqiu.agilemvpframe.feature.logmanager.LogManagerProvider;

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

    private static Context sContext;//上下文对象
    private static volatile AgileMVPFrameConfigure sConfigure;//配置信息对象
    private static boolean debug;//标记是否处于debug模式

    /**
     * 进行框架初始化，需要在application中进行初始化
     *
     * @param context 传入上下文
     */
    public static void init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context can not be null!");
        }
        sContext = context;
        debug(false);
    }

    /**
     * 获取主应用的全局context
     *
     * @return 返回全局上下文
     */
    public static Context getAppContext() {
        if (sContext == null) {
            throw new IllegalArgumentException("Context has not been initialized!");
        }
        return sContext;
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
        sConfigure = configure;
    }

    /**
     * 获取框架配置
     *
     * @return 返回配置对象
     */
    @NonNull
    public static AgileMVPFrameConfigure getConfigure() {
        if (sConfigure == null) {
            synchronized (AgileMVPFrame.class) {
                if (sConfigure == null) {
                    sConfigure = new AgileMVPFrameConfigure.Builder().build();
                }
            }
        }
        return sConfigure;
    }

    /**
     * 设置调试模式（在init方法后调用，否则会被init里的debug方法调用覆盖）
     *
     * @param debug 是否开启调试模式
     */
    public static void debug(boolean debug) {
        AgileMVPFrame.debug = debug;
        LogManagerProvider.showLog(debug);
    }

    public static boolean isDebug() {
        return debug;
    }

}
