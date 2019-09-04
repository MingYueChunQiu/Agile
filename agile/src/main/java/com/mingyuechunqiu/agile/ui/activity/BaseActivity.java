package com.mingyuechunqiu.agile.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.mingyuechunqiu.agile.feature.loading.data.Constants;
import com.mingyuechunqiu.agile.feature.loading.data.LoadingDialogFragmentOption;
import com.mingyuechunqiu.agile.feature.loading.provider.LoadingDfgProvideFactory;
import com.mingyuechunqiu.agile.feature.loading.provider.LoadingDfgProviderable;
import com.mingyuechunqiu.agile.ui.fragment.BaseFragment;
import com.mingyuechunqiu.agile.util.ExitApplicationManager;
import com.noober.background.BackgroundLibrary;

import java.util.ArrayList;
import java.util.List;

import static com.mingyuechunqiu.agile.constants.CommonConstants.BUNDLE_RETURN_TO_PREVIOUS_PAGE;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/16
 *     desc   : 所有AppCompatActivity的基类
 *     version: 1.0
 * </pre>
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected List<BaseFragment.OnKeyEventListener> mKeyEventListenerList;

    private Toast mToast;
    private LoadingDfgProviderable mLoadingDfgProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);
        initOnCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        dismissLoadingDialog();
        super.onDestroy();
        release();
        if (mKeyEventListenerList != null) {
            mKeyEventListenerList.clear();
            mKeyEventListenerList = null;
        }
        mToast = null;
        mLoadingDfgProvider = null;
    }

    /**
     * 添加fragment的按键监听器
     *
     * @param listener fragment按键监听器
     */
    public void addOnKeyEventListener(@Nullable BaseFragment.OnKeyEventListener listener) {
        if (listener == null) {
            return;
        }
        if (mKeyEventListenerList == null) {
            mKeyEventListenerList = new ArrayList<>();
        }
        mKeyEventListenerList.add(listener);
    }

    /**
     * 删除fragment的按键监听器
     *
     * @param listener fragment按键监听器
     * @return 如果删除成功返回true，否则返回false
     */
    public boolean removeOnKeyEventListener(@Nullable BaseFragment.OnKeyEventListener listener) {
        if (listener == null || mKeyEventListenerList == null) {
            return false;
        }
        return mKeyEventListenerList.remove(listener);
    }

    public List<BaseFragment.OnKeyEventListener> getOnKeyEventListenerList() {
        return mKeyEventListenerList;
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
    protected boolean dispatchOnKeyDownListener(int keyCode, KeyEvent event) {
        if (mKeyEventListenerList == null || mKeyEventListenerList.size() == 0) {
            return false;
        }
        boolean isContinueTransfer = false;//是否继续传递
        for (BaseFragment.OnKeyEventListener listener : mKeyEventListenerList) {
            if (listener.onFragmentKeyDown(keyCode, event)) {
                isContinueTransfer = true;
            }
        }
        return isContinueTransfer;
    }

    /**
     * 设置状态栏为轻色调，避免白色字体被白色活动条遮挡
     */
    protected void setLightStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
     * 根据资源id显示提示信息
     *
     * @param stringResourceId 提示文本资源id
     */
    protected void showToast(@StringRes int stringResourceId) {
        showToast(getString(stringResourceId));
    }

    /**
     * 显示提示信息
     *
     * @param hint 提示文本
     */
    protected void showToast(@Nullable String hint) {
        if (TextUtils.isEmpty(hint)) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(this, hint, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(hint);
        }
        mToast.show();
    }

    /**
     * 显示加载对话框
     *
     * @param hint       提示文本
     * @param cancelable 是否可以取消
     */
    protected void showLoadingDialog(@Nullable String hint, boolean cancelable) {
        LoadingDialogFragmentOption option = getCurrentLoadingDialog().getLoadingFragmentOption();
        option.setText(hint);
        option.setCancelWithOutside(cancelable);
        showLoadingDialog(interceptLoadingFragmentOption(option, Constants.ModeType.TYPE_DIALOG));
    }

    /**
     * 显示加载Fragment
     *
     * @param option 加载配置参数信息对象
     */
    protected void showLoadingDialog(@Nullable LoadingDialogFragmentOption option) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager == null) {
            return;
        }
        LoadingDialogFragmentOption temp = interceptLoadingFragmentOption(option, Constants.ModeType.TYPE_DIALOG);
        if (mLoadingDfgProvider == null) {
            mLoadingDfgProvider = LoadingDfgProvideFactory.newInstance(temp);
        } else {
            //在这儿默认逻辑为如果option为空，代表不变
            if (temp != null) {
                mLoadingDfgProvider.setLoadingFragmentOption(temp);
            }
            if (mLoadingDfgProvider.showLoadingDialog()) {
                return;
            }
        }
        //除隐藏对话框再显示用getDialog().show()，其他都直接用show()
        mLoadingDfgProvider.showLoadingDialog(fragmentManager);
    }

    /**
     * 添加显示加载对话框
     *
     * @param containerId 对话框所属布局ID
     * @param option      加载对话框配置信息对象
     */
    protected void showLoadingDialog(@IdRes int containerId, @Nullable LoadingDialogFragmentOption option) {
        showLoadingDialog(getSupportFragmentManager(), containerId, option);
    }

    /**
     * 添加显示加载对话框
     *
     * @param manager     Fragment管理器
     * @param containerId 对话框所属布局ID
     * @param option      加载对话框配置信息对象
     */
    protected void showLoadingDialog(@Nullable FragmentManager manager, @IdRes int containerId, @Nullable LoadingDialogFragmentOption option) {
        if (manager == null) {
            return;
        }
        getCurrentLoadingDialog().showLoadingDialog(manager, containerId,
                interceptLoadingFragmentOption(option, Constants.ModeType.TYPE_FRAGMENT));
    }

    /**
     * 关闭加载对话框
     */
    protected void dismissLoadingDialog() {
        if (mLoadingDfgProvider != null) {
            mLoadingDfgProvider.dismissLoadingDialog(true);
        }
    }

    /**
     * 隐藏加载对话框
     *
     * @param manager Fragment管理器
     */
    protected void hideLoadingDialog(FragmentManager manager) {
        getCurrentLoadingDialog().hideLoadingDialog(manager);
    }

    /**
     * 获取加载Fragment实例
     *
     * @return 返回加载Fragment实例
     */
    @NonNull
    protected LoadingDfgProviderable getCurrentLoadingDialog() {
        if (mLoadingDfgProvider == null) {
            mLoadingDfgProvider = LoadingDfgProvideFactory.newInstance();
        }
        return mLoadingDfgProvider;
    }

    /**
     * 拦截加载对话框配置信息对象
     *
     * @param option   加载对话框配置信息对象
     * @param modeType 加载对话框模式
     * @return 返回进行过拦截处理的加载对话框配置信息对象
     */
    protected LoadingDialogFragmentOption interceptLoadingFragmentOption(
            @Nullable LoadingDialogFragmentOption option, Constants.ModeType modeType) {
        return modeType == Constants.ModeType.TYPE_NOT_SET ? null : option;
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
        if (fragmentManager == null) {
            return false;
        }
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
