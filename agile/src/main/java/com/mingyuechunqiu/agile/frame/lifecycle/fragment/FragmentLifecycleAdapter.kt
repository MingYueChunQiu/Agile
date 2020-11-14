package com.mingyuechunqiu.agile.frame.lifecycle.fragment

import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider
import com.mingyuechunqiu.agile.ui.fragment.BaseFragment

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       11/14/20 1:44 PM
 *      Desc:       Fragment生命周期回调适配器（提供默认空方法实现）
 *                  实现FragmentLifecycleCallback
 *      Version:    1.0
 * </pre>
 */
open class FragmentLifecycleAdapter : FragmentLifecycleCallback {

    override fun onAttach(fragment: BaseFragment) {
        LogManagerProvider.i("FragmentLifecycleAdapter", "onAttach:${fragment::class.java.simpleName}")
    }

    override fun onCreate(fragment: BaseFragment) {
        LogManagerProvider.i("FragmentLifecycleAdapter", "onCreate:${fragment::class.java.simpleName}")
    }

    override fun onCreateView(fragment: BaseFragment) {
        LogManagerProvider.i("FragmentLifecycleAdapter", "onCreateView:${fragment::class.java.simpleName}")
    }

    override fun onActivityCreated(fragment: BaseFragment) {
        LogManagerProvider.i("FragmentLifecycleAdapter", "onActivityCreated:${fragment::class.java.simpleName}")
    }

    override fun onStart(fragment: BaseFragment) {
        LogManagerProvider.i("FragmentLifecycleAdapter", "onStart:${fragment::class.java.simpleName}")

    }

    override fun onResume(fragment: BaseFragment) {
        LogManagerProvider.i("FragmentLifecycleAdapter", "onResume:${fragment::class.java.simpleName}")

    }

    override fun onPause(fragment: BaseFragment) {
        LogManagerProvider.i("FragmentLifecycleAdapter", "onPause:${fragment::class.java.simpleName}")

    }

    override fun onStop(fragment: BaseFragment) {
        LogManagerProvider.i("FragmentLifecycleAdapter", "onStop:${fragment::class.java.simpleName}")

    }

    override fun onDestroyView(fragment: BaseFragment) {
        LogManagerProvider.i("FragmentLifecycleAdapter", "onDestroyView:${fragment::class.java.simpleName}")

    }

    override fun onDestroy(fragment: BaseFragment) {
        LogManagerProvider.i("FragmentLifecycleAdapter", "onDestroy:${fragment::class.java.simpleName}")

    }

    override fun onDetach(fragment: BaseFragment) {
        LogManagerProvider.i("FragmentLifecycleAdapter", "onDetach:${fragment::class.java.simpleName}")
    }
}