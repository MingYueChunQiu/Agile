package com.mingyuechunqiu.agile.base.view;

import com.mingyuechunqiu.agile.base.presenter.BaseDialogPresenter;
import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;

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
     * 获取加载Fragment提供者实例
     *
     * @return 返回加载Fragment提供者实例
     */
    IStatusViewManager getLoadingDialog();
}
