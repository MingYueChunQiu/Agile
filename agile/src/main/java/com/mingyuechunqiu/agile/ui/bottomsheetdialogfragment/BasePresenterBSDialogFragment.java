package com.mingyuechunqiu.agile.ui.bottomsheetdialogfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mingyuechunqiu.agile.base.presenter.BaseDialogPresenter;
import com.mingyuechunqiu.agile.base.view.IBaseDialogView;
import com.mingyuechunqiu.agile.base.view.IViewAttachPresenter;

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
     * 关联添加Present
     */
    @Override
    public void attachPresenter() {
        if (aloneView()) {
            return;
        }
        bindPresenter(false, null);
    }

    /**
     * 绑定Presenter
     *
     * @param outside   Presenter是否是从外部传入
     * @param presenter 外部传入的Presenter
     */
    @SuppressWarnings("unchecked")
    @Override
    public void bindPresenter(boolean outside, P presenter) {
        ((V) this).setPresenter(outside ? presenter : initPresenter());
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
            getLifecycle().addObserver(mPresenter);
        }
    }
}
