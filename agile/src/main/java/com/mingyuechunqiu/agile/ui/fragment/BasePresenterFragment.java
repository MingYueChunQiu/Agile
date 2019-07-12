package com.mingyuechunqiu.agile.ui.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.presenter.BaseDialogPresenter;
import com.mingyuechunqiu.agile.base.view.IBaseDialogView;
import com.mingyuechunqiu.agile.base.view.IViewAttachPresenter;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/16
 *     desc   : 所有MVP层Fragment的基类
 *              继承自BaseFragment
 *     version: 1.0
 * </pre>
 */
public abstract class BasePresenterFragment<V extends IBaseDialogView<P>, P extends BaseDialogPresenter> extends BaseFragment
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
            //不能放在onDestroyView中执行，因为像输入框失去焦点这种事件会在onDestroyView之后才被调用
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
