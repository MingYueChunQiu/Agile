package com.mingyuechunqiu.agile.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.IdRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;

import com.mingyuechunqiu.agile.data.bean.ErrorInfo;
import com.mingyuechunqiu.agile.feature.helper.ui.hint.IPopHintOwner;
import com.mingyuechunqiu.agile.feature.helper.ui.hint.ToastHelper;
import com.mingyuechunqiu.agile.feature.helper.ui.insets.IWindowInsetsHelperOwner;
import com.mingyuechunqiu.agile.feature.helper.ui.insets.WindowInsetsHelper;
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.ITransferPageDataDispatcherHelper;
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.TransferPageDataDispatcherHelper;
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewConfigure;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.feature.statusview.framework.IStatusViewOwner;
import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;
import com.mingyuechunqiu.agile.feature.statusview.function.StatusViewManagerProvider;
import com.mingyuechunqiu.agile.frame.Agile;
import com.mingyuechunqiu.agile.frame.lifecycle.AgileLifecycle;
import com.mingyuechunqiu.agile.frame.ui.dialog.IAgileDialogPage;
import com.mingyuechunqiu.agile.framework.ui.IDialogInflateLayoutViewCreator;
import com.mingyuechunqiu.agile.framework.ui.WindowHandler;
import com.mingyuechunqiu.agile.framework.ui.dialog.DialogLifecycleOwner;

import org.jetbrains.annotations.NotNull;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020/4/7 9:10 PM
 *      Desc:       所有对话框基类
 *                  继承自AppCompatDialog
 *      Version:    1.0
 * </pre>
 */
public abstract class BaseDialog extends AppCompatDialog implements IAgileDialogPage, IWindowInsetsHelperOwner, IPopHintOwner, IStatusViewOwner {

    @Nullable
    private WindowInsetsHelper mWindowInsetsHelper;
    @Nullable
    private IStatusViewManager mStatusViewManager;
    private final Object mStatusViewLock = new Object();//使用私有锁对象模式用于同步状态视图
    @Nullable
    private ITransferPageDataDispatcherHelper mTransferPageDataHelper;
    @Nullable
    private final DialogLifecycleOwner mDialogLifecycleOwner;

    public BaseDialog(Context context) {
        super(context);
        mDialogLifecycleOwner = new DialogLifecycleOwner();
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        mDialogLifecycleOwner = new DialogLifecycleOwner();
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mDialogLifecycleOwner = new DialogLifecycleOwner();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDialogLifecycleOwner().handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        Agile.getLifecycleDispatcher().updateDialogLifecycleState(this, AgileLifecycle.State.DialogState.CREATED);
        initOnCreate(savedInstanceState);
        setOnDismissListener(dialog -> releaseOnDetach());
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDialogLifecycleOwner().handleLifecycleEvent(Lifecycle.Event.ON_START);
        Agile.getLifecycleDispatcher().updateDialogLifecycleState(this, AgileLifecycle.State.DialogState.STARTED);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDialogLifecycleOwner().handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        Agile.getLifecycleDispatcher().updateDialogLifecycleState(this, AgileLifecycle.State.DialogState.STOPPED);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return getDialogLifecycleOwner().getLifecycle();
    }

    @NotNull
    @Override
    public String getPageTag() {
        return getClass().getSimpleName();
    }

    @Nullable
    @Override
    public FragmentActivity getOwnedActivity() {
        Activity activity = getOwnerActivity();
        if (activity instanceof FragmentActivity) {
            return (FragmentActivity) activity;
        }
        return null;
    }

    @Nullable
    @Override
    public Fragment getOwnedParentFragment() {
        return null;
    }

    @Nullable
    @Override
    public Fragment getOwnedTargetFragment() {
        return null;
    }

    @NonNull
    @Override
    public WindowInsetsHelper getWindowInsetsHelper() {
        FragmentActivity activity = getOwnedActivity();
        if (activity == null) {
            throw new IllegalStateException("GetOwnedActivity() must not be null!");
        }
        Window window = activity.getWindow();
        if (window == null) {
            throw new IllegalStateException("Window must not be null!");
        }
        if (mWindowInsetsHelper == null) {
            synchronized (this) {
                if (mWindowInsetsHelper == null) {
                    mWindowInsetsHelper = new WindowInsetsHelper(window);
                }
            }
        }
        return mWindowInsetsHelper;
    }

