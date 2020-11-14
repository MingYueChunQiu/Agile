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
 *      Time:       2020/11/13 9:49 PM
 *      Desc:       Activity生命周期回调接口
 *      Version:    1.0
 * </pre>
 */
interface ActivityLifecycleCallback {

    fun onCreate(activity: BaseActivity)

    fun onStart(activity: BaseActivity)

    fun onResume(activity: BaseActivity)

    fun onPause(activity: BaseActivity)

    fun onStop(activity: BaseActivity)

    fun onDestroy(activity: BaseActivity)
}