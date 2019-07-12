package com.mingyuechunqiu.agile.util;

import android.content.Context;
import androidx.annotation.StringRes;
import android.widget.Toast;

import com.mingyuechunqiu.agile.frame.Agile;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
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
        showToast(Agile.getAppContext(), hint);
    }

    /**
     * 发送提示信息
     *
     * @param context 上下文
     * @param hint    提示信息
     */
    public static void showToast(Context context, String hint) {
        if (context == null) {
            return;
        }
        Toast.makeText(context, hint, Toast.LENGTH_SHORT).show();
    }

    /**
     * 发送提示信息
     *
     * @param stringResId 提示信息资源id
     */
    public static void showToast(@StringRes int stringResId) {
        showToast(Agile.getAppContext(), stringResId);
    }

    /**
     * 发送提示信息
     *
     * @param context     上下文
     * @param stringResId 提示信息资源id
     */
    public static void showToast(Context context, @StringRes int stringResId) {
        if (context == null) {
            return;
        }
        Toast.makeText(context, stringResId, Toast.LENGTH_SHORT).show();
    }
}
