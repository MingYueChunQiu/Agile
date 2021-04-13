package com.mingyuechunqiu.agile.feature.helper.ui.widget

import android.widget.TextView

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       3/27/21 7:13 PM
 *      Desc:       文本控件辅助类（单例）
 *      Version:    1.0
 * </pre>
 */
object TextViewHelper {

    /**
     * 禁止文本控件长按弹出操作编辑栏
     */
    fun forbidPopLongOperationBar(textView: TextView) {
        textView.isLongClickable = false
    }
}