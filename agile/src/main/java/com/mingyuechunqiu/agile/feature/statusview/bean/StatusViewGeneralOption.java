package com.mingyuechunqiu.agile.feature.statusview.bean;

import android.content.DialogInterface;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-11-24 21:50
 *      Desc:       通用状态视图配置类
 *      Version:    1.0
 * </pre>
 */
public class StatusViewGeneralOption {

    private Builder mBuilder;

    public StatusViewGeneralOption() {
        this(new Builder());
    }

    public StatusViewGeneralOption(@NonNull Builder builder) {
        mBuilder = builder;
    }

    public boolean isCancelWithOutside() {
        return mBuilder.cancelWithOutside;
    }

    public void setCancelWithOutside(boolean cancelWithOutside) {
        mBuilder.cancelWithOutside = cancelWithOutside;
    }

    public int getDialogWidth() {
        return mBuilder.dialogWidth;
    }

    public void setDialogWidth(int dialogWidth) {
        mBuilder.dialogWidth = dialogWidth;
    }

    public int getDialogHeight() {
        return mBuilder.dialogHeight;
    }

    public void setDialogHeight(int dialogHeight) {
        mBuilder.dialogHeight = dialogHeight;
    }

    public boolean isShowContent() {
        return mBuilder.showContent;
    }

    public void setShowContent(boolean showContent) {
        mBuilder.showContent = showContent;
    }

    public String getContent() {
        return mBuilder.content;
    }

    public void setContent(String content) {
        mBuilder.content = content;
    }

    public int getContentTextColor() {
        return mBuilder.contentTextColor;
    }

    public void setContentTextColor(int contentTextColor) {
        mBuilder.contentTextColor = contentTextColor;
    }

    public int getContentTextSize() {
        return mBuilder.contentTextSize;
    }

    public void setContentTextSize(int contentTextSize) {
        mBuilder.contentTextSize = contentTextSize;
    }

    public int getContentTextAppearance() {
        return mBuilder.contentTextAppearance;
    }

    public void setContentTextAppearance(int contentTextAppearance) {
        mBuilder.contentTextAppearance = contentTextAppearance;
    }

    public OnStatusViewListener getOnStatusViewListener() {
        return mBuilder.listener;
    }

    public void setOnStatusViewListener(OnStatusViewListener listener) {
        mBuilder.listener = listener;
    }

    /**
     * 链式调用
     */
    public static class Builder {

        private boolean cancelWithOutside;//是否能触摸外围区域取消对话框
        private int dialogWidth, dialogHeight;//对话框宽高
        private boolean showContent;//显示内容
        private String content;//内容文本
        private @ColorInt
        int contentTextColor;//内容文本颜色
        private int contentTextSize;//内容文本大小
        private @StyleRes
        int contentTextAppearance;//内容文本样式
        private OnStatusViewListener listener;//状态视图监听器

        public boolean isCancelWithOutside() {
            return cancelWithOutside;
        }

        public Builder setCancelWithOutside(boolean cancelWithOutside) {
            this.cancelWithOutside = cancelWithOutside;
            return this;
        }

        public int getDialogWidth() {
            return dialogWidth;
        }

        public Builder setDialogWidth(int dialogWidth) {
            this.dialogWidth = dialogWidth;
            return this;
        }

        public int getDialogHeight() {
            return dialogHeight;
        }

        public Builder setDialogHeight(int dialogHeight) {
            this.dialogHeight = dialogHeight;
            return this;
        }

        public boolean isShowContent() {
            return showContent;
        }

        public Builder setShowContent(boolean showContent) {
            this.showContent = showContent;
            return this;
        }

        public String getContent() {
            return content;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public int getContentTextColor() {
            return contentTextColor;
        }

        public Builder setContentTextColor(int contentTextColor) {
            this.contentTextColor = contentTextColor;
            return this;
        }

        public int getContentTextSize() {
            return contentTextSize;
        }

        public Builder setContentTextSize(int contentTextSize) {
            this.contentTextSize = contentTextSize;
            return this;
        }

        public int getContentTextAppearance() {
            return contentTextAppearance;
        }

        public Builder setContentTextAppearance(int contentTextAppearance) {
            this.contentTextAppearance = contentTextAppearance;
            return this;
        }

        public OnStatusViewListener getOnStatusViewListener() {
            return listener;
        }

        public Builder setOnStatusViewListener(OnStatusViewListener listener) {
            this.listener = listener;
            return this;
        }
    }

    public interface OnStatusViewListener {

        /**
         * 当点击返回键时回调
         *
         * @param dialog 对话框实例
         * @return 拦截返回键事件返回true，否则返回false
         */
        boolean onClickKeyBack(DialogInterface dialog);

        /**
         * 当对话框消失时回调
         *
         * @param dialogFragment 对话框实例
         */
        void onDismissListener(DialogFragment dialogFragment);
    }
}
