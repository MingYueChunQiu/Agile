package com.mingyuechunqiu.agilemvpframe.feature.loading;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mingyuechunqiu.agilemvpframe.R;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2019/1/22
 *     desc   : 用于加载显示的Fragment
 *              继承自DialogFragment
 *              实现LoadingFragmentable
 *     version: 1.0
 * </pre>
 */
public class LoadingDialogFragment extends DialogFragment implements LoadingDialogFragmentable {

    private LoadingDfgDelegate mDelegate;

    public LoadingDialogFragment() {
        mDelegate = new LoadingDfgDelegate();
    }

    /**
     * 创建加载Fragment实例
     *
     * @return 返回加载Fragment实例
     */
    @NonNull
    public static LoadingDialogFragment newInstance() {
        return newInstance(null);
    }

    /**
     * 创建加载Fragment实例
     *
     * @param option 加载配置信息对象
     * @return 返回加载Fragment实例
     */
    @NonNull
    public static LoadingDialogFragment newInstance(LoadingDialogFragmentOption option) {
        LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.setLoadingFragmentOption(option);
        return loadingDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        View view;
        LinearLayoutCompat llContainer;
        ProgressBar pbLoading;
        AppCompatTextView tvText;
        checkOrCreateLoadingDfgDelegate();
        if (mDelegate.getLoadingFragmentOption().getThemeType() == Constants.ThemeType.DARK_THEME) {
            view = inflater.inflate(R.layout.agile_dialog_fragment_dark_loading, container, false);
            llContainer = view.findViewById(R.id.ll_agile_dfg_dark_loading_container);
            pbLoading = view.findViewById(R.id.pb_agile_dfg_dark_loading_progress);
            tvText = view.findViewById(R.id.tv_agile_dfg_dark_loading_text);
        } else {
            view = inflater.inflate(R.layout.agile_dialog_fragment_light_loading, container, false);
            llContainer = view.findViewById(R.id.ll_agile_dfg_light_loading_container);
            pbLoading = view.findViewById(R.id.pb_agile_dfg_light_loading_progress);
            tvText = view.findViewById(R.id.tv_agile_dfg_light_loading_text);
        }
        mDelegate.initialize(getDialog(), llContainer, pbLoading, tvText);
        if (getDialog() != null) {
            getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (mDelegate == null) {
                            return false;
                        }
                        //拦截返回键事件，是否要做额外处理
                        if (mDelegate.getLoadingFragmentOption().getOnLoadingOptionListener() != null) {
                            return mDelegate.getLoadingFragmentOption().getOnLoadingOptionListener()
                                    .onClickKeyBack(dialog);
                        }
                    }
                    return false;
                }
            });
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mDelegate != null) {
            mDelegate.executeActions();
        }
    }

    @Override
    public void onDestroy() {
        if (mDelegate != null &&
                mDelegate.getLoadingFragmentOption().getOnLoadingOptionListener() != null) {
            mDelegate.getLoadingFragmentOption().getOnLoadingOptionListener().onDismissListener(this);
        }
        release();
        super.onDestroy();
    }

    /**
     * 设置加载配置信息
     *
     * @param option 配置信息对象
     */
    @Override
    public void setLoadingFragmentOption(LoadingDialogFragmentOption option) {
        checkOrCreateLoadingDfgDelegate();
        mDelegate.setLoadingFragmentOption(option);
    }

    /**
     * 获取配置信息
     *
     * @return 返回配置信息对象
     */
    @Override
    public LoadingDialogFragmentOption getLoadingFragmentOption() {
        checkOrCreateLoadingDfgDelegate();
        return mDelegate.getLoadingFragmentOption();
    }

    @Override
    public void release() {
        if (mDelegate != null) {
            mDelegate.release();
            mDelegate = null;
        }
    }

    @Override
    public void setCanCancelWithOutside(boolean canCancelWithOutside) {
        checkOrCreateLoadingDfgDelegate();
        mDelegate.getLoadingFragmentOption().setCancelWithOutside(canCancelWithOutside);
    }

    @Override
    public void setDialogSize(int width, int height) {
        checkOrCreateLoadingDfgDelegate();
        mDelegate.setDialogSize(width, height);
    }

    /**
     * 设置加载背景
     *
     * @param drawable 背景图像对象
     */
    @Override
    public void setLoadingBackground(Drawable drawable) {
        checkOrCreateLoadingDfgDelegate();
        mDelegate.setLoadingBackground(drawable);
    }

    /**
     * 设置无进度加载图像
     *
     * @param drawable 加载图像对象
     */
    @Override
    public void setIndeterminateProgressDrawable(Drawable drawable) {
        checkOrCreateLoadingDfgDelegate();
        mDelegate.setIndeterminateProgressDrawable(drawable);
    }

    @Override
    public void setShowLoadingMessage(boolean showLoadingMessage) {
        checkOrCreateLoadingDfgDelegate();
        mDelegate.setShowLoadingMessage(showLoadingMessage);
    }

    /**
     * 设置加载信息背景
     *
     * @param drawable 背景图像对象
     */
    @Override
    public void setLoadingMessageBackground(Drawable drawable) {
        checkOrCreateLoadingDfgDelegate();
        mDelegate.setLoadingMessageBackground(drawable);
    }

    /**
     * 设置并显示加载文本信息
     *
     * @param msg 加载文本
     */
    @Override
    public void setLoadingMessage(@Nullable String msg) {
        checkOrCreateLoadingDfgDelegate();
        mDelegate.setLoadingMessage(msg);
    }

    /**
     * 设置加载文本颜色
     *
     * @param color 颜色值
     */
    @Override
    public void setLoadingMessageColor(@ColorInt int color) {
        checkOrCreateLoadingDfgDelegate();
        mDelegate.setLoadingMessageColor(color);
    }

    /**
     * 设置加载文本样式
     *
     * @param textAppearance 文本样式
     */
    @Override
    public void setLoadingMessageTextAppearance(@StyleRes int textAppearance) {
        checkOrCreateLoadingDfgDelegate();
        mDelegate.setLoadingMessageTextAppearance(textAppearance);
    }

    @Override
    public void setOnLoadingOptionListener(LoadingDialogFragmentOption.OnLoadingOptionListener listener) {
        checkOrCreateLoadingDfgDelegate();
        mDelegate.setOnLoadingOptionListener(listener);
    }

    @Override
    public boolean showLoadingDialog() {
        if (getDialog() != null) {
            getDialog().show();
            return true;
        }
        return false;
    }

    @Override
    public void showLoadingDialog(FragmentManager manager) {
        if (isAdded() || isVisible() || manager == null) {
            return;
        }
        show(manager, LoadingDialogFragment.class.getSimpleName());
    }

    @Override
    public void dismissLoadingDialog() {
        dismiss();
    }

    @Override
    public DialogFragment getDialogFragment() {
        return this;
    }

    /**
     * 检测代理类实例是否存在，不存在则创建
     */
    private void checkOrCreateLoadingDfgDelegate() {
        if (mDelegate == null) {
            mDelegate = new LoadingDfgDelegate();
        }
    }
}
