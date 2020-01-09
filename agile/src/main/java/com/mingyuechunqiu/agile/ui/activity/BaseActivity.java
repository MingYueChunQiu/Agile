package com.mingyuechunqiu.agile.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;
import com.mingyuechunqiu.agile.feature.statusview.function.StatusViewManagerProvider;
import com.mingyuechunqiu.agile.framework.ui.OnKeyEventListener;
import com.mingyuechunqiu.agile.util.ExitApplicationManager;
import com.mingyuechunqiu.agile.util.ToastUtils;
import com.noober.background.BackgroundLibrary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.mingyuechunqiu.agile.constants.CommonConstants.BUNDLE_RETURN_TO_PREVIOUS_PAGE;

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
public abstract class BaseActivity extends AppCompatActivity {

    protected Map<Fragment, List<OnKeyEventListener>> mKeyEventListenerMap;

    private IStatusViewManager mStatusViewManager;
    private final Object mStatusViewLock = new Object();//使用私有锁对象模式用于同步状态视图

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);
        initOnCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        dismissStatusView();
        super.onDestroy();
        release();
        if (mKeyEventListenerMap != null) {
            mKeyEventListenerMap.clear();
            mKeyEventListenerMap = null;
        }
        mStatusViewManager = null;
    }

    /**
     * 添加fragment的按键监听器
     *
     * @param listener fragment按键监听器
     */
    public void addOnKeyEventListener(@NonNull Fragment fragment, @NonNull OnKeyEventListener listener) {
        if (mKeyEventListenerMap == null) {
            mKeyEventListenerMap = new ConcurrentHashMap<>();
        }
        List<OnKeyEventListener> list;
        if (mKeyEventListenerMap.containsKey(fragment)) {
            list = mKeyEventListenerMap.get(fragment);
            if (list == null) {
                list = new ArrayList<>();
                list.add(listener);
                mKeyEventListenerMap.put(fragment, list);
            } else {
                list.add(listener);
            }
        } else {
            list = new ArrayList<>();
            list.add(listener);
            mKeyEventListenerMap.put(fragment, list);
        }
    }

    /**
     * 删除fragment的按键监听器
     *
     * @param listener fragment按键监听器
     * @return 如果删除成功返回true，否则返回false
     */
    public boolean removeOnKeyEventListener(@Nullable OnKeyEventListener listener) {
        if (listener == null || mKeyEventListenerMap == null) {
            return false;
        }
        for (List<OnKeyEventListener> list : mKeyEventListenerMap.values()) {
            if (list.contains(listener)) {
                return list.remove(listener);
            }
        }
        return false;
    }

    /**
     * 移除与Fragment键关联的所有按键监听器
     *
     * @param fragment 与按键监听器相关联的Fragment
     */
    public void removeOnKeyEventListeners(@NonNull Fragment fragment) {
        if (mKeyEventListenerMap == null) {
            return;
        }
        Iterator<Map.Entry<Fragment, List<OnKeyEventListener>>> iterator = mKeyEventListenerMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Fragment, List<OnKeyEventListener>> entry = iterator.next();
            if (entry.getKey() == fragment) {
                iterator.remove();
                break;
            }
        }
    }

    @NonNull
    public Map<Fragment, List<OnKeyEventListener>> getOnKeyEventListenerList() {
        if (mKeyEventListenerMap == null) {
            mKeyEventListenerMap = new ConcurrentHashMap<>();
        }
        return mKeyEventListenerMap;
    }

    /**
     * 在创建时执行初始化操作
     *
     * @param savedInstanceState 界面销毁时保存的状态数据实例
     */
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {
        initView(savedInstanceState);
        ExitApplicationManager.addActivity(this);
    }

    /**
     * 分发按键事件
     *
     * @param keyCode 键值
     * @param event   按键事件
     * @return 如果有fragment阻止了事件继续传递则返回true，否则返回false
     */
    protected boolean dispatchOnKeyEventListener(int keyCode, KeyEvent event) {
        if (event == null) {
            return false;
        }
        if (mKeyEventListenerMap == null || mKeyEventListenerMap.size() == 0) {
            return false;
        }
        boolean isForbidTransfer = false;//是否禁止继续传递
        for (List<OnKeyEventListener> list : mKeyEventListenerMap.values()) {
            for (OnKeyEventListener listener : list) {
                if (listener.onKeyEvent(keyCode, event)) {
                    isForbidTransfer = true;
                }
            }
        }
        return isForbidTransfer;
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
     * @param msg        提示文本
     * @param cancelable 是否可以取消
     */
    protected void showLoadingStatusView(@Nullable String msg, boolean cancelable) {
        StatusViewOption option = StatusViewManagerProvider.getGlobalStatusViewOptionByType(StatusViewConstants.StatusType.TYPE_LOADING);
        option.getContentOption().setText(msg);
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
                }
            }
        }
        return mStatusViewManager;
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
