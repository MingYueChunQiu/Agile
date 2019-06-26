package com.mingyuechunqiu.agile.frame;

import com.mingyuechunqiu.agile.frame.engine.GlideImageEngine;
import com.mingyuechunqiu.agile.frame.data.remote.AgileNetworkConfig;
import com.mingyuechunqiu.agile.framework.engine.IImageEngine;

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

    private AgileNetworkConfig networkConfig;//网络框架配置信息对象
    private IImageEngine imageEngine;//图片显示引擎

    public AgileFrameConfigure() {
        //设置默认配置
        networkConfig = new AgileNetworkConfig();
        imageEngine = new GlideImageEngine();
    }

    public AgileNetworkConfig getNetworkConfig() {
        return networkConfig;
    }

    public void setNetworkConfig(AgileNetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
    }

    public IImageEngine getImageEngine() {
        return imageEngine;
    }

    public void setImageEngine(IImageEngine imageEngine) {
        if (this.imageEngine == imageEngine || imageEngine == null) {
            return;
        }
        this.imageEngine = imageEngine;
    }

    /**
     * 链式调用
     */
    public static class Builder {

        private AgileFrameConfigure mConfigure;

        public Builder() {
            mConfigure = new AgileFrameConfigure();
        }

        public AgileFrameConfigure build() {
            return mConfigure;
        }

        public AgileNetworkConfig getNetworkConfig() {
            return mConfigure.networkConfig;
        }

        public Builder setNetworkConfig(AgileNetworkConfig networkConfig) {
            mConfigure.setNetworkConfig(networkConfig);
            return this;
        }

        public IImageEngine getImageEngine() {
            return mConfigure.imageEngine;
        }

        public Builder setImageEngine(IImageEngine imageEngine) {
            mConfigure.setImageEngine(imageEngine);
            return this;
        }
    }
}
