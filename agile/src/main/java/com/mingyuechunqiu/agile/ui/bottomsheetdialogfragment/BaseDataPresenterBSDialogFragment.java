package com.mingyuechunqiu.agile.ui.bottomsheetdialogfragment;

import com.mingyuechunqiu.agile.base.presenter.BaseAbstractDataPresenter;
import com.mingyuechunqiu.agile.base.view.IBaseDataView;

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
public abstract class BaseDataPresenterBSDialogFragment<V extends IBaseDataView<P>, P extends BaseAbstractDataPresenter> extends BasePresenterBSDialogFragment<V, P> {
}
