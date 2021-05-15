package com.mingyuechunqiu.agile.ui.diaglogfragment;

import com.mingyuechunqiu.agile.base.model.BaseAbstractDataModel;
import com.mingyuechunqiu.agile.base.presenter.BaseAbstractDataPresenter;
import com.mingyuechunqiu.agile.base.view.IBaseDataView;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/9
 *     desc   : 所有MVP层具有数据功能的DialogFragment的基类
 *              继承自BaseAbstractPresenterDialogFragment
 *     version: 1.0
 * </pre>
 */
public abstract class BaseDataPresenterDialogFragment<V extends IBaseDataView, P extends BaseAbstractDataPresenter<V, ? extends BaseAbstractDataModel>> extends BaseAbstractPresenterDialogFragment<V, P> {
}
