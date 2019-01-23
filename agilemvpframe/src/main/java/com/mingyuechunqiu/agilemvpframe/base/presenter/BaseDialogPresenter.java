package com.mingyuechunqiu.agilemvpframe.base.presenter;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.mingyuechunqiu.agilemvpframe.base.model.BaseAbstractModel;
import com.mingyuechunqiu.agilemvpframe.base.view.BaseDialogView;
import com.mingyuechunqiu.agilemvpframe.feature.loadingFragment.LoadingFragmentOption;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/12
 *     desc   : 所有带对话框功能的Presenter的基类
 *              继承自BaseNetPresenter
 *     version: 1.0
 * </pre>
 */
public abstract class BaseDialogPresenter<V extends BaseDialogView, M extends BaseAbstractModel> extends BaseAbstractPresenter<V, M> {

    /**
     * 显示提示信息
     *
     * @param hint 提示文本
     */
    protected void showToast(String hint) {
        if (mViewRef.get() != null) {
            mViewRef.get().showToast(hint);
        }
    }

    /**
     * 显示提示信息
     *
     * @param stringResourceId 提示文本资源ID
     */
    protected void showToast(@StringRes int stringResourceId) {
        if (mViewRef.get() != null) {
            mViewRef.get().showToast(stringResourceId);
        }
    }

    /**
     * 显示加载对话框
     *
     * @param hint       提示文本
     * @param cancelable 对话框是否可以被取消
     */
    protected void showLoadingDialog(String hint, boolean cancelable) {
        if (mViewRef.get() == null) {
            return;
        }
        mViewRef.get().showLoadingDialog(hint, cancelable);
    }

    /**
     * 关闭加载对话框
     */
    protected void disappearLoadingDialog() {
        if (mViewRef.get() != null) {
            mViewRef.get().disappearLoadingDialog();
        }
    }

    /**
     * 显示加载界面
     *
     * @param containerId 依附的父布局资源ID
     * @param option      加载配置参数信息对象
     */
    protected void showLoadingFragment(@IdRes int containerId, @Nullable LoadingFragmentOption option) {
        if (mViewRef.get() != null) {
            mViewRef.get().showLoadingFragment(containerId, option);
        }
    }

    /**
     * 隐藏加载界面
     */
    protected void hideLoadingFragment() {
        if (mViewRef.get() != null) {
            mViewRef.get().hideLoadingFragment();
        }
    }

    /**
     * 显示提示信息并关闭加载对话框
     *
     * @param hint 提示文本
     */
    protected void showToastAndDisappearLoadingDialog(String hint) {
        if (mViewRef.get() != null) {
            mViewRef.get().showToast(hint);
            mViewRef.get().disappearLoadingDialog();
        }
    }

    /**
     * 显示提示信息并关闭加载对话框
     *
     * @param stringResourceId 提示文本资源ID
     */
    protected void showToastAndDisappearLoadingDialog(int stringResourceId) {
        if (mViewRef.get() != null) {
            mViewRef.get().showToast(stringResourceId);
            mViewRef.get().disappearLoadingDialog();
        }
    }
}
