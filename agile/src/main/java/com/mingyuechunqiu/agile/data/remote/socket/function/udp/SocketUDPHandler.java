package com.mingyuechunqiu.agile.data.remote.socket.function.udp;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.data.remote.socket.bean.SocketIpInfo;
import com.mingyuechunqiu.agile.data.remote.socket.bean.SocketSendData;
import com.mingyuechunqiu.agile.data.remote.socket.exception.SocketHandlerException;
import com.mingyuechunqiu.agile.data.remote.socket.function.SocketManagerProvider;
import com.mingyuechunqiu.agile.data.remote.socket.function.framework.data.SocketDataCallback;
import com.mingyuechunqiu.agile.data.remote.socket.function.framework.handler.SocketUDPHandlerCallback;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketConnectState.STATE_ERROR;
import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketConnectState.STATE_IDLE;
import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketConnectState.STATE_SUCCESS;
import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketConstants.SOCKET_RETRY_COUNT;
import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketConstants.SOCKET_TIME_OUT;
import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketErrorType.TYPE_IP_ERROR;
import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketErrorType.TYPE_REQUEST_PARAMS_ERROR;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/9/25 13:39
 *       Desc:       Socket处理UDP的具体实现类
 *                   实现ISocketUdpHandler
 *       Version:    1.0
 * </pre>
 */
public class SocketUDPHandler implements ISocketUDPHandler {

    private SocketUDPHandlerCallback mCallback;
    private DatagramSocket mSocket;
    private SocketSendData mSendData;
    private boolean waitReceiveData = false;//是否等待接受数据
    private int mRetryCount = 0;//失败重试次数
    private SocketDataCallback mDataCallback;
    private int mState = STATE_IDLE;

    public SocketUDPHandler(@NonNull SocketUDPHandlerCallback callback) {
        mCallback = callback;
    }

    @Override
    public boolean isConnectSuccess() {
        return mState == STATE_SUCCESS;
    }

    @Override
    public void sendHeartBeat(@NonNull SocketSendData data, @NonNull SocketDataCallback callback) {
        if (isConnectSuccess()) {
            if (mCallback != null) {
                mCallback.onReceiveUdpHeartBeatSuccess(data, callback);
            }
            return;
        }
        if (!initUDPSocket(data.getSocketIpInfo())) {
            return;
        }
        mSendData = data;
        SocketIpInfo info = data.getSocketIpInfo();
        if (info == null) {
            info = SocketManagerProvider.getGlobalSocketConfigure().getSocketIpInfo();
            mSendData.setSocketIpInfo(info);
        }
        if (info == null) {
            mSendData = null;
            callback.onGetDataFailed(new SocketHandlerException(TYPE_IP_ERROR, "IP地址异常"));
            return;
        }
        mDataCallback = callback;
        if (mCallback != null) {
            final SocketIpInfo finalInfo = info;
            mCallback.executeTask(new Runnable() {
                @Override
                public void run() {
                    try {
                        InetAddress address = InetAddress.getByName(finalInfo.getIp());
                        String heartBeat = SocketManagerProvider.getGlobalSocketConfigure().getHeartBeat().getData();
                        mSocket.send(new DatagramPacket(heartBeat.getBytes(),
                                heartBeat.length(),
                                address,
                                finalInfo.getPort()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        retrySendHeartBeat();
                    }
                    receiveHeartBeat();
                }
            });
        }
    }

    @Override
    public void receiveHeartBeat() {
        //如果已经开启线程等待接收数据了，就不再开启
        if (waitReceiveData || mCallback == null) {
            return;
        }
        mCallback.executeTask(new Runnable() {
            @Override
            public void run() {
                if (mSendData == null) {
                    if (mCallback != null && mDataCallback != null) {
                        mCallback.handleFailureResult(mDataCallback, TYPE_REQUEST_PARAMS_ERROR, "网络请求参数错误");
                    }
                    return;
                }
                DatagramPacket receivePacket = null;
                byte[] data = new byte[1024];
                try {
                    SocketIpInfo info = mSendData.getSocketIpInfo();
                    if (info == null) {
                        if (mCallback != null && mDataCallback != null) {
                            mCallback.handleFailureResult(mDataCallback, TYPE_IP_ERROR, "IP地址错误");
                        }
                        return;
                    }
                    InetAddress address = InetAddress.getByName(info.getIp());
                    receivePacket = new DatagramPacket(
                            data, data.length, address, info.getPort());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    if (mCallback != null && mDataCallback != null) {
                        mCallback.handleFailureResult(mDataCallback, TYPE_REQUEST_PARAMS_ERROR, "网络请求参数错误");
                    }
                }
                waitReceiveData = true;
                while (waitReceiveData) {
                    try {
                        mSocket.receive(receivePacket);
                        if (mSendData != null) {
                            mState = STATE_SUCCESS;
                            if (mCallback != null) {
                                mCallback.onReceiveUdpHeartBeatSuccess(mSendData, mDataCallback);
                            }
                        }
                    } catch (IOException e) {
                        retrySendHeartBeat();
                    }
                    waitReceiveData = false;
                }
            }
        });
    }

    @Override
    public void release() {
        if (mSocket != null) {
            if (!mSocket.isClosed()) {
                mSocket.close();
            }
            mSocket = null;
        }
        waitReceiveData = false;
        mSendData = null;
        mRetryCount = 0;
        mCallback = null;
        mState = STATE_IDLE;
    }

    /**
     * 初始化UDP socket资源
     *
     * @param info 连接地址信息
     * @return 初始化socket成功返回true，否则返回false
     */
    private boolean initUDPSocket(SocketIpInfo info) {
        if (mSocket == null) {
            try {
                mSocket = new DatagramSocket((info.getPort()));
                mSocket.setReuseAddress(true);
                mSocket.setSoTimeout(SOCKET_TIME_OUT);
                return true;
            } catch (SocketException e) {
                e.printStackTrace();
                mState = STATE_ERROR;
            }
        }
        return true;
    }

    /**
     * 重试发送心跳包
     */
    private void retrySendHeartBeat() {
        mState = STATE_ERROR;
        if (mRetryCount >= SOCKET_RETRY_COUNT) {
            return;
        }
        if (mSendData != null && mDataCallback != null) {
            sendHeartBeat(mSendData, mDataCallback);
        }
    }
}
