package com.mingyuechunqiu.agilemvpframe.util;

import android.app.ActivityManager;
import android.content.Context;

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
     * 判断应用是否正在前台运行
     *
     * @return 如果运行在前台则返回true，否则返回false
     */
    public static boolean judgeAppIsInForeground() {
        ActivityManager manager = (ActivityManager) AgileMVPFrame.getAppContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        List<ActivityManager.RunningTaskInfo> list = manager.getRunningTasks(1);
        if (list == null || list.size() == 0) {
            return false;
        }
        return AgileMVPFrame.getAppContext().getPackageName().equals(
                list.get(0).topActivity.getPackageName());
    }

    /**
     * 获取前台栈里正在运行的Activity
     *
     * @return 如果获取成功则返回类名，否则返回null
     */
    public static String getTopActivityName() {
        ActivityManager manager = (ActivityManager) AgileMVPFrame.getAppContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return null;
        }
        List<ActivityManager.RunningTaskInfo> list = manager.getRunningTasks(1);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0).topActivity.getClassName();
    }

}
