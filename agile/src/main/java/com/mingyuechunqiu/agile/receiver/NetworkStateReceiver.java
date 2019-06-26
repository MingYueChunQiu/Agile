package com.mingyuechunqiu.agile.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.mingyuechunqiu.agile.R;
import com.mingyuechunqiu.agile.util.NetworkUtils;

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
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            if (!NetworkUtils.checkNetState(context)) {
                Toast.makeText(context, context.getString(R.string.agile_network_disconnected),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isFirstNetConnected) {
                Toast.makeText(context, R.string.agile_network_connected, Toast.LENGTH_SHORT).show();
            } else {
                isFirstNetConnected = false;
            }
        }
    }
}
