package com.mingyuechunqiu.agile.feature.statusview.bean;

import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.mingyuechunqiu.agile.feature.statusview.ui.IStatusViewContainer;
import com.mingyuechunqiu.agile.feature.statusview.ui.view.IStatusView;

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

    private final Builder mBuilder;

    public StatusViewOption() {
        this(new Builder());
    }

    public StatusViewOption(@NonNull Builder builder) {
        mBuilder = builder;
    }

    @NonNull
    public Builder getBuilder() {
        return mBuilder;
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

    public boolean isBackWithDismiss() {
        return mBuilder.backWithDismiss;
    }

    public void setBackWithDismiss(boolean backWithDismiss) {
        mBuilder.backWithDismiss = backWithDismiss;
    }

    public float getDialogDimAmount() {
        return mBuilder.dialogDimAmount;
    }

    public void setDialogDimAmount(@FloatRange(from = 0.0, to = 1.0) float dialogDimAmount) {
        mBuilder.dialogDimAmount = dialogDimAmount;
    }

    @StyleRes
    public int getDialogAnimationResId() {
        return mBuilder.dialogAnimationResId;
    }

    public void setDialogAnimationResId(@StyleRes int dialogAnimationResId) {
        mBuilder.dialogAnimationResId = dialogAnimationResId;
    }

    public float getContainerElevation() {
        return mBuilder.containerElevation;
    }

    public void setContainerElevation(float containerElevation) {
        mBuilder.containerElevation = containerElevation;
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

    public StatusViewProgressOption getProgressOption() {
        return mBuilder.progressOption;
    }

    public void setProgressOption(@NonNull StatusViewProgressOption progressOption) {
        mBuilder.progressOption = progressOption;
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
        private boolean backWithDismiss;//是否按返回键时视图消失（当设置OnStatusViewDialogListener时，backWithDismiss属性失效）
        @FloatRange(from = 0.0, to = 1.0)
        private float dialogDimAmount;//对话框背景遮罩
        private @StyleRes
        int dialogAnimationResId;//对话框动画资源ID
        private float containerElevation;//容器阴影大小
        private @DrawableRes
        int containerBackgroundResId;//容器背景图像资源ID
        private Drawable containerBackground;//容器背景图像
        private StatusViewProgressOption progressOption;//进度条配置信息对象
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
            backWithDismiss = false;
            showProgressView = false;
            showReloadIcon = false;
            showContentText = true;
            showReloadText = false;
            dialogDimAmount = 0.5F;
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

        public boolean isBackWithDismiss() {
            return backWithDismiss;
        }

        public Builder setBackWithDismiss(boolean backWithDismiss) {
            this.backWithDismiss = backWithDismiss;
            return this;
        }

        public float getDialogDimAmount() {
            return dialogDimAmount;
        }

        public Builder setDialogDimAmount(@FloatRange(from = 0.0, to = 1.0) float dialogDimAmount) {
            this.dialogDimAmount = dialogDimAmount;
            return this;
        }

        @StyleRes
        public int getDialogAnimationResId() {
            return dialogAnimationResId;
        }

        public Builder setDialogAnimationResId(@StyleRes int dialogAnimationResId) {
            this.dialogAnimationResId = dialogAnimationResId;
            return this;
        }

        public float getContainerElevation() {
            return containerElevation;
        }

        public Builder setContainerElevation(float containerElevation) {
            this.containerElevation = containerElevation;
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

        public StatusViewProgressOption getProgressOption() {
            return progressOption;
        }

        public Builder setProgressOption(@NonNull StatusViewProgressOption progressOption) {
            this.progressOption = progressOption;
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
         * @param view 状态视图实例
         * @return 拦截返回键事件返回true，否则返回false
         */
        boolean onClickKeyBack(@NonNull IStatusView view);

        /**
         * 当对话框消失时回调
         *
         * @param view 状态视图实例
         */
        void onDismissListener(@NonNull IStatusView view);

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
