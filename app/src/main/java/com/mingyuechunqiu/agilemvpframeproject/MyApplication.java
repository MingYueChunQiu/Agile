package com.mingyuechunqiu.agilemvpframeproject;

import android.app.Application;
import android.content.Context;

import com.mingyuechunqiu.agilemvpframe.agile.AgileMVPFrame;
import com.mingyuechunqiu.agilemvpframe.agile.AgileMVPFrameConfigure;
import com.mingyuechunqiu.agilemvpframeproject.constants.URLConstants;
import com.mingyuechunqiu.agilemvpframeproject.data.remote.retrofit.service.APIService;

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
     * 获取应用全局context
     *
     * @return
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
                .setNetTimeout(20)
                .build();
        AgileMVPFrame.init(this);
        AgileMVPFrame.setConfigure(configure);
    }
}
