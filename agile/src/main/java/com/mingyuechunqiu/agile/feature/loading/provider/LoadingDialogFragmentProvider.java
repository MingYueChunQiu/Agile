package com.mingyuechunqiu.agile.feature.loading.provider;

import android.graphics.drawable.Drawable;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mingyuechunqiu.agile.feature.loading.data.Constants;
import com.mingyuechunqiu.agile.feature.loading.data.LoadingDialogFragmentOption;
import com.mingyuechunqiu.agile.feature.loading.function.LoadingDialogFragment;
import com.mingyuechunqiu.agile.feature.loading.function.LoadingDialogFragmentable;
import com.mingyuechunqiu.agile.util.FragmentUtils;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2019/1/24
 *     desc   : 加载Fragment对外提供类
 *              实现LoadingDfgProviderable
 *     version: 1.0
 * </pre>
 */
class LoadingDialogFragmentProvider implements LoadingDfgProviderable {

    private LoadingDialogFragmentable mLoadingDfgable;
    private LoadingDialogFragmentOption mOption;
    private Constants.ModeType mModeType;//当前所属模式

    LoadingDialogFragmentProvider(LoadingDialogFragmentable loadingDfgable) {
        mLoadingDfgable = loadingDfgable;
        checkOrCreateLoadingDfgable();
        mOption = mLoadingDfgable.getLoadingFragmentOption();
    }

    @Override
    public boolean showLoadingDialog() {
        checkOrCreateLoadingDfgable();
        if (getDialogFragment().isVisible()) {
            return true;
        }
        if (mModeType == Constants.ModeType.TYPE_FRAGMENT) {
            return false;
        }
        mModeType = Constants.ModeType.TYPE_DIALOG;
        return mLoadingDfgable.showLoadingDialog();
    }

    @Override
    public void showLoadingDialog(FragmentManager manager) {
        checkOrCreateLoadingDfgable();
        if (getDialogFragment().isVisible()) {
            return;
        }
        if (mModeType == Constants.ModeType.TYPE_FRAGMENT) {
            hideLoadingDialog(manager);
            removeLoadingDialog(manager);
            mLoadingDfgable = LoadingDialogFragment.newInstance(mOption);
        }
        mLoadingDfgable.showLoadingDialog(manager);
        mModeType = Constants.ModeType.TYPE_DIALOG;
    }

    @Override
    public void dismissLoadingDialog(boolean allowStateLoss) {
        if (mLoadingDfgable == null) {
            return;
        }
        mLoadingDfgable.dismissLoadingDialog(allowStateLoss);
        mLoadingDfgable = null;
        mModeType = Constants.ModeType.TYPE_NOT_SET;
    }

    @Override
    public void release() {
        if (mLoadingDfgable == null) {
            return;
        }
        mLoadingDfgable.release();
        mLoadingDfgable = null;
        mModeType = Constants.ModeType.TYPE_NOT_SET;
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
            mLoadingDfgable.dismissLoadingDialog(true);
            mLoadingDfgable = null;
        }
        checkOrCreateLoadingDfgable();
        mOption.setThemeType(themeType);
        mLoadingDfgable = LoadingDfgProvideFactory.newInstance(mOption);
    }

    @Override
    public void addOrShowLoadingDialog(FragmentManager manager, @IdRes int containerId, LoadingDialogFragmentOption option) {
        if (manager == null || getDialogFragment().isVisible()) {
            return;
        }
        if (option != null) {
            mOption = option;
        }
        if (mModeType == Constants.ModeType.TYPE_DIALOG) {
            dismissLoadingDialog(true);
        }
        mLoadingDfgable = LoadingDialogFragment.newInstance(mOption);
        FragmentTransaction transaction = manager.beginTransaction();
        if (getDialogFragment().isAdded()) {
            transaction.show(getDialogFragment()).commitAllowingStateLoss();
        } else {
            transaction.add(containerId, getDialogFragment()).commitAllowingStateLoss();
        }
        mModeType = Constants.ModeType.TYPE_FRAGMENT;
    }

    @Override
    public void hideLoadingDialog(FragmentManager manager) {
        if (manager == null || mModeType == Constants.ModeType.TYPE_DIALOG ||
                !getDialogFragment().isAdded() || getDialogFragment().isHidden()) {
            return;
        }
        manager.beginTransaction().hide(getDialogFragment()).commitAllowingStateLoss();
    }

    @Override
    public void removeLoadingDialog(FragmentManager manager) {
        removeLoadingDialog(manager, getDialogFragment());
    }

    @Override
    public void removeLoadingDialog(FragmentManager manager, DialogFragment dialogFragment) {
        if (mModeType == Constants.ModeType.TYPE_DIALOG) {
            return;
        }
        FragmentUtils.removeFragments(manager, true, dialogFragment);
        mLoadingDfgable = null;
    }

    @Override
    public void resetLoadingDialog() {
        if (mLoadingDfgable != null) {
            mLoadingDfgable.dismissLoadingDialog(true);
            mLoadingDfgable = null;
        }
        mOption = null;
    }

    @Override
    public DialogFragment getDialogFragment() {
        checkOrCreateLoadingDfgable();
        return mLoadingDfgable.getDialogFragment();
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
