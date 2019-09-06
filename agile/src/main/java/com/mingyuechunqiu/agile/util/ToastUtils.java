package com.mingyuechunqiu.agile.util;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

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
    public static void showToast(@Nullable Context context, String hint) {
        showToast(context, hint, false);
    }

    /**
     * @param context      上下文
     * @param hint         提示信息
     * @param longDuration 提示信息持续时间长短，true表示长时间，false表示短时间
     */
    public static void showToast(@Nullable Context context, String hint, boolean longDuration) {
        if (context == null) {
            return;
        }
        Toast.makeText(context, hint, longDuration ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
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
    public static void showToast(@Nullable Context context, @StringRes int stringResId) {
        showToast(context, stringResId, false);
    }

    /**
     * 发送提示信息
     *
     * @param context     上下文
     * @param stringResId 提示信息资源id
     */
    public static void showToast(@Nullable Context context, @StringRes int stringResId, boolean longDuration) {
        if (context == null) {
            return;
        }
        Toast.makeText(context, stringResId, longDuration ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }
}
