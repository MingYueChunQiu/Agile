package com.mingyuechunqiu.agilemvpframe.base.view;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;

import com.mingyuechunqiu.agilemvpframe.base.presenter.BaseDialogPresenter;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/12
 *     desc   : 所有带对话框的view视图接口的父接口
 *              继承自BaseNetView
 *     version: 1.0
 * </pre>
 */
public interface BaseDialogView<P extends BaseDialogPresenter> extends BaseView<P> {

    /**
     * 显示加载对话框
     *
     * @param hint       加载提示文字
     * @param cancelable 点击界面对话框是否消失
     */
    void showLoadingDialog(@Nullable String hint, boolean cancelable);

    /**
     * 让加载对话框消失
     */
    void disappearLoadingDialog();

    /**
     * 显示加载Fragment
     *
     * @param containerId 依附的父布局资源ID
     * @param bundle      参数数据包
     */
    void showLoadingFragment(@IdRes int containerId, @Nullable Bundle bundle);

    /**
     * 隐藏加载Fragment
     */
    void hideLoadingFragment();
}
