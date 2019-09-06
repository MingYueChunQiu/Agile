package com.mingyuechunqiu.agile.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.mingyuechunqiu.agile.util.NetworkUtils;

import static com.mingyuechunqiu.agile.util.NetworkUtils.NetworkTypeConstants.NET_TYPE_MOBILE;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/07/04
 *     desc   : 直播中监听网络连接类型改变的广播接收器
 *              继承自BroadcastReceiver
 *     version: 1.0
 * </pre>
 */
public class NetworkConnectedTypeReceiver extends BroadcastReceiver {

    private OnNetworkTypeChangedListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            if (mListener == null) {
                return;
            }
            if (NetworkUtils.getNetworkType() == NET_TYPE_MOBILE) {
                mListener.onNetworkTypeChanged(true);
            } else {
                mListener.onNetworkTypeChanged(false);
            }
        }
    }

    public OnNetworkTypeChangedListener getOnNetworkTypeChangedListener() {
        return mListener;
    }

    public void setOnNetworkTypeChangedListener(OnNetworkTypeChangedListener listener) {
        mListener = listener;
    }

    /**
     * 监听网络连接类型改变的监听器
     */
    public interface OnNetworkTypeChangedListener {

        /**
         * 当网络状态类型改变时回调
         *
         * @param isMobile 是否是使用移动网络
         */
        void onNetworkTypeChanged(boolean isMobile);
    }
}
