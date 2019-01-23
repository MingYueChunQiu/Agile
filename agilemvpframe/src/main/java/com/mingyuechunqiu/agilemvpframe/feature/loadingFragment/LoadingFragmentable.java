package com.mingyuechunqiu.agilemvpframe.feature.loadingFragment;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2019/1/23
 *     desc   : 加载Fragment功能接口
 *     version: 1.0
 * </pre>
 */
interface LoadingFragmentable {

    /**
     * 释放资源
     */
    void release();

    /**
     * 设置是否可以触摸外围区域
     *
     * @param canTouchOutside 能否触摸外围区域
     */
    void setCanTouchOutside(boolean canTouchOutside);

    /**
     * 设置容器背景
     *
     * @param drawable 背景图像对象
     */
    void setContainerBackground(Drawable drawable);

    /**
     * 设置加载背景
     *
     * @param drawable 背景图像对象
     */
    void setLoadingBackground(final Drawable drawable);

    /**
     * 设置无进度加载图像
     *
     * @param drawable 加载图像对象
     */
    void setIndeterminateProgressDrawable(final Drawable drawable);

    /**
     * 设置是否显示加载文本
     *
     * @param showLoadingMessage 是否显示加载文本
     */
    void setShowLoadingMessage(boolean showLoadingMessage);

    /**
     * 设置加载信息背景
     *
     * @param drawable 背景图像对象
     */
    void setLoadingMessageBackground(final Drawable drawable);

    /**
     * 设置并显示加载文本信息
     *
     * @param msg 加载文本
     */
    void setLoadingMessage(@Nullable final String msg);

    /**
     * 设置加载文本颜色
     *
     * @param color 颜色值
     */
    void setLoadingMessageColor(@ColorInt final int color);

    /**
     * 设置加载文本样式
     *
     * @param textAppearance 文本样式
     */
    void setLoadingMessageTextAppearance(@StyleRes final int textAppearance);

    /**
     * 设置加载配置信息
     *
     * @param option 配置信息对象
     */
    void setLoadingFragmentOption(LoadingFragmentOption option);

    /**
     * 获取配置信息
     *
     * @return 返回配置信息对象
     */
    LoadingFragmentOption getLoadingFragmentOption();
}
