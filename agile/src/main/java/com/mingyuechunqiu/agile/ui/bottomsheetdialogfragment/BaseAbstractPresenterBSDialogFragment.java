package com.mingyuechunqiu.agile.ui.bottomsheetdialogfragment;

import com.mingyuechunqiu.agile.base.model.BaseAbstractModel;
import com.mingyuechunqiu.agile.base.presenter.IBasePresenter;
import com.mingyuechunqiu.agile.base.view.IBaseView;
import com.mingyuechunqiu.agile.base.view.IViewAttachPresenter;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/9 9:37 下午
 *      Desc:       P层功能的BSDialogFragment的基类
 *                  继承自BaseBSDialogFragment
 *      Version:    1.0
 * </pre>
 */
public abstract class BaseAbstractPresenterBSDialogFragment<V extends IBaseView, P extends IBasePresenter<V, ? extends BaseAbstractModel>> extends BaseBSDialogFragment implements IViewAttachPresenter<P> {
}
