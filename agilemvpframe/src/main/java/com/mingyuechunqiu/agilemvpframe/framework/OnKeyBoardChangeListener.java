package com.mingyuechunqiu.agilemvpframe.framework;

import android.app.Activity;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.mingyuechunqiu.agilemvpframe.util.ScreenUtils;

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
public class OnKeyBoardChangeListener {

    private WeakReference<Activity> mActivityRef;
    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;
    private boolean removeStatusHeight;//标记是否移除状态栏高度

    @NonNull
    public static OnKeyBoardChangeListener getInstance(Activity activity, boolean removeStatusHeight) {
        OnKeyBoardChangeListener listener = new OnKeyBoardChangeListener(activity);
        listener.removeStatusHeight = removeStatusHeight;
        return listener;
    }

    private OnKeyBoardChangeListener(Activity activity) {
        mActivityRef = new WeakReference<>(activity);
    }

    public boolean isRemoveStatusHeight() {
        return removeStatusHeight;
    }

    public void setRemoveStatusHeight(boolean removeStatusHeight) {
        this.removeStatusHeight = removeStatusHeight;
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
                frameLayoutParams.height = usableHeightSansKeyboard
                        - heightDifference;
                if (removeStatusHeight) {
                    frameLayoutParams.height += ScreenUtils.getStatusBarHeight(mActivityRef.get());
                }
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard;
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

}
