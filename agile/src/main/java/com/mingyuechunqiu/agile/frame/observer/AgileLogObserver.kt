package com.mingyuechunqiu.agile.frame.observer

import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider
import com.mingyuechunqiu.agile.frame.Agile
import com.mingyuechunqiu.agile.frame.lifecycle.activity.ActivityLifecycleCallback
import com.mingyuechunqiu.agile.frame.lifecycle.bottomsheetdialogfragment.BSDialogFragmentLifecycleCallback
import com.mingyuechunqiu.agile.frame.lifecycle.dialog.DialogLifecycleCallback
import com.mingyuechunqiu.agile.frame.lifecycle.dialogfragment.DialogFragmentLifecycleCallback
import com.mingyuechunqiu.agile.frame.lifecycle.fragment.FragmentLifecycleCallback
import com.mingyuechunqiu.agile.ui.activity.BaseActivity
import com.mingyuechunqiu.agile.ui.bottomsheetdialogfragment.BaseBSDialogFragment
import com.mingyuechunqiu.agile.ui.diaglogfragment.BaseDialogFragment
import com.mingyuechunqiu.agile.ui.dialog.BaseDialog
import com.mingyuechunqiu.agile.ui.fragment.BaseFragment

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/12/31 7:43 下午
 *      Desc:       框架日志观察者
 *                  实现IAgileFrameObserver
 *      Version:    1.0
 * </pre>
 */
internal class AgileLogObserver : IAgileFrameObserver {

    override fun onFrameInit() {
        Agile.getLifecycleDispatcher().apply {
            registerActivityLifecycleCallback(object : ActivityLifecycleCallback {
                override fun onCreate(activity: BaseActivity) {
                    LogManagerProvider.i(activity.getPageTag(), "onCreate")
                }

                override fun onStart(activity: BaseActivity) {
                    LogManagerProvider.i(activity.getPageTag(), "onStart")
                }

                override fun onResume(activity: BaseActivity) {
                    LogManagerProvider.i(activity.getPageTag(), "onResume")
                }

                override fun onPause(activity: BaseActivity) {
                    LogManagerProvider.i(activity.getPageTag(), "onPause")
                }

                override fun onStop(activity: BaseActivity) {
                    LogManagerProvider.i(activity.getPageTag(), "onStop")
                }

                override fun onDestroy(activity: BaseActivity) {
                    LogManagerProvider.i(activity.getPageTag(), "onDestroy")
                }

            })
            registerFragmentLifecycleCallback(object : FragmentLifecycleCallback {
                override fun onAttach(fragment: BaseFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onAttach")
                }

                override fun onCreate(fragment: BaseFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onCreate")
                }

                override fun onCreateView(fragment: BaseFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onCreateView")
                }

                override fun onActivityCreated(fragment: BaseFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onActivityCreated")
                }

                override fun onStart(fragment: BaseFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onStart")
                }

                override fun onResume(fragment: BaseFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onResume")
                }

                override fun onPause(fragment: BaseFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onPause")
                }

                override fun onStop(fragment: BaseFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onStop")
                }

                override fun onDestroyView(fragment: BaseFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onDestroyView")
                }

                override fun onDestroy(fragment: BaseFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onDestroy")
                }

                override fun onDetach(fragment: BaseFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onDetach")
                }

            })
            registerDialogFragmentLifecycleCallback(object : DialogFragmentLifecycleCallback {
                override fun onAttach(fragment: BaseDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onAttach")
                }

                override fun onCreate(fragment: BaseDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onCreate")
                }

                override fun onCreateView(fragment: BaseDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onCreateView")
                }

                override fun onActivityCreated(fragment: BaseDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onActivityCreated")
                }

                override fun onStart(fragment: BaseDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onStart")
                }

                override fun onResume(fragment: BaseDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onResume")
                }

                override fun onPause(fragment: BaseDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onPause")
                }

                override fun onStop(fragment: BaseDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onStop")
                }

                override fun onDestroyView(fragment: BaseDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onDestroyView")
                }

                override fun onDestroy(fragment: BaseDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onDestroy")
                }

                override fun onDetach(fragment: BaseDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onDetach")
                }

            })
            registerBottomSheetDialogFragmentLifecycleCallback(object :
                BSDialogFragmentLifecycleCallback {
                override fun onAttach(fragment: BaseBSDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onAttach")
                }

                override fun onCreate(fragment: BaseBSDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onCreate")
                }

                override fun onCreateView(fragment: BaseBSDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onCreateView")
                }

                override fun onActivityCreated(fragment: BaseBSDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onActivityCreated")
                }

                override fun onStart(fragment: BaseBSDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onStart")
                }

                override fun onResume(fragment: BaseBSDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onResume")
                }

                override fun onPause(fragment: BaseBSDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onPause")
                }

                override fun onStop(fragment: BaseBSDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onStop")
                }

                override fun onDestroyView(fragment: BaseBSDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onDestroyView")
                }

                override fun onDestroy(fragment: BaseBSDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onDestroy")
                }

                override fun onDetach(fragment: BaseBSDialogFragment) {
                    LogManagerProvider.i(fragment.getPageTag(), "onDetach")
                }

            })
            registerDialogLifecycleCallback(object : DialogLifecycleCallback {
                override fun onCreate(dialog: BaseDialog) {
                    LogManagerProvider.i(dialog.getPageTag(), "onCreate")
                }

                override fun onStart(dialog: BaseDialog) {
                    LogManagerProvider.i(dialog.getPageTag(), "onStart")
                }

                override fun onStop(dialog: BaseDialog) {
                    LogManagerProvider.i(dialog.getPageTag(), "onStop")
                }

                override fun onDismiss(dialog: BaseDialog) {
                    LogManagerProvider.i(dialog.getPageTag(), "onDismiss")
                }

            })
        }
    }
}