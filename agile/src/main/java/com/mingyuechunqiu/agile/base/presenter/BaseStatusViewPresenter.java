package com.mingyuechunqiu.agile.base.presenter;

import android.text.TextUtils;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.mingyuechunqiu.agile.base.model.BaseAbstractModel;
import com.mingyuechunqiu.agile.base.view.IBaseStatusView;
import com.mingyuechunqiu.agile.data.bean.ErrorInfo;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.feature.statusview.function.StatusViewManagerProvider;
import com.mingyuechunqiu.agile.util.ToastUtils;

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
    protected void showLoadingStatusView(@Nullable String hint, boolean cancelable) {
        if (checkViewRefIsNull()) {
            return;
        }
        StatusViewOption option = StatusViewManagerProvider.getGlobalStatusViewOptionByType(StatusViewConstants.StatusType.TYPE_LOADING);
        option.getContentOption().setText(hint);
        option.setCancelWithOutside(cancelable);
        showStatusView(StatusViewConstants.StatusType.TYPE_LOADING, getCurrentFragmentManager(), option);
    }

    /**
     * 显示加载状态视图
     *
     * @param containerId 状态视图添加布局ID
     */
    protected void showLoadingStatusView(@IdRes int containerId) {
        showStatusView(StatusViewConstants.StatusType.TYPE_LOADING, getCurrentFragmentManager(),
                containerId, null);
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
     * 显示信息并关闭加载对话框
     *
     * @param msg 文本
     */
    protected void showToastAndDismissStatusView(@Nullable String msg) {
        showToastAndDismissStatusView(new ToastUtils.ToastConfig.Builder()
                .setMsg(msg)
                .build());
    }

    /**
     * 显示信息并关闭加载对话框
     *
     * @param msgResId 文本资源ID
     */
    protected void showToastAndDismissStatusView(@StringRes int msgResId) {
        showToastAndDismissStatusView(new ToastUtils.ToastConfig.Builder()
                .setMsgResId(msgResId)
                .build());
    }

    /**
     * 显示信息并关闭加载对话框
     *
     * @param info 错误信息对象
     */
    protected void showToastAndDismissStatusView(@NonNull ErrorInfo info) {
        showToastAndDismissStatusView(new ToastUtils.ToastConfig.Builder()
                .setMsg(info.getErrorMsg())
                .setMsgResId(info.getErrorMsgResId())
                .build());
    }

    /**
     * 显示信息并关闭加载对话框
     *
     * @param config 配置信息对象
     */
    protected void showToastAndDismissStatusView(@NonNull ToastUtils.ToastConfig config) {
        if (!checkViewRefIsNull()) {
            mViewRef.get().showToast(config);
            mViewRef.get().dismissStatusView();
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
}
