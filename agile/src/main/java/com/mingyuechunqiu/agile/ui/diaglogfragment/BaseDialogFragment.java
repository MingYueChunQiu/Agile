package com.mingyuechunqiu.agile.ui.diaglogfragment;

import static com.mingyuechunqiu.agile.frame.ui.AgilePagesKt.createPageTag;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.mingyuechunqiu.agile.data.bean.ErrorInfo;
import com.mingyuechunqiu.agile.feature.helper.ui.hint.IPopHintOwner;
import com.mingyuechunqiu.agile.feature.helper.ui.hint.ToastHelper;
import com.mingyuechunqiu.agile.feature.helper.ui.insets.IWindowInsetsHelperOwner;
import com.mingyuechunqiu.agile.feature.helper.ui.insets.WindowInsetsHelper;
import com.mingyuechunqiu.agile.feature.helper.ui.key.dispatcher.IKeyEventDispatcherPage;
import com.mingyuechunqiu.agile.feature.helper.ui.key.receiver.IKeyEventReceiverHelper;
import com.mingyuechunqiu.agile.feature.helper.ui.key.receiver.KeyEventReceiverHelper;
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.dispatcher.ITransferPageDataDispatcherHelper;
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.dispatcher.TransferPageDataDispatcherHelper;
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.receiver.ITransferPageDataReceiverHelper;
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.receiver.TransferPageDataReceiverHelper;
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewConfigure;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.feature.statusview.framework.IStatusViewOwner;
import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;
import com.mingyuechunqiu.agile.feature.statusview.function.StatusViewManagerProvider;
import com.mingyuechunqiu.agile.frame.Agile;
import com.mingyuechunqiu.agile.frame.lifecycle.AgileLifecycle;
import com.mingyuechunqiu.agile.frame.ui.fragment.FragmentViewPage;
import com.mingyuechunqiu.agile.frame.ui.fragment.IAgileFragmentPage;
import com.mingyuechunqiu.agile.frame.ui.fragment.IAgileFragmentViewPage;
import com.mingyuechunqiu.agile.framework.ui.IFragmentInflateLayoutViewCreator;
import com.mingyuechunqiu.agile.framework.ui.WindowHandler;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/06/14
 *     desc   : 所有BaseDialogFragment的基类
 *              继承自AppCompatDialogFragment
 *     version: 1.0
 * </pre>
 */
public abstract class BaseDialogFragment extends AppCompatDialogFragment implements IAgileFragmentPage, IWindowInsetsHelperOwner, IPopHintOwner, IStatusViewOwner {

    @NonNull
    private final String mTag = getClass().getSimpleName();
    @Nullable
    private IAgileFragmentViewPage mFragmentViewPage;//代表包含的视图界面
    @Nullable
    private WindowInsetsHelper mWindowInsetsHelper;//处理系统窗口辅助类
    @Nullable
    private IStatusViewManager mStatusViewManager;//处理应用层各类状态视图管理器
    @Nullable
    private ITransferPageDataDispatcherHelper mTransferPageDataDispatcherHelper;//处理跨页面数据分发辅助类
    @Nullable
    private ITransferPageDataReceiverHelper mTransferPageDataReceiverHelper;//处理跨页面数据接受辅助类
    @Nullable
    private IKeyEventReceiverHelper mKeyEventReceiverHelper;//处理页面按键接受辅助类
    @NonNull
    private final Lock mWindowInsetsHelperLock = new ReentrantLock();
    @NonNull
    private final Lock mStatusViewManagerLock = new ReentrantLock();
    @NonNull
    private final Lock mTransferPageDataDispatcherHelperLock = new ReentrantLock();
    @NonNull
    private final Lock mTransferPageDataReceiverHelperLock = new ReentrantLock();
    @NonNull
    private final Lock mKeyEventReceiverHelperLock = new ReentrantLock();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Agile.getLifecycleDispatcher().updateDialogFragmentLifecycleState(this, AgileLifecycle.State.DialogFragmentState.ATTACHED);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Agile.getLifecycleDispatcher().updateDialogFragmentLifecycleState(this, AgileLifecycle.State.DialogFragmentState.CREATED);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Agile.getLifecycleDispatcher().updateDialogFragmentLifecycleState(this, AgileLifecycle.State.DialogFragmentState.CREATED_VIEW);
        initDialogBackground();
        View view = initInflateLayoutView(inflater, container, savedInstanceState);
        mFragmentViewPage = new FragmentViewPage(getViewLifecycleOwner(), getPageTag(), view == null);
        if (view == null) {
            initOnData(savedInstanceState);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initOnViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Agile.getLifecycleDispatcher().updateDialogFragmentLifecycleState(this, AgileLifecycle.State.DialogFragmentState.ACTIVITY_CREATED);
    }

    @Override
    public void onStart() {
        super.onStart();
        Agile.getLifecycleDispatcher().updateDialogFragmentLifecycleState(this, AgileLifecycle.State.DialogFragmentState.STARTED);
    }

