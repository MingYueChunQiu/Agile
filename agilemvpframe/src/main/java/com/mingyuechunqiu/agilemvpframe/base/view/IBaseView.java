package com.mingyuechunqiu.agilemvpframe.base.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.mingyuechunqiu.agilemvpframe.base.presenter.IBasePresenter;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/06/14
 *     desc   : 所有view视图接口的父接口
 *     version: 1.0
 * </pre>
 */
public interface IBaseView<P extends IBasePresenter> {

    void setPresenter(@NonNull P presenter);

    void showToast(@Nullable String hint);

    void showToast(@StringRes int stringResourceId);

    /**
     * 获取当前界面的上下文
     *
     * @return 返回上下文
     */
    Context getCurrentContext();
}
