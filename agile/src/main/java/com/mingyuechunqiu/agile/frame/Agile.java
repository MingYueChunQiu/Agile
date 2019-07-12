package com.mingyuechunqiu.agile.frame;

import android.content.Context;
import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/11/7
 *     desc   : 框架主类
 *     version: 1.0
 * </pre>
 */
public class Agile {

    private static final Agile INSTANCE = new Agile();//单例

    private volatile Context mApplicationContext;//上下文对象
    private AgileFrameConfigure mConfigure;//配置信息对象
    private boolean mDebug;//标记是否处于debug模式

    private Agile() {
        mConfigure = new AgileFrameConfigure.Builder().build();
    }

    /**
     * 进行框架初始化，需要在application中进行初始化，必须最先调用，否则可能会报错
     *
     * @param context 传入上下文
     */
    public static void init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context can not be null!");
        }
        INSTANCE.mApplicationContext = context.getApplicationContext() != null ? context.getApplicationContext() : context;
        debug(false);
    }

    /**
     * 获取主应用的全局context
     *
     * @return 返回全局上下文
     */
    public static Context getAppContext() {
        if (INSTANCE.mApplicationContext == null) {
            throw new IllegalArgumentException("Context has not been initialized!");
        }
        return INSTANCE.mApplicationContext;
    }

    /**
     * 设置框架配置
     *
     * @param configure 配置对象
     */
    public static void setConfigure(AgileFrameConfigure configure) {
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
    public static AgileFrameConfigure getConfigure() {
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
}
