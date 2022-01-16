package com.mingyuechunqiu.agile.feature.helper.ui.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author : MingYueChunQiu
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/12/26
 *     desc   : 软键盘状态改变监听器
 *     version: 1.0
 * </pre>
 */
public final class SoftInputKeyBoardHelper {

    private final WeakReference<Activity> mActivityRef;
    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;
    private boolean removeStatusHeight;//标记是否移除状态栏高度
    private boolean changeContentHeight;//标记是否随软键盘弹出更改内容视图高度
    private OnSoftKeyBoardChangeListener mListener;

    private SoftInputKeyBoardHelper(@NonNull Activity activity) {
        mActivityRef = new WeakReference<>(activity);
    }

    @NonNull
    public static SoftInputKeyBoardHelper newInstance(@NonNull Activity activity) {
        return newInstance(activity, false);
    }

    @NonNull
    public static SoftInputKeyBoardHelper newInstance(@NonNull Activity activity, boolean removeStatusHeight) {
        return newInstance(activity, true, removeStatusHeight);
    }

    @NonNull
    public static SoftInputKeyBoardHelper newInstance(@NonNull Activity activity, boolean changeContentHeight,
                                                      boolean removeStatusHeight) {
        SoftInputKeyBoardHelper helper = new SoftInputKeyBoardHelper(activity);
        helper.changeContentHeight = changeContentHeight;
        helper.removeStatusHeight = removeStatusHeight;
        return helper;
    }

    public boolean isRemoveStatusHeight() {
        return removeStatusHeight;
    }

    @NonNull
    public SoftInputKeyBoardHelper setRemoveStatusHeight(boolean removeStatusHeight) {
        this.removeStatusHeight = removeStatusHeight;
        return this;
    }

    public boolean isChangeContentHeight() {
        return changeContentHeight;
    }

    @NonNull
    public SoftInputKeyBoardHelper setChangeContentHeight(boolean changeContentHeight) {
        this.changeContentHeight = changeContentHeight;
        return this;
    }

    @Nullable
    public OnSoftKeyBoardChangeListener getOnSoftKeyBoardChangeListener() {
        return mListener;
    }

    @NonNull
    public SoftInputKeyBoardHelper setOnSoftKeyBoardChangeListener(@NonNull OnSoftKeyBoardChangeListener listener) {
        mListener = listener;
        return this;
    }

    public void init() {
        if (mActivityRef.get() == null) {
            return;
        }
        FrameLayout content = mActivityRef.get().findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        possiblyResizeChildOfContent();
                    }
                });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent
                .getLayoutParams();
    }

    /**
     * 重新布局内容视图
     */
    private void possiblyResizeChildOfContent() {
        if (mActivityRef.get() == null) {
            return;
        }
        //当前内容显示高度
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            //内容实际高度
            int usableHeightSansKeyboard = mChildOfContent.getRootView()
                    .getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard probably just became visible
                if (changeContentHeight) {
                    frameLayoutParams.height = usableHeightSansKeyboard
                            - heightDifference;
                    if (removeStatusHeight) {
                        frameLayoutParams.height += ScreenHelper.getStatusBarHeight(mActivityRef.get());
                    }
                }
                if (mListener != null) {
                    mListener.onShowSoftKeyBoard(heightDifference);
                }
            } else {
                // keyboard probably just became hidden
                if (changeContentHeight) {
                    frameLayoutParams.height = usableHeightSansKeyboard;
                    if (ScreenHelper.judgeWindowHasNavigationBar(mActivityRef.get(), isScreenPortrait())) {
                        //要减去底部导航栏高度，否则在有导航栏情况下，会导致布局延伸到导航栏里面
                        frameLayoutParams.height -= ScreenHelper.getNavigationBarHeight(mActivityRef.get());
                    }
                }
                if (mListener != null) {
                    mListener.onHideSoftKeyBoard();
                }
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    /**
     * 计算内容视图高度
     *
     * @return 返回content视图高度
     */
    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }

    /**
     * 判断屏幕是否是竖屏
     *
     * @return 如果是竖屏返回true，否则返回false
     */
    @SuppressLint("SwitchIntDef")
    public boolean isScreenPortrait() {
        if (mActivityRef.get() == null) {
            return true;
        }
        switch (mActivityRef.get().getRequestedOrientation()) {
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:// 横屏
            case ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE:
                return false;
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:// 竖屏
            case ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT:
                return true;
            default:
                return true;
        }
    }

    /**
     * 软键盘变化监听器
     */
    public interface OnSoftKeyBoardChangeListener {

        /**
         * 当软键盘显示时调用
         *
         * @param softInputHeight 软键盘高度
         */
        void onShowSoftKeyBoard(int softInputHeight);

        /**
         * 当隐藏软键盘时调用
         */
        void onHideSoftKeyBoard();
    }
}
