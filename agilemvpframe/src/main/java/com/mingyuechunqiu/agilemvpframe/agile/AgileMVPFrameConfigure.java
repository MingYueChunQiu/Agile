package com.mingyuechunqiu.agilemvpframe.agile;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/11/7
 *     desc   : 框架配置类
 *     version: 1.0
 * </pre>
 */
public class AgileMVPFrameConfigure {

    private int netTimeout;//网络超时时间

    public int getNetTimeout() {
        return netTimeout;
    }

    public void setNetTimeout(int netTimeout) {
        this.netTimeout = netTimeout;
    }

    public static class Builder {

        private AgileMVPFrameConfigure mConfigure;

        public Builder() {
            mConfigure = new AgileMVPFrameConfigure();
        }

        public AgileMVPFrameConfigure build() {
            return mConfigure;
        }

        public int getNetTimeout() {
            return mConfigure.netTimeout;
        }

        public void setNetTimeout(int netTimeout) {
            mConfigure.netTimeout = netTimeout;
        }
    }
}
