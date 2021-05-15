package com.mingyuechunqiu.agile.data.remote.ftp.bean;

import androidx.annotation.NonNull;

import static com.mingyuechunqiu.agile.data.remote.ftp.constants.FTPConstants.DEFAULT_FTP_CONNECT_TIMEOUT;
import static com.mingyuechunqiu.agile.data.remote.ftp.constants.FTPConstants.DEFAULT_FTP_DATA_TIMEOUT;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/10/24 17:01
 *       Desc:       FTP信息类
 *       Version:    1.0
 * </pre>
 */
public class FTPConfigure {

    private final Builder mBuilder;

    public FTPConfigure(@NonNull Builder builder) {
        mBuilder = builder;
    }

    public String getHostName() {
        return mBuilder.hostName;
    }

    public void setHostName(String hostName) {
        mBuilder.hostName = hostName;
    }

    public int getPort() {
        return mBuilder.port;
    }

    public void setPort(int port) {
        mBuilder.port = port;
    }

    public String getUsername() {
        return mBuilder.username;
    }

    public void setUsername(String username) {
        mBuilder.username = username;
    }

    public String getPassword() {
        return mBuilder.password;
    }

    public void setPassword(String password) {
        mBuilder.password = password;
    }

    public String getControlEncoding() {
        return mBuilder.controlEncoding;
    }

    public void setControlEncoding(String controlEncoding) {
        mBuilder.controlEncoding = controlEncoding;
    }

    public int getConnectTimeout() {
        return mBuilder.connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        mBuilder.connectTimeout = connectTimeout;
    }

    public int getDataTimeout() {
        return mBuilder.dataTimeout;
    }

    public void setDataTimeout(int dataTimeout) {
        mBuilder.dataTimeout = dataTimeout;
    }

    /**
     * 链式调用
     */
    public static class Builder {

        private String hostName;//主机名
        private int port;//端口号
        private String username;//用户名 (POS)
        private String password;//密码
        private String controlEncoding;//文件传输编码
        private int connectTimeout;//连接超时（毫秒）
        private int dataTimeout;//数据读取超时（毫秒）

        public Builder(@NonNull String hostName, @NonNull String username, @NonNull String password) {
            this.hostName = hostName;
            this.port = 21;//默认端口号为21
            this.username = username;
            this.password = password;
            this.controlEncoding = "UTF-8";
            this.connectTimeout = DEFAULT_FTP_CONNECT_TIMEOUT;
            this.dataTimeout = DEFAULT_FTP_DATA_TIMEOUT;
        }

        public FTPConfigure build() {
            return new FTPConfigure(this);
        }

        public String getHostName() {
            return hostName;
        }

        public Builder setHostName(String hostName) {
            this.hostName = hostName;
            return this;
        }

        public int getPort() {
            return port;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public String getUsername() {
            return username;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public String getPassword() {
            return password;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public String getControlEncoding() {
            return controlEncoding;
        }

        public Builder setControlEncoding(String controlEncoding) {
            this.controlEncoding = controlEncoding;
            return this;
        }

        public int getConnectTimeout() {
            return connectTimeout;
        }

        public Builder setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public int getDataTimeout() {
            return dataTimeout;
        }

        public Builder setDataTimeout(int dataTimeout) {
            this.dataTimeout = dataTimeout;
            return this;
        }
    }
}
