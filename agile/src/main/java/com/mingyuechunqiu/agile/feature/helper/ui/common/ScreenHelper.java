package com.mingyuechunqiu.agile.feature.helper.ui.common;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/03/27
 *     desc   : 屏幕相关的工具类
 *     version: 1.0
 * </pre>
 */

public final class ScreenHelper {

    private ScreenHelper() {
    }

    /**
     * 获取屏幕宽度平均划分数量后大小
     *
     * @param context 上下文
     * @param radio   需要截取的尺寸比例
     * @param count   均分个数
     * @return 返回平均后的大小（整型值）
     */
    public static int getAverageSizeWithParentWidth(@NonNull Context context, float radio, int count) {
        return getAverageSizeWithSpecifiedSize(context.getResources().getDisplayMetrics().widthPixels, radio, count);
    }

    /**
     * 获取屏幕高度平均划分数量后大小
     *
     * @param context 上下文
     * @param radio   需要截取的尺寸比例
     * @param count   均分个数
     * @return 返回平均后的大小（整型值）
     */
    public static int getAverageSizeWithParentHeight(@NonNull Context context, float radio, int count) {
        return getAverageSizeWithSpecifiedSize(context.getResources().getDisplayMetrics().heightPixels, radio, count);
    }

    /**
     * 获取指定尺寸平均划分数量后大小
     *
     * @param radio 需要截取的尺寸比例
     * @param count 均分个数
     * @return 返回平均后的大小（整型值）
     */
    public static int getAverageSizeWithSpecifiedSize(float specifiedSize, float radio, int count) {
        return (int) (specifiedSize * radio / count);
    }

