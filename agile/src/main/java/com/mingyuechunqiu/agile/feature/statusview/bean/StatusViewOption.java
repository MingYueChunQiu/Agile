package com.mingyuechunqiu.agile.feature.statusview.bean;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;

import com.mingyuechunqiu.agile.feature.statusview.framework.IStatusViewContainer;
import com.mingyuechunqiu.agile.feature.statusview.function.LoadingDfgContainerable;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2019/1/23
 *     desc   : 加载配置参数类
 *     version: 1.0
 * </pre>
 */
public class StatusViewOption {

    private Builder mBuilder;

    private StatusViewOption() {
        this(new Builder());
    }

    private StatusViewOption(@NonNull Builder builder) {
        mBuilder = builder;
    }

    public IStatusViewContainer getStatusViewContainer() {
        return mBuilder.container;
    }

    public void setStatusViewContainer(IStatusViewContainer container) {
        mBuilder.container = container;
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

    public Drawable getLoadingBackground() {
        return mBuilder.loadingBackground;
    }

    public void setLoadingBackground(Drawable loadingBackground) {
        mBuilder.loadingBackground = loadingBackground;
    }

    public Drawable getIndeterminateDrawable() {
        return mBuilder.indeterminateDrawable;
    }

    public void setIndeterminateDrawable(Drawable indeterminateDrawable) {
        mBuilder.indeterminateDrawable = indeterminateDrawable;
    }

    public boolean isShowLoadingText() {
        return mBuilder.showLoadingText;
    }

    public void setShowLoadingText(boolean showLoadingText) {
        mBuilder.showLoadingText = showLoadingText;
    }

    public Drawable getTextBackground() {
        return mBuilder.textBackground;
    }

    public void setTextBackground(Drawable textBackground) {
        mBuilder.textBackground = textBackground;
    }

    public String getText() {
        return mBuilder.text;
    }

    public void setText(String text) {
        mBuilder.text = text;
    }

    public int getTextColor() {
        return mBuilder.textColor;
    }

    public void setTextColor(@ColorInt int textColor) {
        mBuilder.textColor = textColor;
    }

    public int getTextAppearance() {
        return mBuilder.textAppearance;
    }

    public void setTextAppearance(@StyleRes int textAppearance) {
        mBuilder.textAppearance = textAppearance;
    }

    public OnStatusViewDialogListener getOnLoadingOptionListener() {
        return mBuilder.dialogListener;
    }

    public void setOnLoadingOptionListener(OnStatusViewDialogListener listener) {
        mBuilder.dialogListener = listener;
    }

    public OnStatusViewButtonListener getOnStatusViewButtonListener() {
        return mBuilder.buttonListener;
    }

    public void setOnStatusViewButtonListener(OnStatusViewButtonListener buttonListener) {
        mBuilder.buttonListener = buttonListener;
    }

    /**
     * 链式调用
     */
    public static class Builder {

        private IStatusViewContainer container;//对话框布局容器
        private boolean cancelWithOutside;//是否能触摸外围区域取消对话框
        private int dialogWidth, dialogHeight;//对话框宽高
        private Drawable loadingBackground;//加载背景图像
        private Drawable indeterminateDrawable;//无进度图像
        private boolean showLoadingText;//是否显示加载文本（默认显示）
        private Drawable textBackground;//文本背景图像
        private String text;//加载文本
        private int textColor;//文本颜色
        private int textAppearance;//文本样式
        private OnStatusViewDialogListener dialogListener;//加载相关监听器
        private OnStatusViewButtonListener buttonListener;//按钮监听器

        public Builder() {
            showLoadingText = true;
        }

        public StatusViewOption build() {
            return new StatusViewOption(this);
        }

        public IStatusViewContainer getStatusViewContainer() {
            return container;
        }

        public Builder setStatusViewContainer(IStatusViewContainer container) {
            this.container = container;
            return this;
        }

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

        public Drawable getLoadingBackground() {
            return loadingBackground;
        }

        public Builder setLoadingBackground(Drawable loadingBackground) {
            this.loadingBackground = loadingBackground;
            return this;
        }

        public Drawable getIndeterminateDrawable() {
            return indeterminateDrawable;
        }

        public Builder setIndeterminateDrawable(Drawable indeterminateDrawable) {
            this.indeterminateDrawable = indeterminateDrawable;
            return this;
        }

        public boolean isShowLoadingText() {
            return showLoadingText;
        }

        public Builder setShowLoadingText(boolean showLoadingText) {
            this.showLoadingText = showLoadingText;
            return this;
        }

        public Drawable getTextBackground() {
            return textBackground;
        }

        public Builder setTextBackground(Drawable textBackground) {
            this.textBackground = textBackground;
            return this;
        }

        public String getText() {
            return text;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public int getTextColor() {
            return textColor;
        }

        public Builder setTextColor(@ColorInt int textColor) {
            this.textColor = textColor;
            return this;
        }

        public int getTextAppearance() {
            return textAppearance;
        }

        public Builder setTextAppearance(@StyleRes int textAppearance) {
            this.textAppearance = textAppearance;
            return this;
        }

        public OnStatusViewDialogListener getOnStatusViewDialogListener() {
            return dialogListener;
        }

        public Builder setOnStatusViewDialogListener(OnStatusViewDialogListener listener) {
            this.dialogListener = listener;
            return this;
        }

        public OnStatusViewButtonListener getOnStatusViewButtonListener() {
            return buttonListener;
        }

        public Builder setOnStatusViewButtonListener(OnStatusViewButtonListener buttonListener) {
            this.buttonListener = buttonListener;
            return this;
        }
    }

    /**
     * 加载相关监听器
     */
    public interface OnStatusViewDialogListener {

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

    /**
     * 状态视图按钮监听器
     */
    public interface OnStatusViewButtonListener {

        /**
         * 当点击重新加载按钮时回调
         */
        void onClickReload();
    }
}
