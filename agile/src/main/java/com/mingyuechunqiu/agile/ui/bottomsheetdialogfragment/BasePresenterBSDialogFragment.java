package com.mingyuechunqiu.agile.ui.bottomsheetdialogfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.mingyuechunqiu.agile.base.model.BaseAbstractModel;
import com.mingyuechunqiu.agile.base.presenter.AgilePresenterViewModel;
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
public abstract class BasePresenterBSDialogFragment<V extends IBaseView, P extends IBasePresenter<V, ? extends BaseAbstractModel>> extends BaseBSDialogFragment implements IViewAttachPresenter<P> {

    @Nullable
    private AgilePresenterViewModel<P> mAgilePresenterViewModel = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        P presenter = getAgilePresenterViewModel().getPresenter();
        if (presenter != null) {
            getLifecycle().removeObserver(presenter);
            //不能放在onDestroyView中执行，因为像输入框失去焦点这种事件会在onDestroyView之后才被调用
            getAgilePresenterViewModel().setPresenter(null);
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
        if (!(this instanceof IBaseView)) {
            throw new IllegalStateException("Current DialogFragment must implements IBaseView or it subclass");
        }
        setPresenter(presenter);
        P finalPresenter = getAgilePresenterViewModel().getPresenter();
        if (finalPresenter != null) {
            finalPresenter.attachView((V) this);
            getLifecycle().addObserver(finalPresenter);
        }
    }

    @Override
    public void setPresenter(@NonNull P presenter) {
        getAgilePresenterViewModel().setPresenter(presenter);
    }

    @Nullable
    @Override
    public P getPresenter() {
        return getAgilePresenterViewModel().getPresenter();
    }

    @SuppressWarnings("unchecked")
    private AgilePresenterViewModel<P> getAgilePresenterViewModel() {
        if (mAgilePresenterViewModel == null) {
            mAgilePresenterViewModel = new ViewModelProvider(this).get(AgilePresenterViewModel.class);
        }
        return mAgilePresenterViewModel;
    }
}
