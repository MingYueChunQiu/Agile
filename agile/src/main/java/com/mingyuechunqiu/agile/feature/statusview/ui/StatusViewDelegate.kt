package com.mingyuechunqiu.agile.feature.statusview.ui

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.Nullable
import com.mingyuechunqiu.agile.R
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewTextOption
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants
import com.mingyuechunqiu.agile.ui.widget.DaisyLoadingView

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-11-24 19:38
 *      Desc:       状态视图代理类
 *                  实现IStatusViewDelegate
 *      Version:    1.0
 * </pre>
 */
internal class StatusViewDelegate(private val mOption: StatusViewOption?) : IStatusViewDelegate {

    override fun release() {
    }

    override fun applyOption(view:View) {
        var vContainer: View? = null
        var vProgress: View? = null
        var ivIcon: ImageView? = null
        var tvContent: TextView? = null
        var tvReload: TextView? = null
        mOption?.let {
            if (it.statusViewContainer?.customLayoutId != null) {
                vContainer = view.findViewById(it.statusViewContainer.containerId)
                vProgress = view.findViewById(it.statusViewContainer.progressViewId)
                ivIcon = view.findViewById(it.statusViewContainer.iconViewId)
                tvContent = view.findViewById(it.statusViewContainer.contentViewId)
                tvReload = view.findViewById(it.statusViewContainer.reloadViewId)
            } else {
                vContainer = view.findViewById(R.id.ll_agile_dfg_status_view_container)

                val progressViewId = when (it.progressOption?.progressStyle) {
                    StatusViewConstants.ProgressStyle.STYLE_SYSTEM -> R.id.pb_agile_dfg_status_view_progress
                    StatusViewConstants.ProgressStyle.STYLE_DAISY -> R.id.dlv_agile_dfg_status_view_progress
                    else -> R.id.pb_agile_dfg_status_view_progress
                }
                if (progressViewId != 0) {
                    vProgress = view.findViewById(progressViewId)
                    vProgress?.visibility = View.VISIBLE
                }

                ivIcon = view.findViewById(R.id.iv_agile_dfg_status_view_icon)
                tvContent = view.findViewById(R.id.tv_agile_dfg_status_view_content)
                tvReload = view.findViewById(R.id.tv_agile_dfg_status_view_reload)
            }
            tvReload?.setOnClickListener { _ ->
                it.onStatusViewButtonListener?.onClickReload()
            }
        }
        applyContainerConfigure(vContainer)
        applyProgressConfigure(vProgress)
        applyIconConfigure(ivIcon)
        applyTextConfigure(tvContent, tvReload)
    }

    private fun applyProgressConfigure(vProgress: View?) {
        vProgress?.visibility = if (mOption?.isShowProgressView == true) View.VISIBLE else View.GONE
        val progressOption = mOption?.progressOption ?: return
        when (progressOption.progressStyle) {
            StatusViewConstants.ProgressStyle.STYLE_SYSTEM -> {
                vProgress?.takeIf { it is ProgressBar }?.let {
                    val pbProgress = it as ProgressBar
                    progressOption.progressDrawable?.let { drawable ->
                        pbProgress.progressDrawable = drawable
                    }
                    progressOption.progressSize.takeIf { size -> size > 0 }
                            ?.let { size ->
                                pbProgress.layoutParams.width = size
                                pbProgress.layoutParams.height = size
                            }
                }
            }
            StatusViewConstants.ProgressStyle.STYLE_DAISY -> {
                vProgress?.takeIf { it is DaisyLoadingView }?.let {
                    val dlvProgress = it as DaisyLoadingView
                    progressOption.progressSize.takeIf { size -> size > 0 }
                            ?.let { size ->
                                dlvProgress.size = size.toFloat()
                            }
                    dlvProgress.color = progressOption.daisyColor
                }
            }
            else -> {
                throw IllegalArgumentException("progressStyle params error")
            }
        }

    }

    private fun applyIconConfigure(ivIcon: ImageView?) {
        mOption?.let { option ->
            ivIcon?.visibility = if (option.isShowReloadIcon) View.VISIBLE else View.GONE
            ivIcon?.let {
                option.reloadDrawable?.let { drawable ->
                    it.setImageDrawable(drawable)
                }
                option.reloadDrawableResId.takeIf { it != 0 }?.let { id ->
                    it.setImageResource(id)
                }
            }
        }
    }

    private fun applyTextConfigure(tvContent: TextView?, tvReload: TextView?) {
        mOption?.let {
            tvContent?.visibility = if (it.isShowContentText) View.VISIBLE else View.GONE
            if (it.isShowContentText) {
                initTextButton(tvContent, it.contentOption)
            }
            tvReload?.visibility = if (it.isShowReloadText) View.VISIBLE else View.GONE
            if (it.isShowReloadText) {
                initTextButton(tvReload, it.reloadOption)
            }
        }
    }

    private fun applyContainerConfigure(vContainer: View?) {
        mOption?.let { option ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (option.containerElevation != 0F) {
                    vContainer?.elevation = option.containerElevation
                }
            }
            option.containerBackgroundResId.takeIf { it != 0 }?.let {
                vContainer?.setBackgroundResource(it)
            }
            option.containerBackground?.let {
                vContainer?.background = it
            }
        }
    }

    /**
     * 初始化文本控件
     *
     * @param textView 文本控件
     * @param option 文本控件配置信息对象
     */
    private fun initTextButton(textView: TextView?, @Nullable option: StatusViewTextOption?) {
        textView?.let {
            option?.let { textOption ->
                if (!textOption.text.isNullOrEmpty()) {
                    it.text = textOption.text
                }
                textOption.textSize.takeIf { size ->
                    size > 0
                }?.let { size ->
                    it.textSize = size
                }
                textOption.textColor.takeIf { color ->
                    color != 0
                }?.let { color ->
                    it.setTextColor(color)
                }
                it.background?.let { drawable ->
                    it.background = drawable
                }
                textOption.backgroundResId.takeIf { id ->
                    id != 0
                }?.let { id ->
                    it.setBackgroundResource(id)
                }
            }
        }
    }
}