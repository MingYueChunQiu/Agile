package com.mingyuechunqiu.agile.feature.statusview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mingyuechunqiu.agile.R
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption

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

    private var vContainer: View? = null
    private var pbProgress: View? = null
    private var tvContent: TextView? = null
    private var tvReload: TextView? = null

    private var mDelegate: IStatusViewDelegate? = null
    private var mOption: StatusViewOption? = null

    override fun releaseOnDestroyView() {
        mDelegate = null
    }

    override fun releaseOnDestroy() {

    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vContainer = inflater.inflate(R.layout.agile_dialog_fragment_status_view, container, false)
        mOption?.let {
            if (it.statusViewContainer?.customLayoutId != null) {
                vContainer = inflater.inflate(it.statusViewContainer.customLayoutId, container, false)
                pbProgress = vContainer?.findViewById(it.statusViewContainer.progressViewId)
                tvContent = vContainer?.findViewById(it.statusViewContainer.contentViewId)
                tvReload = vContainer?.findViewById(it.statusViewContainer.reloadViewId)
            } else {
                pbProgress = vContainer?.findViewById(R.id.pb_agile_dfg_status_view_progress)
                tvContent = vContainer?.findViewById(R.id.tv_agile_dfg_status_view_content)
                tvReload = vContainer?.findViewById(R.id.tv_agile_dfg_status_view_reload)
            }
        }
        return view
    }

    companion object {

        fun newInstance(option: StatusViewOption): StatusViewDialogFragment {
            val fragment = StatusViewDialogFragment()
            fragment.mOption = option
            return fragment
        }
    }
}
