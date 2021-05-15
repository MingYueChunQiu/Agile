package com.mingyuechunqiu.agile.frame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.frame.data.remote.AgileNetworkConfig;
import com.mingyuechunqiu.agile.frame.engine.image.IAgileImageEngine;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/11/7
 *     desc   : 框架配置类
 *     version: 1.0
 * </pre>
 */
public class AgileFrameConfigure {

    private Builder mBuilder;

    public AgileFrameConfigure() {
        this(null);
    }

    public AgileFrameConfigure(@Nullable Builder builder) {
        mBuilder = builder;
        if (mBuilder == null) {
            mBuilder = new Builder();
        }
    }

    public AgileNetworkConfig getNetworkConfig() {
        return mBuilder.networkConfig;
    }

    public void setNetworkConfig(@NonNull AgileNetworkConfig networkConfig) {
        mBuilder.networkConfig = networkConfig;
    }

    public IAgileImageEngine getImageEngine() {
        return mBuilder.imageEngine;
    }

    public void setImageEngine(@NonNull IAgileImageEngine imageEngine) {
        mBuilder.imageEngine = imageEngine;
    }

    /**
     * 链式调用
     */
    public static class Builder {

        private AgileNetworkConfig networkConfig;//网络框架配置信息对象
        private IAgileImageEngine imageEngine;//图片显示引擎

        public Builder() {
            //设置默认配置
            networkConfig = new AgileNetworkConfig();
        }

        public AgileFrameConfigure build() {
            return new AgileFrameConfigure(this);
        }

        public AgileNetworkConfig getNetworkConfig() {
            return networkConfig;
        }

        public Builder setNetworkConfig(@NonNull AgileNetworkConfig networkConfig) {
            this.networkConfig = networkConfig;
            return this;
        }

        public IAgileImageEngine getImageEngine() {
            return imageEngine;
        }

        public Builder setImageEngine(@NonNull IAgileImageEngine imageEngine) {
            this.imageEngine = imageEngine;
            return this;
        }
    }
}
