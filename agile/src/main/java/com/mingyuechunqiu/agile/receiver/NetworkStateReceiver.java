package com.mingyuechunqiu.agile.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.mingyuechunqiu.agile.R;
import com.mingyuechunqiu.agile.util.NetworkUtils;
import com.mingyuechunqiu.agile.util.ToastUtils;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2017/12/27
 *     desc   : 手机网络连接状态改变的接收者
 *              继承自BroadcastReceiver
 *     version: 1.0
 * </pre>
 */
public class NetworkStateReceiver extends BroadcastReceiver {

    private boolean isFirstNetConnected = true;//标记是否第一次进入应用时网络连接着

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context == null || intent == null) {
            return;
        }
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            if (!NetworkUtils.checkNetworkIsConnected()) {
                ToastUtils.showToast(context, R.string.agile_network_disconnected);
                return;
            }
            if (!isFirstNetConnected) {
                ToastUtils.showToast(context, R.string.agile_network_connected);
            } else {
                isFirstNetConnected = false;
            }
        }
    }
}
