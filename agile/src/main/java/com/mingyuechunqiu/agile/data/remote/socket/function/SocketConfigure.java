package com.mingyuechunqiu.agile.data.remote.socket.function;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.data.remote.socket.bean.SocketIpInfo;
import com.mingyuechunqiu.agile.data.remote.socket.bean.SocketSendData;

import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketConstants.SOCKET_HEART_BEAT;
import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketConstants.SOCKET_RETRY_COUNT;
import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketConstants.SOCKET_SILENT_DURATION;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/9/29 13:55
 *       Desc:       Socket管理器配置类
 *       Version:    1.0
 * </pre>
 */
public class SocketConfigure {

    private Builder mBuilder;

    public SocketConfigure() {
        this(new Builder());
    }

    public SocketConfigure(@NonNull Builder builder) {
        mBuilder = builder;
    }

    public SocketSendData getHeartBeat() {
        return mBuilder.heartBeat;
    }

    public void setHeartBeat(SocketSendData heartBeat) {
        mBuilder.heartBeat = heartBeat;
    }

    public int getRetryCount() {
        return mBuilder.retryCount;
    }

    public void setRetryCount(int retryCount) {
        mBuilder.retryCount = retryCount;
    }

    public int getSilentDuration() {
        return mBuilder.silentDuration;
    }

    public void setSilentDuration(int silentDuration) {
        mBuilder.silentDuration = silentDuration;
    }

    public boolean isLongConnection() {
        return mBuilder.longConnection;
    }

    public void setLongConnection(boolean longConnection) {
        mBuilder.longConnection = longConnection;
    }

    @Nullable
    public SocketIpInfo getSocketIpInfo() {
        return mBuilder.socketIpInfo;
    }

    public void setSocketIpInfo(@NonNull SocketIpInfo socketIpInfo) {
        mBuilder.socketIpInfo = socketIpInfo;
    }

    /**
     * 链式调用
     */
    public static class Builder {

        private SocketSendData heartBeat;//心跳包数据
        private int retryCount;//重试次数
        private int silentDuration;//无数据传递时持续时间
        private boolean longConnection;//标记是否是长来连接
        @Nullable
        private SocketIpInfo socketIpInfo;//连接的IP地址信息

        public Builder() {
            SocketSendData heartBeat = new SocketSendData();
            heartBeat.setData(SOCKET_HEART_BEAT);
            this.heartBeat = heartBeat;
            retryCount = SOCKET_RETRY_COUNT;
            this.silentDuration = SOCKET_SILENT_DURATION;
            longConnection = false;
        }

        public SocketConfigure build() {
            return new SocketConfigure(this);
        }

        public SocketSendData getHeartBeat() {
            return heartBeat;
        }

        public Builder setHeartBeat(SocketSendData heartBeat) {
            this.heartBeat = heartBeat;
            return this;
        }

        public int getRetryCount() {
            return retryCount;
        }

        public Builder setRetryCount(int retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        public int getSilentDuration() {
            return silentDuration;
        }

        public Builder setSilentDuration(int silentDuration) {
            this.silentDuration = silentDuration;
            return this;
        }

        public boolean isLongConnection() {
            return longConnection;
        }

        public Builder setLongConnection(boolean longConnection) {
            this.longConnection = longConnection;
            return this;
        }

        @Nullable
        public SocketIpInfo getSocketIpInfo() {
            return socketIpInfo;
        }

        public Builder setSocketIpInfo(@NonNull SocketIpInfo socketIpInfo) {
            this.socketIpInfo = socketIpInfo;
            return this;
        }
    }
}
