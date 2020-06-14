package com.mingyuechunqiu.agile.util;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.frame.Agile;
import com.mingyuechunqiu.agile.service.NetworkStateService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/12
 *     desc   : 应用退出管理类
 *     version: 1.0
 * </pre>
 */

public final class ExitApplicationManager {

    private static volatile ExitApplicationManager sExitApplicationManager;
    private List<WeakReference<Activity>> mList;
    private List<ExitApplicationCallback> mCallbackList;

    private ExitApplicationManager() {
        mList = new ArrayList<>();
    }

    /**
     * 将界面添加进集合
     *
     * @param activity Activity
     */
    public static void addActivity(@Nullable Activity activity) {
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
        if (Agile.getAppContext() != null) {
            Agile.getAppContext().stopService(new Intent(
                    Agile.getAppContext(), NetworkStateService.class));
        }
        for (WeakReference<Activity> weakReference : sExitApplicationManager.mList) {
            if (weakReference.get() != null) {
                weakReference.get().finish();
            }
        }
        sExitApplicationManager.mList.clear();
        sExitApplicationManager.mList = null;

        if (sExitApplicationManager.mCallbackList == null) {
            return;
        }
        for (ExitApplicationCallback callback : sExitApplicationManager.mCallbackList) {
            if (callback != null) {
                callback.onExitApplication();
            }
        }
        sExitApplicationManager = null;
    }

    public void addExitApplicationCallback(@NonNull ExitApplicationCallback callback) {
        if (mCallbackList == null) {
            mCallbackList = new ArrayList<>();
        }
        mCallbackList.add(callback);
    }

    public void removeExitApplicationCallback(@NonNull ExitApplicationCallback callback) {
        if (mCallbackList == null) {
            return;
        }
        mCallbackList.remove(callback);
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

    public interface ExitApplicationCallback {

        void onExitApplication();
    }
}

