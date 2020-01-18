package com.mingyuechunqiu.agile.ui.fragment;

import com.mingyuechunqiu.agile.base.presenter.BaseAbstractDataPresenter;
import com.mingyuechunqiu.agile.base.view.IBaseDataView;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/9
 *     desc   : 所有MVP层具有网络功能的Fragment的基类
 *              继承自BaseStatusViewPresenterFragment
 *     version: 1.0
 * </pre>
 */
public abstract class BaseDataPresenterFragment<V extends IBaseDataView<P>, P extends BaseAbstractDataPresenter> extends BaseStatusViewPresenterFragment<V, P> {
}
