package com.mingyuechunqiu.agile.frame.lifecycle.bottomsheetdialogfragment

import com.mingyuechunqiu.agile.ui.bottomsheetdialogfragment.BaseBSDialogFragment

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       11/14/20 3:37 PM
 *      Desc:       BottomSheetDialogFragment生命周期回调接口
 *      Version:    1.0
 * </pre>
 */
interface BSDialogFragmentLifecycleCallback {

    fun onAttach(fragment: BaseBSDialogFragment)

    fun onCreate(fragment: BaseBSDialogFragment)

    fun onCreateView(fragment: BaseBSDialogFragment)

    fun onActivityCreated(fragment: BaseBSDialogFragment)

    fun onStart(fragment: BaseBSDialogFragment)

    fun onResume(fragment: BaseBSDialogFragment)

    fun onPause(fragment: BaseBSDialogFragment)

    fun onStop(fragment: BaseBSDialogFragment)

    fun onDestroyView(fragment: BaseBSDialogFragment)

    fun onDestroy(fragment: BaseBSDialogFragment)

    fun onDetach(fragment: BaseBSDialogFragment)
}