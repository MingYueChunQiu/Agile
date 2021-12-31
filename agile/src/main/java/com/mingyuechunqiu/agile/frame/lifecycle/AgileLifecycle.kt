package com.mingyuechunqiu.agile.frame.lifecycle

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020/11/14 11:39 AM
 *      Desc:       生命周期类（单例）
 *      Version:    1.0
 * </pre>
 */
object AgileLifecycle {

    object State {

        /**
         * Activity生命周期状态
         */
        enum class ActivityState {

            CREATED, STARTED, RESUMED, PAUSED, STOPPED, DESTROYED
        }

        /**
         * Fragment生命周期状态
         */
        enum class FragmentState {

            ATTACHED, CREATED, CREATED_VIEW, ACTIVITY_CREATED, STARTED, RESUMED, PAUSED, STOPPED, DESTROYED_VIEW, DESTROYED, DETACHED
        }

        /**
         * DialogFragment生命周期状态
         */
        enum class DialogFragmentState {

            ATTACHED, CREATED, CREATED_VIEW, ACTIVITY_CREATED, STARTED, RESUMED, PAUSED, STOPPED, DESTROYED_VIEW, DESTROYED, DETACHED
        }

        /**
         * BottomSheetDialogFragment生命周期状态
         */
        enum class BottomSheetDialogFragmentState {

            ATTACHED, CREATED, CREATED_VIEW, ACTIVITY_CREATED, STARTED, RESUMED, PAUSED, STOPPED, DESTROYED_VIEW, DESTROYED, DETACHED
        }

        /**
         * 对话框生命周期状态
         */
        enum class DialogState {

            CREATED, STARTED, STOPPED, DISMISSED
        }
    }

    enum class LifecycleType {

        COMPONENT, VIEW
    }
}