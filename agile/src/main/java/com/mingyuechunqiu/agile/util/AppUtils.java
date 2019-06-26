package com.mingyuechunqiu.agile.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mingyuechunqiu.agile.frame.Agile;

import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/07/12
 *     desc   : 应用相关工具类
 *     version: 1.0
 * </pre>
 */
public class AppUtils {

    /**
     * 检测应用是否在前台显示
     *
     * @param context 上下文
     * @return 如果处于前台显示返回true，否则返回false
     */
    public static boolean checkAppIsForeground(@NonNull Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return false;
        }
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        if (list == null || list.size() == 0) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo info : list) {
            if (info != null && info.processName.equals(context.getPackageName())) {
                return info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
            }
        }
        return false;
    }

    /**
     * 判断某个Activity是否在前台显示
     *
     * @param context   上下文
     * @param className Activity的类名
     * @return 如果在前台显示返回true，否则返回false
     */
    public static boolean checkActivityIsForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return false;
        }
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list == null || list.size() == 0 || list.get(0) == null) {
            return false;
        }
        ComponentName cpn = list.get(0).topActivity;
        return cpn != null && className.equals(cpn.getClassName());
    }

    /**
     * 获取应用版本迭代次数
     *
     * @return 返回版本迭代次数
     */
    public static long getAppVersionCode() {
        PackageManager manager = Agile.getAppContext().getPackageManager();
        try {
            return manager.getPackageInfo(Agile.getAppContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取应用版本迭代名称
     *
     * @return 返回版本迭代名称
     */
    @NonNull
    public static String getAppVersionName() {
        PackageManager manager = Agile.getAppContext().getPackageManager();
        try {
            return manager.getPackageInfo(Agile.getAppContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 检测是否需要更新应用
     *
     * @param versionCode 应用版本号
     * @return 如果需要更新返回true，否则返回false
     */
    public static boolean checkIsNeedUpdateApp(int versionCode) {
        return versionCode > getAppVersionCode();
    }
}
