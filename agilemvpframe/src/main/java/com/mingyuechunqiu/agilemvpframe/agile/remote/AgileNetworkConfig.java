package com.mingyuechunqiu.agilemvpframe.agile.remote;

import com.mingyuechunqiu.agilemvpframe.data.remote.retrofit.controller.BaseRetrofitManager;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/5/31
 *     desc   : 网络框架配置信息类
 *     version: 1.0
 * </pre>
 */
public class AgileNetworkConfig {

    private int connectNetTimeout;//网络连接超时时间(秒数)
    private int readNetTimeout;//网络读取超时时间(秒数)
    private int writeNetTimeout;//网络写入超时时间(秒数)

    public AgileNetworkConfig() {
        connectNetTimeout = readNetTimeout = writeNetTimeout = BaseRetrofitManager.DEFAULT_TIMEOUT;
    }

    public int getConnectNetTimeout() {
        return connectNetTimeout;
    }

    public void setConnectNetTimeout(int connectNetTimeout) {
        this.connectNetTimeout = connectNetTimeout;
    }

    public int getReadNetTimeout() {
        return readNetTimeout;
    }

    public void setReadNetTimeout(int readNetTimeout) {
        this.readNetTimeout = readNetTimeout;
    }

    public int getWriteNetTimeout() {
        return writeNetTimeout;
    }

    public void setWriteNetTimeout(int writeNetTimeout) {
        this.writeNetTimeout = writeNetTimeout;
    }

    /**
     * 链式调用
     */
    public static class Builder {
        private AgileNetworkConfig mConfig;

        public Builder() {
            mConfig = new AgileNetworkConfig();
        }

        public AgileNetworkConfig build() {
            return mConfig;
        }

        public int getConnectNetTimeout() {
            return mConfig.connectNetTimeout;
        }

        public Builder setConnectNetTimeout(int connectNetTimeout) {
            mConfig.connectNetTimeout = connectNetTimeout;
            return this;
        }

        public int getReadNetTimeout() {
            return mConfig.readNetTimeout;
        }

        public Builder setReadNetTimeout(int readNetTimeout) {
            mConfig.readNetTimeout = readNetTimeout;
            return this;
        }

        public int getWriteNetTimeout() {
            return mConfig.writeNetTimeout;
        }

        public Builder setWriteNetTimeout(int writeNetTimeout) {
            mConfig.writeNetTimeout = writeNetTimeout;
            return this;
        }
    }
}
