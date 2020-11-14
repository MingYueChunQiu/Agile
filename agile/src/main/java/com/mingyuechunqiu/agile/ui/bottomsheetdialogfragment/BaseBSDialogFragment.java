package com.mingyuechunqiu.agile.ui.bottomsheetdialogfragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewConfigure;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;
import com.mingyuechunqiu.agile.feature.statusview.function.StatusViewManagerProvider;
import com.mingyuechunqiu.agile.feature.statusview.ui.IStatusView;
import com.mingyuechunqiu.agile.frame.Agile;
import com.mingyuechunqiu.agile.frame.lifecycle.AgileLifecycle;
import com.mingyuechunqiu.agile.framework.function.TransferDataCallback;
import com.mingyuechunqiu.agile.framework.ui.OnKeyEventListener;
import com.mingyuechunqiu.agile.framework.ui.WindowHandler;
import com.mingyuechunqiu.agile.ui.activity.BaseActivity;
import com.mingyuechunqiu.agile.util.ToastUtils;

import static com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants.TAG_AGILE_STATUS_VIEW;

/**
 * <pre>
 *     author : 明月春秋
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/10/21
 *     desc   : 所有底部对话框碎片的基类
 *              继承自BottomSheetDialogFragment
 *     version: 1.0
 * </pre>
 */
public abstract class BaseBSDialogFragment extends BottomSheetDialogFragment {

    private IStatusViewManager mStatusViewManager;
    private final Object mStatusViewLock = new Object();//使用私有锁对象模式用于同步状态视图

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Agile.getLifecycleDispatcher().updateBottomSheetDialogFragmentLifecycleState(this, AgileLifecycle.State.BottomSheetDialogFragmentState.ATTACHED);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Agile.getLifecycleDispatcher().updateBottomSheetDialogFragmentLifecycleState(this, AgileLifecycle.State.BottomSheetDialogFragmentState.CREATED);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Agile.getLifecycleDispatcher().updateBottomSheetDialogFragmentLifecycleState(this, AgileLifecycle.State.BottomSheetDialogFragmentState.CREATED_VIEW);
        initDialogBackground();
        int id = getInflateLayoutId();
        if (id != 0) {
            return inflater.inflate(id, container, false);
        }
        return getInflateLayoutView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restoreAgileResource(savedInstanceState);
        initView(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Agile.getLifecycleDispatcher().updateBottomSheetDialogFragmentLifecycleState(this, AgileLifecycle.State.BottomSheetDialogFragmentState.ACTIVITY_CREATED);
    }

    @Override
    public void onStart() {
        super.onStart();
        Agile.getLifecycleDispatcher().updateBottomSheetDialogFragmentLifecycleState(this, AgileLifecycle.State.BottomSheetDialogFragmentState.STARTED);
    }

    @Override
    public void onResume() {
        super.onResume();
        Agile.getLifecycleDispatcher().updateBottomSheetDialogFragmentLifecycleState(this, AgileLifecycle.State.BottomSheetDialogFragmentState.RESUMED);
    }

    @Override
    public void onPause() {
        super.onPause();
        Agile.getLifecycleDispatcher().updateBottomSheetDialogFragmentLifecycleState(this, AgileLifecycle.State.BottomSheetDialogFragmentState.PAUSED);
    }

    @Override
    public void onStop() {
        super.onStop();
        Agile.getLifecycleDispatcher().updateBottomSheetDialogFragmentLifecycleState(this, AgileLifecycle.State.BottomSheetDialogFragmentState.STOPPED);
    }

    @Override
    public void onDestroyView() {
        removeAllOnKeyEventListeners();
        dismissStatusView();
        super.onDestroyView();
        Agile.getLifecycleDispatcher().updateBottomSheetDialogFragmentLifecycleState(this, AgileLifecycle.State.BottomSheetDialogFragmentState.DESTROYED_VIEW);
        releaseOnDestroyView();
        mStatusViewManager = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Agile.getLifecycleDispatcher().updateBottomSheetDialogFragmentLifecycleState(this, AgileLifecycle.State.BottomSheetDialogFragmentState.DESTROYED);
        releaseOnDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Agile.getLifecycleDispatcher().updateBottomSheetDialogFragmentLifecycleState(this, AgileLifecycle.State.BottomSheetDialogFragmentState.DETACHED);
    }