    @Override
    public void onResume() {
        super.onResume();
        Agile.getLifecycleDispatcher().updateDialogFragmentLifecycleState(this, AgileLifecycle.State.DialogFragmentState.RESUMED);
    }

    @Override
    public void onPause() {
        super.onPause();
        Agile.getLifecycleDispatcher().updateDialogFragmentLifecycleState(this, AgileLifecycle.State.DialogFragmentState.PAUSED);
    }

    @Override
    public void onStop() {
        super.onStop();
        Agile.getLifecycleDispatcher().updateDialogFragmentLifecycleState(this, AgileLifecycle.State.DialogFragmentState.STOPPED);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Agile.getLifecycleDispatcher().updateDialogFragmentLifecycleState(this, AgileLifecycle.State.DialogFragmentState.DESTROYED_VIEW);
        releaseOnDestroyView();
        mStatusViewManager = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Agile.getLifecycleDispatcher().updateDialogFragmentLifecycleState(this, AgileLifecycle.State.DialogFragmentState.DESTROYED);
        releaseOnDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Agile.getLifecycleDispatcher().updateDialogFragmentLifecycleState(this, AgileLifecycle.State.DialogFragmentState.DETACHED);
    }

    @NonNull
    @Override
    public String getPageTag() {
        return createPageTag(this);
    }

    @NonNull
    @Override
    public IAgileFragmentViewPage getFragmentViewPage() {
        return Objects.requireNonNull(mFragmentViewPage, "getFragmentViewPage() must call after onCreateView()");
    }

    @Nullable
    @Override
    public FragmentActivity getOwnedActivity() {
        return getActivity();
    }

    @Nullable
    @Override
    public Fragment getOwnedParentFragment() {
        return getParentFragment();
    }

    @Nullable
    @Override
    public Fragment getOwnedTargetFragment() {
        return getTargetFragment();
    }

    @NonNull
    @Override
    public WindowInsetsHelper getWindowInsetsHelper() {
        Dialog dialog = getDialog();
        if (dialog == null) {
            throw new IllegalStateException("GetDialog() must not be null!");
        }
        Window window = dialog.getWindow();
        if (window == null) {
            throw new IllegalStateException("Window must not be null!");
        }
        if (mWindowInsetsHelper == null) {
            mWindowInsetsHelperLock.lock();
            try {
                if (mWindowInsetsHelper == null) {
                    mWindowInsetsHelper = new WindowInsetsHelper(window);
                }
            } catch (Exception e) {
                LogManagerProvider.e(mTag, "getWindowInsetsHelper error: " + e.getMessage());
            } finally {
                mWindowInsetsHelperLock.unlock();
            }
        }
        return mWindowInsetsHelper;
    }

    @NonNull
    @Override
    public ITransferPageDataDispatcherHelper getTransferPageDataDispatcherHelper() {
        if (mTransferPageDataDispatcherHelper == null) {
            mTransferPageDataDispatcherHelperLock.lock();
            try {
                if (mTransferPageDataDispatcherHelper == null) {
                    mTransferPageDataDispatcherHelper = new TransferPageDataDispatcherHelper(this);
                }
            } catch (Exception e) {
                LogManagerProvider.e(mTag, "getTransferPageDataDispatcherHelper error: " + e.getMessage());
            } finally {
                mTransferPageDataDispatcherHelperLock.unlock();
            }
        }
        return mTransferPageDataDispatcherHelper;
    }

    @NonNull
    @Override
    public ITransferPageDataReceiverHelper getTransferPageDataReceiverHelper() {
        if (mTransferPageDataReceiverHelper == null) {
            mTransferPageDataReceiverHelperLock.lock();
            try {
                if (mTransferPageDataReceiverHelper == null) {
                    mTransferPageDataReceiverHelper = new TransferPageDataReceiverHelper(this);
                }
            } catch (Exception e) {
                LogManagerProvider.e(mTag, "getTransferPageDataReceiverHelper error: " + e.getMessage());
            } finally {
                mTransferPageDataReceiverHelperLock.unlock();
            }
        }
        return mTransferPageDataReceiverHelper;
    }

    @NonNull
    @Override
    public IKeyEventReceiverHelper getKeyEventReceiverHelper() {
        if (mKeyEventReceiverHelper == null) {
            mKeyEventReceiverHelperLock.lock();
            try {
                if (mKeyEventReceiverHelper == null) {
                    mKeyEventReceiverHelper = new KeyEventReceiverHelper(this);
                }
            } catch (Exception e) {
                LogManagerProvider.e(mTag, "getKeyEventReceiverHelper error: " + e.getMessage());
            } finally {
                mKeyEventReceiverHelperLock.unlock();
            }
        }
        return mKeyEventReceiverHelper;
    }

