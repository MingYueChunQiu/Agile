package com.mingyuechunqiu.agilemvpframe.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.mingyuechunqiu.agilemvpframe.ui.fragment.BaseFragment;
import com.mingyuechunqiu.agilemvpframe.util.DialogUtils;
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
    private Dialog mLoadingDialog;

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
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mToast = null;
        mLoadingDialog = null;
        if (mKeyDownListenerList != null) {
            mKeyDownListenerList.clear();
            mKeyDownListenerList = null;
        }
        release();
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
     * 显示提示信息
     *
     * @param hint 提示文本
     */
    protected void showToast(String hint) {
        if (mToast == null) {
            mToast = Toast.makeText(this, hint, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(hint);
        }
        mToast.show();
    }

    /**
     * 根据资源id显示提示信息
     *
     * @param stringResourceId 提示文本资源id
     */
    protected void showToast(int stringResourceId) {
        showToast(getString(stringResourceId));
    }

    /**
     * 显示加载对话框
     *
     * @param hint       提示文本
     * @param cancelable 是否可以取消
     */
    protected void showLoadingDialog(String hint, boolean cancelable) {
        if (mLoadingDialog == null) {
            mLoadingDialog = DialogUtils.getLoadingDialog(this, hint, cancelable);
        }
        mLoadingDialog.show();
    }

    /**
     * 隐藏加载对话框
     */
    protected void disappearLoadingDialog() {
        DialogUtils.disappearDialog(mLoadingDialog);
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
