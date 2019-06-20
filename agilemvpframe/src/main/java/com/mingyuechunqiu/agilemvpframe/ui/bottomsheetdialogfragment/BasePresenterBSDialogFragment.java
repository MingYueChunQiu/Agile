package com.mingyuechunqiu.agilemvpframe.ui.bottomsheetdialogfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mingyuechunqiu.agilemvpframe.base.presenter.BaseDialogPresenter;
import com.mingyuechunqiu.agilemvpframe.base.view.IBaseDialogView;
import com.mingyuechunqiu.agilemvpframe.base.view.IViewAttachPresenter;

/**
 * <pre>
 *     author : 明月春秋
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/10/21
 *     desc   : 所有MVP层底部对话框碎片的基类
 *              继承自BaseBSDialogFragment
 *     version: 1.0
 * </pre>
 */
public abstract class BasePresenterBSDialogFragment<V extends IBaseDialogView<P>, P extends BaseDialogPresenter> extends BaseBSDialogFragment
        implements IViewAttachPresenter<P> {

    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            getLifecycle().removeObserver(mPresenter);
            mPresenter = null;
        }
    }

    /**
     * 是否单独使用View，不绑定Presenter（默认返回false）
     *
     * @return 返回true表示只用View不绑定Presenter，否则返回false
     */
    @Override
    public boolean aloneView() {
        return false;
    }

    /**
     * 绑定Presenter层
     */
    @SuppressWarnings("unchecked")
    @Override
    public void attachPresenter() {
        if (aloneView()) {
            return;
        }
        ((V) this).setPresenter(initPresenter());
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
            getLifecycle().addObserver(mPresenter);
        }
    }
}
