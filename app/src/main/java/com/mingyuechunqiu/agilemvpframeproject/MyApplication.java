package com.mingyuechunqiu.agilemvpframeproject;

import android.app.Application;
import android.content.Context;

import com.mingyuechunqiu.agilemvpframe.agile.AgileMVPFrame;
import com.mingyuechunqiu.agilemvpframe.agile.AgileMVPFrameConfigure;

import io.realm.Realm;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/18
 *     desc   :
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
                .setConnectNetTimeout(20)
                .setReadNetTimeout(20)
                .setWriteNetTimeout(20)
                .build();
        AgileMVPFrame.init(this);
        AgileMVPFrame.setConfigure(configure);
    }
}
