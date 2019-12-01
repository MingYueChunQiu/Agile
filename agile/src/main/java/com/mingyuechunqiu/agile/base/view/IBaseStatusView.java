package com.mingyuechunqiu.agile.base.view;

import com.mingyuechunqiu.agile.base.presenter.BaseStatusViewPresenter;
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
public interface IBaseStatusView<P extends BaseStatusViewPresenter> extends IBaseView<P> {

    /**
     * 获取状态视图管理器实例
     *
     * @return 返回状态视图管理器实例
     */
    IStatusViewManager getStatusViewManager();

    /**
     * 关闭状态视图
     */
    void dismissStatusView();
}
