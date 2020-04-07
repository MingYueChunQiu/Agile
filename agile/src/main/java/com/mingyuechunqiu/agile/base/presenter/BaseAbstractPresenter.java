package com.mingyuechunqiu.agile.base.presenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.mingyuechunqiu.agile.base.model.IBaseModel;
import com.mingyuechunqiu.agile.base.presenter.engine.IBasePresenterEngine;
import com.mingyuechunqiu.agile.base.view.IBaseView;
import com.mingyuechunqiu.agile.data.bean.ParamsInfo;
import com.mingyuechunqiu.agile.data.bean.ErrorInfo;
import com.mingyuechunqiu.agile.util.ToastUtils;

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
public abstract class BaseAbstractPresenter<V extends IBaseView<?>, M extends IBaseModel<?>> implements IBasePresenter<V, M>, LifecycleObserver {

    protected WeakReference<V> mViewRef;
    @Nullable
    protected M mModel;
    @Nullable
    private List<IBasePresenterEngine> mPresenterEngineList;

    @Override
    public void attachView(@NonNull V view) {
        mViewRef = new WeakReference<>(view);
        mModel = initModel();
        onAttachView(view, mModel);
    }

    @Override
    public void detachView() {
        releaseOnDetach();
        if (mModel != null) {
            mModel.releaseOnDetach();
            mModel = null;
        }
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    /**
     * 带参数的业务请求（在model为空情况下会抛出IllegalArgumentException）
     *
     * @param info 请求参数对象
     */
    @Override
    public void requestWithParamsInfo(@NonNull ParamsInfo info) {
        if (mModel == null) {
            throw new IllegalArgumentException("Model has not been set!");
        }
        requestModel(info);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void callOnStart() {
        onStart();
        if (mModel != null) {
            mModel.callOnStart();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void callOnResume() {
        onResume();
        if (mModel != null) {
            mModel.callOnResume();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void callOnPause() {
        onPause();
        if (mModel != null) {
            mModel.callOnPause();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void callOnStop() {
        onStop();
        if (mModel != null) {
            mModel.callOnStop();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void onDestroy() {
        detachView();
    }

    protected void onStart() {
    }

    protected void onResume() {
    }

    protected void onPause() {
    }

    protected void onStop() {
    }

    protected void releaseOnDetach() {
        release();
        if (mPresenterEngineList != null) {
            for (IBasePresenterEngine engine : mPresenterEngineList) {
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
    protected boolean addPresenterEngine(@Nullable IBasePresenterEngine engine) {
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
    protected boolean removePresenterEngine(@Nullable IBasePresenterEngine engine) {
        if (engine == null || mPresenterEngineList == null) {
            return false;
        }
        return mPresenterEngineList.remove(engine);
    }

    /**
     * 显示信息
     *
     * @param msg 文本
     */
    protected void showToast(@Nullable String msg) {
        showToast(new ToastUtils.ToastConfig.Builder()
                .setMsg(msg)
                .build());
    }

    /**
     * 显示信息
     *
     * @param msgResId 文本资源ID
     */
    protected void showToast(@StringRes int msgResId) {
        showToast(new ToastUtils.ToastConfig.Builder()
                .setMsgResId(msgResId)
                .build());
    }

    protected void showToast(@NonNull ErrorInfo info) {
        showToast(new ToastUtils.ToastConfig.Builder()
                .setMsg(info.getErrorMsg())
                .setMsgResId(info.getErrorMsgResId())
                .build());
    }

    /**
     * 显示信息
     *
     * @param config 配置信息对象
     */
    protected void showToast(@NonNull ToastUtils.ToastConfig config) {
        if (!checkViewRefIsNull()) {
            mViewRef.get().showToast(config);
        }
    }

    /**
     * 当和视图View进行依附关联时调用
     *
     * @param view  依附的View
     * @param model 控制的Model
     */
    protected void onAttachView(@NonNull V view, @Nullable M model) {
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
    protected abstract void requestModel(@NonNull ParamsInfo info);

    /**
     * 释放资源
     */
    protected abstract void release();
}
