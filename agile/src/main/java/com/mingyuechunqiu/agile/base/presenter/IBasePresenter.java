package com.mingyuechunqiu.agile.base.presenter;

import com.mingyuechunqiu.agile.base.model.IBaseModel;
import com.mingyuechunqiu.agile.base.view.IBaseView;

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

    void attachView(V view);

    void detachView();

    void start();

    void resume();

    void pause();

    M initModel();

}
