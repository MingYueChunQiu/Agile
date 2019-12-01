package com.mingyuechunqiu.agile.base.presenter;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.mingyuechunqiu.agile.base.model.BaseAbstractModel;
import com.mingyuechunqiu.agile.base.view.IBaseStatusView;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.feature.statusview.function.StatusViewManagerProvider;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/12
 *     desc   : 所有带对话框功能的Presenter的基类
 *              继承自BaseAbstractPresenter
 *     version: 1.0
 * </pre>
 */
public abstract class BaseStatusViewPresenter<V extends IBaseStatusView, M extends BaseAbstractModel> extends BaseAbstractPresenter<V, M> {

    /**
     * 显示加载对话框
     *
     * @param hint       提示文本
     * @param cancelable 对话框是否可以被取消
     */
    protected void showLoadingDialog(@Nullable String hint, boolean cancelable) {
        if (checkViewRefIsNull()) {
            return;
        }
        FragmentManager manager = null;
        if (mViewRef.get() instanceof FragmentActivity) {
            manager = ((FragmentActivity) mViewRef.get()).getSupportFragmentManager();
        } else if (mViewRef.get() instanceof Fragment) {
            manager = ((Fragment) mViewRef.get()).getFragmentManager();
        }
        StatusViewOption option = StatusViewManagerProvider.getGlobalStatusViewOptionByType(StatusViewConstants.StatusType.TYPE_LOADING);
        option.getContentOption().setText(hint);
        option.setCancelWithOutside(cancelable);
        showStatusView(StatusViewConstants.StatusType.TYPE_LOADING, manager, option);
    }

    protected void showStatusView(@NonNull StatusViewConstants.StatusType type, @Nullable FragmentManager manager,
                                  @Nullable StatusViewOption option) {
        if (checkViewRefIsNull() || manager == null) {
            return;
        }
        mViewRef.get().getStatusViewManager().showStatusView(type, manager, option);
    }

    protected void showStatusView(@NonNull StatusViewConstants.StatusType type, @Nullable FragmentManager manager,
                                  @IdRes int containerId, @Nullable StatusViewOption option) {
        if (checkViewRefIsNull() || manager == null) {
            return;
        }
        mViewRef.get().getStatusViewManager().showStatusView(type, manager, containerId, option);
    }

    /**
     * 关闭加载对话框
     */
    protected void dismissStatusView() {
        if (!checkViewRefIsNull()) {
            mViewRef.get().dismissStatusView();
        }
    }

    /**
     * 显示提示信息并关闭加载对话框
     *
     * @param hint 提示文本
     */
    protected void showToastAndDismissStatusView(@Nullable String hint) {
        if (!checkViewRefIsNull()) {
            mViewRef.get().showToast(hint);
            mViewRef.get().dismissStatusView();
        }
    }

    /**
     * 显示提示信息并关闭加载对话框
     *
     * @param stringResourceId 提示文本资源ID
     */
    protected void showToastAndDismissStatusView(@StringRes int stringResourceId) {
        if (!checkViewRefIsNull()) {
            mViewRef.get().showToast(stringResourceId);
            mViewRef.get().dismissStatusView();
        }
    }
}
