package com.mingyuechunqiu.agilemvpframe.agile;

import com.mingyuechunqiu.agilemvpframe.agile.engine.GlideImageEngine;
import com.mingyuechunqiu.agilemvpframe.agile.remote.AgileNetworkConfig;
import com.mingyuechunqiu.agilemvpframe.framework.engine.IImageEngine;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/11/7
 *     desc   : 框架配置类
 *     version: 1.0
 * </pre>
 */
public class AgileMVPFrameConfigure {

    private AgileNetworkConfig networkConfig;//网络框架配置信息对象
    private IImageEngine imageEngine;//图片显示引擎

    public AgileMVPFrameConfigure() {
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
        this.imageEngine = imageEngine;
    }

    /**
     * 链式调用
     */
    public static class Builder {

        private AgileMVPFrameConfigure mConfigure;

        public Builder() {
            mConfigure = new AgileMVPFrameConfigure();
        }

        public AgileMVPFrameConfigure build() {
            return mConfigure;
        }

        public AgileNetworkConfig getNetworkConfig() {
            return mConfigure.networkConfig;
        }

        public Builder setNetworkConfig(AgileNetworkConfig networkConfig) {
            mConfigure.networkConfig = networkConfig;
            return this;
        }

        public IImageEngine getImageEngine() {
            return mConfigure.imageEngine;
        }

        public Builder setImageEngine(IImageEngine imageEngine) {
            mConfigure.imageEngine = imageEngine;
            return this;
        }
    }
}
