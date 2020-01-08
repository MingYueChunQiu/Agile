package com.mingyuechunqiu.agile.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
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
     * @param msg 提示信息
     */
    public static void showToast(@Nullable String msg) {
        showToast(Agile.getAppContext(), msg);
    }

    /**
     * 发送提示信息
     *
     * @param context 上下文
     * @param msg     提示信息
     */
    public static void showToast(@Nullable Context context, @Nullable String msg) {
        showToast(context, new ToastConfig.Builder().setMsg(msg).build());
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
     * @param context  上下文
     * @param msgResId 提示信息资源id
     */
    public static void showToast(@Nullable Context context, @StringRes int msgResId) {
        showToast(context, new ToastConfig.Builder().setMsgResId(msgResId).build());
    }

    /**
     * 显示文本信息
     *
     * @param config 配置信息对象
     */
    public static void showToast(@NonNull ToastConfig config) {
        showToast(Agile.getAppContext(), config);
    }

    /**
     * 显示文本信息
     *
     * @param context 上下文
     * @param config  配置信息对象
     */
    public static void showToast(@Nullable Context context, @NonNull ToastConfig config) {
        if (context == null) {
            return;
        }
        String msg = null;
        if (!TextUtils.isEmpty(config.getMsg())) {
            msg = config.getMsg();
        } else if (config.getMsgResId() != 0) {
            msg = context.getString(config.getMsgResId());
        }
        if (TextUtils.isEmpty(msg)) {
            msg = "未设置文本信息";
        }
        Toast toast = Toast.makeText(context, msg, config.isLongDuration() ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        if (config.getGravity() != Gravity.NO_GRAVITY) {
            toast.setGravity(config.getGravity(), config.getXOffset(), config.getYOffset());
        }
        toast.show();
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

        @Nullable
        public String getMsg() {
            return mBuilder.msg;
        }

        public void setMsg(@Nullable String msg) {
            mBuilder.msg = msg;
        }

        public int getMsgResId() {
            return mBuilder.msgResId;
        }

        public void setMsgResId(@StringRes int msgResId) {
            mBuilder.msgResId = msgResId;
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

            @Nullable
            private String msg;//文本
            private @StringRes
            int msgResId;//文本资源ID
            private boolean longDuration;//提示信息持续时间长短，true表示长时间，false表示短时间
            private int gravity;//对齐位置
            private int xOffset;//X轴偏移量
            private int yOffset;//Y轴偏移量

            public ToastConfig build() {
                return new ToastConfig(this);
            }

            @Nullable
            public String getMsg() {
                return msg;
            }

            public Builder setMsg(@Nullable String msg) {
                this.msg = msg;
                return this;
            }

            public int getMsgResId() {
                return msgResId;
            }

            public Builder setMsgResId(@StringRes int msgResId) {
                this.msgResId = msgResId;
                return this;
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
