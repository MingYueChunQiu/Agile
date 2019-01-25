package com.mingyuechunqiu.agilemvpframe.feature.loadingDialogFragment;

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
     * 重置加载对话框配置信息
     */
    void resetLoadingDialog();
}
