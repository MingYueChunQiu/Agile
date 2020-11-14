package com.mingyuechunqiu.agile.frame.lifecycle

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
 *      Time:       2020/11/13 9:54 PM
 *      Desc:       生命周期分发器（单例）
 *      Version:    1.0
 * </pre>
 */
internal object AgileLifecycleDispatcher {

    private var mActivityLifecycleCallbacks: MutableList<ActivityLifecycleCallback>? = null
    private var mFragmentLifecycleCallbacks: MutableList<FragmentLifecycleCallback>? = null
    private var mDialogFragmentLifecycleCallbacks: MutableList<DialogFragmentLifecycleCallback>? = null
    private var mBsDialogFragmentLifecycleCallbacks: MutableList<BSDialogFragmentLifecycleCallback>? = null
    private var mDialogLifecycleCallbacks: MutableList<DialogLifecycleCallback>? = null

    fun registerActivityLifecycleCallback(callback: ActivityLifecycleCallback) {
        if (mActivityLifecycleCallbacks == null) {
            mActivityLifecycleCallbacks = ArrayList()
        }
        mActivityLifecycleCallbacks?.add(callback)
    }

    fun unregisterActivityLifecycleCallback(callback: ActivityLifecycleCallback) {
        mActivityLifecycleCallbacks?.remove(callback)
    }

    fun registerFragmentLifecycleCallback(callback: FragmentLifecycleCallback) {
        if (mFragmentLifecycleCallbacks == null) {
            mFragmentLifecycleCallbacks = ArrayList()
        }
        mFragmentLifecycleCallbacks?.add(callback)
    }

    fun unregisterFragmentLifecycleCallback(callback: FragmentLifecycleCallback) {
        mFragmentLifecycleCallbacks?.remove(callback)
    }

    fun registerDialogFragmentLifecycleCallback(callback: DialogFragmentLifecycleCallback) {
        if (mDialogFragmentLifecycleCallbacks == null) {
            mDialogFragmentLifecycleCallbacks = ArrayList()
        }
        mDialogFragmentLifecycleCallbacks?.add(callback)
    }

    fun unregisterDialogFragmentLifecycleCallback(callback: DialogFragmentLifecycleCallback) {
        mDialogFragmentLifecycleCallbacks?.remove(callback)
    }

    fun registerBottomSheetDialogFragmentLifecycleCallback(callback: BSDialogFragmentLifecycleCallback) {
        if (mBsDialogFragmentLifecycleCallbacks == null) {
            mBsDialogFragmentLifecycleCallbacks = ArrayList()
        }
        mBsDialogFragmentLifecycleCallbacks?.add(callback)
    }

    fun unregisterBottomSheetDialogFragmentLifecycleCallback(callback: BSDialogFragmentLifecycleCallback) {
        mBsDialogFragmentLifecycleCallbacks?.remove(callback)
    }

    fun registerDialogLifecycleCallback(callback: DialogLifecycleCallback) {
        if (mDialogLifecycleCallbacks == null) {
            mDialogLifecycleCallbacks = ArrayList()
        }
        mDialogLifecycleCallbacks?.add(callback)
    }

    fun unregisterDialogLifecycleCallback(callback: DialogLifecycleCallback) {
        mDialogLifecycleCallbacks?.remove(callback)
    }

    /**
     * 更新Activity生命周期状态
     *
     * @param activity  Activity
     * @param state     生命周期状态
     */
    fun updateActivityLifecycleState(activity: BaseActivity, state: AgileLifecycle.State.ActivityState) {
        mActivityLifecycleCallbacks?.forEach {
            when (state) {
                AgileLifecycle.State.ActivityState.CREATED -> it.onCreate(activity)
                AgileLifecycle.State.ActivityState.STARTED -> it.onStart(activity)
                AgileLifecycle.State.ActivityState.RESUMED -> it.onResume(activity)
                AgileLifecycle.State.ActivityState.PAUSED -> it.onPause(activity)
                AgileLifecycle.State.ActivityState.STOPPED -> it.onStop(activity)
                AgileLifecycle.State.ActivityState.DESTROYED -> it.onDestroy(activity)
            }
        }
    }

