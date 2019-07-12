package com.mingyuechunqiu.agile.framework.function;

import android.app.Activity;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.mingyuechunqiu.agile.util.ScreenUtils;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/12/26
 *     desc   : 软键盘状态改变监听器
 *     version: 1.0
 * </pre>
 */
public class SoftInputKeyBoardHelper {

    private WeakReference<Activity> mActivityRef;
    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;
    private boolean removeStatusHeight;//标记是否移除状态栏高度
    private boolean changeContentHeight;//标记是否随软键盘弹出更改内容视图高度
    private OnSoftKeyBoardChangeListener mListener;

    @NonNull
    public static SoftInputKeyBoardHelper getInstance(Activity activity) {
        return getInstance(activity, false);
    }

    @NonNull
    public static SoftInputKeyBoardHelper getInstance(Activity activity, boolean removeStatusHeight) {
        return getInstance(activity, true, removeStatusHeight);
    }

    @NonNull
    public static SoftInputKeyBoardHelper getInstance(Activity activity, boolean changeContentHeight,
                                                      boolean removeStatusHeight) {
        SoftInputKeyBoardHelper listener = new SoftInputKeyBoardHelper(activity);
        listener.changeContentHeight = changeContentHeight;
        listener.removeStatusHeight = removeStatusHeight;
        return listener;
    }

    private SoftInputKeyBoardHelper(Activity activity) {
        mActivityRef = new WeakReference<>(activity);
    }

    public boolean isRemoveStatusHeight() {
        return removeStatusHeight;
    }

    public void setRemoveStatusHeight(boolean removeStatusHeight) {
        this.removeStatusHeight = removeStatusHeight;
    }

    public boolean isChangeContentHeight() {
        return changeContentHeight;
    }

    public void setChangeContentHeight(boolean changeContentHeight) {
        this.changeContentHeight = changeContentHeight;
    }

    public OnSoftKeyBoardChangeListener getOnSoftKeyBoardChangeListener() {
        return mListener;
    }

    public void setOnSoftKeyBoardChangeListener(OnSoftKeyBoardChangeListener listener) {
        mListener = listener;
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
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView()
                    .getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard probably just became visible
                if (changeContentHeight) {
                    frameLayoutParams.height = usableHeightSansKeyboard
                            - heightDifference;
                    if (removeStatusHeight) {
                        frameLayoutParams.height += ScreenUtils.getStatusBarHeight(mActivityRef.get());
                    }
                }
                if (mListener != null) {
                    mListener.onShowSoftKeyBoard(heightDifference);
                }
            } else {
                // keyboard probably just became hidden
                if (changeContentHeight) {
                    frameLayoutParams.height = usableHeightSansKeyboard;
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