    @Nullable
    @Override
    public IKeyEventDispatcherPage getBelongToKeyEventDispatcherPage() {
        FragmentActivity activity = getActivity();
        if (activity instanceof IKeyEventDispatcherPage) {
            return (IKeyEventDispatcherPage) activity;
        }
        return null;
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
     * 根据资源ID显示文本
     *
     * @param config 配置信息对象
     */
    @Override
    public void showToast(@NonNull ToastHelper.ToastConfig config) {
        ToastHelper.showToast(getContext(), config);
    }

    /**
     * 显示加载对话框
     *
     * @param hint       提示文本
     * @param cancelable 是否可以取消
     */
    @Override
    public void showLoadingStatusView(@Nullable String hint, boolean cancelable) {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            LogManagerProvider.e("BaseFragment", "showLoadingStatusView: activity == null");
            return;
        }
        StatusViewConfigure configure = getStatusViewManager().getStatusViewConfigure();
        StatusViewOption option = configure == null ? null : configure.getLoadingOption();
        if (option == null) {
            option = StatusViewManagerProvider.getGlobalStatusViewOptionByType(StatusViewConstants.StatusViewType.TYPE_LOADING);
        }
        option.getContentOption().setText(hint);
        option.setCancelWithOutside(cancelable);
        getStatusViewManager().showStatusView(StatusViewConstants.StatusViewType.TYPE_LOADING, (ViewGroup) activity.getWindow().getDecorView(), option);
    }

    /**
     * 显示加载状态视图
     *
     * @param containerId 状态视图添加布局ID
     */
    @Override
    public void showLoadingStatusView(@IdRes int containerId) {
        View view = getView();
        if (view == null) {
            return;
        }
        getStatusViewManager().showStatusView(StatusViewConstants.StatusViewType.TYPE_LOADING, getView().findViewById(containerId), null);
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
            mStatusViewManagerLock.lock();
            try {
                if (mStatusViewManager == null) {
                    mStatusViewManager = StatusViewManagerProvider.newInstance(getFragmentViewPage());
                    onInitStatusViewManager(mStatusViewManager);
                }
            } catch (Exception e) {
                LogManagerProvider.e(mTag, "getStatusViewManager error: " + e.getMessage());
            } finally {
                mStatusViewManagerLock.unlock();
            }
        }
        return mStatusViewManager;
    }

    /**
     * 设置对话框消失监听器
     *
     * @param listener 监听器
     */
    public void setOnDismissListener(@NonNull DialogInterface.OnDismissListener listener) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setOnDismissListener(listener);
        }
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
        if (isAdded() || isStateSaved()) {
            return;
        }
        show(manager, tag);
    }

    /**
     * 在视图创建成功时执行初始化操作
     *
     * @param savedInstanceState 界面销毁时保存的状态数据实例
     */
    protected void initOnViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initOnView(view, savedInstanceState);
        initOnData(savedInstanceState);
    }

    protected void initOnView(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view, savedInstanceState);
    }

    protected void initOnData(@Nullable Bundle savedInstanceState) {
        initData(savedInstanceState);
    }

    /**
     * 初始化填充布局视图
     *
     * @param inflater           布局填充器
     * @param container          父布局
     * @param savedInstanceState 状态存储实例
     * @return 返回填充视图
     */
    @Nullable
    protected View initInflateLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        IFragmentInflateLayoutViewCreator creator = generateInflateLayoutViewCreator();
        if (creator == null) {
            return null;
        }
        int id = creator.getInflateLayoutId();
        if (id != 0) {
            return inflater.inflate(id, container, false);
        }
        return creator.getInflateLayoutView(inflater, container);
    }

    /**
     * 初始化对话框背景，去除默认背景
     */
    protected void initDialogBackground() {
        setDialogWindow(window -> {
            //去掉对话框的背景，以便设置自已样式的背景
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
     * 初始化状态视图管理器
     *
     * @param manager 刚创建好的状态视图
     */
    protected void onInitStatusViewManager(@NonNull IStatusViewManager manager) {
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
     * 获取填充布局视图创建者
     *
     * @return 返回创建者对象，非空
     */
    @Nullable
    protected abstract IFragmentInflateLayoutViewCreator generateInflateLayoutViewCreator();

    /**
     * 由子类重写控件的初始化方法，只有当view存在时才会被调用
     *
     * @param view               界面父容器View
     * @param savedInstanceState 界面销毁时保存的状态数据实例
     */
    protected abstract void initView(@NonNull View view, @Nullable Bundle savedInstanceState);

    /**
     * 由子类重写初始化数据方法，不管view是否存在，都会调用，如果view存在，调用时机将在initView之后
     *
     * @param savedInstanceState 界面销毁时保存的状态数据实例
     */
    protected abstract void initData(@Nullable Bundle savedInstanceState);

    /**
     * 释放资源（在onDestroyView时调用）
     */
    protected abstract void releaseOnDestroyView();

    /**
     * 释放资源（在onDestroy时调用）
     */
    protected abstract void releaseOnDestroy();
}
