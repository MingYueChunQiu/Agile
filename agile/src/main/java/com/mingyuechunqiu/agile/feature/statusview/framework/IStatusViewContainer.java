package com.mingyuechunqiu.agile.feature.statusview.framework;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-11-26 21:48
 *      Desc:       状态视图容器接口
 *      Version:    1.0
 * </pre>
 */
public interface IStatusViewContainer {

    /**
     * 获取用户自定义布局资源ID
     *
     * @return 返回资源ID
     */
    @LayoutRes
    int getCustomLayoutId();

    /**
     * 获取包裹显示内容的容器资源ID
     *
     * @return 返回资源ID
     */
    @IdRes
    int getContainerId();

    /**
     * 获取进度条控件ID
     *
     * @return 返回控件资源ID
     */
    @IdRes
    int getProgressViewId();

    /**
     * 获取内容控件资源ID
     *
     * @return 返回控件资源ID
     */
    @IdRes
    int getContentViewId();

    /**
     * 获取重新加载控件资源ID
     *
     * @return 返回控件资源ID
     */
    @IdRes
    int getReloadViewId();
}
