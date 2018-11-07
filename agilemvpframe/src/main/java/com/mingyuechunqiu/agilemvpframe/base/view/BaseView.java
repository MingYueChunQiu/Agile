package com.mingyuechunqiu.agilemvpframe.base.view;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mingyuechunqiu.agilemvpframe.base.presenter.BasePresenter;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/06/14
 *     desc   : 所有view视图接口的父接口
 *     version: 1.0
 * </pre>
 */
public interface BaseView<P extends BasePresenter> {

    void setPresenter(@NonNull P presenter);

    void showToast(String hint);

    void showToast(int stringResourceId);

    /**
     * 获取当前界面的上下文
     *
     * @return 返回上下文
     */
    Context getCurrentContext();
}
