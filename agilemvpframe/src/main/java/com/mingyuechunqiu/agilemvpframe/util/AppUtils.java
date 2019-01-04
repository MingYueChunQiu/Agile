package com.mingyuechunqiu.agilemvpframe.util;

import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

}
