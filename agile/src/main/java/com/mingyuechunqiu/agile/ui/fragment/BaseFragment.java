package com.mingyuechunqiu.agile.ui.fragment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.feature.statusview.function.StatusViewManagerProvider;
import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;
import com.mingyuechunqiu.agile.framework.function.TransferDataCallback;
import com.mingyuechunqiu.agile.framework.ui.OnKeyEventListener;
import com.mingyuechunqiu.agile.ui.activity.BaseActivity;

import static com.mingyuechunqiu.agile.constants.CommonConstants.BUNDLE_RETURN_TO_PREVIOUS_PAGE;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/16
 *     desc   : 所有Fragment的基类
 *              继承自Fragment
 *     version: 1.0
 * </pre>
 */
public abstract class BaseFragment extends Fragment {

    //禁止返回界面flag
    private boolean forbidBackToActivity, forbidBackToFragment;
    private Toast mToast;
    private IStatusViewManager mLoadingDfgProvider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        removeAllOnKeyEventListeners();
        dismissLoadingDialog();
        super.onDestroyView();
        releaseOnDestroyView();
        mToast = null;
        mLoadingDfgProvider = null;
        forbidBackToActivity = forbidBackToFragment = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseOnDestroy();
    }

    public boolean isForbidBackToActivity() {
        return forbidBackToActivity;
    }

    public void setForbidBackToActivity(boolean forbidBackToActivity) {
        this.forbidBackToActivity = forbidBackToActivity;
    }

    public boolean isForbidBackToFragment() {
        return forbidBackToFragment;
    }

    public void setForbidBackToFragment(boolean forbidBackToFragment) {
        this.forbidBackToFragment = forbidBackToFragment;
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
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_VISIBLE);
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
     * 添加按返回键通过Activity返回上一个界面
     */
    protected void addBackKeyToPreviousPageWithParentFg() {
        addBackKeyToPreviousPageWithParentFg(null);
    }

    /**
     * 添加按返回键通过Activity返回上一个界面
     *
     * @param interceptor 跳转参数拦截设置器
     */
    protected void addBackKeyToPreviousPageWithParentFg(@Nullable final JumpPageInterceptor interceptor) {
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).addOnKeyEventListener(this, new OnKeyEventListener() {
                @Override
                public boolean onKeyEvent(int keyCode, KeyEvent event) {
                    if (isVisible() && !forbidBackToFragment) {
                        return returnToPreviousPageWithParentFg(interceptor);
                    }
                    return false;
                }
            });
        }
    }

    /**
     * 添加按返回键通过Activity返回上一个界面
     */
    protected void addBackKeyToPreviousPageWithActivity() {
        addBackKeyToPreviousPageWithActivity(null);
    }

    /**
     * 添加按返回键通过Activity返回上一个界面
     *
     * @param interceptor 跳转参数拦截设置器
     */
    protected void addBackKeyToPreviousPageWithActivity(@Nullable final JumpPageInterceptor interceptor) {
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).addOnKeyEventListener(this, new OnKeyEventListener() {
                @Override
                public boolean onKeyEvent(int keyCode, KeyEvent event) {
                    if (isVisible() && !forbidBackToActivity) {
                        return returnToPreviousPageWithActivity(interceptor);
                    }
                    return false;
                }
            });
        }
    }

    /**
     * 移除按键监听器
     *
     * @param listener 按键监听器
     */
    protected void removeKeyEventListener(@Nullable OnKeyEventListener listener) {
        if (listener == null) {
            return;
        }
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).removeOnKeyEventListener(listener);
        }
    }

    /**
     * 返回父fragment界面
     *
     * @return 如果成功进行回调则返回true，否则返回false
     */
    protected boolean returnToPreviousPageWithParentFg() {
        return returnToPreviousPageWithParentFg(null);
    }

    /**
     * 返回父fragment界面
     *
     * @param interceptor 跳转参数拦截设置器
     * @return 如果成功进行回调则返回true，否则返回false
     */
    protected boolean returnToPreviousPageWithParentFg(@Nullable JumpPageInterceptor interceptor) {
        return returnToPreviousPageWithFragment(getParentFragment(), interceptor);
    }

    /**
     * 返回目标fragment界面
     *
     * @return 如果成功进行回调则返回true，否则返回false
     */
    protected boolean returnToPreviousPageWithTargetFg() {
        return returnToPreviousPageWithTargetFg(null);
    }

    /**
     * 返回目标fragment界面
     *
     * @param interceptor 跳转参数拦截设置器
     * @return 如果成功进行回调则返回true，否则返回false
     */
    protected boolean returnToPreviousPageWithTargetFg(@Nullable JumpPageInterceptor interceptor) {
        return returnToPreviousPageWithFragment(getTargetFragment(), interceptor);
    }

    /**
     * 返回前一个界面
     *
     * @param callbackFragment 被回调的fragment
     * @param interceptor      跳转参数拦截设置器
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean returnToPreviousPageWithFragment(@Nullable Fragment callbackFragment,
                                                       @Nullable final JumpPageInterceptor interceptor) {
        return returnToPreviousPageWithFragment(this, callbackFragment, interceptor);
    }

    /**
     * 返回前一个界面
     *
     * @param fragment         指定的fragment
     * @param callbackFragment 被回调的fragment
     * @param interceptor      跳转参数拦截设置器
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean returnToPreviousPageWithFragment(@Nullable Fragment fragment,
                                                       @Nullable Fragment callbackFragment,
                                                       @Nullable final JumpPageInterceptor interceptor) {
        return callFragment(fragment, callbackFragment, new TransferDataCallback() {
            @Override
            public Bundle transferData() {
                Bundle bundle = new Bundle();
                bundle.putBoolean(BUNDLE_RETURN_TO_PREVIOUS_PAGE, true);
                if (interceptor != null) {
                    interceptor.interceptJumpPage(bundle);
                }
                return bundle;
            }
        });
    }

    /**
     * 返回上一个界面
     *
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean returnToPreviousPageWithActivity() {
        return returnToPreviousPageWithActivity(null);
    }

    /**
     * 返回上一个界面
     *
     * @param interceptor 跳转参数拦截设置器
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean returnToPreviousPageWithActivity(@Nullable final JumpPageInterceptor interceptor) {
        return returnToPreviousPageWithActivity(this, interceptor);
    }

    /**
     * 返回上一个界面
     *
     * @param fragment    指定的fragment
     * @param interceptor 跳转参数拦截设置器
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean returnToPreviousPageWithActivity(@Nullable Fragment fragment,
                                                       @Nullable final JumpPageInterceptor interceptor) {
        return callActivity(fragment, new TransferDataCallback() {
            @Override
            public Bundle transferData() {
                Bundle bundle = new Bundle();
                bundle.putBoolean(BUNDLE_RETURN_TO_PREVIOUS_PAGE, true);
                if (interceptor != null) {
                    interceptor.interceptJumpPage(bundle);
                }
                return bundle;
            }
        });
    }

    /**
     * 添加按键监听事件
     *
     * @param listener 按键监听器
     */
    protected void addOnKeyEventListenerToActivity(@NonNull OnKeyEventListener listener) {
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).addOnKeyEventListener(this, listener);
        }
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
        StatusViewOption option = getCurrentLoadingDialog().getLoadingFragmentOption();
        option.setText(hint);
        option.setCancelWithOutside(cancelable);
        showLoadingDialog(interceptLoadingFragmentOption(option, StatusViewConstants.ModeType.TYPE_DIALOG));
    }

    /**
     * 显示加载Fragment
     *
     * @param option 加载配置参数信息对象
     */
    protected void showLoadingDialog(@Nullable StatusViewOption option) {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) {
            return;
        }
        StatusViewOption temp = interceptLoadingFragmentOption(option, StatusViewConstants.ModeType.TYPE_DIALOG);
        if (mLoadingDfgProvider == null) {
            mLoadingDfgProvider = StatusViewManagerProvider.newInstance(temp);
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
    protected void showLoadingDialog(@IdRes int containerId, @Nullable StatusViewOption option) {
        showLoadingDialog(getChildFragmentManager(), containerId, option);
    }

    /**
     * 添加显示加载对话框
     *
     * @param manager     Fragment管理器
     * @param containerId 对话框所属布局ID
     * @param option      加载对话框配置信息对象
     */
    protected void showLoadingDialog(@Nullable FragmentManager manager, @IdRes int containerId, @Nullable StatusViewOption option) {
        if (manager == null) {
            return;
        }
        getCurrentLoadingDialog().showLoadingDialog(manager, containerId,
                interceptLoadingFragmentOption(option, StatusViewConstants.ModeType.TYPE_FRAGMENT));
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
     * 移除加载对话框
     *
     * @param manager Fragment管理器
     */
    protected void removeLoadingDialog(FragmentManager manager) {
        getCurrentLoadingDialog().removeLoadingDialog(manager);
    }

    /**
     * 获取加载Fragment实例
     *
     * @return 返回加载Fragment实例
     */
    @NonNull
    protected IStatusViewManager getCurrentLoadingDialog() {
        if (mLoadingDfgProvider == null) {
            mLoadingDfgProvider = StatusViewManagerProvider.newInstance();
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
    protected StatusViewOption interceptLoadingFragmentOption(
            @Nullable StatusViewOption option, StatusViewConstants.ModeType modeType) {
        return modeType == StatusViewConstants.ModeType.TYPE_NOT_SET ? null : option;
    }

    /**
     * 回调父类Fragment传递数据
     *
     * @param callback 传递数据回调
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean callParentFragment(@Nullable TransferDataCallback callback) {
        return callFragment(getParentFragment(), callback);
    }

    /**
     * 回调目标Fragment传递数据
     *
     * @param callback 传递数据回调
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean callTargetFragment(@Nullable TransferDataCallback callback) {
        return callFragment(getTargetFragment(), callback);
    }

    /**
     * 回调指定Fragment传递数据
     *
     * @param callbackFragment 被回调的fragment
     * @param callback         传递数据回调
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean callFragment(@Nullable Fragment callbackFragment, @Nullable TransferDataCallback callback) {
        return callFragment(this, callbackFragment, callback);
    }

    /**
     * 回调指定Fragment传递数据
     *
     * @param fragment         指定的Fragment
     * @param callbackFragment 被回调的fragment
     * @param callback         传递数据回调
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean callFragment(@Nullable Fragment fragment, @Nullable Fragment callbackFragment,
                                   @Nullable TransferDataCallback callback) {
        if (fragment == null) {
            return false;
        }
        if (callbackFragment instanceof Callback) {
            ((Callback) callbackFragment).onCall(fragment, callback == null ? null : callback.transferData());
            return true;
        }
        return false;
    }

    /**
     * 回调Activity传递数据
     *
     * @param callback 传递数据回调
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean callActivity(@Nullable TransferDataCallback callback) {
        return callActivity(this, callback);
    }

    /**
     * 回调Activity传递数据
     *
     * @param fragment 指定的Fragment
     * @param callback 传递数据回调
     * @return 如果进行回调则返回true，否则返回false
     */
    protected boolean callActivity(@Nullable Fragment fragment, @Nullable TransferDataCallback callback) {
        if (fragment == null) {
            return false;
        }
        FragmentActivity activity = getActivity();
        if (activity instanceof Callback) {
            ((Callback) activity).onCall(fragment, callback == null ? null : callback.transferData());
            return true;
        }
        return false;
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
     * 移除所有的按键监听器
     */
    private void removeAllOnKeyEventListeners() {
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).removeOnKeyEventListeners(this);
        }
    }

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
     * @param inflater           布局填充器
     * @param container          填充的布局所在父布局
     * @param savedInstanceState 界面销毁时保存的状态数据实例
     * @return 返回创建的填充View
     */
    protected abstract View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

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
        void onCall(@NonNull Fragment fragment, @Nullable Bundle bundle);
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
