package com.mingyuechunqiu.agile.ui.activity;

import static com.mingyuechunqiu.agile.constants.CommonConstants.BUNDLE_RETURN_TO_PREVIOUS_PAGE;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.mingyuechunqiu.agile.data.bean.ErrorInfo;
import com.mingyuechunqiu.agile.feature.helper.ui.hint.IPopHintOwner;
import com.mingyuechunqiu.agile.feature.helper.ui.hint.ToastHelper;
import com.mingyuechunqiu.agile.feature.helper.ui.insets.IWindowInsetsHelperOwner;
import com.mingyuechunqiu.agile.feature.helper.ui.insets.WindowInsetsHelper;
import com.mingyuechunqiu.agile.feature.helper.ui.key.IKeyEventDispatcherHelper;
import com.mingyuechunqiu.agile.feature.helper.ui.key.IKeyEventReceiver;
import com.mingyuechunqiu.agile.feature.helper.ui.key.KeyEventDispatcherHelper;
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.ITransferPageDataDispatcherHelper;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewConfigure;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.feature.statusview.framework.IStatusViewOwner;
import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;
import com.mingyuechunqiu.agile.feature.statusview.function.StatusViewManagerProvider;
import com.mingyuechunqiu.agile.frame.Agile;
import com.mingyuechunqiu.agile.frame.lifecycle.AgileLifecycle;
import com.mingyuechunqiu.agile.frame.ui.activity.IAgileActivityPage;
import com.mingyuechunqiu.agile.framework.ui.IActivityInflateLayoutViewCreator;
import com.mingyuechunqiu.agile.util.ExitApplicationManager;

import org.jetbrains.annotations.NotNull;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/16
 *     desc   : 所有AppCompatActivity的基类
 *              继承自AppCompatActivity
 *     version: 1.0
 * </pre>
 */
public abstract class BaseActivity extends AppCompatActivity implements IAgileActivityPage, IWindowInsetsHelperOwner, IPopHintOwner, IStatusViewOwner {

