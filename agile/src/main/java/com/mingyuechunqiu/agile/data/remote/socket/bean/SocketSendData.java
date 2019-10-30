package com.mingyuechunqiu.agile.data.remote.socket.bean;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/9/25 9:49
 *       Desc:       socket发送数据类
 *       Version:    1.0
 * </pre>
 */
public class SocketSendData {

    private String data;

    private SocketIpInfo socketIpInfo;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public SocketIpInfo getSocketIpInfo() {
        return socketIpInfo;
    }

    public void setSocketIpInfo(SocketIpInfo socketIpInfo) {
        this.socketIpInfo = socketIpInfo;
    }
}
