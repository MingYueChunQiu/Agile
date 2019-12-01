package com.mingyuechunqiu.agile.feature.statusview.ui

import android.os.Build
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.Nullable
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewTextOption
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants.StatusType
import com.mingyuechunqiu.agile.util.ScreenUtils

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
internal class StatusViewDelegate(private val mOption: StatusViewOption) : IStatusViewDelegate {

    private var mModeType: StatusViewConstants.ModeType = StatusViewConstants.ModeType.TYPE_INVALID
    private var mStatusType = StatusType.TYPE_LOADING
    override fun getStatusViewOption(): StatusViewOption {
        return mOption
    }

    override fun setModeType(type: StatusViewConstants.ModeType) {
        mModeType = type
    }

    override fun getModeType(): StatusViewConstants.ModeType {
        return mModeType
    }

    override fun setStatusType(type: StatusType) {
        mStatusType = type
    }

    override fun getStatusType(): StatusType {
        return mStatusType
    }

    override fun release() {
        mModeType = StatusViewConstants.ModeType.TYPE_INVALID
    }

    override fun applyOption(vContainer: View?, vProgress: View?, tvContent: TextView?, tvReload: TextView?) {
        applyContainerConfigure(vContainer)

        applyProgressConfigure(vProgress)

        tvContent?.visibility = if (mOption.isShowContentText) View.VISIBLE else View.GONE
        if (mOption.isShowContentText) {
            initTextButton(tvContent, mOption.contentOption)
        }
        tvReload?.visibility = if (mOption.isShowReloadText) View.VISIBLE else View.GONE
        if (mOption.isShowReloadText) {
            initTextButton(tvReload, mOption.reloadOption)
        }
    }

    private fun applyProgressConfigure(vProgress: View?) {
        vProgress?.takeIf { it is ProgressBar }?.let {
            val pbProgress = it as ProgressBar
            mOption.progressDrawable?.let { drawable ->
                pbProgress.progressDrawable = drawable
            }
        }
    }

    private fun applyContainerConfigure(vContainer: View?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            vContainer?.elevation = vContainer?.resources
                    ?.takeIf { mOption.isShowStatusViewFloating }
                    ?.let {
                        ScreenUtils.getPxFromDp(it, 10F)
                    } ?: 0F
        }
        mOption.containerBackgroundResId.takeIf { it != 0 }?.let {
            vContainer?.setBackgroundResource(it)
        }
        mOption.containerBackground?.let {
            vContainer?.background = it
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
                it.text = textOption.text
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
                textOption.backgroundResId.takeIf { id ->
                    id != 0
                }?.let { id ->
                    it.setBackgroundResource(id)
                }
                it.background?.let { drawable ->
                    it.background = drawable
                }
            }
        }
    }
}