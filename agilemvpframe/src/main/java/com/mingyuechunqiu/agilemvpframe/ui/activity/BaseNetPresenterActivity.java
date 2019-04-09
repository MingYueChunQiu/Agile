package com.mingyuechunqiu.agilemvpframe.ui.activity;

import com.mingyuechunqiu.agilemvpframe.base.presenter.BaseNetPresenter;
import com.mingyuechunqiu.agilemvpframe.base.view.IBaseNetView;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/9
 *     desc   : 所有MVP层具有网络功能的界面的基类
 *              继承自BasePresenterActivity
 *     version: 1.0
 * </pre>
 */
public abstract class BaseNetPresenterActivity<V extends IBaseNetView<P>, P extends BaseNetPresenter> extends BasePresenterActivity<V, P> {
}
