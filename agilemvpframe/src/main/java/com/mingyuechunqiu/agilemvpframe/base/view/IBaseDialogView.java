package com.mingyuechunqiu.agilemvpframe.base.view;

import android.support.annotation.Nullable;

import com.mingyuechunqiu.agilemvpframe.base.presenter.BaseDialogPresenter;
import com.mingyuechunqiu.agilemvpframe.feature.loadingdialogfragment.LoadingDfgProviderable;
import com.mingyuechunqiu.agilemvpframe.feature.loadingdialogfragment.LoadingDialogFragmentOption;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/12
 *     desc   : 所有带对话框的view视图接口的父接口
 *              继承自BaseView
 *     version: 1.0
 * </pre>
 */
public interface IBaseDialogView<P extends BaseDialogPresenter> extends IBaseView<P> {

    /**
     * 显示加载对话框
     *
     * @param hint       加载提示文字
     * @param cancelable 点击界面对话框是否消失
     */
    void showLoadingDialog(@Nullable String hint, boolean cancelable);

    /**
     * 关闭加载对话框
     */
    void dismissLoadingDialog();

    /**
     * 显示加载Fragment
     *
     * @param option 加载配置参数信息对象
     */
    void showLoadingDialog(@Nullable LoadingDialogFragmentOption option);

    /**
     * 获取加载Fragment提供者实例
     *
     * @return 返回加载Fragment提供者实例
     */
    LoadingDfgProviderable getLoadingDialog();
}
