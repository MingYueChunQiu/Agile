package com.mingyuechunqiu.agilemvpframe.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.mingyuechunqiu.agilemvpframe.feature.loadingdialogfragment.LoadingDfgProvideFactory;
import com.mingyuechunqiu.agilemvpframe.feature.loadingdialogfragment.LoadingDfgProviderable;
import com.mingyuechunqiu.agilemvpframe.feature.loadingdialogfragment.LoadingDialogFragmentOption;
import com.mingyuechunqiu.agilemvpframe.ui.fragment.BaseFragment;
import com.mingyuechunqiu.agilemvpframe.util.ExitApplicationManager;
import com.noober.background.BackgroundLibrary;

import java.util.ArrayList;
import java.util.List;

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

    protected List<BaseFragment.OnKeyDownListener> mKeyDownListenerList;

    private Toast mToast;
    private LoadingDfgProviderable mLoadingDfgProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);
        initView();
        ExitApplicationManager.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
        if (mKeyDownListenerList != null) {
            mKeyDownListenerList.clear();
            mKeyDownListenerList = null;
        }
        mToast = null;
        dismissLoadingDialog();
        mLoadingDfgProvider = null;
    }

    /**
     * 添加fragment的按键监听器
     *
     * @param listener 按键监听器
     */
    public void addOnKeyDownListener(BaseFragment.OnKeyDownListener listener) {
        if (listener == null) {
            return;
        }
        if (mKeyDownListenerList == null) {
            mKeyDownListenerList = new ArrayList<>();
        }
        mKeyDownListenerList.add(listener);
    }

    public List<BaseFragment.OnKeyDownListener> getOnKeyDownListenerList() {
        return mKeyDownListenerList;
    }

    /**
     * 分发按键事件
     *
     * @param keyCode 键值
     * @param event   按键事件
     * @return 如果有fragment阻止了事件继续传递则返回true，否则返回false
     */
    protected boolean dispatchOnKeyDownListener(int keyCode, KeyEvent event) {
        if (mKeyDownListenerList == null || mKeyDownListenerList.size() == 0) {
            return false;
        }
        boolean isContinueTransfer = false;//是否继续传递
        for (BaseFragment.OnKeyDownListener listener : mKeyDownListenerList) {
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
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
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
        LoadingDialogFragmentOption option = getLoadingDialog().getLoadingFragmentOption();
        option.setText(hint);
        option.setCancelWithOutside(cancelable);
        showLoadingDialog(option);
    }

    /**
     * 显示加载Fragment
     *
     * @param option 加载配置参数信息对象
     */
    protected void showLoadingDialog(@Nullable LoadingDialogFragmentOption option) {
        if (getSupportFragmentManager() == null) {
            return;
        }
        if (mLoadingDfgProvider == null) {
            mLoadingDfgProvider = LoadingDfgProvideFactory.newInstance(option);
        } else {
            //在这儿默认逻辑为如果option为空，代表不变
            if (option != null) {
                mLoadingDfgProvider.setLoadingFragmentOption(option);
            }
            if (mLoadingDfgProvider.showLoadingDialog()) {
                return;
            }
        }
        //除隐藏对话框再显示用getDialog().show()，其他都直接用show()
        mLoadingDfgProvider.showLoadingDialog(getSupportFragmentManager());
    }

    /**
     * 关闭加载对话框
     */
    protected void dismissLoadingDialog() {
        if (mLoadingDfgProvider != null) {
            mLoadingDfgProvider.dismissLoadingDialog();
        }
    }

    /**
     * 获取加载Fragment实例
     *
     * @return 返回加载Fragment实例
     */
    @NonNull
    protected LoadingDfgProviderable getLoadingDialog() {
        if (mLoadingDfgProvider == null) {
            mLoadingDfgProvider = LoadingDfgProvideFactory.newInstance();
        }
        return mLoadingDfgProvider;
    }

    /**
     * 释放资源
     */
    protected abstract void release();

    /**
     * 由子类重写控件的初始化方法
     */
    protected abstract void initView();

}
