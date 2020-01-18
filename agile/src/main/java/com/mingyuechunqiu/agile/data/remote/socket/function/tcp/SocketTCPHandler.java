package com.mingyuechunqiu.agile.data.remote.socket.function.tcp;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.data.remote.socket.bean.SocketIpInfo;
import com.mingyuechunqiu.agile.data.remote.socket.bean.SocketReceiveData;
import com.mingyuechunqiu.agile.data.remote.socket.bean.SocketSendData;
import com.mingyuechunqiu.agile.data.remote.socket.exception.SocketHandlerException;
import com.mingyuechunqiu.agile.data.remote.socket.function.SocketManagerProvider;
import com.mingyuechunqiu.agile.data.remote.socket.function.framework.data.SocketDataCallback;
import com.mingyuechunqiu.agile.data.remote.socket.function.framework.handler.SocketTCPHandlerCallback;
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketConnectState.STATE_ERROR;
import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketConnectState.STATE_IDLE;
import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketConnectState.STATE_SUCCESS;
import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketConstants.SOCKET_TIME_OUT;
import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketErrorType.TYPE_EMPTY_RESPONSE_DATA;
import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketErrorType.TYPE_IP_ERROR;
import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketErrorType.TYPE_REQUEST_PARAMS_ERROR;
import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketErrorType.TYPE_SOCKET_ERROR;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/9/24 14:09
 *       Desc:       socket处理TCP的实现类
 *                   实现ISocketTcpHandler
 *       Version:    1.0
 * </pre>
 */
public class SocketTCPHandler implements ISocketTCPHandler {

    private SocketTCPHandlerCallback mCallback;
    private SocketDataCallback mDataCallback;
    private Socket mSocket;
    private SocketSendData mSendData;
    private boolean waitReceiveData = false;//是否等待接受数据
    private Disposable mHeartBeatDisposable;
    private int mState = STATE_IDLE;//当前Socket状态
    private boolean beInConnection;//是否处于成功连接状态
    private long mSendDataTime;//记录下发送数据时间，用于超过无数据传递时持续时间时断开连接

    public SocketTCPHandler(@NonNull SocketTCPHandlerCallback callback) {
        mCallback = callback;
    }

    @Override
    public boolean isConnectSuccess() {
        return mState == STATE_SUCCESS;
    }

