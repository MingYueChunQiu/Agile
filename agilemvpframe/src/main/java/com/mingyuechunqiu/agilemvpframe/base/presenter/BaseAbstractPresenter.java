package com.mingyuechunqiu.agilemvpframe.base.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.mingyuechunqiu.agilemvpframe.R;
import com.mingyuechunqiu.agilemvpframe.base.model.BaseTokenNetModel;
import com.mingyuechunqiu.agilemvpframe.base.model.IBaseModel;
import com.mingyuechunqiu.agilemvpframe.base.presenter.engine.IPresenterEngine;
import com.mingyuechunqiu.agilemvpframe.base.view.IBaseView;
import com.mingyuechunqiu.agilemvpframe.data.bean.BaseInfo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/06/14
 *     desc   : 所有Presenter层的抽象基类
 *              实现IBasePresenter
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractPresenter<V extends IBaseView, M extends IBaseModel> implements IBasePresenter<V, M>, LifecycleObserver {

    protected WeakReference<V> mViewRef;
    protected M mModel;
    protected List<IPresenterEngine> mPresenterEngineList;

    @Override
    public void attachView(V view) {
        mViewRef = new WeakReference<>(view);
        mModel = initModel();
        if (!checkViewRefIsNull() && mViewRef.get().getCurrentContext() != null) {
            if (mModel instanceof BaseTokenNetModel) {
                ((BaseTokenNetModel) mModel).setContextRef(mViewRef.get().getCurrentContext());
            }
        }
        onAttachView(view, mModel);
    }

    @Override
    public void detachView() {
        releaseOnDetach();
        if (mModel != null) {
            mModel.release();
            mModel = null;
        }
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    /**
     * 带参数的业务请求
     *
     * @param info 请求参数对象
     */
    public void setParamsInfo(BaseInfo info) {
        if (mModel == null) {
            throw new IllegalArgumentException("Model has not been set!");
        }
        if (info == null) {
            showToast(R.string.error_set_net_params);
            return;
        }
        requestModel(info);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected void onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void onStart() {
        if (mModel != null) {
            mModel.start();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected void onResume() {
        start();
        if (mModel != null) {
            mModel.resume();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected void onPause() {
        if (mModel != null) {
            mModel.pause();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected void onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void onDestroy() {
    }

    protected void releaseOnDetach() {
        release();
        if (mPresenterEngineList != null) {
            for (IPresenterEngine engine : mPresenterEngineList) {
                if (engine != null) {
                    engine.release();
                }
            }
            mPresenterEngineList.clear();
            mPresenterEngineList = null;
        }
    }

    /**
     * 添加模型层engine单元
     *
     * @param engine engine单元模块
     * @return 如果添加成功返回true，否则返回false
     */
    protected boolean addPresenterEngine(IPresenterEngine engine) {
        if (engine == null) {
            return false;
        }
        if (mPresenterEngineList == null) {
            mPresenterEngineList = new ArrayList<>();
        }
        return mPresenterEngineList.add(engine);
    }

    /**
     * 删除指定的模型层engine单元
     *
     * @param engine engine单元模块
     * @return 如果删除成功返回true，否则返回false
     */
    protected boolean removePresenterEngine(IPresenterEngine engine) {
        if (engine == null || mPresenterEngineList == null) {
            return false;
        }
        return mPresenterEngineList.remove(engine);
    }

    /**
     * 显示提示信息
     *
     * @param hint 提示文本
     */
    protected void showToast(@Nullable String hint) {
        if (!checkViewRefIsNull()) {
            mViewRef.get().showToast(hint);
        }
    }

    /**
     * 显示提示信息
     *
     * @param stringResourceId 提示文本资源ID
     */
    protected void showToast(@StringRes int stringResourceId) {
        if (!checkViewRefIsNull()) {
            mViewRef.get().showToast(stringResourceId);
        }
    }

    /**
     * 当和视图View进行依附关联时调用
     *
     * @param view  依附的View
     * @param model 控制的Model
     */
    protected void onAttachView(V view, M model) {
    }

    /**
     * 检测关联的View层是否为空
     *
     * @return 如果为空返回true，否则返回false
     */
    protected boolean checkViewRefIsNull() {
        return mViewRef == null || mViewRef.get() == null;
    }

    /**
     * 由子类重写，调用model进行业务请求操作
     *
     * @param info 请求参数对象
     */
    protected abstract void requestModel(BaseInfo info);

    /**
     * 释放资源
     */
    protected abstract void release();
}
