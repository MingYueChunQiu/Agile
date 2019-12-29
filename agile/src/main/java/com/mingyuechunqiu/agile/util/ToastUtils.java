package com.mingyuechunqiu.agile.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
public final class ToastUtils {

    private ToastUtils() {
    }

    /**
     * 使用应用的全局context发送toast
     *
     * @param hint 提示信息
     */
    public static void showToast(@Nullable String hint) {
        showToast(Agile.getAppContext(), hint);
    }

    /**
     * 发送提示信息
     *
     * @param context 上下文
     * @param hint    提示信息
     */
    public static void showToast(@Nullable Context context, @Nullable String hint) {
        showToast(context, hint, null);
    }

    /**
     * 发送提示信息
     *
     * @param context 上下文
     * @param hint    提示信息
     * @param config  配置信息对象
     */
    public static void showToast(@Nullable Context context, @Nullable String hint,
                                 @Nullable ToastConfig config) {
        if (context == null || TextUtils.isEmpty(hint)) {
            return;
        }
        Toast toast = Toast.makeText(context, hint, config == null ? Toast.LENGTH_SHORT :
                config.isLongDuration() ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        if (config != null) {
            toast.setGravity(config.getGravity(), config.getXOffset(), config.getYOffset());
        }
        toast.show();
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
        showToast(context, stringResId, null);
    }

    /**
     * 发送提示信息
     *
     * @param context     上下文
     * @param stringResId 提示信息资源id
     * @param config      配置信息对象
     */
    public static void showToast(@Nullable Context context, @StringRes int stringResId,
                                 @Nullable ToastConfig config) {
        if (context == null) {
            return;
        }
        Toast toast = Toast.makeText(context, stringResId, config == null ? Toast.LENGTH_SHORT :
                config.isLongDuration() ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        if (config != null) {
            toast.setGravity(config.getGravity(), config.getXOffset(), config.getYOffset());
        }
        toast.show();
    }

    /**
     * 发送提示信息
     *
     * @param context     上下文
     * @param hint        提示信息
     * @param stringResId 提示信息资源id（为0时表示无字符显示资源）
     * @param config      配置信息对象
     */
    @SuppressLint("ShowToast")
    public static void showToast(@Nullable Context context, @Nullable String hint,
                                 @StringRes int stringResId, @Nullable ToastConfig config) {
        if (context == null) {
            return;
        }
        Toast toast = null;
        if (TextUtils.isEmpty(hint)) {
            if (stringResId != 0) {
                toast = Toast.makeText(context, stringResId, config == null ? Toast.LENGTH_SHORT :
                        config.isLongDuration() ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
            }
        } else {
            toast = Toast.makeText(context, hint, config == null ? Toast.LENGTH_SHORT :
                    config.isLongDuration() ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        }
        if (toast != null && config != null) {
            toast.setGravity(config.getGravity(), config.getXOffset(), config.getYOffset());
        }
        if (toast != null) {
            toast.show();
        }
    }

    /**
     * Toast配置信息类
     */
    public static class ToastConfig {

        private Builder mBuilder;

        public ToastConfig() {
            this(new Builder());
        }

        public ToastConfig(@NonNull Builder builder) {
            mBuilder = builder;
        }

        public boolean isLongDuration() {
            return mBuilder.longDuration;
        }

        public void setLongDuration(boolean longDuration) {
            mBuilder.longDuration = longDuration;
        }

        public int getGravity() {
            return mBuilder.gravity;
        }

        public void setGravity(int gravity) {
            mBuilder.gravity = gravity;
        }

        public int getXOffset() {
            return mBuilder.xOffset;
        }

        public void setXOffset(int xOffset) {
            mBuilder.xOffset = xOffset;
        }

        public int getYOffset() {
            return mBuilder.yOffset;
        }

        public void setYOffset(int yOffset) {
            mBuilder.yOffset = yOffset;
        }

        /**
         * 链式调用
         */
        public static class Builder {

            private boolean longDuration;//提示信息持续时间长短，true表示长时间，false表示短时间
            private int gravity;//对齐位置
            private int xOffset;//X轴偏移量
            private int yOffset;//Y轴偏移量

            public ToastConfig build() {
                return new ToastConfig(this);
            }

            public boolean isLongDuration() {
                return longDuration;
            }

            public Builder setLongDuration(boolean longDuration) {
                this.longDuration = longDuration;
                return this;
            }

            public int getGravity() {
                return gravity;
            }

            public Builder setGravity(int gravity) {
                this.gravity = gravity;
                return this;
            }

            public int getXOffset() {
                return xOffset;
            }

            public Builder setXOffset(int xOffset) {
                this.xOffset = xOffset;
                return this;
            }

            public int getYOffset() {
                return yOffset;
            }

            public Builder setYOffset(int yOffset) {
                this.yOffset = yOffset;
                return this;
            }
        }
    }
}
