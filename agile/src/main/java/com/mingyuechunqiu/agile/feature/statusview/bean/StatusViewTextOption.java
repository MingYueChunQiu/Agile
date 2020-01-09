package com.mingyuechunqiu.agile.feature.statusview.bean;

import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-11-28 22:29
 *      Desc:       状态视图文本配置信息类
 *      Version:    1.0
 * </pre>
 */
public class StatusViewTextOption {

    private Builder mBuilder;

    public StatusViewTextOption() {
        this(new Builder());
    }

    public StatusViewTextOption(@NonNull Builder builder) {
        mBuilder = builder;
    }

    @Nullable
    public String getText() {
        return mBuilder.text;
    }

    public void setText(@Nullable String text) {
        mBuilder.text = text;
    }

    public int getTextColor() {
        return mBuilder.textColor;
    }

    public void setTextColor(@ColorInt int textColor) {
        mBuilder.textColor = textColor;
    }

    public float getTextSize() {
        return mBuilder.textSize;
    }

    public void setTextSize(float textSize) {
        mBuilder.textSize = textSize;
    }

    public int getTextAppearance() {
        return mBuilder.textAppearance;
    }

    public void setTextAppearance(@StyleRes int textAppearance) {
        mBuilder.textAppearance = textAppearance;
    }

    public int getBackgroundResId() {
        return mBuilder.backgroundResId;
    }

    public void setBackgroundResId(@DrawableRes int backgroundResId) {
        mBuilder.backgroundResId = backgroundResId;
    }

    public Drawable getBackground() {
        return mBuilder.background;
    }

    public void setBackground(Drawable background) {
        mBuilder.background = background;
    }

    /**
     * 链式调用
     */
    public static class Builder {

        public StatusViewTextOption build() {
            return new StatusViewTextOption(this);
        }

        private String text;
        private @ColorInt
        int textColor;
        private float textSize;
        private @StyleRes
        int textAppearance;
        private @DrawableRes
        int backgroundResId;
        private Drawable background;

        @Nullable
        public String getText() {
            return text;
        }

        public Builder setText(@Nullable String text) {
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

        public float getTextSize() {
            return textSize;
        }

        public Builder setTextSize(float textSize) {
            this.textSize = textSize;
            return this;
        }

        public int getTextAppearance() {
            return textAppearance;
        }

        public Builder setTextAppearance(@StyleRes int textAppearance) {
            this.textAppearance = textAppearance;
            return this;
        }

        public int getBackgroundResId() {
            return backgroundResId;
        }

        public Builder setBackgroundResId(@DrawableRes int backgroundResId) {
            this.backgroundResId = backgroundResId;
            return this;
        }

        public Drawable getBackground() {
            return background;
        }

        public Builder setBackground(Drawable background) {
            this.background = background;
            return this;
        }
    }

}
