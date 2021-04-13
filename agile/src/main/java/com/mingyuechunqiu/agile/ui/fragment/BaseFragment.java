
package com.mingyuechunqiu.agile.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.mingyuechunqiu.agile.feature.helper.ui.key.IKeyEventReceiver;
import com.mingyuechunqiu.agile.feature.helper.ui.key.IKeyEventReceiverHelper;
import com.mingyuechunqiu.agile.feature.helper.ui.key.KeyEventReceiverHelper;
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.ITransferPageDataHelper;
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.TransferPageDataHelper;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewConfigure;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;
import com.mingyuechunqiu.agile.feature.statusview.function.StatusViewManagerProvider;
import com.mingyuechunqiu.agile.feature.statusview.ui.IStatusView;
import com.mingyuechunqiu.agile.frame.Agile;
import com.mingyuechunqiu.agile.frame.lifecycle.AgileLifecycle;
import com.mingyuechunqiu.agile.frame.ui.IAgileFragmentPage;
import com.mingyuechunqiu.agile.framework.ui.IFragmentInflateLayoutViewCreator;
import com.mingyuechunqiu.agile.util.ToastUtils;

import org.jetbrains.annotations.NotNull;

import static com.mingyuechunqiu.agile.constants.CommonConstants.BUNDLE_RETURN_TO_PREVIOUS_PAGE;
import static com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants.TAG_AGILE_STATUS_VIEW;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/16
 *     desc   : 所有Fragment的基类
 *              继承自Fragment
 *              实现IAgileFragmentPage, ITransferDataPage, ITransferPageDataOwner, IKeyEventReceiver
 *     version: 1.0
 * </pre>
 */
public abstract class BaseFragment extends Fragment implements IAgileFragmentPage {

