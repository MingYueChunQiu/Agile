package com.mingyuechunqiu.agile.ui.bottomsheetdialogfragment;

import com.mingyuechunqiu.agile.base.presenter.BaseNetPresenter;
import com.mingyuechunqiu.agile.base.view.IBaseNetView;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/9
 *     desc   : 所有MVP层具有网络功能的底部对话框碎片的基类
 *              继承自BasePresenterBSDialogFragment
 *     version: 1.0
 * </pre>
 */
public abstract class BaseNetPresenterBSDialogFragment<V extends IBaseNetView<P>, P extends BaseNetPresenter> extends BasePresenterBSDialogFragment<V, P> {
}
