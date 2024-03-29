package com.mingyuechunqiu.agile.base.businessengine.countdown

import com.mingyuechunqiu.agile.base.businessengine.IBaseBusinessEngine

/**
 * <pre>
 *      Project:    Demo
 *      Github :    https://github.com/MingYueChunQiu
 *      Author:     XiYuJie
 *      Email:      xiyujie@jinying.com
 *      Time:       2020/1/20 16:57
 *      Desc:       业务逻辑层计时引擎接口
 *                  继承自IBaseBusinessEngine
 *      Version:    1.0
 * </pre>
 */
interface ITimingBusinessEngine : IBaseBusinessEngine {

    /**
     * 开启一个延迟任务
     *
     * @param delay 延迟时长（毫秒数）
     * @param task  任务
     */
    fun startDelayTask(delay: Long, task: Runnable)

    /**
     * 开启一个延迟计时任务
     *
     * @param delay     延迟时长（毫秒数）
     * @param period    间隔时长（毫秒数）
     * @param task      任务
     */
    fun startDelayPeriodTask(delay: Long, period: Long, task: Runnable)

    fun startCountDownTask(count: Int, callback: CountDownCallback)

    /**
     * 倒计时回调
     */
    interface CountDownCallback {

        /**
         * 每次倒计时刷新时回调
         */
        fun onEachCountDown(remainder: Int)

        /**
         * 当完后倒计时时回调
         */
        fun onCompleteCountDown()
    }
}