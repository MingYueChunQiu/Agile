package com.mingyuechunqiu.agile.framework.ui.dialog

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/9 11:48 下午
 *      Desc:       对话框生命周期拥有者
 *                  实现LifecycleOwner
 *      Version:    1.0
 * </pre>
 */
class DialogLifecycleOwner : LifecycleOwner {

    private val mLifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }

    fun handleLifecycleEvent(event: Lifecycle.Event) {
        mLifecycleRegistry.handleLifecycleEvent(event)
    }
}