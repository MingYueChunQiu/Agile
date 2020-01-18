package com.mingyuechunqiu.agile.data.remote.socket.function;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.data.remote.socket.bean.SocketIpInfo;
import com.mingyuechunqiu.agile.data.remote.socket.bean.SocketReceiveData;
import com.mingyuechunqiu.agile.data.remote.socket.bean.SocketResultInfo;
import com.mingyuechunqiu.agile.data.remote.socket.bean.SocketSendData;
import com.mingyuechunqiu.agile.data.remote.socket.exception.SocketHandlerException;
import com.mingyuechunqiu.agile.data.remote.socket.function.framework.data.SocketDataCallback;
import com.mingyuechunqiu.agile.data.remote.socket.function.framework.handler.SocketTCPHandlerCallback;
import com.mingyuechunqiu.agile.data.remote.socket.function.framework.handler.SocketUDPHandlerCallback;
import com.mingyuechunqiu.agile.data.remote.socket.function.tcp.ISocketTCPHandler;
import com.mingyuechunqiu.agile.data.remote.socket.function.tcp.SocketTCPHandler;
import com.mingyuechunqiu.agile.data.remote.socket.function.udp.ISocketUDPHandler;
import com.mingyuechunqiu.agile.data.remote.socket.function.udp.SocketUDPHandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.mingyuechunqiu.agile.data.remote.socket.constants.SocketErrorType.TYPE_IP_ERROR;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    https://github.com/MingYueChunQiu
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/9/25 9:27
 *       Desc:       Socket处理器
 *                   实现ISocketHandler,SocketUDPHandlerCallback,SocketTCPHandlerCallback
 *       Version:    1.0
 * </pre>
 */
