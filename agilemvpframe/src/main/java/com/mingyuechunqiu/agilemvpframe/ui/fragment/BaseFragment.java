package com.mingyuechunqiu.agilemvpframe.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mingyuechunqiu.agilemvpframe.util.DialogUtils;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/16
 *     desc   : 所有Fragment的基类
 *     version: 1.0
 * </pre>
 */
public abstract class BaseFragment extends Fragment {

    private Toast mToast;
    private Dialog mLoadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater, container);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        release();
        mToast = null;
        mLoadingDialog = null;
    }

    /**
     * 显示提示信息
     *
     * @param hint 提示文本
     */
    protected void showToast(String hint) {
        if (mToast == null) {
            mToast = Toast.makeText(getContext(), hint, Toast.LENGTH_SHORT);
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
            mLoadingDialog = DialogUtils.getLoadingDialog(getContext(), null, cancelable);
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
     *
     * @param inflater  布局填充器
     * @param container 填充的布局所在父布局
     * @return 返回创建的填充View
     */
    protected abstract View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    /**
     * 供Activity实现的回调接口，实现对Fragment的调用
     */
    public interface Callback {

        /**
         * 供Activity使用的回调方法
         *
         * @param fragment 传递Fragment自身给其所在的Activity使用
         * @param bundle   用于Fragment向Activity传递数据
         */
        void onCall(BaseFragment fragment, Bundle bundle);
    }

    /**
     * 按键监听器
     */
    public interface OnKeyDownListener {

        /**
         * 当触发按键事件时回调
         *
         * @param keyCode 键值
         * @param event   按键事件
         * @return 如果自己处理完成，不需要Activity继续处理返回true，否则返回false
         */
        boolean onFragmentKeyDown(int keyCode, KeyEvent event);
    }
}
