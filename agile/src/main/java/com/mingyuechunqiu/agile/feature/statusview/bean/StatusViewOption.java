package com.mingyuechunqiu.agile.feature.statusview.bean;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mingyuechunqiu.agile.feature.statusview.framework.IStatusViewContainer;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2019/1/23
 *     desc   : 状态视图配置信息类
 *     version: 1.0
 * </pre>
 */
public class StatusViewOption {

    private Builder mBuilder;

    public StatusViewOption() {
        this(new Builder());
    }

    public StatusViewOption(@NonNull Builder builder) {
        mBuilder = builder;
    }

    public IStatusViewContainer getStatusViewContainer() {
        return mBuilder.container;
    }

    public void setStatusViewContainer(@Nullable IStatusViewContainer container) {
        mBuilder.container = container;
    }

    public boolean isCancelWithOutside() {
        return mBuilder.cancelWithOutside;
    }

    public void setCancelWithOutside(boolean cancelWithOutside) {
        mBuilder.cancelWithOutside = cancelWithOutside;
    }

    public int getDialogWidth() {
        return mBuilder.dialogWidth;
    }

    public void setDialogWidth(int dialogWidth) {
        mBuilder.dialogWidth = dialogWidth;
    }

    public int getDialogHeight() {
        return mBuilder.dialogHeight;
    }

    public void setDialogHeight(int dialogHeight) {
        mBuilder.dialogHeight = dialogHeight;
    }

    public boolean isShowStatusViewFloating() {
        return mBuilder.showStatusViewFloating;
    }

    public void setShowStatusViewFloating(boolean showStatusViewFloating) {
        mBuilder.showStatusViewFloating = showStatusViewFloating;
    }

    public int getContainerBackgroundResId() {
        return mBuilder.containerBackgroundResId;
    }

    public void setContainerBackgroundResId(@DrawableRes int containerBackgroundResId) {
        mBuilder.containerBackgroundResId = containerBackgroundResId;
    }

    public Drawable getContainerBackground() {
        return mBuilder.containerBackground;
    }

    public void setContainerBackground(@NonNull Drawable containerBackground) {
        mBuilder.containerBackground = containerBackground;
    }

    public Drawable getProgressDrawable() {
        return mBuilder.progressDrawable;
    }

    public void setProgressDrawable(@NonNull Drawable progressDrawable) {
        mBuilder.progressDrawable = progressDrawable;
    }

    public Drawable getReloadDrawable() {
        return mBuilder.reloadDrawable;
    }

    public void setReloadDrawable(@NonNull Drawable reloadDrawable) {
        mBuilder.reloadDrawable = reloadDrawable;
    }

    public int getReloadDrawableResId() {
        return mBuilder.reloadDrawableResId;
    }

    public void setReloadDrawableResId(@DrawableRes int reloadDrawableResId) {
        mBuilder.reloadDrawableResId = reloadDrawableResId;
    }

    public boolean isShowProgressView() {
        return mBuilder.showProgressView;
    }

    public void setShowProgressView(boolean showProgressView) {
        mBuilder.showProgressView = showProgressView;
    }

    public boolean isShowReloadIcon() {
        return mBuilder.showReloadIcon;
    }

    public void setShowReloadIcon(boolean showReloadIcon) {
        mBuilder.showReloadIcon = showReloadIcon;
    }

    public boolean isShowContentText() {
        return mBuilder.showContentText;
    }

    public void setShowContentText(boolean showContentText) {
        mBuilder.showContentText = showContentText;
    }

    public boolean isShowReloadText() {
        return mBuilder.showReloadText;
    }

    public void setShowReloadText(boolean showReloadText) {
        mBuilder.showReloadText = showReloadText;
    }

    public StatusViewTextOption getContentOption() {
        return mBuilder.contentOption;
    }

    public void setContentOption(@Nullable StatusViewTextOption contentOption) {
        mBuilder.contentOption = contentOption;
    }

    public StatusViewTextOption getReloadOption() {
        return mBuilder.reloadOption;
    }

    public void setReloadOption(@Nullable StatusViewTextOption reloadOption) {
        mBuilder.reloadOption = reloadOption;
    }

    public OnStatusViewDialogListener getOnStatusViewDialogListener() {
        return mBuilder.dialogListener;
    }

    public void setOnStatusViewDialogListener(@Nullable OnStatusViewDialogListener listener) {
        mBuilder.dialogListener = listener;
    }

    public OnStatusViewButtonListener getOnStatusViewButtonListener() {
        return mBuilder.buttonListener;
    }

    public void setOnStatusViewButtonListener(@Nullable OnStatusViewButtonListener buttonListener) {
        mBuilder.buttonListener = buttonListener;
    }

    /**
     * 链式调用
     */
    public static class Builder {

        private IStatusViewContainer container;//对话框布局容器
        private boolean cancelWithOutside;//是否能触摸外围区域取消对话框
        private int dialogWidth, dialogHeight;//对话框宽高
        private boolean showStatusViewFloating;//是否设置状态视图悬浮
        private @DrawableRes
        int containerBackgroundResId;//容器背景图像资源ID
        private Drawable containerBackground;//容器背景图像
        private Drawable progressDrawable;//无进度图像
        private Drawable reloadDrawable;//重新加载图片
        private @DrawableRes
        int reloadDrawableResId;//重新加载图片资源ID
        private boolean showProgressView;//是否显示进度控件
        private boolean showReloadIcon;//是否显示重新加载图标
        private boolean showContentText;//是否显示内容文本（默认显示）
        private boolean showReloadText;//是否显示加载文本
        private StatusViewTextOption contentOption;//内容配置信息对象
        private StatusViewTextOption reloadOption;//重新加载配置对象
        private OnStatusViewDialogListener dialogListener;//加载相关监听器
        private OnStatusViewButtonListener buttonListener;//按钮监听器

