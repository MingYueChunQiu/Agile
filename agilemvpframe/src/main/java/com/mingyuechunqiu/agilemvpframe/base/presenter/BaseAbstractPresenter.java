package com.mingyuechunqiu.agilemvpframe.base.presenter;

import com.mingyuechunqiu.agilemvpframe.base.model.BaseModel;
import com.mingyuechunqiu.agilemvpframe.base.model.BaseTokenNetModel;
import com.mingyuechunqiu.agilemvpframe.base.view.BaseView;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/06/14
 *     desc   : 所有Presenter的基类
 *              实现BasePresenter
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractPresenter<V extends BaseView, M extends BaseModel> implements BasePresenter<V, M> {

    protected WeakReference<V> mViewRef;
    protected M mModel;

    @Override
    public void attachView(V view) {
        mViewRef = new WeakReference<>(view);
        mModel = initModel();
        if (mViewRef.get() != null && mViewRef.get().getCurrentContext() != null) {
            if (mModel instanceof BaseTokenNetModel) {
                ((BaseTokenNetModel) mModel).setContextRef(mViewRef.get().getCurrentContext());
            }
        }
    }

    @Override
    public void detachView() {
        if (mModel != null) {
            mModel.destroy();
            mModel = null;
        }
        releaseOnDetach();
    }

    protected void releaseOnDetach() {
        release();
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    protected void showToast(String hint) {
        if (mViewRef.get() != null) {
            mViewRef.get().showToast(hint);
        }
    }

    protected void showToast(int stringResourceId) {
        if (mViewRef.get() != null) {
            mViewRef.get().showToast(stringResourceId);
        }
    }
}
