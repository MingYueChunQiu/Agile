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
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/12
 *     desc   : 控制应用退出
 *     version: 1.0
 * </pre>
 */

public class ExitApplicationManager {

    private static volatile ExitApplicationManager sExitApplicationManager;
    private ArrayList<WeakReference<Activity>> mList;

    private ExitApplicationManager() {
        mList = new ArrayList<>();
    }

    /**
     * 将界面添加进集合
     *
     * @param activity Activity
     */
    public static void addActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        checkInstance();
        sExitApplicationManager.mList.add(new WeakReference<>(activity));
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
        if (AgileMVPFrame.getAppContext() != null) {
            AgileMVPFrame.getAppContext().stopService(new Intent(
                    AgileMVPFrame.getAppContext(), NetworkStateService.class));
        }
        for (WeakReference<Activity> weakReference : sExitApplicationManager.mList) {
            if (weakReference.get() != null) {
                weakReference.get().finish();
            }
        }
        sExitApplicationManager.mList.clear();
        sExitApplicationManager.mList = null;
        sExitApplicationManager = null;
    }

    /**
     * 检测单例是否存在，不存在则创建
     */
    private static void checkInstance() {
        if (sExitApplicationManager == null) {
            synchronized (ExitApplicationManager.class) {
                if (sExitApplicationManager == null) {
                    sExitApplicationManager = new ExitApplicationManager();
                }
            }
        }
    }

}