    /**
     * 向指定界面传递数据
     *
     * @param targetPage 目标界面
     * @param data       传递的数据
     * @return 传递成功返回true，否则返回false
     */
    @Override
    public boolean transferDataToPage(@NonNull TransferPageDataCallback targetPage, @Nullable TransferPageData data) {
        return getTransferPageDataHelper().transferDataToPage(targetPage, data);
    }

    /**
     * 向Activity传递数据
     *
     * @param data 传递的数据
     * @return 传递成功返回true，否则返回false
     */
    @Override
    public boolean transferDataToActivity(@Nullable TransferPageData data) {
        return getTransferPageDataHelper().transferDataToActivity(data);
    }

    /**
     * 向父Fragment传递数据
     *
     * @param data 传递的数据
     * @return 传递成功返回true，否则返回false
     */
    @Override
    public boolean transferDataToParentFragment(@Nullable TransferPageData data) {
        return getTransferPageDataHelper().transferDataToParentFragment(data);
    }

    /**
     * 向目标Fragment传递数据
     *
     * @param data 传递的数据
     * @return 传递成功返回true，否则返回false
     */
    @Override
    public boolean transferDataToTargetFragment(@Nullable TransferPageData data) {
        return getTransferPageDataHelper().transferDataToTargetFragment(data);
    }

    /**
     * 返回上一个界面
     *
     * @param interceptor 跳转参数拦截设置器
     * @return 如果进行调用则返回true，否则返回false
     */
    @Override
    public boolean returnToPreviousPageWithActivity(@Nullable TransferPageDataInterceptor interceptor) {
        return getTransferPageDataHelper().returnToPreviousPageWithActivity(interceptor);
    }

    /**
     * 返回上一个界面
     *
     * @param interceptor 跳转参数拦截设置器
     * @return 如果进行调用则返回true，否则返回false
     */
    @Override
    public boolean returnToPreviousPageWithParentFragment(@Nullable TransferPageDataInterceptor interceptor) {
        return getTransferPageDataHelper().returnToPreviousPageWithParentFragment(interceptor);
    }

    /**
     * 返回上一个界面
     *
     * @param interceptor 跳转参数拦截设置器
     * @return 如果进行调用则返回true，否则返回false
     */
    @Override
    public boolean returnToPreviousPageWithTargetFragment(@Nullable TransferPageDataInterceptor interceptor) {
        return getTransferPageDataHelper().returnToPreviousPageWithTargetFragment(interceptor);
    }

    @NonNull
    @Override
    public ITransferPageDataDispatcherHelper getTransferPageDataHelper() {
        if (mTransferPageDataHelper == null) {
            synchronized (this) {
                if (mTransferPageDataHelper == null) {
                    mTransferPageDataHelper = new TransferPageDataDispatcherHelper(this);
                }
            }
        }
        return mTransferPageDataHelper;
    }

    @MainThread
    @NonNull
    @Override
    public DialogLifecycleOwner getDialogLifecycleOwner() {
        if (mDialogLifecycleOwner == null) {
            throw new IllegalStateException("Can't access the Dialog's LifecycleOwner when "
                    + "getView() is null i.e., before onCreate() or after onDismiss()");
        }
        return mDialogLifecycleOwner;
    }

    /**
     * 根据资源id显示文本
     *
     * @param msgResId 文本资源id
     */
    @Override
    public void showToast(@StringRes int msgResId) {
        ToastHelper.showToast(getContext(), msgResId);
    }

    /**
     * 显示文本
     *
     * @param msg 文本
     */
    @Override
    public void showToast(@Nullable String msg) {
        ToastHelper.showToast(getContext(), msg);
    }

    /**
     * 根据资源ID显示文本
     *
     * @param config 配置信息对象
     */
    @Override
    public void showToast(@NonNull ToastHelper.ToastConfig config) {
        ToastHelper.showToast(getContext(), config);
    }

    /**
     * 根据错误信息显示文本
     *
     * @param info 错误信息对象
     */
    @Override
    public void showToast(@NotNull ErrorInfo info) {
        showToast(new ToastHelper.ToastConfig.Builder()
                .setMsg(info.getErrorMsg())
                .setMsgResId(info.getErrorMsgResId())
                .build());
    }

