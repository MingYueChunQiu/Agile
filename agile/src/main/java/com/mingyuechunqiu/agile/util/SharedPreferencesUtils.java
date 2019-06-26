package com.mingyuechunqiu.agile.util;

import android.content.Context;
import android.text.TextUtils;

import java.util.Set;

/**
 * <pre>
 *     author : 明月春秋
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/26
 *     desc   : 配置工具类
 *     version: 1.0
 * </pre>
 */
public class SharedPreferencesUtils {

    public static boolean putBoolean(Context context, String name, String key, boolean value) {
        if (context == null || TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
            return false;
        }
        return context.getSharedPreferences(name, Context.MODE_PRIVATE).edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String name, String key, boolean defValue) {
        if (context == null || TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
            return false;
        }
        return context.getSharedPreferences(name, Context.MODE_PRIVATE).getBoolean(key, defValue);
    }

    public static boolean putInt(Context context, String name, String key, int value) {
        if (context == null || TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
            return false;
        }
        return context.getSharedPreferences(name, Context.MODE_PRIVATE).edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String name, String key, int defValue) {
        if (context == null || TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
            return -1;
        }
        return context.getSharedPreferences(name, Context.MODE_PRIVATE).getInt(key, defValue);
    }

    public static boolean putLong(Context context, String name, String key, long value) {
        if (context == null || TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
            return false;
        }
        return context.getSharedPreferences(name, Context.MODE_PRIVATE).edit().putLong(key, value).commit();
    }

    public static long getLong(Context context, String name, String key, long defValue) {
        if (context == null || TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
            return -1;
        }
        return context.getSharedPreferences(name, Context.MODE_PRIVATE).getLong(key, defValue);
    }

    public static boolean putFloat(Context context, String name, String key, float value) {
        if (context == null || TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
            return false;
        }
        return context.getSharedPreferences(name, Context.MODE_PRIVATE).edit().putFloat(key, value).commit();
    }

    public static float getFloat(Context context, String name, String key, float defValue) {
        if (context == null || TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
            return -1;
        }
        return context.getSharedPreferences(name, Context.MODE_PRIVATE).getFloat(key, defValue);
    }

    public static boolean putString(Context context, String name, String key, String value) {
        if (context == null || TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
            return false;
        }
        return context.getSharedPreferences(name, Context.MODE_PRIVATE).edit().putString(key, value).commit();
    }

    public static String getString(Context context, String name, String key, String defValue) {
        if (context == null || TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
            return null;
        }
        return context.getSharedPreferences(name, Context.MODE_PRIVATE).getString(key, defValue);
    }

    public static boolean putStringSet(Context context, String name, String key, Set<String> value) {
        if (context == null || TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
            return false;
        }
        return context.getSharedPreferences(name, Context.MODE_PRIVATE).edit().putStringSet(key, value).commit();
    }

    public static Set<String> getStringSet(Context context, String name, String key, Set<String> defValue) {
        if (context == null || TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
            return null;
        }
        return context.getSharedPreferences(name, Context.MODE_PRIVATE).getStringSet(key, defValue);
    }

    /**
     * 清空配置中所有内容
     *
     * @param context 上下文
     * @param name    配置名称
     * @return 是否清楚成功
     */
    public static boolean clearAll(Context context, String name) {
        if (context == null || TextUtils.isEmpty(name)) {
            return false;
        }
        return context.getSharedPreferences(name, Context.MODE_PRIVATE).edit().clear().commit();
    }
}
