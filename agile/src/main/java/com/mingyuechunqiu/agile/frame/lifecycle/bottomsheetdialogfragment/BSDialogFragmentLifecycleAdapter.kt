package com.mingyuechunqiu.agile.frame.lifecycle.bottomsheetdialogfragment

import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider
import com.mingyuechunqiu.agile.ui.bottomsheetdialogfragment.BaseBSDialogFragment

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       11/14/20 3:40 PM
 *      Desc:       BottomSheetDialogFragment生命周期回调适配器（提供默认空方法实现）
 *                  实现BSDialogFragmentLifecycleCallback
 *      Version:    1.0
 * </pre>
 */
open class BSDialogFragmentLifecycleAdapter : BSDialogFragmentLifecycleCallback {

    override fun onAttach(fragment: BaseBSDialogFragment) {
        LogManagerProvider.i("BSDialogFragmentLifecycleAdapter", "onAttach:${fragment::class.java.simpleName}")
    }

    override fun onCreate(fragment: BaseBSDialogFragment) {
        LogManagerProvider.i("BSDialogFragmentLifecycleAdapter", "onCreate:${fragment::class.java.simpleName}")
    }

    override fun onCreateView(fragment: BaseBSDialogFragment) {
        LogManagerProvider.i("BSDialogFragmentLifecycleAdapter", "onCreateView:${fragment::class.java.simpleName}")
    }

    override fun onActivityCreated(fragment: BaseBSDialogFragment) {
        LogManagerProvider.i("BSDialogFragmentLifecycleAdapter", "onActivityCreated:${fragment::class.java.simpleName}")
    }

    override fun onStart(fragment: BaseBSDialogFragment) {
        LogManagerProvider.i("BSDialogFragmentLifecycleAdapter", "onStart:${fragment::class.java.simpleName}")

    }

    override fun onResume(fragment: BaseBSDialogFragment) {
        LogManagerProvider.i("BSDialogFragmentLifecycleAdapter", "onResume:${fragment::class.java.simpleName}")

    }

    override fun onPause(fragment: BaseBSDialogFragment) {
        LogManagerProvider.i("BSDialogFragmentLifecycleAdapter", "onPause:${fragment::class.java.simpleName}")

    }

    override fun onStop(fragment: BaseBSDialogFragment) {
        LogManagerProvider.i("BSDialogFragmentLifecycleAdapter", "onStop:${fragment::class.java.simpleName}")

    }

    override fun onDestroyView(fragment: BaseBSDialogFragment) {
        LogManagerProvider.i("BSDialogFragmentLifecycleAdapter", "onDestroyView:${fragment::class.java.simpleName}")

    }

    override fun onDestroy(fragment: BaseBSDialogFragment) {
        LogManagerProvider.i("BSDialogFragmentLifecycleAdapter", "onDestroy:${fragment::class.java.simpleName}")

    }

    override fun onDetach(fragment: BaseBSDialogFragment) {
        LogManagerProvider.i("BSDialogFragmentLifecycleAdapter", "onDetach:${fragment::class.java.simpleName}")
    }
}