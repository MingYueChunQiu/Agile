package com.mingyuechunqiu.agilemvpframe.feature.loadingFragment;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.StyleRes;

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
public class LoadingFragmentOption {

    private boolean canTouchOutside;//是否能触摸外围区域
    private Drawable containerBackground;//容器背景图像
    private Drawable loadingBackground;//加载背景图像
    private Drawable indeterminateDrawable;//无进度图像
    private boolean showLoadingText;//是否显示加载文本
    private Drawable textBackground;//文本背景图像
    private String text;//加载文本
    private int textColor;//文本颜色
    private int textAppearance;//文本样式

    public boolean isCanTouchOutside() {
        return canTouchOutside;
    }

    public void setCanTouchOutside(boolean canTouchOutside) {
        this.canTouchOutside = canTouchOutside;
    }

    public Drawable getContainerBackground() {
        return containerBackground;
    }

    public void setContainerBackground(Drawable containerBackground) {
        this.containerBackground = containerBackground;
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

    /**
     * 链式调用
     */
    public static class Builder {

        private LoadingFragmentOption mOption;

        public Builder() {
            mOption = new LoadingFragmentOption();
        }

        public LoadingFragmentOption build() {
            return mOption;
        }

        public boolean isCanTouchOutside() {
            return mOption.canTouchOutside;
        }

        public Builder setCanTouchOutside(boolean canTouchOutside) {
            mOption.canTouchOutside = canTouchOutside;
            return this;
        }

        public Drawable getContainerBackground() {
            return mOption.containerBackground;
        }

        public Builder setContainerBackground(Drawable containerBackground) {
            mOption.containerBackground = containerBackground;
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
    }
}
