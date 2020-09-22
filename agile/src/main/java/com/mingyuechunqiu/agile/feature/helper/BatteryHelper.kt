package com.mingyuechunqiu.agile.feature.helper

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.annotation.IntDef

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020/9/22 9:29 PM
 *      Desc:       电池操作辅助类（单例类）
 *      Version:    1.0
 * </pre>
 */
object BatteryHelper {

    /**
     * 获取电池状态
     * BatteryManager 会在一个包含充电状态的粘性 Intent 中广播所有电池和充电详情
     *
     * @param context 上下文
     * @return 返回电视状态信息对象
     */
    fun getBatteryStatusInfo(context: Context): BatteryStatusInfo {
        return IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { filter ->
            context.registerReceiver(null, filter)
        }.let {
            val status: Int = it?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
            val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
                    || status == BatteryManager.BATTERY_STATUS_FULL
            //从电池状态 intent 提取当前电池电量和刻度来了解当前电池电量
            BatteryStatusInfo(isCharging, it?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) ?: -1,
                    it?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1,
                    it?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1)
        }
    }

    data class BatteryStatusInfo(val isCharging: Boolean, @ChargeType val chargeType: Int, val level: Int, val scale: Int)

    //充电方式
    const val TYPE_CHARGE_INVALID = -1//无效
    const val TYPE_CHARGE_USB = 0//USB
    const val TYPE_CHARGE_AC = 1//交流电输入充电
    const val TYPE_CHARGE_WIRELESS = 2//无线

    @IntDef(TYPE_CHARGE_INVALID, TYPE_CHARGE_USB, TYPE_CHARGE_AC, TYPE_CHARGE_WIRELESS)
    annotation class ChargeType
}