package com.mingyuechunqiu.agilemvpframeproject;

import android.app.Application;
import android.content.Context;

import com.mingyuechunqiu.agilemvpframe.agile.AgileMVPFrame;
import com.mingyuechunqiu.agilemvpframe.agile.AgileMVPFrameConfigure;
import com.mingyuechunqiu.agilemvpframe.agile.remote.AgileNetworkConfig;

import io.realm.Realm;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/18
 *     desc   : 应用类
 *              继承自Application
 *     version: 1.0
 * </pre>
 */
public class MyApplication extends Application {

    private static Context sContext;

    /**
     * 获取应用全局Context
     *
     * @return 返回全局Context
     */
    public static Context getAppContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        Realm.init(this);
        AgileMVPFrameConfigure configure = new AgileMVPFrameConfigure.Builder()
                .setNetworkConfig(new AgileNetworkConfig.Builder()
                        .setConnectNetTimeout(20)
                        .setReadNetTimeout(20)
                        .setWriteNetTimeout(20)
                        .build())
                .build();
        AgileMVPFrame.init(this);
        AgileMVPFrame.setConfigure(configure);
    }
}
