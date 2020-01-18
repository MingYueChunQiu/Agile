package com.mingyuechunqiu.agile.ui.bottomsheetdialogfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.presenter.BaseAbstractStatusViewPresenter;
import com.mingyuechunqiu.agile.base.view.IBaseStatusView;
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
public abstract class BasePresenterBSDialogFragment<V extends IBaseStatusView<P>, P extends BaseAbstractStatusViewPresenter> extends BaseBSDialogFragment
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
