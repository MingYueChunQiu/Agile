package com.mingyuechunqiu.agilemvpframe.ui.diaglogfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mingyuechunqiu.agilemvpframe.base.presenter.BaseDialogPresenter;
import com.mingyuechunqiu.agilemvpframe.base.view.IBaseDialogView;
import com.mingyuechunqiu.agilemvpframe.base.view.IViewAttachPresenter;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/06/14
 *     desc   : 所有MVP层DialogFragment的基类
 *              继承自BaseDialogFragment
 *     version: 1.0
 * </pre>
 */
public abstract class BasePresenterDialogFragment<V extends IBaseDialogView<P>, P extends BaseDialogPresenter> extends BaseDialogFragment
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
     * 添加Present相关
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
