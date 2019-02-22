package com.mingyuechunqiu.agilemvpframe.ui.bottomsheetdialogfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mingyuechunqiu.agilemvpframe.R;
import com.mingyuechunqiu.agilemvpframe.agile.AgileMVPFrame;

/**
 * <pre>
 *     author : 明月春秋
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/10/21
 *     desc   : 所有对话框碎片的父类
 *              继承自BottomSheetDialogFragment
 *     version: 1.0
 * </pre>
 */
public abstract class BaseBSDialogFragment extends BottomSheetDialogFragment {

    private Toast mToast;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_dialog));
        }
        return initView(inflater, container);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        release();
        mToast = null;
    }

    /**
     * 初始化控件资源
     *
     * @param inflater  布局填充器
     * @param container 父布局容器
     * @return 返回填充布局
     */
    protected abstract View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    /**
     * 释放资源
     */
    protected abstract void release();

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
            mToast = Toast.makeText(AgileMVPFrame.getAppContext(), hint, Toast.LENGTH_SHORT);
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
    protected void showToast(@StringRes int stringResourceId) {
        showToast(getString(stringResourceId));
    }

    public interface Callback {

        /**
         * 由Activity实现的回调方法
         *
         * @param fragment 回调的fragment
         * @param bundle   传递的参数值
         */
        void onCall(BottomSheetDialogFragment fragment, Bundle bundle);

    }
}
