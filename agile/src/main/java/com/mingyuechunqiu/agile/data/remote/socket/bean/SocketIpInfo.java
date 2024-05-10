package com.mingyuechunqiu.agile.data.remote.socket.bean;

import androidx.annotation.NonNull;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/9/25 13:32
 *       Desc:       Socket使用的IP相关信息类
 *       Version:    1.0
 * </pre>
 */
public class SocketIpInfo implements Comparable<SocketIpInfo> {

    private String ip;
    private int port;

    public SocketIpInfo(@NonNull String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public int compareTo(@NonNull SocketIpInfo other) {
        if (this == other) {
            return 0;
        } else {
            return this.ip.compareTo(other.ip);
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