    /**
     * 显示加载对话框
     *
     * @param hint       提示文本
     * @param cancelable 是否可以取消
     */
    @Override
    public void showLoadingStatusView(@Nullable String hint, boolean cancelable) {
        Window window = getWindow();
        if (window == null) {
            LogManagerProvider.e("BaseFragment", "showLoadingStatusView: window == null");
            return;
        }
        StatusViewConfigure configure = getStatusViewManager().getStatusViewConfigure();
        StatusViewOption option = configure == null ? null : configure.getLoadingOption();
        if (option == null) {
            option = StatusViewManagerProvider.getGlobalStatusViewOptionByType(StatusViewConstants.StatusViewType.TYPE_LOADING);
        }
        option.getContentOption().setText(hint);
        option.setCancelWithOutside(cancelable);
        getStatusViewManager().showStatusView(StatusViewConstants.StatusViewType.TYPE_LOADING, (ViewGroup) window.getDecorView(), option);
    }

    /**
     * 显示加载状态视图
     *
     * @param containerId 状态视图添加布局ID
     */
    @Override
    public void showLoadingStatusView(@IdRes int containerId) {
        getStatusViewManager().showStatusView(StatusViewConstants.StatusViewType.TYPE_LOADING, getWindow().findViewById(containerId), null);
    }

    /**
     * 关闭状态视图
     */
    @Override
    public void dismissStatusView() {
        if (mStatusViewManager == null) {
            return;
        }
        getStatusViewManager().dismissStatusView();
    }

    /**
     * 获取获取状态视图管理器实例（线程安全）
     *
     * @return 返回获取状态视图管理器实例
     */
    @NonNull
    @Override
    public IStatusViewManager getStatusViewManager() {
        if (mStatusViewManager == null) {
            synchronized (mStatusViewLock) {
                if (mStatusViewManager == null) {
                    mStatusViewManager = StatusViewManagerProvider.newInstance(this);
                    onInitStatusViewManager(mStatusViewManager);
                }
            }
        }
        return mStatusViewManager;
    }

    /**
     * 在创建时执行初始化操作
     *
     * @param savedInstanceState 界面销毁时保存的状态数据实例
     */
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {
        initDialogBackground();
        initInflateLayoutView(savedInstanceState);
        initView(savedInstanceState);
    }

    protected void releaseOnDetach() {
        getDialogLifecycleOwner().handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        Agile.getLifecycleDispatcher().updateDialogLifecycleState(this, AgileLifecycle.State.DialogState.DISMISSED);
        mStatusViewManager = null;
        release();
    }

    /**
     * 初始化填充布局视图
     *
     * @param savedInstanceState 状态存储实例
     */
    protected void initInflateLayoutView(@Nullable Bundle savedInstanceState) {
        IDialogInflateLayoutViewCreator creator = generateInflateLayoutViewCreator();
        int id = creator.getInflateLayoutId();
        if (id != 0) {
            setContentView(id);
            return;
        }
        View view = creator.getInflateLayoutView();
        if (view != null) {
            setContentView(view);
            return;
        }
        throw new IllegalStateException("initInflateLayoutView must be set inflateLayoutId or inflateLayoutView");
    }

    /**
     * 设置对话框窗口
     *
     * @param handler 处理器
     */
    protected void setDialogWindow(@NonNull WindowHandler handler) {
        Window window = getWindow();
        if (window != null) {
            handler.onHandle(window);
        }
    }

    /**
     * 初始化对话框背景，去除默认背景
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
     * 初始化状态视图管理器
     *
     * @param manager 刚创建好的状态视图
     */
    protected void onInitStatusViewManager(@NonNull IStatusViewManager manager) {
    }

    /**
     * 获取填充布局视图创建者
     *
     * @return 返回创建者对象，非空
     */
    @NonNull
    protected abstract IDialogInflateLayoutViewCreator generateInflateLayoutViewCreator();

    /**
     * 由子类重写控件的初始化方法
     *
     * @param savedInstanceState 界面销毁时保存的状态数据实例
     */
    protected abstract void initView(@Nullable Bundle savedInstanceState);

    /**
     * 释放资源
     */
    protected abstract void release();
}
