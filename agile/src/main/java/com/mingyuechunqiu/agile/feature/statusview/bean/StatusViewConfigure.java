package com.mingyuechunqiu.agile.feature.statusview.bean;

import androidx.annotation.NonNull;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-11-24 18:26
 *      Desc:       全局状态配置存储类
 *      Version:    1.0
 * </pre>
 */
public final class StatusViewConfigure {

    private Builder mBuilder;

    private StatusViewConfigure() {
        this(new Builder());
    }

    public StatusViewConfigure(@NonNull Builder builder) {
        mBuilder = builder;
    }

    @NonNull
    public Builder getBuilder() {
        return mBuilder;
    }

    public StatusViewOption getLoadingOption() {
        return mBuilder.loadingOption;
    }

    public void setLoadingOption(StatusViewOption loadingOption) {
        mBuilder.loadingOption = loadingOption;
    }

    public StatusViewOption getEmptyOption() {
        return mBuilder.emptyOption;
    }

    public void setEmptyOption(StatusViewOption emptyOption) {
        mBuilder.emptyOption = emptyOption;
    }

    public StatusViewOption getNetworkAnomalyOption() {
        return mBuilder.networkAnomalyOption;
    }

    public void setNetworkAnomalyOption(StatusViewOption networkAnomalyOption) {
        mBuilder.networkAnomalyOption = networkAnomalyOption;
    }

    public StatusViewOption getErrorOption() {
        return mBuilder.errorOption;
    }

    public void setErrorOption(StatusViewOption errorOption) {
        mBuilder.errorOption = errorOption;
    }

    public StatusViewOption getCustomOption() {
        return mBuilder.customOption;
    }

    public void setCustomOption(StatusViewOption customOption) {
        mBuilder.customOption = customOption;
    }

    /**
     * 链式调用
     */
    public static class Builder {

        private StatusViewOption generalOption;//通用状态配置信息
        private StatusViewOption loadingOption;//加载状态配置信息
        private StatusViewOption emptyOption;//空状态配置信息
        private StatusViewOption networkAnomalyOption;//网络异常状态配置信息
        private StatusViewOption errorOption;//错误配置信息
        private StatusViewOption customOption;//自定义状态配置信息

        public StatusViewConfigure build() {
            return new StatusViewConfigure(this);
        }

        public StatusViewOption getStatusViewOption() {
            return generalOption;
        }

        public void setStatusViewOption(StatusViewOption generalOption) {
            this.generalOption = generalOption;
        }

        public StatusViewOption getLoadingOption() {
            return loadingOption;
        }

        public Builder setLoadingOption(StatusViewOption loadingOption) {
            this.loadingOption = loadingOption;
            return this;
        }

        public StatusViewOption getEmptyOption() {
            return emptyOption;
        }

        public Builder setEmptyOption(StatusViewOption emptyOption) {
            this.emptyOption = emptyOption;
            return this;
        }

        public StatusViewOption getNetworkAnomalyOption() {
            return networkAnomalyOption;
        }

        public Builder setNetworkAnomalyOption(StatusViewOption networkAnomalyOption) {
            this.networkAnomalyOption = networkAnomalyOption;
            return this;
        }

        public StatusViewOption getErrorOption() {
            return errorOption;
        }

        public Builder setErrorOption(StatusViewOption errorOption) {
            this.errorOption = errorOption;
            return this;
        }

        public StatusViewOption getCustomOption() {
            return customOption;
        }

        public Builder setCustomOption(StatusViewOption customOption) {
            this.customOption = customOption;
            return this;
        }
    }
}
