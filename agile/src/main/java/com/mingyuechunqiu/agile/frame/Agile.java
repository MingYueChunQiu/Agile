package com.mingyuechunqiu.agile.frame;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;
import com.mingyuechunqiu.agile.frame.engine.image.IImageEngine;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/11/7
 *     desc   : 框架主类
 *     version: 1.0
 * </pre>
 */
public final class Agile {

    private volatile Context mApplicationContext;//上下文对象
    private AgileFrameConfigure mConfigure;//配置信息对象
    private boolean debug;//标记是否处于debug模式

    private Agile() {
        mConfigure = new AgileFrameConfigure.Builder().build();
    }

    /**
     * 进行框架初始化，需要在application中进行初始化，必须最先调用，否则可能会报错
     *
     * @param context 传入上下文
     */
    public static void init(@NonNull Context context) {
        AgileHolder.sInstance.mApplicationContext = context.getApplicationContext() != null ? context.getApplicationContext() : context;
        debug(false);
    }

    /**
     * 获取主应用的全局context
     *
     * @return 返回全局上下文
     */
    public static Context getAppContext() {
        if (AgileHolder.sInstance.mApplicationContext == null) {
            throw new IllegalArgumentException("Context has not been initialized!");
        }
        return AgileHolder.sInstance.mApplicationContext;
    }

    /**
     * 设置框架配置
     *
     * @param configure 配置对象
     */
    public static void setConfigure(@Nullable AgileFrameConfigure configure) {
        if (configure == null) {
            return;
        }
        AgileHolder.sInstance.mConfigure = configure;
    }

    /**
     * 获取框架配置
     *
     * @return 返回配置对象
     */
    @NonNull
    public static AgileFrameConfigure getConfigure() {
        return AgileHolder.sInstance.mConfigure;
    }

    /**
     * 获取框架图片加载引擎（由用户实现具体子类，没有设置直接调用将抛出异常）
     *
     * @return 返回图片加载引擎接口
     */
    @NonNull
    public static IImageEngine getImageEngine() {
        IImageEngine engine = getConfigure().getImageEngine();
        if (engine == null) {
            throw new IllegalStateException("ImageEngine must be set first");
        }
        return engine;
    }

    /**
     * 设置调试模式（在init方法后调用，否则会被init里的debug方法调用覆盖）
     *
     * @param debug 是否开启调试模式
     */
    public static void debug(boolean debug) {
        AgileHolder.sInstance.debug = debug;
        LogManagerProvider.showLog(debug);
    }

    public static boolean isDebug() {
        return AgileHolder.sInstance.debug;
    }

    /**
     * 线程安全
     */
    private static class AgileHolder {

        private static final Agile sInstance = new Agile();
    }
}
