package com.mingyuechunqiu.agilemvpframe.agile;

import android.content.Context;
import android.content.Intent;

import com.mingyuechunqiu.agilemvpframe.data.remote.retrofit.controller.BaseRetrofitManager;
import com.mingyuechunqiu.agilemvpframe.service.NetworkStateService;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/11/7
 *     desc   : 框架类
 *     version: 1.0
 * </pre>
 */
public class AgileMVPFrame {

    private static Context sContext;
    private static AgileMVPFrameConfigure sConfigure;

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
     * 释放资源
     */
    public static void releaseResource() {
        sConfigure = null;
        sContext = null;
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
        BaseRetrofitManager.setTimeout(sConfigure.getNetTimeout());
    }

    /**
     * 获取框架配置
     *
     * @return 返回配置对象
     */
    public static AgileMVPFrameConfigure getConfigure() {
        return sConfigure;
    }

}
