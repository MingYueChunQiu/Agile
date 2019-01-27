package com.mingyuechunqiu.agilemvpframe.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.mingyuechunqiu.agilemvpframe.agile.AgileMVPFrame;

import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/07/12
 *     desc   : 应用相关工具类
 *     version: 1.0
 * </pre>
 */
public class AppUtils {

    /**
     * 检测应用是否处于前台显示
     *
     * @param context 上下文
     * @return 如果处于前台显示返回true，否则返回false
     */
    public static boolean checkAppIsForeground(@NonNull Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = activityManager.getRunningAppProcesses();
        if (list == null || list.size() == 0) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo info : list) {
            if (info.processName.equals(context.getPackageName())) {
                return info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
            }
        }
        return false;
    }

    /**
     * 获取应用版本迭代次数
     *
     * @return 返回版本迭代次数
     */
    public static long getAppVersionCode() {
        PackageManager manager = AgileMVPFrame.getAppContext().getPackageManager();
        try {
            return manager.getPackageInfo(AgileMVPFrame.getAppContext().getPackageName(), 0).versionCode;
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
        PackageManager manager = AgileMVPFrame.getAppContext().getPackageManager();
        try {
            return manager.getPackageInfo(AgileMVPFrame.getAppContext().getPackageName(), 0).versionName;
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
