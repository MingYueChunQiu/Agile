package com.mingyuechunqiu.agile.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.mingyuechunqiu.agile.feature.helper.ui.key.IKeyEventDispatcher;
import com.mingyuechunqiu.agile.feature.helper.ui.key.IKeyEventReceiver;
import com.mingyuechunqiu.agile.feature.helper.ui.key.KeyEventDispatcherHelper;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewConfigure;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;
import com.mingyuechunqiu.agile.feature.statusview.function.StatusViewManagerProvider;
import com.mingyuechunqiu.agile.feature.statusview.ui.IStatusView;
import com.mingyuechunqiu.agile.frame.Agile;
import com.mingyuechunqiu.agile.frame.lifecycle.AgileLifecycle;
import com.mingyuechunqiu.agile.frame.ui.IAgileActivityPage;
import com.mingyuechunqiu.agile.framework.ui.IActivityInflateLayoutViewCreator;
import com.mingyuechunqiu.agile.util.ExitApplicationManager;
import com.mingyuechunqiu.agile.util.ToastUtils;

import org.jetbrains.annotations.NotNull;

import static com.mingyuechunqiu.agile.constants.CommonConstants.BUNDLE_RETURN_TO_PREVIOUS_PAGE;
import static com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants.TAG_AGILE_STATUS_VIEW;

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
public abstract class BaseActivity extends AppCompatActivity implements IAgileActivityPage {

    private IStatusViewManager mStatusViewManager;
    private final Object mStatusViewLock = new Object();//使用私有锁对象模式用于同步状态视图
    @Nullable
    private IKeyEventDispatcher mKeyEventDispatcher = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Agile.getLifecycleDispatcher().updateActivityLifecycleState(this, AgileLifecycle.State.ActivityState.CREATED);
        restoreAgileResource(savedInstanceState);
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
        dismissStatusView();
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
    public String addOnKeyEventListener(@NotNull String tag, @NotNull IKeyEventReceiver.OnKeyEventListener listener) {
        return getKeyEventDispatcher().addOnKeyEventListener(tag, listener);
    }

    @Override
    public boolean removeOnKeyEventListener(@NotNull String observerId) {
        return getKeyEventDispatcher().removeOnKeyEventListener(observerId);
    }

    @Override
    public boolean removeOnKeyEventListenersWithTag(@NotNull String tag) {
        return getKeyEventDispatcher().removeOnKeyEventListenersWithTag(tag);
    }

    @Override
    public boolean dispatchOnKeyEventListener(int keyCode, @Nullable KeyEvent event) {
        return getKeyEventDispatcher().dispatchOnKeyEventListener(keyCode, event);
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
     * 恢复意外销毁被保存的资源
     *
     * @param savedInstanceState 实例资源对象
     */
    protected void restoreAgileResource(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        //DialogFragment在界面意外销毁后会由系统重新创建
        IStatusView statusView = (IStatusView) getSupportFragmentManager().findFragmentByTag(TAG_AGILE_STATUS_VIEW);
        if (statusView != null) {
            getStatusViewManager().setStatusView(statusView);
        }
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
     * 设置状态栏为轻色调，避免白色字体被白色活动条遮挡
     */
    protected void setLightStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN必须加，否则手机状态栏会显示底层背景，内容颜色没有延伸
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    /**
     * 设置状态栏为深色调
     */
    protected void setDarkStatusBar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_VISIBLE);
    }

    /**
     * 显示信息
     *
     * @param msg 文本
     */
    protected void showToast(@Nullable String msg) {
        ToastUtils.showToast(this, msg);
    }

    /**
     * 根据资源id显示信息
     *
     * @param msgResId 文本资源id
     */
    protected void showToast(@StringRes int msgResId) {
        ToastUtils.showToast(this, msgResId);
    }

    /**
     * 根据资源ID显示信息
     *
     * @param config 配置信息对象
     */
    protected void showToast(@NonNull ToastUtils.ToastConfig config) {
        ToastUtils.showToast(this, config);
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
                getSupportFragmentManager(), option);
    }

    /**
     * 显示加载状态视图
     *
     * @param containerId 状态视图添加布局ID
     */
    protected void showLoadingStatusView(@IdRes int containerId) {
        showStatusView(StatusViewConstants.StatusType.TYPE_LOADING, getSupportFragmentManager(),
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

    @NonNull
    private IKeyEventDispatcher getKeyEventDispatcher() {
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