    private IStatusViewManager mStatusViewManager;
    private final Object mStatusViewLock = new Object();//使用私有锁对象模式用于同步状态视图
    @Nullable
    private ITransferPageDataHelper mTransferPageDataHelper;
    @Nullable
    private IKeyEventReceiver mKeyEventReceiverHelper;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Agile.getLifecycleDispatcher().updateFragmentLifecycleState(this, AgileLifecycle.State.FragmentState.ATTACHED);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Agile.getLifecycleDispatcher().updateFragmentLifecycleState(this, AgileLifecycle.State.FragmentState.CREATED);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Agile.getLifecycleDispatcher().updateFragmentLifecycleState(this, AgileLifecycle.State.FragmentState.CREATED_VIEW);
        return initInflateLayoutView(inflater, container, savedInstanceState);
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
        Agile.getLifecycleDispatcher().updateFragmentLifecycleState(this, AgileLifecycle.State.FragmentState.ACTIVITY_CREATED);
    }

    @Override
    public void onStart() {
        super.onStart();
        Agile.getLifecycleDispatcher().updateFragmentLifecycleState(this, AgileLifecycle.State.FragmentState.STARTED);
    }

    @Override
    public void onResume() {
        super.onResume();
        Agile.getLifecycleDispatcher().updateFragmentLifecycleState(this, AgileLifecycle.State.FragmentState.RESUMED);
    }

    @Override
    public void onPause() {
        super.onPause();
        Agile.getLifecycleDispatcher().updateFragmentLifecycleState(this, AgileLifecycle.State.FragmentState.PAUSED);
    }

    @Override
    public void onStop() {
        super.onStop();
        Agile.getLifecycleDispatcher().updateFragmentLifecycleState(this, AgileLifecycle.State.FragmentState.STOPPED);
    }

    @Override
    public void onDestroyView() {
        dismissStatusView();
        super.onDestroyView();
        Agile.getLifecycleDispatcher().updateFragmentLifecycleState(this, AgileLifecycle.State.FragmentState.DESTROYED_VIEW);
        releaseOnDestroyView();
        mStatusViewManager = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Agile.getLifecycleDispatcher().updateFragmentLifecycleState(this, AgileLifecycle.State.FragmentState.DESTROYED);
        releaseOnDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Agile.getLifecycleDispatcher().updateFragmentLifecycleState(this, AgileLifecycle.State.FragmentState.DETACHED);
    }

    @NonNull
    @Override
    public String getPageTag() {
        return getClass().getSimpleName();
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

    /**
     * 向Activity传递数据
     *
     * @param data 传递的数据
     * @return 传递成功返回true，否则返回false
     */
    @Override
    public boolean transferDataToActivity(@Nullable TransferPageDataHelper.TransferPageData data) {
        return getTransferPageDataHelper().transferDataToActivity(data);
    }

    /**
     * 向父Fragment传递数据
     *
     * @param data 传递的数据
     * @return 传递成功返回true，否则返回false
     */
    @Override
    public boolean transferDataToParentFragment(@Nullable TransferPageDataHelper.TransferPageData data) {
        return getTransferPageDataHelper().transferDataToParentFragment(data);
    }

    /**
     * 向目标Fragment传递数据
     *
     * @param data 传递的数据
     * @return 传递成功返回true，否则返回false
     */
    @Override
    public boolean transferDataToTargetFragment(@Nullable TransferPageDataHelper.TransferPageData data) {
        return getTransferPageDataHelper().transferDataToTargetFragment(data);
    }

    /**
     * 向指定界面传递数据
     *
     * @param targetPage 目标界面
     * @param data       传递的数据
     * @return 传递成功返回true，否则返回false
     */
    @Override
    public boolean transferDataToPage(@NonNull TransferPageDataHelper.TransferPageDataCallback targetPage, @Nullable TransferPageDataHelper.TransferPageData data) {
        return getTransferPageDataHelper().transferDataToPage(targetPage, data);
    }

    /**
     * 返回上一个界面
     *
     * @param interceptor 跳转参数拦截设置器
     * @return 如果进行调用则返回true，否则返回false
     */
    @Override
    public boolean returnToPreviousPageWithActivity(@Nullable TransferPageDataHelper.TransferPageDataInterceptor interceptor) {
        return getTransferPageDataHelper().returnToPreviousPageWithActivity(interceptor);
    }

    /**
     * 返回上一个界面
     *
     * @param interceptor 跳转参数拦截设置器
     * @return 如果进行调用则返回true，否则返回false
     */
    @Override
    public boolean returnToPreviousPageWithParentFragment(@Nullable TransferPageDataHelper.TransferPageDataInterceptor interceptor) {
        return getTransferPageDataHelper().returnToPreviousPageWithParentFragment(interceptor);
    }

    /**
     * 返回上一个界面
     *
     * @param interceptor 跳转参数拦截设置器
     * @return 如果进行调用则返回true，否则返回false
     */
    @Override
    public boolean returnToPreviousPageWithTargetFragment(@Nullable TransferPageDataHelper.TransferPageDataInterceptor interceptor) {
        return getTransferPageDataHelper().returnToPreviousPageWithTargetFragment(interceptor);
    }

    @Override
    public boolean isForbidBackToActivity() {
        return getKeyEventReceiverHelper().isForbidBackToActivity();
    }

    @Override
    public void setForbidBackToActivity(boolean isForbidBackToActivity) {
        getKeyEventReceiverHelper().setForbidBackToActivity(isForbidBackToActivity);
    }

    @Override
    public boolean isForbidBackToFragment() {
        return getKeyEventReceiverHelper().isForbidBackToFragment();
    }

    @Override
    public void setForbidBackToFragment(boolean isForbidBackToFragment) {
        getKeyEventReceiverHelper().setForbidBackToFragment(isForbidBackToFragment);
    }

    /**
     * 添加按键监听器
     *
     * @param listener 按键监听器
     * @return 返回按键观察者Id
     */
    @Nullable
    @Override
    public String addOnKeyEventListener(@NotNull OnKeyEventListener listener) {
        return getKeyEventReceiverHelper().addOnKeyEventListener(listener);
    }

    /**
     * 移除按键监听器
     *
     * @param observerId 按键观察者Id
     * @return 移除成功返回true，否则返回false
     */
    @Override
    public boolean removeOnKeyEventListener(@NonNull String observerId) {
        return getKeyEventReceiverHelper().removeOnKeyEventListener(observerId);
    }

    /**
     * 清除当前界面所有按键监听器
     */
    @Override
    public void clearAllOnKeyEventListeners() {
        getKeyEventReceiverHelper().clearAllOnKeyEventListeners();
    }

    @Nullable
    @Override
    public BackPressedObserver getBackPressedObserver() {
        return getKeyEventReceiverHelper().getBackPressedObserver();
    }

    @Override
    public void setEnableBackPressedCallback(boolean enabled) {
        getKeyEventReceiverHelper().setEnableBackPressedCallback(enabled);
    }

    @Override
    public boolean listenBackKeyToPreviousPageWithActivity(@NonNull ITransferPageDataHelper helper, @Nullable TransferPageDataInterceptor interceptor) {
        return getKeyEventReceiverHelper().listenBackKeyToPreviousPageWithActivity(getTransferPageDataHelper(), interceptor);
    }

    @Override
    public boolean listenBackKeyToPreviousPageWithParentFragment(@NonNull ITransferPageDataHelper helper, @Nullable TransferPageDataInterceptor interceptor) {
        return getKeyEventReceiverHelper().listenBackKeyToPreviousPageWithParentFragment(getTransferPageDataHelper(), interceptor);
    }

    @Override
    public boolean listenBackKeyToPreviousPageWithTargetFragment(@NonNull ITransferPageDataHelper helper, @Nullable TransferPageDataInterceptor interceptor) {
        return getKeyEventReceiverHelper().listenBackKeyToPreviousPageWithTargetFragment(getTransferPageDataHelper(), interceptor);
    }

    /**
     * 设置状态栏为轻色调，避免白色字体被白色活动条遮挡
     */
    protected void setLightStatusBar() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN必须加，否则手机状态栏会显示底层背景，内容颜色没有延伸
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    /**
     * 设置状态栏为深色调
     */
    protected void setDarkStatusBar() {
        if (getActivity() == null) {
            return;
        }
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_VISIBLE);
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
     * 销毁Activity
     *
     * @return 如果成功销毁返回true，否则返回false
     */
    protected boolean finishActivity() {
        Activity activity = getActivity();
        if (activity == null) {
            return false;
        }
        activity.finish();
        return true;
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
        FragmentManager fragmentManager = getChildFragmentManager();
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
    @Nullable
    protected abstract IFragmentInflateLayoutViewCreator generateInflateLayoutViewCreator();

    /**
     * 释放资源（在onDestroyView时调用）
     */
    protected abstract void releaseOnDestroyView();

    /**
     * 释放资源（在onDestroy时调用）
     */
    protected abstract void releaseOnDestroy();

    /**
     * 由子类重写控件的初始化方法
     *
     * @param view               界面父容器View
     * @param savedInstanceState 界面销毁时保存的状态数据实例
     */
    protected abstract void initView(@NonNull View view, @Nullable Bundle savedInstanceState);

    @NonNull
    private ITransferPageDataHelper getTransferPageDataHelper() {
        if (mTransferPageDataHelper == null) {
            synchronized (this) {
                if (mTransferPageDataHelper == null) {
                    mTransferPageDataHelper = new TransferPageDataHelper(this);
                }
            }
        }
        return mTransferPageDataHelper;
    }

    @NonNull
    private IKeyEventReceiverHelper getKeyEventReceiverHelper() {
        if (mKeyEventReceiverHelper == null) {
            synchronized (this) {
                if (mKeyEventReceiverHelper == null) {
                    mKeyEventReceiverHelper = new KeyEventReceiverHelper(this);
                }
            }
        }
        return mKeyEventReceiverHelper;
    }
}
