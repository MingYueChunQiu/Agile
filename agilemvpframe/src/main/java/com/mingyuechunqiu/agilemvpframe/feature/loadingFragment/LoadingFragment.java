package com.mingyuechunqiu.agilemvpframe.feature.loadingFragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
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
 *              继承自Fragment
 *              实现LoadingFragmentable
 *     version: 1.0
 * </pre>
 */
public class LoadingFragment extends Fragment implements LoadingFragmentable {

    private LoadingFragmentDelegate mDelegate;

    public LoadingFragment() {
        mDelegate = new LoadingFragmentDelegate();
    }

    /**
     * 获取加载Fragment实例
     *
     * @return 返回加载Fragment实例
     */
    @NonNull
    public static LoadingFragment getInstance() {
        return getInstance(null);
    }

    /**
     * 获取加载Fragment实例
     *
     * @param option 加载配置信息对象
     * @return 返回加载Fragment实例
     */
    @NonNull
    public static LoadingFragment getInstance(LoadingFragmentOption option) {
        LoadingFragment loadingFragment = new LoadingFragment();
        loadingFragment.mDelegate.setLoadingFragmentOption(option);
        return loadingFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_loading, container, false);
        LinearLayoutCompat llContainer = view.findViewById(R.id.ll_agile_fg_loading_container);
        ProgressBar pbLoading = view.findViewById(R.id.pb_agile_fg_loading_progress);
        AppCompatTextView tvText = view.findViewById(R.id.tv_agile_fg_loading_text);
        mDelegate.initialize(view, llContainer, pbLoading, tvText);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        release();
    }

    /**
     * 设置加载配置信息
     *
     * @param option 配置信息对象
     */
    @Override
    public void setLoadingFragmentOption(LoadingFragmentOption option) {
        mDelegate.setLoadingFragmentOption(option);
    }

    /**
     * 获取配置信息
     *
     * @return 返回配置信息对象
     */
    @Override
    public LoadingFragmentOption getLoadingFragmentOption() {
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
    public void setCanTouchOutside(boolean canTouchOutside) {
        mDelegate.setCanTouchOutside(canTouchOutside);
    }

    /**
     * 设置容器背景
     *
     * @param drawable 背景图像对象
     */
    @Override
    public void setContainerBackground(Drawable drawable) {
        mDelegate.setContainerBackground(drawable);
    }

    /**
     * 设置加载背景
     *
     * @param drawable 背景图像对象
     */
    @Override
    public void setLoadingBackground(Drawable drawable) {
        mDelegate.setLoadingBackground(drawable);
    }

    /**
     * 设置无进度加载图像
     *
     * @param drawable 加载图像对象
     */
    @Override
    public void setIndeterminateProgressDrawable(Drawable drawable) {
        mDelegate.setIndeterminateProgressDrawable(drawable);
    }

    @Override
    public void setShowLoadingMessage(boolean showLoadingMessage) {
        mDelegate.setShowLoadingMessage(showLoadingMessage);
    }

    /**
     * 设置加载信息背景
     *
     * @param drawable 背景图像对象
     */
    @Override
    public void setLoadingMessageBackground(Drawable drawable) {
        mDelegate.setLoadingMessageBackground(drawable);
    }

    /**
     * 设置并显示加载文本信息
     *
     * @param msg 加载文本
     */
    @Override
    public void setLoadingMessage(@Nullable String msg) {
        mDelegate.setLoadingMessage(msg);
    }

    /**
     * 设置加载文本颜色
     *
     * @param color 颜色值
     */
    @Override
    public void setLoadingMessageColor(@ColorInt int color) {
        mDelegate.setLoadingMessageColor(color);
    }

    /**
     * 设置加载文本样式
     *
     * @param textAppearance 文本样式
     */
    @Override
    public void setLoadingMessageTextAppearance(@StyleRes int textAppearance) {
        mDelegate.setLoadingMessageTextAppearance(textAppearance);
    }
}
