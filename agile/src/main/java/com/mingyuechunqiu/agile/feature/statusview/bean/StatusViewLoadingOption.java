package com.mingyuechunqiu.agile.feature.statusview.bean;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-11-24 22:23
 *      Desc:       加载状态视图配置类
 *      Version:    1.0
 * </pre>
 */
public class StatusViewLoadingOption {

    private Builder mBuilder;

    public StatusViewLoadingOption() {
        this(new Builder());
    }

    public StatusViewLoadingOption(@NonNull Builder builder) {
        mBuilder = builder;
    }

    /**
     * 链式调用
     */
    public static class Builder {

        private StatusViewGeneralOption option;//通用配置对象
        private Drawable loadingBackground;//加载背景图像
        private Drawable indeterminateDrawable;//无进度图像

        public StatusViewLoadingOption build() {
            return new StatusViewLoadingOption(this);
        }

        public StatusViewGeneralOption getStatusViewGeneralOption() {
            return option;
        }

        public Builder setStatusViewGeneralOption(StatusViewGeneralOption option) {
            this.option = option;
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
    }
}
