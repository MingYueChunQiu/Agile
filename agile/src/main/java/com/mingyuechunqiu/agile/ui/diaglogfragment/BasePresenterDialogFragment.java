package com.mingyuechunqiu.agile.ui.diaglogfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.presenter.BaseAbstractStatusViewPresenter;
import com.mingyuechunqiu.agile.base.view.IBaseStatusView;
import com.mingyuechunqiu.agile.base.view.IViewAttachPresenter;

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
public abstract class BasePresenterDialogFragment<V extends IBaseStatusView<P>, P extends BaseAbstractStatusViewPresenter> extends BaseDialogFragment
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
     * 关联添加Present
     */
    @Override
    public void attachPresenter() {
        P presenter = initPresenter();
        if (presenter == null) {
            return;
        }
        bindPresenter(presenter);
    }

    /**
     * 绑定Presenter
     *
     * @param presenter 外部传入的Presenter
     */
    @SuppressWarnings("unchecked")
    @Override
    public void bindPresenter(@NonNull P presenter) {
        ((V) this).setPresenter(presenter);
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
            getLifecycle().addObserver(mPresenter);
        }
    }
}
