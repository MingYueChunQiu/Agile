package com.mingyuechunqiu.agile.feature.statusview.function;

import android.graphics.drawable.Drawable;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.util.FragmentUtils;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2019/1/24
 *     desc   : 加载Fragment对外提供类
 *              实现ILoadingManager
 *     version: 1.0
 * </pre>
 */
class StatusViewManager implements IStatusViewManager {

    private ILoadingHelper mLoadingHelper;
    private StatusViewOption mOption;
    private StatusViewConstants.ModeType mModeType;//当前所属模式

    StatusViewManager(ILoadingHelper helper) {
        mLoadingHelper = helper;
        checkOrCreateLoadingHelper();
        mOption = mLoadingHelper.getLoadingFragmentOption();
    }

    @Override
    public boolean showLoadingDialog() {
        checkOrCreateLoadingHelper();
        if (getDialogFragment().isVisible()) {
            return true;
        }
        if (mModeType == StatusViewConstants.ModeType.TYPE_FRAGMENT) {
            return false;
        }
        mModeType = StatusViewConstants.ModeType.TYPE_DIALOG;
        return mLoadingHelper.showLoadingDialog();
    }

    @Override
    public void showLoadingDialog(FragmentManager manager) {
        checkOrCreateLoadingHelper();
        if (getDialogFragment().isVisible()) {
            return;
        }
        if (mModeType == StatusViewConstants.ModeType.TYPE_FRAGMENT) {
            hideLoadingDialog(manager);
            removeLoadingDialog(manager);
            mLoadingHelper = LoadingDialogFragment.newInstance(mOption);
        }
        mLoadingHelper.showLoadingDialog(manager);
        mModeType = StatusViewConstants.ModeType.TYPE_DIALOG;
    }

    @Override
    public void dismissLoadingDialog(boolean allowStateLoss) {
        if (mLoadingHelper == null) {
            return;
        }
        mLoadingHelper.dismissLoadingDialog(allowStateLoss);
        mLoadingHelper = null;
        mModeType = StatusViewConstants.ModeType.TYPE_NOT_SET;
    }

    @Override
    public void release() {
        if (mLoadingHelper == null) {
            return;
        }
        mLoadingHelper.release();
        mLoadingHelper = null;
        mModeType = StatusViewConstants.ModeType.TYPE_NOT_SET;
    }

    @Override
    public void setCanCancelWithOutside(boolean canCancelWithOutside) {
        checkOrCreateLoadingHelper();
        mLoadingHelper.setCanCancelWithOutside(canCancelWithOutside);
    }

    @Override
    public void setDialogSize(int width, int height) {
        checkOrCreateLoadingHelper();
        mLoadingHelper.setDialogSize(width, height);
    }

    @Override
    public void setLoadingBackground(Drawable drawable) {
        checkOrCreateLoadingHelper();
        mLoadingHelper.setLoadingBackground(drawable);
    }

    @Override
    public void setIndeterminateProgressDrawable(Drawable drawable) {
        checkOrCreateLoadingHelper();
        mLoadingHelper.setIndeterminateProgressDrawable(drawable);
    }

    @Override
    public void setShowLoadingMessage(boolean showLoadingMessage) {
        checkOrCreateLoadingHelper();
        mLoadingHelper.setShowLoadingMessage(showLoadingMessage);
    }

    @Override
    public void setLoadingMessageBackground(Drawable drawable) {
        checkOrCreateLoadingHelper();
        mLoadingHelper.setLoadingMessageBackground(drawable);
    }

    @Override
    public void setLoadingMessage(@Nullable String msg) {
        checkOrCreateLoadingHelper();
        mLoadingHelper.setLoadingMessage(msg);
    }

    @Override
    public void setLoadingMessageColor(int color) {
        checkOrCreateLoadingHelper();
        mLoadingHelper.setLoadingMessageColor(color);
    }

    @Override
    public void setLoadingMessageTextAppearance(@StyleRes int textAppearance) {
        checkOrCreateLoadingHelper();
        mLoadingHelper.setLoadingMessageTextAppearance(textAppearance);
    }

    @Override
    public void setOnLoadingOptionListener(StatusViewOption.OnLoadingOptionListener listener) {
        checkOrCreateLoadingHelper();
        mLoadingHelper.setOnLoadingOptionListener(listener);
    }

    @Override
    public void setLoadingFragmentOption(StatusViewOption option) {
        checkOrCreateLoadingHelper();
        mOption = option;
        mLoadingHelper.setLoadingFragmentOption(option);
    }

    @NonNull
    @Override
    public StatusViewOption getLoadingFragmentOption() {
        checkOrCreateLoadingHelper();
        return mLoadingHelper.getLoadingFragmentOption();
    }

    @Override
    public void showLoadingDialog(FragmentManager manager, @IdRes int containerId, StatusViewOption option) {
        if (manager == null || getDialogFragment().isVisible()) {
            return;
        }
        if (option != null) {
            mOption = option;
        }
        if (mModeType == StatusViewConstants.ModeType.TYPE_DIALOG) {
            dismissLoadingDialog(true);
        }
        mLoadingHelper = LoadingDialogFragment.newInstance(mOption);
        FragmentTransaction transaction = manager.beginTransaction();
        if (getDialogFragment().isAdded()) {
            transaction.show(getDialogFragment()).commitAllowingStateLoss();
        } else {
            transaction.add(containerId, getDialogFragment()).commitAllowingStateLoss();
        }
        mModeType = StatusViewConstants.ModeType.TYPE_FRAGMENT;
    }

    @Override
    public void hideLoadingDialog(FragmentManager manager) {
        if (manager == null || mModeType == StatusViewConstants.ModeType.TYPE_DIALOG ||
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
        if (mModeType == StatusViewConstants.ModeType.TYPE_DIALOG) {
            return;
        }
        FragmentUtils.removeFragments(manager, true, dialogFragment);
        mLoadingHelper = null;
    }

    @Override
    public void resetLoadingDialog() {
        if (mLoadingHelper != null) {
            mLoadingHelper.dismissLoadingDialog(true);
            mLoadingHelper = null;
        }
        mOption = null;
    }

    @Override
    public DialogFragment getDialogFragment() {
        checkOrCreateLoadingHelper();
        return mLoadingHelper.getDialogFragment();
    }

    /**
     * 检测对象是否存在，若不存在则创建
     */
    private void checkOrCreateLoadingHelper() {
        if (mLoadingHelper == null) {
            mLoadingHelper = LoadingDialogFragment.newInstance(mOption);
        } else {
            //因为用户通过触摸外围区域关闭对话框的话，mLoadingHelper不会置为空，会导致重新
            //内部生成Delegate并使用新的option，所以要设置一下
            mLoadingHelper.setLoadingFragmentOption(mOption);
        }
    }
}
