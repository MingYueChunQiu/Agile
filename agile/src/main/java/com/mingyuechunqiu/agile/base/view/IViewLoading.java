package com.mingyuechunqiu.agile.base.view;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;

/**
 * <pre>
 *      Project:    Agile
 *
 *      author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-11-24 14:33
 *      Desc:       View层带Dialog功能接口
 *      Version:    1.0
 * </pre>
 */
public interface IViewLoading {

    /**
     * 显示加载Fragment
     *
     * @param option 加载配置参数信息对象
     */
    void showLoadingDialog(@Nullable StatusViewOption option);

    /**
     * 添加显示加载对话框
     *
     * @param containerId 对话框所属布局ID
     * @param option      加载对话框配置信息对象
     */
    void showLoadingDialog(@IdRes int containerId, @Nullable StatusViewOption option);

    /**
     * 关闭加载对话框（默认关闭时允许丢失状态）
     */
    void dismissLoadingDialog();

    /**
     * 获取当前的加载管理器
     *
     * @return 返回加载管理器实例
     */
    IStatusViewManager getCurrentLoadingManager();
}
