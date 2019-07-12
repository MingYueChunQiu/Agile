package com.mingyuechunqiu.agile.feature.loading.function;

import androidx.fragment.app.FragmentManager;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2019/1/24
 *     desc   : 加载界面控制接口
 *     version: 1.0
 * </pre>
 */
interface LoadingDfgControlable {

    /**
     * 显示加载对话框
     *
     * @return 如果执行了显示返回true，否则返回false
     */
    boolean showLoadingDialog();

    /**
     * 显示加载对话框
     *
     * @param manager Fragment管理器
     */
    void showLoadingDialog(FragmentManager manager);

    /**
     * 关闭加载对话框
     *
     * @param allowStateLoss 是否允许丢失状态信息
     */
    void dismissLoadingDialog(boolean allowStateLoss);
}
