package com.mingyuechunqiu.agile.frame.data.remote;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.data.remote.retrofit.controller.BaseRetrofitManager;

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

    private Builder mBuilder;

    public AgileNetworkConfig() {
        this(new Builder());
    }

    public AgileNetworkConfig(@NonNull Builder builder) {
        mBuilder = builder;
    }

    public long getConnectNetTimeout() {
        return mBuilder.connectNetTimeout;
    }

    public void setConnectNetTimeout(long connectNetTimeout) {
        mBuilder.connectNetTimeout = connectNetTimeout;
    }

    public long getReadNetTimeout() {
        return mBuilder.readNetTimeout;
    }

    public void setReadNetTimeout(long readNetTimeout) {
        mBuilder.readNetTimeout = readNetTimeout;
    }

    public long getWriteNetTimeout() {
        return mBuilder.writeNetTimeout;
    }

    public void setWriteNetTimeout(long writeNetTimeout) {
        mBuilder.writeNetTimeout = writeNetTimeout;
    }

    /**
     * 链式调用
     */
    public static class Builder {

        private long connectNetTimeout;//网络连接超时时间(毫秒数)
        private long readNetTimeout;//网络读取超时时间(毫秒数)
        private long writeNetTimeout;//网络写入超时时间(毫秒数)

        public Builder() {
            connectNetTimeout = readNetTimeout = writeNetTimeout = BaseRetrofitManager.DEFAULT_TIMEOUT;
        }

        public AgileNetworkConfig build() {
            return new AgileNetworkConfig(this);
        }

        public long getConnectNetTimeout() {
            return connectNetTimeout;
        }

        public Builder setConnectNetTimeout(long connectNetTimeout) {
            this.connectNetTimeout = connectNetTimeout;
            return this;
        }

        public long getReadNetTimeout() {
            return readNetTimeout;
        }

        public Builder setReadNetTimeout(long readNetTimeout) {
            this.readNetTimeout = readNetTimeout;
            return this;
        }

        public long getWriteNetTimeout() {
            return writeNetTimeout;
        }

        public Builder setWriteNetTimeout(long writeNetTimeout) {
            this.writeNetTimeout = writeNetTimeout;
            return this;
        }
    }
}
