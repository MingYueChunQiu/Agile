package com.mingyuechunqiu.agile.frame;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.BuildConfig;
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;
import com.mingyuechunqiu.agile.frame.engine.image.IAgileImageEngine;
import com.mingyuechunqiu.agile.frame.lifecycle.AgileLifecycleDispatcher;
import com.mingyuechunqiu.agile.frame.observer.AgileLogObserver;
import com.mingyuechunqiu.agile.frame.observer.IAgileFrameObserver;

import java.util.ArrayList;
import java.util.List;

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

    private volatile static Agile sInstance = null;
    @NonNull
    private final Context mApplicationContext;//上下文对象
    @NonNull
    private AgileFrameConfigure mConfigure;//配置信息对象
    private boolean debug;//标记是否处于debug模式
    @NonNull
    private final AgileLifecycleDispatcher mLifecycleDispatcher = AgileLifecycleDispatcher.INSTANCE;
    @NonNull
    private final List<IAgileFrameObserver> mAgileFrameObserverList = new ArrayList<>();

    private Agile(@NonNull Context context, @Nullable AgileFrameConfigure configure) {
        mApplicationContext = context.getApplicationContext();
        if (configure == null) {
            mConfigure = new AgileFrameConfigure.Builder().build();
        } else {
            mConfigure = configure;
        }
        mAgileFrameObserverList.add(new AgileLogObserver());
    }

    /**
     * 进行框架初始化，需要在application中进行初始化，必须最先调用，否则可能会报错
     *
     * @param context 传入上下文
     */
    public static void init(@NonNull Context context) {
        init(context, null);
    }

    /**
     * 进行框架初始化，需要在application中进行初始化，必须最先调用，否则可能会报错
     *
     * @param context   传入上下文
     * @param configure 框架配置
     */
    public static void init(@NonNull Context context, @Nullable AgileFrameConfigure configure) {
        init(context, configure, BuildConfig.DEBUG);
    }

    /**
     * 进行框架初始化，需要在application中进行初始化，必须最先调用，否则可能会报错
     *
     * @param context   传入上下文
     * @param configure 框架配置
     * @param isDebug   是否处于调试模式
     */
    public static void init(@NonNull Context context, @Nullable AgileFrameConfigure configure, boolean isDebug) {
        if (sInstance == null) {
            synchronized (Agile.class) {
                if (sInstance == null) {
                    sInstance = new Agile(context, configure);
                }
            }
        }
        debug(isDebug);
        setConfigure(configure);
        for (IAgileFrameObserver observer : sInstance.mAgileFrameObserverList) {
            observer.onFrameInit();
        }
    }

    /**
     * 获取主应用的全局context
     *
     * @return 返回全局上下文
     */
    @NonNull
    public static Context getAppContext() {
        return sInstance.mApplicationContext;
    }

    /**
     * 设置框架配置
     *
     * @param configure 配置对象
     */
    @NonNull
    public static Agile setConfigure(@Nullable AgileFrameConfigure configure) {
        if (configure != null) {
            sInstance.mConfigure = configure;
        }
        return Agile.sInstance;
    }

    /**
     * 获取框架配置
     *
     * @return 返回配置对象
     */
    @NonNull
    public static AgileFrameConfigure getConfigure() {
        return sInstance.mConfigure;
    }

    /**
     * 获取生命周期分发器（单例）
     *
     * @return 返回分发器对象
     */
    @NonNull
    public static AgileLifecycleDispatcher getLifecycleDispatcher() {
        return sInstance.mLifecycleDispatcher;
    }

    /**
     * 获取框架图片加载引擎（由用户实现具体子类，没有设置直接调用将抛出异常）
     *
     * @return 返回图片加载引擎接口
     */
    @NonNull
    public static IAgileImageEngine getImageEngine() {
        IAgileImageEngine engine = getConfigure().getImageEngine();
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
    @NonNull
    public static Agile debug(boolean debug) {
        sInstance.debug = debug;
        LogManagerProvider.showLog(debug);
        return Agile.sInstance;
    }

    public static boolean isDebug() {
        return sInstance.debug;
    }
}