        public Builder() {
            showProgressView = false;
            showReloadIcon = false;
            showContentText = true;
            showReloadText = false;
        }

        public StatusViewOption build() {
            return new StatusViewOption(this);
        }

        public IStatusViewContainer getStatusViewContainer() {
            return container;
        }

        public Builder setStatusViewContainer(@Nullable IStatusViewContainer container) {
            this.container = container;
            return this;
        }

        public boolean isCancelWithOutside() {
            return cancelWithOutside;
        }

        public Builder setCancelWithOutside(boolean cancelWithOutside) {
            this.cancelWithOutside = cancelWithOutside;
            return this;
        }

        public int getDialogWidth() {
            return dialogWidth;
        }

        public Builder setDialogWidth(int dialogWidth) {
            this.dialogWidth = dialogWidth;
            return this;
        }

        public int getDialogHeight() {
            return dialogHeight;
        }

        public Builder setDialogHeight(int dialogHeight) {
            this.dialogHeight = dialogHeight;
            return this;
        }

        public boolean isShowStatusViewFloating() {
            return showStatusViewFloating;
        }

        public Builder setShowStatusViewFloating(boolean showStatusViewFloating) {
            this.showStatusViewFloating = showStatusViewFloating;
            return this;
        }

        public int getContainerBackgroundResId() {
            return containerBackgroundResId;
        }

        public Builder setContainerBackgroundResId(@DrawableRes int containerBackgroundResId) {
            this.containerBackgroundResId = containerBackgroundResId;
            return this;
        }

        public Drawable getContainerBackground() {
            return containerBackground;
        }

        public Builder setContainerBackground(@NonNull Drawable containerBackground) {
            this.containerBackground = containerBackground;
            return this;
        }

        public Drawable getProgressDrawable() {
            return progressDrawable;
        }

        public Builder setProgressDrawable(@NonNull Drawable progressDrawable) {
            this.progressDrawable = progressDrawable;
            return this;
        }

        public Drawable getReloadDrawable() {
            return reloadDrawable;
        }

        public Builder setReloadDrawable(@NonNull Drawable reloadDrawable) {
            this.reloadDrawable = reloadDrawable;
            return this;
        }

        public int getReloadDrawableResId() {
            return reloadDrawableResId;
        }

        public Builder setReloadDrawableResId(@DrawableRes int reloadDrawableResId) {
            this.reloadDrawableResId = reloadDrawableResId;
            return this;
        }

        public boolean isShowProgressView() {
            return showProgressView;
        }

        public Builder setShowProgressView(boolean showProgressView) {
            this.showProgressView = showProgressView;
            return this;
        }

        public boolean isShowReloadIcon() {
            return showReloadIcon;
        }

        public Builder setShowReloadIcon(boolean showReloadIcon) {
            this.showReloadIcon = showReloadIcon;
            return this;
        }

        public boolean isShowContentText() {
            return showContentText;
        }

        public Builder setShowContentText(boolean showContentText) {
            this.showContentText = showContentText;
            return this;
        }

        public boolean isShowReloadText() {
            return showReloadText;
        }

        public Builder setShowReloadText(boolean showReloadText) {
            this.showReloadText = showReloadText;
            return this;
        }

        public StatusViewTextOption getContentOption() {
            return contentOption;
        }

        public Builder setContentOption(@Nullable StatusViewTextOption contentOption) {
            this.contentOption = contentOption;
            return this;
        }

        public StatusViewTextOption getReloadOption() {
            return reloadOption;
        }

        public Builder setReloadOption(@Nullable StatusViewTextOption reloadOption) {
            this.reloadOption = reloadOption;
            return this;
        }

        public OnStatusViewDialogListener getOnStatusViewDialogListener() {
            return dialogListener;
        }

        public Builder setOnStatusViewDialogListener(@Nullable OnStatusViewDialogListener listener) {
            this.dialogListener = listener;
            return this;
        }

        public OnStatusViewButtonListener getOnStatusViewButtonListener() {
            return buttonListener;
        }

        public Builder setOnStatusViewButtonListener(@Nullable OnStatusViewButtonListener buttonListener) {
            this.buttonListener = buttonListener;
            return this;
        }
    }

    /**
     * 加载相关监听器
     */
    public interface OnStatusViewDialogListener {

        /**
         * 当点击返回键时回调
         *
         * @param dialog 对话框实例
         * @return 拦截返回键事件返回true，否则返回false
         */
        boolean onClickKeyBack(DialogInterface dialog);

        /**
         * 当对话框消失时回调
         *
         * @param dialogFragment 对话框实例
         */
        void onDismissListener(DialogFragment dialogFragment);

    }

    /**
     * 状态视图按钮监听器
     */
    public interface OnStatusViewButtonListener {

        /**
         * 当点击重新加载按钮时回调
         */
        void onClickReload();
    }
}