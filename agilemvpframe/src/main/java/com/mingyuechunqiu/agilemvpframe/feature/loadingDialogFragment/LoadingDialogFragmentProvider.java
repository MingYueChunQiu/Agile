package com.mingyuechunqiu.agilemvpframe.feature.loadingDialogFragment;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.FragmentManager;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2019/1/24
 *     desc   : 加载Fragment对外提供类
 *              实现LoadingDialogFragmentable
 *     version: 1.0
 * </pre>
 */
public class LoadingDialogFragmentProvider implements LoadingDfgProviderable {

    private LoadingDialogFragmentable mLoadingDfgable;
    private LoadingDialogFragmentOption mOption;

    private LoadingDialogFragmentProvider(LoadingDialogFragmentable loadingDfgable) {
        mLoadingDfgable = loadingDfgable;
        checkOrCreateLoadingDfgable();
        mOption = mLoadingDfgable.getLoadingFragmentOption();
    }

    /**
     * 创建加载Fragment提供者实例
     *
     * @return 返回实例对象
     */
    @NonNull
    public static LoadingDialogFragmentProvider newInstance() {
        return newInstance(LoadingDialogFragment.newInstance());
    }

    /**
     * 创建加载Fragment提供者实例
     *
     * @return 返回实例对象
     */
    @NonNull
    public static LoadingDialogFragmentProvider newInstance(@Nullable LoadingDialogFragmentOption option) {
        return newInstance(LoadingDialogFragment.newInstance(option));
    }

    /**
     * 创建加载Fragment提供者实例
     *
     * @param loadingDfgable 用户提供的加载Fragment实现类
     * @return 返回实例对象
     */
    @NonNull
    public static LoadingDialogFragmentProvider newInstance(LoadingDialogFragmentable loadingDfgable) {
        return new LoadingDialogFragmentProvider(loadingDfgable);
    }

    @Override
    public boolean showLoadingDialog() {
        checkOrCreateLoadingDfgable();
        return mLoadingDfgable.showLoadingDialog();
    }

    @Override
    public void showLoadingDialog(FragmentManager manager) {
        checkOrCreateLoadingDfgable();
        mLoadingDfgable.showLoadingDialog(manager);
    }

    @Override
    public void dismissLoadingDialog() {
        if (mLoadingDfgable == null) {
            return;
        }
        mLoadingDfgable.dismissLoadingDialog();
        mLoadingDfgable = null;
    }

    @Override
    public void release() {
        if (mLoadingDfgable == null) {
            return;
        }
        mLoadingDfgable.release();
        mLoadingDfgable = null;
    }

    @Override
    public void setCanCancelWithOutside(boolean canCancelWithOutside) {
        checkOrCreateLoadingDfgable();
        mLoadingDfgable.setCanCancelWithOutside(canCancelWithOutside);
    }

    @Override
    public void setDialogSize(int width, int height) {
        checkOrCreateLoadingDfgable();
        mLoadingDfgable.setDialogSize(width, height);
    }

    @Override
    public void setLoadingBackground(Drawable drawable) {
        checkOrCreateLoadingDfgable();
        mLoadingDfgable.setLoadingBackground(drawable);
    }

    @Override
    public void setIndeterminateProgressDrawable(Drawable drawable) {
        checkOrCreateLoadingDfgable();
        mLoadingDfgable.setIndeterminateProgressDrawable(drawable);
    }

    @Override
    public void setShowLoadingMessage(boolean showLoadingMessage) {
        checkOrCreateLoadingDfgable();
        mLoadingDfgable.setShowLoadingMessage(showLoadingMessage);
    }

    @Override
    public void setLoadingMessageBackground(Drawable drawable) {
        checkOrCreateLoadingDfgable();
        mLoadingDfgable.setLoadingMessageBackground(drawable);
    }

    @Override
    public void setLoadingMessage(@Nullable String msg) {
        checkOrCreateLoadingDfgable();
        mLoadingDfgable.setLoadingMessage(msg);
    }

    @Override
    public void setLoadingMessageColor(int color) {
        checkOrCreateLoadingDfgable();
        mLoadingDfgable.setLoadingMessageColor(color);
    }

    @Override
    public void setLoadingMessageTextAppearance(@StyleRes int textAppearance) {
        checkOrCreateLoadingDfgable();
        mLoadingDfgable.setLoadingMessageTextAppearance(textAppearance);
    }

    @Override
    public void setOnLoadingOptionListener(LoadingDialogFragmentOption.OnLoadingOptionListener listener) {
        checkOrCreateLoadingDfgable();
        mLoadingDfgable.setOnLoadingOptionListener(listener);
    }

    @Override
    public void setLoadingFragmentOption(LoadingDialogFragmentOption option) {
        checkOrCreateLoadingDfgable();
        mOption = option;
        mLoadingDfgable.setLoadingFragmentOption(option);
    }

    @NonNull
    @Override
    public LoadingDialogFragmentOption getLoadingFragmentOption() {
        checkOrCreateLoadingDfgable();
        return mLoadingDfgable.getLoadingFragmentOption();
    }

    @Override
    public void setThemeType(Constants.ThemeType themeType) {
        if (mLoadingDfgable != null) {
            mLoadingDfgable.dismissLoadingDialog();
            mLoadingDfgable = null;
        }
        checkOrCreateLoadingDfgable();
        mOption.setThemeType(themeType);
        mLoadingDfgable = newInstance(mOption);
    }

    @Override
    public void resetLoadingDialog() {
        if (mLoadingDfgable != null) {
            mLoadingDfgable.dismissLoadingDialog();
            mLoadingDfgable = null;
        }
        mOption = null;
    }

    /**
     * 检测对象是否存在，若不存在则创建
     */
    private void checkOrCreateLoadingDfgable() {
        if (mLoadingDfgable == null) {
            mLoadingDfgable = LoadingDialogFragment.newInstance(mOption);
        } else {
            //因为用户通过触摸外围区域关闭对话框的话，mLoadingDfgable不会置为空，会导致重新
            //内部生成Delegate并使用新的option，所以要设置一下
            mLoadingDfgable.setLoadingFragmentOption(mOption);
        }
    }
}