    /**
     * 更新Fragment生命周期状态
     *
     * @param fragment  Fragment
     * @param state     生命周期状态
     */
    fun updateFragmentLifecycleState(fragment: BaseFragment, state: AgileLifecycle.State.FragmentState) {
        mFragmentLifecycleCallbacks?.forEach {
            when (state) {
                AgileLifecycle.State.FragmentState.ATTACHED -> it.onAttach(fragment)
                AgileLifecycle.State.FragmentState.CREATED -> it.onCreate(fragment)
                AgileLifecycle.State.FragmentState.CREATED_VIEW -> it.onCreateView(fragment)
                AgileLifecycle.State.FragmentState.ACTIVITY_CREATED -> it.onActivityCreated(fragment)
                AgileLifecycle.State.FragmentState.STARTED -> it.onStart(fragment)
                AgileLifecycle.State.FragmentState.RESUMED -> it.onResume(fragment)
                AgileLifecycle.State.FragmentState.PAUSED -> it.onPause(fragment)
                AgileLifecycle.State.FragmentState.STOPPED -> it.onStop(fragment)
                AgileLifecycle.State.FragmentState.DESTROYED_VIEW -> it.onDestroyView(fragment)
                AgileLifecycle.State.FragmentState.DESTROYED -> it.onDestroy(fragment)
                AgileLifecycle.State.FragmentState.DETACHED -> it.onDetach(fragment)
            }
        }
    }

    /**
     * 更新DialogFragment生命周期状态
     *
     * @param fragment  DialogFragment
     * @param state     生命周期状态
     */
    fun updateDialogFragmentLifecycleState(fragment: BaseDialogFragment, state: AgileLifecycle.State.DialogFragmentState) {
        mDialogFragmentLifecycleCallbacks?.forEach {
            when (state) {
                AgileLifecycle.State.DialogFragmentState.ATTACHED -> it.onAttach(fragment)
                AgileLifecycle.State.DialogFragmentState.CREATED -> it.onCreate(fragment)
                AgileLifecycle.State.DialogFragmentState.CREATED_VIEW -> it.onCreateView(fragment)
                AgileLifecycle.State.DialogFragmentState.ACTIVITY_CREATED -> it.onActivityCreated(fragment)
                AgileLifecycle.State.DialogFragmentState.STARTED -> it.onStart(fragment)
                AgileLifecycle.State.DialogFragmentState.RESUMED -> it.onResume(fragment)
                AgileLifecycle.State.DialogFragmentState.PAUSED -> it.onPause(fragment)
                AgileLifecycle.State.DialogFragmentState.STOPPED -> it.onStop(fragment)
                AgileLifecycle.State.DialogFragmentState.DESTROYED_VIEW -> it.onDestroyView(fragment)
                AgileLifecycle.State.DialogFragmentState.DESTROYED -> it.onDestroy(fragment)
                AgileLifecycle.State.DialogFragmentState.DETACHED -> it.onDetach(fragment)
            }
        }
    }

    /**
     * 更新BottomSheetDialogFragment生命周期状态
     *
     * @param fragment  BottomSheetDialogFragment
     * @param state     生命周期状态
     */
    fun updateBottomSheetDialogFragmentLifecycleState(fragment: BaseBSDialogFragment, state: AgileLifecycle.State.BottomSheetDialogFragmentState) {
        mBsDialogFragmentLifecycleCallbacks?.forEach {
            when (state) {
                AgileLifecycle.State.BottomSheetDialogFragmentState.ATTACHED -> it.onAttach(fragment)
                AgileLifecycle.State.BottomSheetDialogFragmentState.CREATED -> it.onCreate(fragment)
                AgileLifecycle.State.BottomSheetDialogFragmentState.CREATED_VIEW -> it.onCreateView(fragment)
                AgileLifecycle.State.BottomSheetDialogFragmentState.ACTIVITY_CREATED -> it.onActivityCreated(fragment)
                AgileLifecycle.State.BottomSheetDialogFragmentState.STARTED -> it.onStart(fragment)
                AgileLifecycle.State.BottomSheetDialogFragmentState.RESUMED -> it.onResume(fragment)
                AgileLifecycle.State.BottomSheetDialogFragmentState.PAUSED -> it.onPause(fragment)
                AgileLifecycle.State.BottomSheetDialogFragmentState.STOPPED -> it.onStop(fragment)
                AgileLifecycle.State.BottomSheetDialogFragmentState.DESTROYED_VIEW -> it.onDestroyView(fragment)
                AgileLifecycle.State.BottomSheetDialogFragmentState.DESTROYED -> it.onDestroy(fragment)
                AgileLifecycle.State.BottomSheetDialogFragmentState.DETACHED -> it.onDetach(fragment)
            }
        }
    }

    /**
     * 更新对话框生命周期状态
     *
     * @param dialog    对话框
     * @param state     生命周期状态
     */
    fun updateDialogLifecycleState(dialog: BaseDialog, state: AgileLifecycle.State.DialogState) {
        mDialogLifecycleCallbacks?.forEach {
            when (state) {
                AgileLifecycle.State.DialogState.CREATED -> it.onCreate(dialog)
                AgileLifecycle.State.DialogState.STARTED -> it.onStart(dialog)
                AgileLifecycle.State.DialogState.STOPPED -> it.onStop(dialog)
                AgileLifecycle.State.DialogState.DISMISSED -> it.onDismiss(dialog)
            }
        }
    }
}