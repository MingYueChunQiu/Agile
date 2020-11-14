package com.mingyuechunqiu.agile.frame.lifecycle.dialogfragment

import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider
import com.mingyuechunqiu.agile.ui.diaglogfragment.BaseDialogFragment

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       11/14/20 2:29 PM
 *      Desc:       DialogFragment生命周期回调适配器（提供默认空方法实现）
 *                  实现DialogFragmentLifecycleCallback
 *      Version:    1.0
 * </pre>
 */
open class DialogFragmentLifecycleAdapter : DialogFragmentLifecycleCallback {

    override fun onAttach(fragment: BaseDialogFragment) {
        LogManagerProvider.i("DialogFragmentLifecycleAdapter", "onAttach:${fragment::class.java.simpleName}")
    }

    override fun onCreate(fragment: BaseDialogFragment) {
        LogManagerProvider.i("DialogFragmentLifecycleAdapter", "onCreate:${fragment::class.java.simpleName}")
    }

    override fun onCreateView(fragment: BaseDialogFragment) {
        LogManagerProvider.i("DialogFragmentLifecycleAdapter", "onCreateView:${fragment::class.java.simpleName}")
    }

    override fun onActivityCreated(fragment: BaseDialogFragment) {
        LogManagerProvider.i("DialogFragmentLifecycleAdapter", "onActivityCreated:${fragment::class.java.simpleName}")
    }

    override fun onStart(fragment: BaseDialogFragment) {
        LogManagerProvider.i("DialogFragmentLifecycleAdapter", "onStart:${fragment::class.java.simpleName}")

    }

    override fun onResume(fragment: BaseDialogFragment) {
        LogManagerProvider.i("DialogFragmentLifecycleAdapter", "onResume:${fragment::class.java.simpleName}")

    }

    override fun onPause(fragment: BaseDialogFragment) {
        LogManagerProvider.i("DialogFragmentLifecycleAdapter", "onPause:${fragment::class.java.simpleName}")

    }

    override fun onStop(fragment: BaseDialogFragment) {
        LogManagerProvider.i("DialogFragmentLifecycleAdapter", "onStop:${fragment::class.java.simpleName}")

    }

    override fun onDestroyView(fragment: BaseDialogFragment) {
        LogManagerProvider.i("DialogFragmentLifecycleAdapter", "onDestroyView:${fragment::class.java.simpleName}")

    }

    override fun onDestroy(fragment: BaseDialogFragment) {
        LogManagerProvider.i("DialogFragmentLifecycleAdapter", "onDestroy:${fragment::class.java.simpleName}")

    }

    override fun onDetach(fragment: BaseDialogFragment) {
        LogManagerProvider.i("DialogFragmentLifecycleAdapter", "onDetach:${fragment::class.java.simpleName}")
    }
}