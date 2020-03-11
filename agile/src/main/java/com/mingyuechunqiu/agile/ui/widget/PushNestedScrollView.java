package com.mingyuechunqiu.agile.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;

import com.mingyuechunqiu.agile.R;

/**
 * <pre>
 *      Project:    PalmGoldenEagle
 *      Github :    https://github.com/MingYueChunQiu
 *      Author:     XiYuJie
 *      Email:      xiyujie@jinying.com
 *      Time:       2019/12/5 9:29
 *      Desc:       可推动嵌套滚动控件（该控件内部默认只嵌套一个子控件，内部针对RecyclerView做嵌套滑动处理）
 *                  继承自FrameLayout
 *      Version:    1.0
 * </pre>
 */
public class PushNestedScrollView extends LinearLayoutCompat implements NestedScrollingParent2, NestedScrollingChild2 {

    private final NestedScrollingParentHelper mParentHelper;
    private final NestedScrollingChildHelper mChildHelper;

    private PushScrollCallback mCallback;
    private boolean openPullEffect;//标记是否打开可以推拉效果

    public PushNestedScrollView(@NonNull Context context) {
        this(context, null);
    }

    public PushNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PushNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PushNestedScrollView);
        openPullEffect = a.getBoolean(R.styleable.PushNestedScrollView_pnsv_open_pull_effect, true);
        a.recycle();

        mParentHelper = new NestedScrollingParentHelper(this);
        mChildHelper = new NestedScrollingChildHelper(this);

        // ...because why else would you be using this widget?
        setNestedScrollingEnabled(true);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        mParentHelper.onNestedScrollAccepted(child, target, axes, type);
        startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        mParentHelper.onStopNestedScroll(target, type);
        stopNestedScroll(type);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        if (type == ViewCompat.TYPE_NON_TOUCH && openPullEffect) {
            if (dyUnconsumed < 0 && mCallback != null) {
                handlePushDown(target, dyUnconsumed, null, mCallback.getPushScrollDistance());
            }
        }
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        dispatchNestedPreScroll(dx, dy, consumed, null, type);
        //dy向上是正，向下是负，因为是拿前一个点位置减去后一个点位置
        if (mCallback != null && openPullEffect) {
            int maxDistance = mCallback.getPushScrollDistance();
            if (dy > 0) {
                handlePushUp(dy, consumed, maxDistance);
            } else {
                handlePushDown(target, dy, consumed, maxDistance);
            }
        }
    }

    /**
     * 处理向上推动
     *
     * @param dy          用户触摸移动距离
     * @param consumed    记录当前控件消费距离
     * @param maxDistance 当前控件可消费总距离
     */
    private void handlePushUp(int dy, @NonNull int[] consumed, int maxDistance) {
        if (mCallback == null) {
            return;
        }
        int distance = dy;
        if (getScrollY() < maxDistance) {
            if ((getScrollY() + distance) > maxDistance) {
                distance = distance - (getScrollY() + distance - maxDistance);
            }
            scrollBy(0, distance);
            consumed[0] = 0;
            consumed[1] = distance;
            mCallback.onPushScroll(getScrollY(), maxDistance);
        }
    }

    /**
     * 处理向下推动
     *
     * @param dy          用户触摸移动距离
     * @param consumed    记录当前控件消费距离
     * @param maxDistance 当前控件可消费总距离
     */
    private void handlePushDown(@NonNull View target, int dy, @Nullable int[] consumed, int maxDistance) {
        if (mCallback == null || target.getId() != mCallback.getDependencyViewId() || !mCallback.canPushDown()) {
            return;
        }
        if (getScrollY() > 0) {
            int distance = dy;
            if ((getScrollY() + distance) < 0) {
                distance = distance - (getScrollY() + distance);
            }
            scrollBy(0, distance);
            if (consumed != null) {
                consumed[0] = 0;
                consumed[1] = distance;
            }
            mCallback.onPushScroll(getScrollY(), maxDistance);
        }
    }

    public void setPushScrollCallback(@NonNull PushScrollCallback callback) {
        mCallback = callback;
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        return mChildHelper.startNestedScroll(axes, type);
    }

    @Override
    public void stopNestedScroll(int type) {
        mChildHelper.stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return mChildHelper.hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
                offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    /**
     * 推动回调
     */
    public interface PushScrollCallback {

        /**
         * 推动依据的控件ID
         *
         * @return 返回控件资源ID
         */
        @IdRes
        int getDependencyViewId();

        /**
         * 是否可以向下滚动
         *
         * @return 返回true表示可以，否则返回false
         */
        boolean canPushDown();

        /**
         * 获取可推动距离
         *
         * @return 返回距离值
         */
        int getPushScrollDistance();

        /**
         * 当推动触发时回调
         *
         * @param distance      当前推动距离
         * @param totalDistance 总计可推动距离
         */
        void onPushScroll(int distance, int totalDistance);
    }
}