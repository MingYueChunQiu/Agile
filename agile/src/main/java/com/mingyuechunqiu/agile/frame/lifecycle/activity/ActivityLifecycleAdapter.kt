package com.mingyuechunqiu.agile.frame.lifecycle.activity

import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider
import com.mingyuechunqiu.agile.ui.activity.BaseActivity

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020/11/14 11:26 AM
 *      Desc:       Activity生命周期回调适配器（提供默认空方法实现）
 *                  实现ActivityLifecycleCallback
 *      Version:    1.0
 * </pre>
 */
open class ActivityLifecycleAdapter : ActivityLifecycleCallback {

    override fun onCreate(activity: BaseActivity) {
        LogManagerProvider.i("ActivityLifecycleAdapter", "onCreate:${activity.localClassName}")
    }

    override fun onStart(activity: BaseActivity) {
        LogManagerProvider.i("ActivityLifecycleAdapter", "onStart:${activity.localClassName}")
    }

    override fun onResume(activity: BaseActivity) {
        LogManagerProvider.i("ActivityLifecycleAdapter", "onResume:${activity.localClassName}")
    }

    override fun onPause(activity: BaseActivity) {
        LogManagerProvider.i("ActivityLifecycleAdapter", "onPause:${activity.localClassName}")
    }

    override fun onStop(activity: BaseActivity) {
        LogManagerProvider.i("ActivityLifecycleAdapter", "onStop:${activity.localClassName}")
    }

    override fun onDestroy(activity: BaseActivity) {
        LogManagerProvider.i("ActivityLifecycleAdapter", "onDestroy:${activity.localClassName}")
    }
}