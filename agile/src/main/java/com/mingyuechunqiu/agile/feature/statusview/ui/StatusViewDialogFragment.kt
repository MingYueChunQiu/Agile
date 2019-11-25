package com.mingyuechunqiu.agile.feature.statusview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mingyuechunqiu.agile.R

import com.mingyuechunqiu.agile.ui.diaglogfragment.BaseDialogFragment

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-11-24 19:36
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
class StatusViewDialogFragment : BaseDialogFragment() {

    private var mDelegate: IStatusViewDelegate? = null

    override fun releaseOnDestroyView() {
        mDelegate = null
    }

    override fun releaseOnDestroy() {

    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.agile_layout_frame, container, false)

        return view
    }
}
