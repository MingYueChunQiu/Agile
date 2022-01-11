package com.mingyuechunqiu.agile.base.presenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleEventObserver;

import com.mingyuechunqiu.agile.base.bridge.call.ICallDispatcher;
import com.mingyuechunqiu.agile.base.businessengine.IBusinessEngineOwner;
import com.mingyuechunqiu.agile.base.model.IBaseModel;
import com.mingyuechunqiu.agile.base.view.IBaseView;
import com.mingyuechunqiu.agile.feature.helper.ui.hint.IPopHintOwner;
import com.mingyuechunqiu.agile.feature.statusview.framework.IStatusViewProcessor;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/11/15
 *     desc   : 所有P层的父接口
 *     version: 1.0
 * </pre>
 */
public interface IBasePresenter<V extends IBaseView, M extends IBaseModel> extends LifecycleEventObserver, ICallDispatcher, IBusinessEngineOwner, IPopHintOwner, IStatusViewProcessor {

    void attachView(@NonNull V view);

    void detachView();

    void callOnStart();

    void callOnResume();

    void callOnPause();

    void callOnStop();

    @Nullable
    V getView();

    @Nullable
    M initModel();

    @Nullable
    M getModel();

    void initBusinessEngines();
}
