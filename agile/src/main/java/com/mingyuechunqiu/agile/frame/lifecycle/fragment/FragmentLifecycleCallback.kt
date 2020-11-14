package com.mingyuechunqiu.agile.frame.lifecycle.fragment

import com.mingyuechunqiu.agile.ui.fragment.BaseFragment

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020/11/13 9:51 PM
 *      Desc:       Fragment生命周期回调接口
 *      Version:    1.0
 * </pre>
 */
interface FragmentLifecycleCallback {

    fun onAttach(fragment: BaseFragment)

    fun onCreate(fragment: BaseFragment)

    fun onCreateView(fragment: BaseFragment)

    fun onActivityCreated(fragment: BaseFragment)

    fun onStart(fragment: BaseFragment)

    fun onResume(fragment: BaseFragment)

    fun onPause(fragment: BaseFragment)

    fun onStop(fragment: BaseFragment)

    fun onDestroyView(fragment: BaseFragment)

    fun onDestroy(fragment: BaseFragment)

    fun onDetach(fragment: BaseFragment)
}