class SocketHandler implements ISocketHandler, SocketUDPHandlerCallback,
        SocketTCPHandlerCallback {

    private static final int MSG_HEART_BEAT_SUCCESS = 1;
    private static final int MSG_DATA_SUCCESS = 2;
    private static final int MSG_FAILURE = 3;

    private ExecutorService mExecutor;
    private InternalHandler mHandler;
    private Map<SocketIpInfo, Pair<ISocketUDPHandler, ISocketTCPHandler>> mCacheMap;

    SocketHandler() {
        int cpuNumbers = Runtime.getRuntime().availableProcessors();
        mExecutor = Executors.newFixedThreadPool(cpuNumbers);
        mHandler = new InternalHandler(Looper.getMainLooper());
        mCacheMap = new HashMap<>();
    }

    @Override
    public void sendUdpHeartBeat(@NonNull SocketSendData data, @NonNull SocketDataCallback callback) {
        if (!SocketManagerProvider.getGlobalSocketConfigure().isLongConnection()) {
            new SocketUDPHandler(this).sendHeartBeat(data, callback);
            return;
        }
        SocketIpInfo info = data.getSocketIpInfo();
        if (info == null) {
            callback.onGetDataFailed(new SocketHandlerException(TYPE_IP_ERROR, "IP地址异常"));
            return;
        }
        boolean hasContained = false;//是否该IP地址已经包含
        ISocketUDPHandler udpHandler = null;
        for (Map.Entry<SocketIpInfo, Pair<ISocketUDPHandler, ISocketTCPHandler>> entry : mCacheMap.entrySet()) {
            if (info.getIp().equals(entry.getKey().getIp())) {
                udpHandler = entry.getValue().first;
                hasContained = true;
                break;
            }
        }
        if (!hasContained) {
            udpHandler = new SocketUDPHandler(this);
            mCacheMap.put(info, new Pair<ISocketUDPHandler, ISocketTCPHandler>(udpHandler, new SocketTCPHandler(this)));
        }
        udpHandler.sendHeartBeat(data, callback);
    }

    @Override
    public void sendTcpData(@NonNull SocketSendData data, @NonNull SocketDataCallback callback) {
        SocketIpInfo info = data.getSocketIpInfo();
        if (info == null) {
            callback.onGetDataFailed(new SocketHandlerException(TYPE_IP_ERROR, "ip地址异常"));
            return;
        }
        sendTcpHeartBeat(data, callback);
    }

    @Override
    public void release() {
        if (mCacheMap != null) {
            for (Pair<ISocketUDPHandler, ISocketTCPHandler> pair : mCacheMap.values()) {
                pair.first.release();
                pair.second.release();
            }
            mCacheMap.clear();
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    @Override
    public void retryReconnect(@NonNull SocketSendData data, @NonNull SocketDataCallback callback) {
        sendUdpHeartBeat(data, callback);
    }

    @Override
    public void connectTcpSuccess(@NonNull SocketSendData data, @NonNull SocketDataCallback callback) {
        SocketIpInfo info = data.getSocketIpInfo();
        if (info == null) {
            callback.onGetDataFailed(new SocketHandlerException(TYPE_IP_ERROR, "IP地址异常"));
            return;
        }
        for (Map.Entry<SocketIpInfo, Pair<ISocketUDPHandler, ISocketTCPHandler>> entry : mCacheMap.entrySet()) {
            if (info.getIp().equals(entry.getKey().getIp())) {
                entry.getValue().second.sendData(data, callback);
                break;
            }
        }
    }

    @Override
    public void onReceiveUdpHeartBeatSuccess(@NonNull SocketSendData data, @NonNull SocketDataCallback callback) {
        if (mCacheMap == null) {
            return;
        }
        SocketIpInfo info = data.getSocketIpInfo();
        if (info == null) {
            callback.onGetDataFailed(new SocketHandlerException(TYPE_IP_ERROR, "IP地址异常"));
            return;
        }
        for (Map.Entry<SocketIpInfo, Pair<ISocketUDPHandler, ISocketTCPHandler>> entry : mCacheMap.entrySet()) {
            if (info.getIp().equals(entry.getKey().getIp())) {
                entry.getValue().second.sendHeartBeat(data, callback);
                break;
            }
        }
    }

    @Override
    public void executeTask(@NonNull Runnable runnable) {
        if (mExecutor != null) {
            mExecutor.execute(runnable);
        }
    }

    @Override
    public void handleHeartBeatSuccessResult(@NonNull SocketDataCallback callback) {
        checkOrCreateInternalHandler();
        SocketResultInfo info = new SocketResultInfo();
        info.setCallback(callback);
        handleResultCallback(MSG_HEART_BEAT_SUCCESS, info);
    }

    @Override
    public void handleDataSuccessResult(@NonNull SocketDataCallback callback, @NonNull SocketReceiveData data) {
        checkOrCreateInternalHandler();
        SocketResultInfo info = new SocketResultInfo();
        info.setCallback(callback);
        info.setReceiveData(data);
        handleResultCallback(MSG_DATA_SUCCESS, info);
    }

    @Override
    public void handleFailureResult(@NonNull SocketDataCallback callback, int errorType, @Nullable String msg) {
        checkOrCreateInternalHandler();
        SocketResultInfo info = new SocketResultInfo();
        info.setCallback(callback);
        info.setErrorType(errorType);
        info.setErrorMessage(msg);
        handleResultCallback(MSG_FAILURE, info);
    }

    @Override
    public void releaseConnect(@Nullable SocketIpInfo info) {
        if (info == null || mCacheMap == null) {
            return;
        }
        for (Iterator<Map.Entry<SocketIpInfo, Pair<ISocketUDPHandler, ISocketTCPHandler>>>
             it = mCacheMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<SocketIpInfo, Pair<ISocketUDPHandler, ISocketTCPHandler>> entry = it.next();
            if (info.getIp().equals(entry.getKey().getIp())) {
                Pair<ISocketUDPHandler, ISocketTCPHandler> pair = entry.getValue();
                if (pair.first != null) {
                    pair.first.release();
                }
                if (pair.second != null) {
                    pair.second.release();
                }
                it.remove();
                break;
            }
        }
    }

    /**
     * 发送TCP心跳包
     *
     * @param data     TCP传输数据
     * @param callback Socket数据接收回调
     */
    private void sendTcpHeartBeat(@NonNull SocketSendData data, @NonNull SocketDataCallback callback) {
        if (!SocketManagerProvider.getGlobalSocketConfigure().isLongConnection()) {
            new SocketTCPHandler(this).sendHeartBeat(data, callback);
            return;
        }
        SocketIpInfo info = data.getSocketIpInfo();
        if (info == null) {
            callback.onGetDataFailed(new SocketHandlerException(TYPE_IP_ERROR, "IP地址异常"));
            return;
        }
        boolean hasContained = false;//是否该IP地址已经包含
        ISocketTCPHandler tcpHandler = null;
        for (Map.Entry<SocketIpInfo, Pair<ISocketUDPHandler, ISocketTCPHandler>> entry : mCacheMap.entrySet()) {
            if (info.getIp().equals(entry.getKey().getIp())) {
                tcpHandler = entry.getValue().second;
                hasContained = true;
                break;
            }
        }
        if (!hasContained) {
            tcpHandler = new SocketTCPHandler(this);
            mCacheMap.put(info, new Pair<ISocketUDPHandler, ISocketTCPHandler>(new SocketUDPHandler(this), tcpHandler));
        }
        tcpHandler.sendHeartBeat(data, callback);
    }

    private void checkOrCreateInternalHandler() {
        if (mHandler == null) {
            mHandler = new InternalHandler(Looper.getMainLooper());
        }
    }

    /**
     * 处理结果回调
     *
     * @param type 消息类型
     * @param info Socket结果信息
     */
    private void handleResultCallback(int type, @NonNull SocketResultInfo info) {
        if (info.getCallback() == null) {
            return;
        }
        checkOrCreateInternalHandler();
        Message message = mHandler.obtainMessage();
        message.what = type;
        message.obj = info;
        mHandler.sendMessage(message);
    }

    /**
     * 内部处理Handler（运行在主线程）
     * 继承自Handler
     */
    private static class InternalHandler extends Handler {

        InternalHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Object resultInfo = msg.obj;
            if (resultInfo instanceof SocketResultInfo) {
                SocketDataCallback callback = ((SocketResultInfo) resultInfo).getCallback();
                switch (msg.what) {
                    case MSG_HEART_BEAT_SUCCESS:
                        callback.onGetHeartBeatSuccess();
                        break;
                    case MSG_DATA_SUCCESS:
                        callback.onGetDataSuccess(((SocketResultInfo) resultInfo).getReceiveData());
                        break;
                    case MSG_FAILURE:
                        callback.onGetDataFailed(
                                new SocketHandlerException(
                                        ((SocketResultInfo) resultInfo).getErrorType(),
                                        ((SocketResultInfo) resultInfo).getErrorMessage())
                        );
                }
            }
        }
    }
}
