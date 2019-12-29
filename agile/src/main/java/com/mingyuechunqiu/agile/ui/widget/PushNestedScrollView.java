package com.mingyuechunqiu.agile.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
public class PushNestedScrollView extends FrameLayout implements NestedScrollingParent3, NestedScrollingChild3 {

    private final NestedScrollingParentHelper mParentHelper;
    private final NestedScrollingChildHelper mChildHelper;

    private int mDistance = 0;
    private PushScrollCallback mCallback;
    private boolean pullDownDirectly;//标记是否可以直接拉下

    public PushNestedScrollView(@NonNull Context context) {
        this(context, null);
    }

    public PushNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PushNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PushNestedScrollView);
        pullDownDirectly = a.getBoolean(R.styleable.PushNestedScrollView_pnsv_pull_down_directly, false);
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
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        dispatchNestedPreScroll(dx, dy, consumed, null, type);

        int distance;//实际移动距离
        //dy向上是正，向下是负，因为是那前一个点位置减去后一个点位置
        if (mCallback != null) {
            int length = mCallback.getMaxPushScrollDistance();
            if (dy > 0) {
                if (mDistance < length) {
                    if (mDistance + dy > length) {
                        distance = length - mDistance;
                        mDistance = length;
                    } else {
                        distance = dy;
                        mDistance += dy;
                    }
                    ViewGroup group = (ViewGroup) getChildAt(0);
                    for (int i = 0; i < group.getChildCount(); i++) {
                        group.getChildAt(i).setTranslationY(-mDistance);
                    }
                    consumed[0] = 0;
                    consumed[1] = distance;
                    mCallback.onPushScroll(mDistance, length);
                }
            } else {
                if (mDistance > 0) {
                    ViewGroup group = (ViewGroup) getChildAt(0);
                    boolean canScroll = false;//是否可以向下滚动，只有RecyclerView最上面Item完全划出时才开始滑回原位置
                    if (pullDownDirectly) {
                        canScroll = true;
                    } else {
                        for (int i = 0; i < group.getChildCount(); i++) {
                            View child = group.getChildAt(i);
                            if (child instanceof RecyclerView) {
                                if (((RecyclerView) child).getLayoutManager() instanceof LinearLayoutManager) {
                                    LinearLayoutManager manager = (LinearLayoutManager) ((RecyclerView) child).getLayoutManager();
                                    if (manager != null && manager.findFirstCompletelyVisibleItemPosition() == 0) {
                                        canScroll = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (canScroll) {
                        if (mDistance + dy < 0) {
                            distance = mDistance;
                            mDistance = 0;
                        } else {
                            distance = dy;
                            mDistance += distance;
                        }
                        for (int i = 0; i < group.getChildCount(); i++) {
                            group.getChildAt(i).setTranslationY(-mDistance);
                        }
                        consumed[0] = 0;
                        consumed[1] = distance;
                        mCallback.onPushScroll(mDistance, length);
                    }
                }
            }
        }
    }

    public void setPushScrollCallback(PushScrollCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {

    }

    @Override
    public void dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type, @NonNull int[] consumed) {
        mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
                offsetInWindow, type, consumed);
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

    public interface PushScrollCallback {

        /**
         * 获取推注滚动的最大距离
         *
         * @return 返回最大距离
         */
        int getMaxPushScrollDistance();

        /**
         * 当推动滚动时回调
         *
         * @param distance      当前滚动距离
         * @param totalDistance 总计已经滚动距离
         */
        void onPushScroll(int distance, int totalDistance);
    }
}