    @Nullable
    private WindowInsetsHelper mWindowInsetsHelper;
    @Nullable
    private IStatusViewManager mStatusViewManager;
    @NonNull
    private final Object mStatusViewLock = new Object();//使用私有锁对象模式用于同步状态视图
    @Nullable
    private IKeyEventDispatcherHelper mKeyEventDispatcher = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Agile.getLifecycleDispatcher().updateActivityLifecycleState(this, AgileLifecycle.State.ActivityState.CREATED);
        initOnCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Agile.getLifecycleDispatcher().updateActivityLifecycleState(this, AgileLifecycle.State.ActivityState.STARTED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Agile.getLifecycleDispatcher().updateActivityLifecycleState(this, AgileLifecycle.State.ActivityState.RESUMED);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Agile.getLifecycleDispatcher().updateActivityLifecycleState(this, AgileLifecycle.State.ActivityState.PAUSED);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Agile.getLifecycleDispatcher().updateActivityLifecycleState(this, AgileLifecycle.State.ActivityState.STOPPED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Agile.getLifecycleDispatcher().updateActivityLifecycleState(this, AgileLifecycle.State.ActivityState.DESTROYED);
        release();
        mStatusViewManager = null;
        ExitApplicationManager.getInstance().removeActivity(this);
    }

    @NonNull
    @Override
    public String getPageTag() {
        return getClass().getSimpleName();
    }

    @NonNull
    @Override
    public WindowInsetsHelper getWindowInsetsHelper() {
        Window window = getWindow();
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

    @Override
    public void onReceiveTransferPageData(@NonNull ITransferPageDataDispatcherHelper.TransferPageDataOwner dataOwner, @Nullable ITransferPageDataDispatcherHelper.TransferPageData data) {
    }

    @NonNull
    @Override
    public String addOnKeyEventListener(@NonNull String tag, @NonNull IKeyEventReceiver.OnKeyEventListener listener) {
        return getKeyEventDispatcherHelper().addOnKeyEventListener(tag, listener);
    }

    @Override
    public boolean removeOnKeyEventListener(@NonNull String observerId) {
        return getKeyEventDispatcherHelper().removeOnKeyEventListener(observerId);
    }

    @Override
    public boolean removeOnKeyEventListenersWithTag(@NonNull String tag) {
        return getKeyEventDispatcherHelper().removeOnKeyEventListenersWithTag(tag);
    }

    @Override
    public boolean dispatchOnKeyEventListener(int keyCode, @Nullable KeyEvent event) {
        return getKeyEventDispatcherHelper().dispatchOnKeyEventListener(keyCode, event);
    }

    @NonNull
    @Override
    public IKeyEventDispatcherHelper getKeyEventDispatcherHelper() {
        if (mKeyEventDispatcher == null) {
            synchronized (this) {
                if (mKeyEventDispatcher == null) {
                    mKeyEventDispatcher = new KeyEventDispatcherHelper(this);
                }
            }
        }
        return mKeyEventDispatcher;
    }

    /**
     * 根据资源id显示文本
     *
     * @param msgResId 文本资源id
     */
    @Override
    public void showToast(@StringRes int msgResId) {
        ToastHelper.showToast(this, msgResId);
    }

    /**
     * 根据文本显示文本
     *
     * @param msg 文本
     */
    @Override
    public void showToast(@Nullable String msg) {
        ToastHelper.showToast(this, msg);
    }

    /**
     * 根据配置信息显示文本
     *
     * @param config 配置信息对象
     */
    @Override
    public void showToast(@NonNull ToastHelper.ToastConfig config) {
        ToastHelper.showToast(this, config);
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
        StatusViewConfigure configure = getStatusViewManager().getStatusViewConfigure();
        StatusViewOption option = configure == null ? null : configure.getLoadingOption();
        if (option == null) {
            option = StatusViewManagerProvider.getGlobalStatusViewOptionByType(StatusViewConstants.StatusViewType.TYPE_LOADING);
        }
        option.getContentOption().setText(hint);
        option.setCancelWithOutside(cancelable);
        getStatusViewManager().showStatusView(StatusViewConstants.StatusViewType.TYPE_LOADING, (ViewGroup) getWindow().getDecorView(), option);
    }

    /**
     * 显示加载状态视图
     *
     * @param containerId 状态视图添加布局ID
     */
    @Override
    public void showLoadingStatusView(@IdRes int containerId) {
        getStatusViewManager().showStatusView(StatusViewConstants.StatusViewType.TYPE_LOADING, findViewById(containerId), null);
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
    @Override
    @NonNull
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
     * 初始化填充布局视图
     *
     * @param savedInstanceState 状态存储实例
     */
    protected void initInflateLayoutView(@Nullable Bundle savedInstanceState) {
        IActivityInflateLayoutViewCreator creator = generateInflateLayoutViewCreator();
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
     * 在创建时执行初始化操作
     *
     * @param savedInstanceState 界面销毁时保存的状态数据实例
     */
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {
        initInflateLayoutView(savedInstanceState);
        initView(savedInstanceState);
        ExitApplicationManager.getInstance().addActivity(this);
    }

    /**
     * 初始化状态视图管理器
     *
     * @param manager 刚创建好的状态视图
     */
    protected void onInitStatusViewManager(@NonNull IStatusViewManager manager) {
    }

    /**
     * 检查是否是返回上一页数据包
     *
     * @param bundle 数据包
     * @return 如果是返回true，否则返回false
     */
    protected boolean checkIsReturnToPreviousPageBundle(@Nullable Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        return bundle.getBoolean(BUNDLE_RETURN_TO_PREVIOUS_PAGE);
    }

    /**
     * 弹出添加的界面
     *
     * @return 成功弹出返回true，否则返回false
     */
    protected boolean popAddedPage() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return true;
        }
        return false;
    }

    /**
     * 获取填充布局视图创建者
     *
     * @return 返回创建者对象，非空
     */
    @NonNull
    protected abstract IActivityInflateLayoutViewCreator generateInflateLayoutViewCreator();

    /**
     * 释放资源
     */
    protected abstract void release();

    /**
     * 由子类重写控件的初始化方法
     *
     * @param savedInstanceState 界面销毁时保存的状态数据实例
     */
    protected abstract void initView(@Nullable Bundle savedInstanceState);
}
