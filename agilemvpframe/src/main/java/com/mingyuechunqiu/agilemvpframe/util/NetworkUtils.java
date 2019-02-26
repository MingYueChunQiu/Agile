package com.mingyuechunqiu.agilemvpframe.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AlertDialog;

import com.mingyuechunqiu.agilemvpframe.agile.AgileMVPFrame;
import com.mingyuechunqiu.agilemvpframe.R;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2017/12/27
 *     desc   : 操作网络的工具类
 *              公共方法：
 *                      1.checkNetState：检测手机网络状态连接
 *     version: 1.0
 * </pre>
 */
public class NetworkUtils {

    public static final int NET_TYPE_DISCONNECTED = 0x00;//网络状态未连接
    public static final int NET_TYPE_UNKNOWN = 0x01;//未知的网络连接类型
    public static final int NET_TYPE_WIFI = 0x02;//WiFi连接状态
    public static final int NET_TYPE_MOBILE = 0x03;//移动网络连接状态

    private static ConnectivityManager sConnMgr;//连接管理器

    /**
     * 提供给外界调用的检测手机网络状态连接方法
     *
     * @param context 获取系统连接管理器的上下文
     * @return 返回手机的网络连接检测结果
     */
    public static boolean checkNetState(Context context) {
        if (Build.VERSION.SDK_INT < 21) {
            return checkNetStateWith21(context);
        }
        return checkNetStateWith21OrNew(context);
    }

    /**
     * 返回当前网络的连接类型
     *
     * @return 分为未连接、未知、WiFi、移动网络四种类型
     */
    public static int getNetworkType() {
        if (!checkNetState(AgileMVPFrame.getAppContext())) {
            return NET_TYPE_DISCONNECTED;
        }
        NetworkInfo networkInfo = sConnMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return NET_TYPE_WIFI;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return NET_TYPE_MOBILE;
            }
        } else {
            return NET_TYPE_UNKNOWN;
        }
        return networkInfo.getType();
    }

    /**
     * 获取系统连接管理器
     *
     * @param context 获取系统连接管理器的上下文
     * @return 返回获取到的系统连接管理器
     */
    private static ConnectivityManager getConnectivityManager(Context context) {
        if (sConnMgr == null) {
            synchronized (NetworkUtils.class) {
                if (sConnMgr == null) {
                    sConnMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    return sConnMgr;
                }
            }
        }
        return sConnMgr;
    }

    /**
     * 检测连接管理器实例是否存在，如果不存在则通过系统进行获取
     *
     * @param context 获取系统连接管理器的上下文
     */
    private static void checkConnMgr(Context context) {
        if (sConnMgr == null) {
            sConnMgr = getConnectivityManager(context);
        }
    }

    /**
     * 检测当前手机的网络连接状态（适用于Android21以下版本）
     *
     * @param context 获取系统连接管理器的上下文
     * @return 返回手机的网络连接检测结果
     */
    private static boolean checkNetStateWith21(Context context) {
        checkConnMgr(context);
        NetworkInfo wifiInfo = sConnMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConnected = wifiInfo.isConnected();
        NetworkInfo mobileInfo = sConnMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConnected = mobileInfo.isConnected();
        return isWifiConnected || isMobileConnected;
    }

    /**
     * 检测当前手机的网络连接状态（适用于Android21及以上版本）
     *
     * @param context 获取系统连接管理器的上下文
     * @return 返回手机的网络连接检测结果
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static boolean checkNetStateWith21OrNew(Context context) {
        checkConnMgr(context);
        Network[] networks = sConnMgr.getAllNetworks();
        for (Network network : networks) {
            NetworkInfo networkInfo = sConnMgr.getNetworkInfo(network);
            if (networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查网络连接类型
     *
     * @param context             对话框所处上下文
     * @param popDialogWithMobile 是否在移动网络状态下弹出确认对话框
     * @param listener            检测网络类型回调接口
     */
    public static void checkNetworkType(Context context, boolean popDialogWithMobile,
                                        final OnCheckNetworkTypeListener listener) {
        checkNetworkType(context, getNetworkType(), popDialogWithMobile, listener);
    }

    /**
     * 检查网络连接类型
     *
     * @param context             对话框所处上下文
     * @param networkType         网络连接类型
     * @param popDialogWithMobile 是否在移动网络状态下弹出确认对话框
     * @param listener            检测网络类型回调接口
     */
    public static void checkNetworkType(Context context, int networkType, boolean popDialogWithMobile,
                                        final OnCheckNetworkTypeListener listener) {
        if (listener == null) {
            return;
        }
        if (checkNetworkTypeIsMobile(networkType)) {
            listener.onConnectedInMobile();
            if (popDialogWithMobile && context != null) {
                new AlertDialog.Builder(context)
                        .setCancelable(false)
                        .setMessage(R.string.prompt_query_mobile_network)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.onConfirmConnectedInMobile();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.onCancelConnectedInMobile();
                            }
                        }).create().show();
            }
        } else {
            listener.onConnectedInWifi();
        }
    }

    /**
     * 检测网络类型是移动网络
     *
     * @param networkType 网络连接类型
     * @return 如果是移动网络返回true，否则返回false
     */
    public static boolean checkNetworkTypeIsMobile(int networkType) {
        return networkType == NetworkUtils.NET_TYPE_MOBILE;
    }

    /**
     * 当检测网络连接类型时回调监听器
     */
    public interface OnCheckNetworkTypeListener {

        /**
         * 当使用移动网络连接时回调
         */
        void onConnectedInMobile();

        /**
         * 当使用WiFi连接时回调
         */
        void onConnectedInWifi();

        /**
         * 当确认使用移动网络连接时回调
         */
        void onConfirmConnectedInMobile();

        /**
         * 当取消使用移动网络连接时回调
         */
        void onCancelConnectedInMobile();
    }

}