    /**
     * 安全显示对话框
     *
     * @param manager Fragment管理器
     * @param tag     Fragment标签
     */
    public void showSafely(@NonNull FragmentManager manager, @Nullable String tag) {
        if (manager.findFragmentByTag(tag) != null) {
            return;
        }
        if (isAdded()) {
            return;
        }
        show(manager, tag);
    }

    /**
     * 恢复意外销毁被保存的资源
     *
     * @param savedInstanceState 实例资源对象
     */
    protected void restoreAgileResource(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        //DialogFragment在界面意外销毁后会由系统重新创建
        IStatusView statusView = (IStatusView) getParentFragmentManager().findFragmentByTag(TAG_AGILE_STATUS_VIEW);
        if (statusView != null) {
            getStatusViewManager().setStatusView(statusView);
        }
    }

    /**
     * 设置对话框背景
     */
    protected void initDialogBackground() {
        setDialogWindow(new WindowHandler() {
            @Override
            public void onHandle(@NonNull Window window) {
                //去掉对话框的背景，以便设置自已样式的背景
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });
    }

    /**
     * 设置对话框窗口
     *
     * @param handler 处理器
     */
    protected void setDialogWindow(@NonNull WindowHandler handler) {
        Dialog dialog = getDialog();
        if (dialog == null) {
            return;
        }
        Window window = dialog.getWindow();
        if (window != null) {
            handler.onHandle(window);
        }
    }

    /**
     * 显示信息
     *
     * @param msg 文本
     */
    protected void showToast(@Nullable String msg) {
        ToastUtils.showToast(getContext(), msg);
    }

    /**
     * 根据资源id显示信息
     *
     * @param msgResId 文本资源id
     */
    protected void showToast(@StringRes int msgResId) {
        ToastUtils.showToast(getContext(), msgResId);
    }

    /**
     * 根据资源ID显示信息
     *
     * @param config 配置信息对象
     */
    protected void showToast(@NonNull ToastUtils.ToastConfig config) {
        ToastUtils.showToast(getContext(), config);
    }

    /**
     * 显示加载对话框
     *
     * @param hint       提示文本
     * @param cancelable 是否可以取消
     */
    protected void showLoadingStatusView(@Nullable String hint, boolean cancelable) {
        StatusViewConfigure configure = getStatusViewManager().getStatusViewConfigure();
        StatusViewOption option = configure == null ? null : configure.getLoadingOption();
        if (option == null) {
            option = StatusViewManagerProvider.getGlobalStatusViewOptionByType(StatusViewConstants.StatusType.TYPE_LOADING);
        }
        option.getContentOption().setText(hint);
        option.setCancelWithOutside(cancelable);
        showStatusView(StatusViewConstants.StatusType.TYPE_LOADING,
                getParentFragmentManager(), option);
    }

    /**
     * 显示加载状态视图
     *
     * @param containerId 状态视图添加布局ID
     */
    protected void showLoadingStatusView(@IdRes int containerId) {
        showStatusView(StatusViewConstants.StatusType.TYPE_LOADING, getChildFragmentManager(),
                containerId, null);
    }

    /**
     * 显示状态视图
     *
     * @param type    状态视图类型
     * @param manager Fragment管理器
     * @param option  状态视图配置信息类
     */
    protected void showStatusView(@NonNull StatusViewConstants.StatusType type,
                                  @Nullable FragmentManager manager,
                                  @Nullable StatusViewOption option) {
        if (manager == null) {
            return;
        }
        dismissStatusView();
        getStatusViewManager().showStatusView(type, manager, option);
    }

    /**
     * 显示状态视图
     *
     * @param type        状态视图类型
     * @param manager     Fragment管理器
     * @param containerId 状态视图添加布局ID
     * @param option      状态视图配置信息类
     */
    protected void showStatusView(@NonNull StatusViewConstants.StatusType type, @Nullable FragmentManager manager,
                                  @IdRes int containerId, @Nullable StatusViewOption option) {
        if (manager == null) {
            return;
        }
        dismissStatusView();
        getStatusViewManager().showStatusView(type, manager, containerId, option);
    }

    /**
     * 关闭状态视图
     */
    protected void dismissStatusView() {
        if (mStatusViewManager != null) {
            mStatusViewManager.dismissStatusView(true);
        }
        mStatusViewManager = null;
    }

    /**
     * 获取获取状态视图管理器实例（线程安全）
     *
     * @return 返回获取状态视图管理器实例
     */
    @NonNull
    protected IStatusViewManager getStatusViewManager() {
        if (mStatusViewManager == null) {
            synchronized (mStatusViewLock) {
                if (mStatusViewManager == null) {
                    mStatusViewManager = StatusViewManagerProvider.newInstance();
                    onInitStatusViewManager(mStatusViewManager);
                }
            }
        }
        return mStatusViewManager;
    }

    /**
     * 初始化状态视图管理器
     *
     * @param manager 刚创建好的状态视图
     */
    protected void onInitStatusViewManager(@NonNull IStatusViewManager manager) {
    }

    /**
     * 回调父类Fragment传递数据
     *
     * @param callback 传递数据回调
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean callParentFragment(@Nullable TransferDataCallback callback) {
        return callFragment(getParentFragment(), callback);
    }

    /**
     * 回调目标Fragment传递数据
     *
     * @param callback 传递数据回调
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean callTargetFragment(@Nullable TransferDataCallback callback) {
        return callFragment(getTargetFragment(), callback);
    }

    /**
     * 回调指定Fragment传递数据
     *
     * @param callbackFragment 被回调的fragment
     * @param callback         传递数据回调
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean callFragment(@Nullable Fragment callbackFragment, @Nullable TransferDataCallback callback) {
        return callFragment(this, callbackFragment, callback);
    }

    /**
     * 回调指定Fragment传递数据
     *
     * @param fragment         指定的Fragment
     * @param callbackFragment 被回调的fragment
     * @param callback         传递数据回调
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean callFragment(@Nullable BottomSheetDialogFragment fragment, @Nullable Fragment callbackFragment,
                                   @Nullable TransferDataCallback callback) {
        if (fragment == null) {
            return false;
        }
        if (callbackFragment instanceof Callback) {
            ((Callback) callbackFragment).onCall(fragment, callback == null ? null : callback.transferData());
            return true;
        }
        return false;
    }

    /**
     * 回调Activity传递数据
     *
     * @param callback 传递数据回调
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean callActivity(@Nullable TransferDataCallback callback) {
        return callActivity(this, callback);
    }

    /**
     * 回调Activity传递数据
     *
     * @param fragment 指定的Fragment
     * @param callback 传递数据回调
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean callActivity(@Nullable BottomSheetDialogFragment fragment, @Nullable TransferDataCallback callback) {
        if (fragment == null) {
            return false;
        }
        FragmentActivity activity = getActivity();
        if (activity instanceof Callback) {
            ((Callback) activity).onCall(fragment, callback == null ? null : callback.transferData());
            return true;
        }
        return false;
    }

    /**
     * 销毁Activity
     *
     * @return 如果成功销毁返回true，否则返回false
     */
    protected boolean finishActivity() {
        dismissAllowingStateLoss();
        Activity activity = getActivity();
        if (activity == null) {
            return false;
        }
        activity.finish();
        return true;
    }

    /**
     * 添加按键监听事件
     *
     * @param listener 按键监听器
     */
    protected void addOnKeyEventListenerToActivity(@NonNull OnKeyEventListener listener) {
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).addOnKeyEventListener(this, listener);
        }
    }

    /**
     * 获取填充布局View（当getInflateLayoutId返回为0时，会被调用）
     *
     * @return 返回View容器
     */
    @Nullable
    protected View getInflateLayoutView() {
        return null;
    }

    /**
     * 移除所有的按键监听器
     */
    private void removeAllOnKeyEventListeners() {
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).removeOnKeyEventListeners(this);
        }
    }

    /**
     * 获取填充布局资源ID
     *
     * @return 返回布局资源ID
     */
    @LayoutRes
    protected abstract int getInflateLayoutId();

    /**
     * 由子类重写控件的初始化方法
     *
     * @param view               界面父容器View
     * @param savedInstanceState 界面销毁时保存的状态数据实例
     */
    protected abstract void initView(@NonNull View view, @Nullable Bundle savedInstanceState);

    /**
     * 释放资源（在onDestroyView时调用）
     */
    protected abstract void releaseOnDestroyView();

    /**
     * 释放资源（在onDestroy时调用）
     */
    protected abstract void releaseOnDestroy();

    /**
     * 供Activity实现的回调接口，实现对DialogFragment的调用
     */
    public interface Callback {

        /**
         * 由Activity实现的回调方法
         *
         * @param fragment 回调的fragment
         * @param bundle   传递的参数值
         */
        void onCall(@NonNull BottomSheetDialogFragment fragment, @Nullable Bundle bundle);

    }
}
