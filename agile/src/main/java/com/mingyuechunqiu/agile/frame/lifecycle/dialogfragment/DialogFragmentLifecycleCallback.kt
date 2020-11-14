package com.mingyuechunqiu.agile.frame.lifecycle.dialogfragment

import com.mingyuechunqiu.agile.ui.diaglogfragment.BaseDialogFragment

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       11/14/20 2:27 PM
 *      Desc:       DialogFragment生命周期回调接口
 *      Version:    1.0
 * </pre>
 */
interface DialogFragmentLifecycleCallback {

    fun onAttach(fragment: BaseDialogFragment)

    fun onCreate(fragment: BaseDialogFragment)

    fun onCreateView(fragment: BaseDialogFragment)

    fun onActivityCreated(fragment: BaseDialogFragment)

    fun onStart(fragment: BaseDialogFragment)

    fun onResume(fragment: BaseDialogFragment)

    fun onPause(fragment: BaseDialogFragment)

    fun onStop(fragment: BaseDialogFragment)

    fun onDestroyView(fragment: BaseDialogFragment)

    fun onDestroy(fragment: BaseDialogFragment)

    fun onDetach(fragment: BaseDialogFragment)
}