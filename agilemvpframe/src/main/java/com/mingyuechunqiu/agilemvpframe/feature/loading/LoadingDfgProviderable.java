package com.mingyuechunqiu.agilemvpframe.feature.loading;

import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2019/1/25
 *     desc   : 加载Fragment提供者接口
 *              继承自LoadingDialogFragmentable
 *     version: 1.0
 * </pre>
 */
public interface LoadingDfgProviderable extends LoadingDialogFragmentable {

    /**
     * 设置对话框主题样式
     *
     * @param themeType 对话框主题样式
     */
    void setThemeType(Constants.ThemeType themeType);

    /**
     * 添加显示对话框
     *
     * @param manager     Fragment管理器
     * @param containerId 父布局ID
     * @param option      加载对话框配置信息对象
     */
    void addOrShowLoadingDialog(FragmentManager manager, @IdRes int containerId, LoadingDialogFragmentOption option);

    /**
     * 隐藏加载对话框
     *
     * @param manager Fragment管理器
     */
    void hideLoadingDialog(FragmentManager manager);

    /**
     * 移除加载对话框
     *
     * @param manager Fragment管理器
     */
    void removeLoadingDialog(FragmentManager manager);

    /**
     * 移除加载对话框
     *
     * @param manager        Fragment管理器
     * @param dialogFragment 对话框Fragment
     */
    void removeLoadingDialog(FragmentManager manager, DialogFragment dialogFragment);

    /**
     * 重置加载对话框配置信息
     */
    void resetLoadingDialog();
}
