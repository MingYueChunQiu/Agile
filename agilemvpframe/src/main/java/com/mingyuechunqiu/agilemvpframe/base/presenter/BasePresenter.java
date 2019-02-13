package com.mingyuechunqiu.agilemvpframe.base.presenter;

import com.mingyuechunqiu.agilemvpframe.base.model.BaseModel;
import com.mingyuechunqiu.agilemvpframe.base.view.BaseView;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/11/15
 *     desc   : 所有P层的父接口
 *     version: 1.0
 * </pre>
 */
public interface BasePresenter<V extends BaseView, M extends BaseModel> {

    void attachView(V view);

    void detachView();

    void start();

    M initModel();

    void release();
}
