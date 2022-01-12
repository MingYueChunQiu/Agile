package com.mingyuechunqiu.agile.base.presenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.mingyuechunqiu.agile.base.bridge.Request;
import com.mingyuechunqiu.agile.base.bridge.call.Call;
import com.mingyuechunqiu.agile.base.businessengine.IBaseBusinessEngine;
import com.mingyuechunqiu.agile.base.model.IBaseModel;
import com.mingyuechunqiu.agile.base.view.IBaseView;
import com.mingyuechunqiu.agile.data.bean.ErrorInfo;
import com.mingyuechunqiu.agile.feature.helper.ui.hint.IPopHintOwner;
import com.mingyuechunqiu.agile.feature.helper.ui.hint.ToastHelper;
import com.mingyuechunqiu.agile.feature.statusview.framework.IStatusViewOwner;
import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;

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
public abstract class BaseAbstractPresenter<V extends IBaseView, M extends IBaseModel> implements IBasePresenter<V, M> {

    private WeakReference<V> mViewRef;
    @Nullable
    private M mModel;
    @Nullable
    private List<IBaseBusinessEngine> mBusinessEngineList;

    @Override
    public void attachView(@NonNull V view) {
        mViewRef = new WeakReference<>(view);
        mModel = initModel();
        onAttachView(view, mModel);
        initBusinessEngines();
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

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        switch (event) {
            case ON_START:
                callOnStart();
                break;
            case ON_RESUME:
                callOnResume();
                break;
            case ON_PAUSE:
                callOnPause();
                break;
            case ON_STOP:
                callOnStop();
                break;
            case ON_DESTROY:
                detachView();
                break;
        }
    }

    @Override
    public void callOnStart() {
        onStart();
        if (mModel != null) {
            mModel.callOnStart();
        }
    }

    @Override
    public void callOnResume() {
        onResume();
        if (mModel != null) {
            mModel.callOnResume();
        }
    }

    @Override
    public void callOnPause() {
        onPause();
        if (mModel != null) {
            mModel.callOnPause();
        }
    }

    @Override
    public void callOnStop() {
        onStop();
        if (mModel != null) {
            mModel.callOnStop();
        }
    }

    @Override
    public <I extends Request.IParamsInfo, T> boolean dispatchCall(@NonNull Call<I, T> call) {
        if (mModel == null) {
            throw new IllegalArgumentException("Model has not been set!");
        }
        return dispatchCallWithModel(call);
    }

    /**
     * 根据资源id显示提示
     *
     * @param msgResId 文本资源ID
     */
    @Override
    public void showToast(@StringRes int msgResId) {
        if (!checkViewRefIsNull()) {
            V v = mViewRef.get();
            if (v instanceof IPopHintOwner) {
                ((IPopHintOwner) v).showToast(msgResId);
            }
        }
    }

    /**
     * 根据文本显示提示
     *
     * @param msg 文本
     */
    @Override
    public void showToast(@Nullable String msg) {
        if (!checkViewRefIsNull()) {
            V v = mViewRef.get();
            if (v instanceof IPopHintOwner) {
                ((IPopHintOwner) v).showToast(msg);
            }
        }
    }

    /**
     * 根据错误信息显示提示
     *
     * @param info 错误信息对象
     */
    @Override
    public void showToast(@NonNull ErrorInfo info) {
        if (!checkViewRefIsNull()) {
            V v = mViewRef.get();
            if (v instanceof IPopHintOwner) {
                ((IPopHintOwner) v).showToast(info);
            }
        }
    }

    /**
     * 根据配置信息显示提示
     *
     * @param config 配置信息对象
     */
    @Override
    public void showToast(@NonNull ToastHelper.ToastConfig config) {
        if (!checkViewRefIsNull()) {
            V v = mViewRef.get();
            if (v instanceof IPopHintOwner) {
                ((IPopHintOwner) v).showToast(config);
            }
        }
    }

    @Nullable
    @Override
    public V getView() {
        return mViewRef == null ? null : mViewRef.get();
    }

    @Nullable
    @Override
    public M getModel() {
        return mModel;
    }

    @Override
    public void initBusinessEngines() {
        initializeBusinessEngines();
    }

    @Override
    public void showLoadingStatusView(@Nullable String hint, boolean cancelable) {
        V v = getView();
        if (v instanceof IStatusViewOwner) {
            ((IStatusViewOwner) v).showLoadingStatusView(hint, cancelable);
        }
    }

