package com.mingyuechunqiu.agile.feature.statusview.function;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/3/14
 *     desc   : 等待加载对话框布局容器接口
 *     version: 1.0
 * </pre>
 */
public interface LoadingDfgContainerable {

    /**
     * 获取容器控件
     *
     * @return 返回View
     */
    View getContainer();

    /**
     * 获取加载容器控件
     *
     * @return 返回View
     */
    View getLoadingContainer();

    /**
     * 获取进度条控件
     *
     * @return 返回ProgressBar
     */
    ProgressBar getProgressBar();

    /**
     * 获取文本控件
     *
     * @return 返回TextView
     */
    TextView getTextView();
}
