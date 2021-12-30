package com.mingyuechunqiu.agile.base.businessengine.countdown

import java.util.*

/**
 * <pre>
 *      Project:    Demo
 *      Github :    https://github.com/MingYueChunQiu
 *      Author:     XiYuJie
 *      Email:      xiyujie@jinying.com
 *      Time:       2020/1/20 17:00
 *      Desc:       业务逻辑层计时引擎
 *                  实现ITimingBusinessEngine
 *      Version:    1.0
 * </pre>
 */
class TimingBusinessEngine : ITimingBusinessEngine {

    private var mTimer: Timer? = null
    private var mTimerTask: TimerTask? = null
    private var mTotalCountDownCount = 0//总计倒计时时长
    private var mCountDownCount: Int = 0//倒计时时长

    override fun startDelayTask(delay: Long, task: Runnable) {
        startDelayPeriodTask(delay, 0, task)
    }

    override fun startDelayPeriodTask(delay: Long, period: Long, task: Runnable) {
        releaseTimingResource()
        mTimer = Timer()
        mTimerTask = object : TimerTask() {

            override fun run() {
                task.run()
            }
        }
        mTimer?.schedule(mTimerTask, delay, period)
    }

    override fun startCountDownTask(count: Int, callback: ITimingBusinessEngine.CountDownCallback) {
        releaseTimingResource()
        mTotalCountDownCount = count
        mCountDownCount = 0
        mTimer = Timer()
        mTimerTask = object : TimerTask() {

            override fun run() {
                mCountDownCount++
                if (mCountDownCount >= mTotalCountDownCount) {
                    callback.onCompleteCountDown()
                } else {
                    callback.onEachCountDown(mTotalCountDownCount - mCountDownCount)
                }
            }
        }
        mTimer?.schedule(mTimerTask, 0, 1000)
    }

    override fun release() {
        releaseTimingResource()
        mCountDownCount = 0
        mTotalCountDownCount = 0
    }

    private fun releaseTimingResource() {
        mTimer?.apply { cancel() }
        mTimer = null
        mTimerTask?.apply { cancel() }
        mTimerTask = null
    }
}