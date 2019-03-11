package com.mingyuechunqiu.agilemvpframe.feature.loading;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;

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
public class LoadingDialogFragmentOption {

    private Constants.ThemeType themeType;//对话框主题类型
    private boolean cancelWithOutside;//是否能触摸外围区域取消对话框
    private int dialogWidth, dialogHeight;//对话框宽高
    private Drawable loadingBackground;//加载背景图像
    private Drawable indeterminateDrawable;//无进度图像
    private boolean showLoadingText;//是否显示加载文本（默认显示）
    private Drawable textBackground;//文本背景图像
    private String text;//加载文本
    private int textColor;//文本颜色
    private int textAppearance;//文本样式
    private OnLoadingOptionListener loadingOptionListener;//加载相关监听器

    public LoadingDialogFragmentOption() {
        showLoadingText = true;
    }

    public Constants.ThemeType getThemeType() {
        return themeType;
    }

    public void setThemeType(Constants.ThemeType themeType) {
        this.themeType = themeType;
    }

    public boolean isCancelWithOutside() {
        return cancelWithOutside;
    }

    public void setCancelWithOutside(boolean cancelWithOutside) {
        this.cancelWithOutside = cancelWithOutside;
    }

    public int getDialogWidth() {
        return dialogWidth;
    }

    public void setDialogWidth(int dialogWidth) {
        this.dialogWidth = dialogWidth;
    }

    public int getDialogHeight() {
        return dialogHeight;
    }

    public void setDialogHeight(int dialogHeight) {
        this.dialogHeight = dialogHeight;
    }

    public Drawable getLoadingBackground() {
        return loadingBackground;
    }

    public void setLoadingBackground(Drawable loadingBackground) {
        this.loadingBackground = loadingBackground;
    }

    public Drawable getIndeterminateDrawable() {
        return indeterminateDrawable;
    }

    public void setIndeterminateDrawable(Drawable indeterminateDrawable) {
        this.indeterminateDrawable = indeterminateDrawable;
    }

    public boolean isShowLoadingText() {
        return showLoadingText;
    }

    public void setShowLoadingText(boolean showLoadingText) {
        this.showLoadingText = showLoadingText;
    }

    public Drawable getTextBackground() {
        return textBackground;
    }

    public void setTextBackground(Drawable textBackground) {
        this.textBackground = textBackground;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(@ColorInt int textColor) {
        this.textColor = textColor;
    }

    public int getTextAppearance() {
        return textAppearance;
    }

    public void setTextAppearance(@StyleRes int textAppearance) {
        this.textAppearance = textAppearance;
    }

    public OnLoadingOptionListener getOnLoadingOptionListener() {
        return loadingOptionListener;
    }

    public void setOnLoadingOptionListener(OnLoadingOptionListener listener) {
        this.loadingOptionListener = listener;
    }

    /**
     * 链式调用
     */
    public static class Builder {

        private LoadingDialogFragmentOption mOption;

        public Builder() {
            mOption = new LoadingDialogFragmentOption();
        }

        public LoadingDialogFragmentOption build() {
            return mOption;
        }

        public Constants.ThemeType getThemeType() {
            return mOption.themeType;
        }

        public Builder setThemeType(Constants.ThemeType themeType) {
            mOption.themeType = themeType;
            return this;
        }

        public boolean isCancelWithOutside() {
            return mOption.cancelWithOutside;
        }

        public Builder setCancelWithOutside(boolean cancelWithOutside) {
            mOption.cancelWithOutside = cancelWithOutside;
            return this;
        }

        public int getDialogWidth() {
            return mOption.dialogWidth;
        }

        public Builder setDialogWidth(int dialogWidth) {
            mOption.dialogWidth = dialogWidth;
            return this;
        }

        public int getDialogHeight() {
            return mOption.dialogHeight;
        }

        public Builder setDialogHeight(int dialogHeight) {
            mOption.dialogHeight = dialogHeight;
            return this;
        }

        public Drawable getLoadingBackground() {
            return mOption.loadingBackground;
        }

        public Builder setLoadingBackground(Drawable loadingBackground) {
            mOption.loadingBackground = loadingBackground;
            return this;
        }

        public Drawable getIndeterminateDrawable() {
            return mOption.indeterminateDrawable;
        }

        public Builder setIndeterminateDrawable(Drawable indeterminateDrawable) {
            mOption.indeterminateDrawable = indeterminateDrawable;
            return this;
        }

        public boolean isShowLoadingText() {
            return mOption.showLoadingText;
        }

        public Builder setShowLoadingText(boolean showLoadingText) {
            mOption.showLoadingText = showLoadingText;
            return this;
        }

        public Drawable getTextBackground() {
            return mOption.textBackground;
        }

        public Builder setTextBackground(Drawable textBackground) {
            mOption.textBackground = textBackground;
            return this;
        }

        public String getText() {
            return mOption.text;
        }

        public Builder setText(String text) {
            mOption.text = text;
            return this;
        }

        public int getTextColor() {
            return mOption.textColor;
        }

        public Builder setTextColor(@ColorInt int textColor) {
            mOption.textColor = textColor;
            return this;
        }

        public int getTextAppearance() {
            return mOption.textAppearance;
        }

        public Builder setTextAppearance(@StyleRes int textAppearance) {
            mOption.textAppearance = textAppearance;
            return this;
        }

        public OnLoadingOptionListener getOnLoadingOptionListener() {
            return mOption.loadingOptionListener;
        }

        public Builder setOnLoadingOptionListener(OnLoadingOptionListener listener) {
            mOption.loadingOptionListener = listener;
            return this;
        }
    }

    /**
     * 加载相关监听器
     */
    public interface OnLoadingOptionListener {

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