    @Override
    public void showLoadingStatusView(int containerId) {
        V v = getView();
        if (v instanceof IStatusViewOwner) {
            ((IStatusViewOwner) v).showLoadingStatusView(containerId);
        }
    }

    @Override
    public void dismissStatusView() {
        V v = getView();
        if (v instanceof IStatusViewOwner) {
            ((IStatusViewOwner) v).dismissStatusView();
        }
    }

    @Nullable
    protected IStatusViewManager getStatusViewManager() {
        V v = getView();
        if (v instanceof IStatusViewOwner) {
            return ((IStatusViewOwner) v).getStatusViewManager();
        }
        return null;
    }

    /**
     * 显示信息并关闭加载对话框
     *
     * @param msg 文本
     */
    protected void showToastAndDismissStatusView(@Nullable String msg) {
        showToastAndDismissStatusView(new ToastHelper.ToastConfig.Builder()
                .setMsg(msg)
                .build());
    }

    /**
     * 显示信息并关闭加载对话框
     *
     * @param msgResId 文本资源ID
     */
    protected void showToastAndDismissStatusView(@StringRes int msgResId) {
        showToastAndDismissStatusView(new ToastHelper.ToastConfig.Builder()
                .setMsgResId(msgResId)
                .build());
    }

    /**
     * 显示信息并关闭加载对话框
     *
     * @param info 错误信息对象
     */
    protected void showToastAndDismissStatusView(@NonNull ErrorInfo info) {
        showToastAndDismissStatusView(new ToastHelper.ToastConfig.Builder()
                .setMsg(info.getErrorMsg())
                .setMsgResId(info.getErrorMsgResId())
                .build());
    }

    /**
     * 显示信息并关闭加载对话框
     *
     * @param config 配置信息对象
     */
    protected void showToastAndDismissStatusView(@NonNull ToastHelper.ToastConfig config) {
        V v = getView();
        if (v instanceof IPopHintOwner) {
            ((IPopHintOwner) v).showToast(config);
        }
        if (v instanceof IStatusViewOwner) {
            ((IStatusViewOwner) v).dismissStatusView();
        }
    }

    /**
     * 获取当前层级的主FragmentManager
     *
     * @return 返回FragmentManager对象
     */
    @Nullable
    protected FragmentManager getCurrentFragmentManager() {
        if (checkViewRefIsNull()) {
            return null;
        }
        FragmentManager manager = null;
        if (mViewRef.get() instanceof FragmentActivity) {
            manager = ((FragmentActivity) mViewRef.get()).getSupportFragmentManager();
        } else if (mViewRef.get() instanceof Fragment) {
            manager = ((Fragment) mViewRef.get()).getFragmentManager();
        }
        return manager;
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
        if (mBusinessEngineList != null) {
            for (IBaseBusinessEngine engine : mBusinessEngineList) {
                if (engine != null) {
                    engine.release();
                }
            }
            mBusinessEngineList.clear();
            mBusinessEngineList = null;
        }
    }

    /**
     * 添加模型层engine单元
     *
     * @param engine engine单元模块
     * @return 如果添加成功返回true，否则返回false
     */
    public boolean addBusinessEngine(@NonNull IBaseBusinessEngine engine) {
        if (mBusinessEngineList == null) {
            mBusinessEngineList = new ArrayList<>();
        }
        return mBusinessEngineList.add(engine);
    }

    /**
     * 删除指定的模型层engine单元
     *
     * @param engine engine单元模块
     * @return 如果删除成功返回true，否则返回false
     */
    public boolean removeBusinessEngine(@Nullable IBaseBusinessEngine engine) {
        if (engine == null || mBusinessEngineList == null) {
            return false;
        }
        return mBusinessEngineList.remove(engine);
    }

    @NonNull
    @Override
    public List<IBaseBusinessEngine> getBusinessEngineList() {
        return mBusinessEngineList != null ? mBusinessEngineList : new ArrayList<>();
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
     * 由子类重写，调用model进行分发调用操作
     *
     * @param call 调用对象
     * @param <I>  请求泛型类型
     * @param <T>  响应泛型类型
     * @return 执行请求返回true，否则返回false
     */
    protected abstract <I extends Request.IParamsInfo, T> boolean dispatchCallWithModel(@NonNull Call<I, T> call);

    /**
     * 供子类重写进行业务引擎初始化
     */
    protected abstract void initializeBusinessEngines();

    /**
     * 释放资源
     */
    protected abstract void release();
}
