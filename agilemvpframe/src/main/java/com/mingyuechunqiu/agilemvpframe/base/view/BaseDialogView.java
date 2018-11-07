package com.mingyuechunqiu.agilemvpframe.base.view;

import com.mingyuechunqiu.agilemvpframe.base.presenter.BaseDialogPresenter;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/12
 *     desc   : 所有带对话框的view视图接口的父接口
 *              继承自BaseView
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
    void showLoadingDialog(String hint, boolean cancelable);

    void disappearLoadingDialog();

}
