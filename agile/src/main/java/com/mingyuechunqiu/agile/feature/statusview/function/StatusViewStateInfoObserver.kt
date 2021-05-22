package com.mingyuechunqiu.agile.feature.statusview.function

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewStateInfo
import com.mingyuechunqiu.agile.frame.ui.IAgilePage

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/16 8:17 下午
 *      Desc:       状态视图状态信息观察者
 *                  实现LifecycleEventObserver
 *      Version:    1.0
 * </pre>
 */
data class StatusViewStateInfoObserver(
    val page: IAgilePage,
    val info: StatusViewStateInfo
) : LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (source.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            page.lifecycle.removeObserver(this)
            StatusViewManagerProvider.removeInstanceStateInfo(this)
        }
    }
}