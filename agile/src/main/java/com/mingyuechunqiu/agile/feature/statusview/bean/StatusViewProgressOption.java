package com.mingyuechunqiu.agile.feature.statusview.bean;

import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020/4/9 4:43 PM
 *      Desc:       状态视图进度条配置信息类
 *      Version:    1.0
 * </pre>
 */
public class StatusViewProgressOption {

    private Builder mBuilder;

    public StatusViewProgressOption() {
        this(new Builder());
    }

    public StatusViewProgressOption(@NonNull Builder builder) {
        mBuilder = builder;
    }

    public StatusViewConstants.ProgressStyle getProgressStyle() {
        return mBuilder.progressStyle;
    }

    public void setProgressStyle(@NonNull StatusViewConstants.ProgressStyle progressStyle) {
        mBuilder.progressStyle = progressStyle;
    }

    public Drawable getProgressDrawable() {
        return mBuilder.progressDrawable;
    }

    public void setProgressDrawable(@NonNull Drawable progressDrawable) {
        mBuilder.progressDrawable = progressDrawable;
    }

    public int getProgressSize() {
        return mBuilder.progressSize;
    }

    public void setProgressSize(int progressSize) {
        mBuilder.progressSize = progressSize;
    }

    @ColorInt
    public int getDaisyColor() {
        return mBuilder.daisyColor;
    }

    public void setDaisyColor(@ColorInt int daisyColor) {
        mBuilder.daisyColor = daisyColor;
    }

    /**
     * 链式调用
     */
    public static class Builder {

        private StatusViewConstants.ProgressStyle progressStyle;
        private Drawable progressDrawable;//无进度图像
        private int progressSize;//进度条大小
        private @ColorInt
        int daisyColor;//菊花样式颜色

        public StatusViewProgressOption build() {
            return new StatusViewProgressOption(this);
        }

        public StatusViewConstants.ProgressStyle getProgressStyle() {
            return progressStyle;
        }

        public Builder setProgressStyle(@NonNull StatusViewConstants.ProgressStyle progressStyle) {
            this.progressStyle = progressStyle;
            return this;
        }

        public Drawable getProgressDrawable() {
            return progressDrawable;
        }

        public Builder setProgressDrawable(@NonNull Drawable progressDrawable) {
            this.progressDrawable = progressDrawable;
            return this;
        }

        public int getProgressSize() {
            return progressSize;
        }

        public Builder setProgressSize(int progressSize) {
            this.progressSize = progressSize;
            return this;
        }

        @ColorInt
        public int getDaisyColor() {
            return daisyColor;
        }

        public Builder setDaisyColor(@ColorInt int daisyColor) {
            this.daisyColor = daisyColor;
            return this;
        }
    }
}
