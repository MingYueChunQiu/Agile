package com.mingyuechunqiu.agile.util;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.frame.Agile;
import com.mingyuechunqiu.agile.service.NetworkStateService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Map<String, WeakReference<Activity>> mActivityMap;
    private List<ExitApplicationCallback> mCallbackList;

    private ExitApplicationManager() {
    }

    public static ExitApplicationManager getInstance() {
        return ExitApplicationManagerHolder.INSTANCE;
    }

    /**
     * 将界面添加进集合
     *
     * @param activity Activity
     */
    public void addActivity(@Nullable Activity activity) {
        if (activity == null) {
            return;
        }
        if (mActivityMap == null) {
            mActivityMap = new HashMap<>();
        }
        mActivityMap.put(activity.getClass().getCanonicalName(), new WeakReference<>(activity));
        if (mCallbackList == null) {
            return;
        }
        for (ExitApplicationCallback callback : mCallbackList) {
            if (callback != null) {
                callback.onAddActivity(activity);
            }
        }
    }

    public void removeActivity(@Nullable Activity activity) {
        if (activity == null || mActivityMap == null) {
            return;
        }
        mActivityMap.remove(activity.getClass().getCanonicalName());
        if (mCallbackList == null) {
            return;
        }
        for (ExitApplicationCallback callback : mCallbackList) {
            if (callback != null) {
                callback.onRemoveActivity(activity);
            }
        }
    }

    /**
     * 彻底退出应用
     */
    public void exit() {
        if (Agile.getAppContext() != null) {
            Agile.getAppContext().stopService(new Intent(
                    Agile.getAppContext(), NetworkStateService.class));
        }
        if (mActivityMap == null || mActivityMap.isEmpty()) {
            return;
        }
        for (WeakReference<Activity> weakReference : mActivityMap.values()) {
            if (weakReference != null && weakReference.get() != null) {
                weakReference.get().finish();
            }
        }
        mActivityMap.clear();
        mActivityMap = null;
        if (mCallbackList == null || mCallbackList.isEmpty()) {
            return;
        }
        for (ExitApplicationCallback callback : mCallbackList) {
            if (callback != null) {
                callback.onExitApplication();
            }
        }
        mCallbackList.clear();
        mCallbackList = null;
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

    private static class ExitApplicationManagerHolder {

        private static ExitApplicationManager INSTANCE = new ExitApplicationManager();
    }

    public interface ExitApplicationCallback {

        void onAddActivity(@NonNull Activity activity);

        void onRemoveActivity(@NonNull Activity activity);

        void onExitApplication();
    }
}

