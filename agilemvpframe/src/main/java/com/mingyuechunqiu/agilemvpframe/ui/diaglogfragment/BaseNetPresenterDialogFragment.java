package com.mingyuechunqiu.agilemvpframe.ui.diaglogfragment;

import com.mingyuechunqiu.agilemvpframe.base.presenter.BaseNetPresenter;
import com.mingyuechunqiu.agilemvpframe.base.view.IBaseNetView;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/9
 *     desc   : 所有MVP层具有网络功能的DialogFragment的基类
 *              继承自BasePresenterDialogFragment
 *     version: 1.0
 * </pre>
 */
public abstract class BaseNetPresenterDialogFragment<V extends IBaseNetView<P>, P extends BaseNetPresenter> extends BasePresenterDialogFragment<V, P> {
}