    /**
     * 将dp转换为px
     *
     * @param resources 资源管理器
     * @param dpVal     dp值
     * @return 返回px值
     */
    public static float getPxFromDp(@NonNull Resources resources, float dpVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, resources.getDisplayMetrics());
    }

    /**
     * 将sp转换成px
     *
     * @param resources 资源管理器
     * @param spVal     sp值
     * @return 返回px值
     */
    public static float getPxFromSp(@NonNull Resources resources, float spVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, resources.getDisplayMetrics());
    }

    /**
     * 设置文本控件字体大小
     *
     * @param textView 控件
     * @param spVal    文本的sp大小
     */
    public static void setTextSize(@NonNull TextView textView, int spVal) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, spVal);
    }

    /**
     * 设置控件的外边距
     *
     * @param layoutParams 控件的布局参数
     * @param resources    资源管理器
     * @param left         左边距
     * @param top          上边距
     * @param right        右边距
     * @param bottom       下边距
     */
    public static void setMargins(@Nullable ViewGroup.MarginLayoutParams layoutParams, @Nullable Resources resources,
                                  int left, int top, int right, int bottom) {
        if (layoutParams == null || resources == null) {
            return;
        }
        layoutParams.setMargins((int) ScreenHelper.getPxFromDp(resources, left),
                (int) ScreenHelper.getPxFromDp(resources, top),
                (int) ScreenHelper.getPxFromDp(resources, right),
                (int) ScreenHelper.getPxFromDp(resources, bottom));
    }

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return 返回状态栏高度
     */
    public static int getStatusBarHeight(@Nullable Context context) {
        if (context == null) {
            return 0;
        }
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /**
     * 获取底部虚拟导航栏高度
     *
     * @param context 上下文
     * @return 返回底部虚拟导航栏高度
     */
    public static int getNavigationBarHeight(@Nullable Context context) {
        if (context == null) {
            return 0;
        }
        int height = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /**
     * 隐藏当前获取焦点view的软键盘
     *
     * @param activity 软键盘所在界面
     */
    public static void hideFocusedSoftInput(@Nullable Activity activity) {
        if (activity == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view == null) {
            return;
        }
        hideViewSoftInput(activity, view);
    }

    /**
     * 隐藏指定view的软键盘
     *
     * @param activity 软键盘所在界面
     * @param view     指定的view
     */
    public static void hideViewSoftInput(@Nullable Activity activity, @Nullable View view) {
        if (activity == null) {
            return;
        }
        hideViewSoftInput((Context) activity, view);
    }

    /**
     * 隐藏DialogFragment指定view的软键盘
     *
     * @param dialogFragment 对话框Fragment
     */
    public static void hideViewSoftInput(@Nullable DialogFragment dialogFragment) {
        if (dialogFragment == null) {
            return;
        }
        hideViewSoftInput(dialogFragment.getDialog());
    }

    /**
     * 隐藏DialogFragment指定view的软键盘
     *
     * @param dialogFragment 对话框Fragment
     * @param view           指定的view
     */
    public static void hideViewSoftInput(@Nullable DialogFragment dialogFragment, @Nullable View view) {
        if (dialogFragment == null) {
            return;
        }
        hideViewSoftInput(dialogFragment.getDialog(), view);
    }

    /**
     * 隐藏对话框指定view的软键盘
     *
     * @param dialog 对话框
     */
    public static void hideViewSoftInput(@Nullable Dialog dialog) {
        if (dialog == null) {
            return;
        }
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        hideViewSoftInput(dialog, window.getDecorView());
    }

    /**
     * 隐藏对话框指定view的软键盘
     *
     * @param dialog 对话框
     * @param view   指定的view
     */
    public static void hideViewSoftInput(@Nullable Dialog dialog, @Nullable View view) {
        if (dialog == null) {
            return;
        }
        hideViewSoftInput(dialog.getContext(), view);
    }

    /**
     * 隐藏对话框指定view的软键盘
     *
     * @param context 上下文
     * @param view    指定的view
     */
    public static void hideViewSoftInput(@Nullable Context context, @Nullable View view) {
        if (context == null || view == null) {
            return;
        }
        hideViewSoftInput((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE), view);
    }

    /**
     * 隐藏对话框指定view的软键盘
     *
     * @param imm  软键盘管理器
     * @param view 指定的view
     */
    public static void hideViewSoftInput(@Nullable InputMethodManager imm, @Nullable View view) {
        if (imm == null || view == null) {
            return;
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示当前获取焦点view的软键盘
     *
     * @param activity 软键盘所在界面
     */
    public static void showViewSoftInput(@Nullable Activity activity) {
        if (activity == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view == null) {
            return;
        }
        showViewSoftInput(activity, view);
    }

    /**
     * 显示指定view的软键盘
     *
     * @param activity 软键盘所在界面
     * @param view     指定的view
     */
    public static void showViewSoftInput(@Nullable Activity activity, @Nullable View view) {
        if (activity == null) {
            return;
        }
        showViewSoftInput((Context) activity, view);
    }

    /**
     * 显示DialogFragment指定view的软键盘
     *
     * @param dialogFragment 对话框Fragment
     */
    public static void showViewSoftInput(@Nullable DialogFragment dialogFragment) {
        if (dialogFragment == null) {
            return;
        }
        showViewSoftInput(dialogFragment.getDialog());
    }

    /**
     * 显示DialogFragment指定view的软键盘
     *
     * @param dialogFragment 对话框Fragment
     * @param view           指定的view
     */
    public static void showViewSoftInput(@Nullable DialogFragment dialogFragment, @Nullable View view) {
        if (dialogFragment == null) {
            return;
        }
        showViewSoftInput(dialogFragment.getDialog(), view);
    }

    /**
     * 显示对话框指定view的软键盘
     *
     * @param dialog 对话框
     */
    public static void showViewSoftInput(@Nullable Dialog dialog) {
        if (dialog == null) {
            return;
        }
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        showViewSoftInput(dialog, window.getDecorView());
    }

    /**
     * 显示对话框指定view的软键盘
     *
     * @param dialog 对话框
     * @param view   指定的view
     */
    public static void showViewSoftInput(@Nullable Dialog dialog, @Nullable View view) {
        if (dialog == null) {
            return;
        }
        showViewSoftInput(dialog.getContext(), view);
    }

    /**
     * 显示对话框指定view的软键盘
     *
     * @param context 上下文
     * @param view    指定的view
     */
    public static void showViewSoftInput(@Nullable Context context, @Nullable View view) {
        if (context == null || view == null) {
            return;
        }
        showViewSoftInput((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE), view);
    }

    /**
     * 显示对话框指定view的软键盘
     *
     * @param imm  软键盘管理器
     * @param view 指定的view
     */
    public static void showViewSoftInput(@Nullable InputMethodManager imm, @Nullable View view) {
        if (imm == null || view == null) {
            return;
        }
        imm.showSoftInput(view, 0);
    }

    /**
     * 判断窗口是否有虚拟导航条
     *
     * @param activity   界面
     * @param isPortrait 是否垂直方向
     * @return 如果有返回true，否则返回false
     */
    public static boolean judgeWindowHasNavigationBar(@NonNull final Activity activity, final boolean isPortrait) {
        boolean flag = false;

        final View content = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (content == null) {
            return false;
        }
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        final Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(point);
        }
        if (isPortrait) {
            int bottom = content.getBottom();// 页面的底部
            if (bottom != point.y) {
                flag = true;
            }
        } else {
            int right = content.getRight();
            if (right != point.y) {
                flag = true;
            }
        }
        return flag;
    }
}
