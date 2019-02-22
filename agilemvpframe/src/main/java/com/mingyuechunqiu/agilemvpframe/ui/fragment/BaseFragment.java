package com.mingyuechunqiu.agilemvpframe.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mingyuechunqiu.agilemvpframe.feature.loadingdialogfragment.LoadingDfgProvideFactory;
import com.mingyuechunqiu.agilemvpframe.feature.loadingdialogfragment.LoadingDfgProviderable;
import com.mingyuechunqiu.agilemvpframe.feature.loadingdialogfragment.LoadingDialogFragmentOption;
import com.mingyuechunqiu.agilemvpframe.ui.activity.BaseActivity;

import static com.mingyuechunqiu.agilemvpframe.constants.CommonConstants.BUNDLE_RETURN_TO_PREVIOUS_PAGE;

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
    private LoadingDfgProviderable mLoadingDfgProvider;

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
        dismissLoadingDialog();
        mLoadingDfgProvider = null;
    }

    /**
     * 设置状态栏为轻色调，避免白色字体被白色活动条遮挡
     */
    protected void setLightStatusBar() {
        if (getActivity() == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
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
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    /**
     * 销毁Activity
     *
     * @return 如果成功销毁返回true，否则返回false
     */
    protected boolean finishActivity() {
        if (getActivity() == null) {
            return false;
        }
        getActivity().finish();
        return true;
    }

    /**
     * 添加按返回键通过Activity返回上一个界面
     *
     * @param fragment 当前fragment
     */
    protected void addBackKeyToPreFragmentWithActivity(final BaseFragment fragment) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).addOnKeyDownListener(new OnKeyDownListener() {
                @Override
                public boolean onFragmentKeyDown(int i, KeyEvent keyEvent) {
                    if (isVisible()) {
                        return returnToPreviousPageWithActivity(fragment);
                    }
                    return false;
                }
            });
        }
    }

    /**
     * 添加按返回键通过Activity返回上一个界面
     *
     * @param fragment 当前fragment
     */
    protected void addBackKeyToPreFgWithParentFg(final BaseFragment fragment) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).addOnKeyDownListener(new OnKeyDownListener() {
                @Override
                public boolean onFragmentKeyDown(int i, KeyEvent keyEvent) {
                    if (isVisible()) {
                        return returnToPreviousPageWithParentFg(fragment);
                    }
                    return false;
                }
            });
        }
    }

    /**
     * 返回上一个界面
     *
     * @param fragment 当前fragment
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean returnToPreviousPageWithParentFg(@NonNull BaseFragment fragment) {
        return returnToPreviousPageWithParentFg(fragment, null);
    }

    /**
     * 返回上一个界面
     *
     * @param fragment    当前fragment
     * @param interceptor 对跳转参数进行拦截设置
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean returnToPreviousPageWithParentFg(@NonNull BaseFragment fragment,
                                                       JumpPageInterceptor interceptor) {
        if (getParentFragment() != null && getParentFragment() instanceof Callback) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(BUNDLE_RETURN_TO_PREVIOUS_PAGE, true);
            if (interceptor != null) {
                interceptor.interceptJumpPage(bundle);
            }
            ((Callback) getParentFragment()).onCall(fragment, bundle);
            return true;
        }
        return false;
    }

    /**
     * 返回上一个界面
     *
     * @param fragment 当前fragment
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean returnToPreviousPageWithActivity(@NonNull BaseFragment fragment) {
        return returnToPreviousPageWithActivity(fragment, null);
    }

    /**
     * 返回上一个界面
     *
     * @param fragment    当前fragment
     * @param interceptor 对跳转参数进行拦截设置
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean returnToPreviousPageWithActivity(@NonNull BaseFragment fragment,
                                                       JumpPageInterceptor interceptor) {
        FragmentActivity activity = fragment.getActivity();
        if (activity instanceof BaseFragment.Callback) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(BUNDLE_RETURN_TO_PREVIOUS_PAGE, true);
            if (interceptor != null) {
                interceptor.interceptJumpPage(bundle);
            }
            ((Callback) activity).onCall(fragment, bundle);
            return true;
        }
        return false;
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
            mToast = Toast.makeText(getContext(), hint, Toast.LENGTH_SHORT);
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
        mLoadingDfgProvider.showLoadingDialog(getFragmentManager());
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

    /**
     * 跳转界面拦截器
     */
    public interface JumpPageInterceptor {

        /**
         * 对跳转界面的参数进行拦截自定义
         *
         * @param bundle 携带参数的数据包
         */
        void interceptJumpPage(@NonNull Bundle bundle);

    }
}