    @Override
    public void sendHeartBeat(@NonNull SocketSendData data, @NonNull SocketDataCallback callback) {
        if (mCallback == null) {
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
        final SocketIpInfo finalInfo = info;
        mCallback.executeTask(new Runnable() {
            @Override
            public void run() {
                if (!initTCPSocket(finalInfo)) {
                    return;
                }
                if (SocketManagerProvider.getGlobalSocketConfigure().isLongConnection()) {
                    releaseHeartBeatDisposable();
                    mHeartBeatDisposable = Observable.interval(5, TimeUnit.SECONDS)
                            .doOnNext(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) {
                                    sendHeartBeat();
                                }
                            })
                            .subscribe();
                } else {
                    sendData(mSendData, mDataCallback);
                }
            }
        });
    }

    /**
     * 每隔5秒发送心跳包，维持长连接
     */
    private void sendHeartBeat() {
        if (mCallback == null || mSendData == null) {
            releaseHeartBeatDisposable();
            if (mDataCallback != null) {
                mCallback.handleFailureResult(mDataCallback, TYPE_REQUEST_PARAMS_ERROR, "Socket网络请求参数错误");
            }
            return;
        }
        sendSocketData(SocketManagerProvider.getGlobalSocketConfigure().getHeartBeat().getData());
    }

    @Override
    public void receiveData() {
        if (mCallback == null) {
            return;
        }
        mCallback.executeTask(new Runnable() {
            @Override
            public void run() {
                if (mSocket == null) {
                    return;
                }
                BufferedReader bfr;
                try {
                    bfr = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), "GB2312"));
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                waitReceiveData = true;
                handleResponse(bfr);
            }
        });
    }

    @Override
    public void sendData(@NonNull SocketSendData data, @Nullable SocketDataCallback callback) {
        if (!waitReceiveData) {
            receiveData();
        }
        mSendDataTime = System.currentTimeMillis();
        sendSocketData(data.getData());
    }

    @Override
    public void release() {
        releaseHeartBeatDisposable();
        if (mSocket != null) {
            if (!mSocket.isClosed()) {
                try {
                    mSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mSocket = null;
        }
        waitReceiveData = false;
        mSendData = null;
        mState = STATE_IDLE;
        mDataCallback = null;
        mCallback = null;
        beInConnection = false;
        mSendDataTime = 0;
    }

    /**
     * 处理网络响应
     *
     * @param bfr 输入流
     */
    private void handleResponse(BufferedReader bfr) {
        while (waitReceiveData) {
            if (checkSilentDuration()) return;
            try {
                StringBuilder sbData = new StringBuilder();
                String line;
                while ((line = bfr.readLine()) != null) {
                    sbData.append(line);
                }
                String response = sbData.toString();
                if (TextUtils.isEmpty(response)) {
                    if (mCallback != null && mDataCallback != null) {
                        mCallback.handleFailureResult(mDataCallback, TYPE_EMPTY_RESPONSE_DATA, "socket数据为空");
                    }
                } else {
                    handleSuccessfulResponse(response);
                }
            } catch (IOException e) {
                LogManagerProvider.e("SocketTCPHandler:handleResponse", e.getMessage());
                mState = STATE_ERROR;
                if (mSendData != null && mCallback != null && mDataCallback != null) {
                    mCallback.handleFailureResult(mDataCallback, TYPE_SOCKET_ERROR, "receiveData异常：" + e.getMessage());
                }
            }
        }
    }

    /**
     * 处理成功响应数据
     *
     * @param response 响应字符串
     */
    private void handleSuccessfulResponse(String response) {
        mState = STATE_SUCCESS;
        if (mDataCallback == null) {
            return;
        }
        if (mDataCallback.isHeartBeat(response)) {
            if (mCallback != null) {
                mCallback.handleHeartBeatSuccessResult(mDataCallback);
            }
            if (!beInConnection) {
                sendData(mSendData, mDataCallback);
                beInConnection = true;
            }
        } else {
            SocketReceiveData data = new SocketReceiveData();
            data.setData(response);
            if (mCallback != null) {
                mCallback.handleDataSuccessResult(mDataCallback, data);
                if (!SocketManagerProvider.getGlobalSocketConfigure().isLongConnection()) {
                    release();
                }
            }
        }
    }

    /**
     * 检查无数据传递时持续时间是否超时
     *
     * @return 如果超时返回true，否则返回false
     */
    private boolean checkSilentDuration() {
        if (mCallback != null) {
            //当超过无数据传递时持续时间时，断开该IP地址的Socket连接
            if (mSendDataTime > 0 && (System.currentTimeMillis() - mSendDataTime)
                    >= SocketManagerProvider.getGlobalSocketConfigure().getSilentDuration()) {
                if (mSendData != null) {
                    mCallback.releaseConnect(mSendData.getSocketIpInfo());
                    return true;
                }
            }
        }
        return false;
    }

    private void sendSocketData(@NonNull String data) {
        if (mSocket == null) {
            return;
        }
        try {
            BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream(), "GB2312"));
            bos.write(data);
            bos.flush();
        } catch (IOException e) {
            LogManagerProvider.e("SocketTCPHandler:sendSocketData", e.getMessage());
            if (mCallback != null && mDataCallback != null) {
                mCallback.handleFailureResult(mDataCallback, TYPE_SOCKET_ERROR, e.getMessage());
            }
        }
    }

    /**
     * 初始化TCP socket
     *
     * @param ipInfo 连接地址信息
     * @return 初始化socket成功返回true，否则返回false
     */
    private boolean initTCPSocket(@NonNull SocketIpInfo ipInfo) {
        if (mSocket == null) {
            try {
                mSocket = new Socket(ipInfo.getIp(), ipInfo.getPort());
                mSocket.setKeepAlive(true);
                mSocket.setTcpNoDelay(true);
                mSocket.setReuseAddress(true);
                mSocket.setSoTimeout(SOCKET_TIME_OUT);
                return true;
            } catch (IOException e) {
                LogManagerProvider.e("SocketTCPHandler:initTCPSocket", e.getMessage());
                mState = STATE_ERROR;
                return false;
            }
        }
        return true;
    }

    private void releaseHeartBeatDisposable() {
        if (mHeartBeatDisposable != null) {
            if (!mHeartBeatDisposable.isDisposed()) {
                mHeartBeatDisposable.dispose();
            }
            waitReceiveData = false;
            mHeartBeatDisposable = null;
        }
    }
}
