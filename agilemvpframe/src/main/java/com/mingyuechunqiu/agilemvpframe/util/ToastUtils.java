package com.mingyuechunqiu.agilemvpframe.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.mingyuechunqiu.agilemvpframe.agile.AgileMVPFrame;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/07/17
 *     desc   : Toast工具类
 *     version: 1.0
 * </pre>
 */
public class ToastUtils {

    /**
     * 使用应用的全局context发送toast
     *
     * @param hint 提示信息
     */
    public static void showToast(String hint) {
        showToast(AgileMVPFrame.getAppContext(), hint);
    }

    /**
     * 发送提示信息
     *
     * @param context 上下文
     * @param hint    提示信息
     */
    public static void showToast(Context context, String hint) {
        Toast.makeText(context, hint, Toast.LENGTH_SHORT).show();
    }

    /**
     * 发送提示信息
     *
     * @param stringResId 提示信息资源id
     */
    public static void showToast(@StringRes int stringResId) {
        showToast(AgileMVPFrame.getAppContext(), stringResId);
    }

    /**
     * 发送提示信息
     *
     * @param context     上下文
     * @param stringResId 提示信息资源id
     */
    public static void showToast(Context context, @StringRes int stringResId) {
        Toast.makeText(context, stringResId, Toast.LENGTH_SHORT).show();
    }
}
