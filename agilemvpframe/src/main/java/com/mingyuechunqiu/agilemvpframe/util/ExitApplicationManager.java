package com.mingyuechunqiu.agilemvpframe.util;

import android.app.Activity;
import android.content.Intent;

import com.mingyuechunqiu.agilemvpframe.agile.AgileMVPFrame;
import com.mingyuechunqiu.agilemvpframe.service.NetworkStateService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/12
 *     desc   : 控制应用退出
 *     version: 1.0
 * </pre>
 */

public class ExitApplicationManager {

    private static volatile ExitApplicationManager sExitApplicationManager;
    private ArrayList<WeakReference<Activity>> mList;

    private ExitApplicationManager() {
    }

    /**
     * 将界面添加进集合
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        newInstance();
        sExitApplicationManager.mList.add(new WeakReference<Activity>(activity));
    }

    /**
     * 彻底退出应用
     */
    public static void exit() {
        if (sExitApplicationManager == null ||
                sExitApplicationManager.mList == null ||
                sExitApplicationManager.mList.size() == 0) {
            return;
        }
        AgileMVPFrame.getAppContext().stopService(new Intent(
                AgileMVPFrame.getAppContext(), NetworkStateService.class));
        for (WeakReference<Activity> weakReference : sExitApplicationManager.mList) {
            if (weakReference.get() != null) {
                weakReference.get().finish();
                weakReference.clear();
            }
        }
        sExitApplicationManager.mList = null;
        AgileMVPFrame.releaseResource();
    }

    private static void newInstance() {
        if (sExitApplicationManager == null) {
            sExitApplicationManager = new ExitApplicationManager();
        }
        if (sExitApplicationManager.mList == null) {
            sExitApplicationManager.mList = new ArrayList<>();
        }
    }

}

