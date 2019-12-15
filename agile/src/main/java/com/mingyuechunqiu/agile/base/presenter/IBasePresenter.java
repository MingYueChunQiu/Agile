package com.mingyuechunqiu.agile.base.presenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.model.IBaseModel;
import com.mingyuechunqiu.agile.base.view.IBaseView;
import com.mingyuechunqiu.agile.data.bean.BaseParamsInfo;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/11/15
 *     desc   : 所有P层的父接口
 *     version: 1.0
 * </pre>
 */
public interface IBasePresenter<V extends IBaseView, M extends IBaseModel> {

    void attachView(@NonNull V view);

    void detachView();

    void start();

    void resume();

    void pause();

    @Nullable
    M initModel();

    /**
     * 设置请求参数对象，进行请求
     *
     * @param info 请求参数对象
     */
    void requestWithParamsInfo(@NonNull BaseParamsInfo info);
}
