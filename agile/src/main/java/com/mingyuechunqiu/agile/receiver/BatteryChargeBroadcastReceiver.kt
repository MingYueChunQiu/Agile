package com.mingyuechunqiu.agile.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *      Email:      xiyujieit@163.com
 *      Time:       2020/9/22 9:49 PM
 *      Desc:       电池状态改变广播接收器
 *                  继承自BroadcastReceiver
 *      Version:    1.0
 * </pre>
 */
class BatteryChargeBroadcastReceiver : BroadcastReceiver() {

    var mListener: OnBatteryChangeListener? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (Intent.ACTION_POWER_CONNECTED == action || Intent.ACTION_POWER_DISCONNECTED == action) {
            mListener?.onBatteryChange(Intent.ACTION_POWER_CONNECTED == action)
        }
    }

    interface OnBatteryChangeListener {

        /**
         *
         */
        fun onBatteryChange(connect: Boolean)
    }
}