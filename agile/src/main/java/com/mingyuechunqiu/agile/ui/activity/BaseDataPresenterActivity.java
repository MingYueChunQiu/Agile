package com.mingyuechunqiu.agile.ui.activity;

import com.mingyuechunqiu.agile.base.model.BaseAbstractDataModel;
import com.mingyuechunqiu.agile.base.presenter.BaseAbstractDataPresenter;
import com.mingyuechunqiu.agile.base.view.IBaseDataView;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/9
 *     desc   : 所有MVP层具有数据处理功能的界面的基类
 *              继承自BaseStatusViewPresenterActivity
 *     version: 1.0
 * </pre>
 */
public abstract class BaseDataPresenterActivity<V extends IBaseDataView, P extends BaseAbstractDataPresenter<V, ? extends BaseAbstractDataModel>> extends BaseAbstractPresenterActivity<V, P> {
}
