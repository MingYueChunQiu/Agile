package com.mingyuechunqiu.agile.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mingyuechunqiu.agile.R;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020/4/19 9:25 PM
 *      Desc:       回弹容器控件
 *                  继承自FrameLayout
 *      Version:    1.0
 * </pre>
 */
public class ReboundViewGroup extends FrameLayout implements NestedScrollingParent2, NestedScrollingParent3, NestedScrollingChild2, NestedScrollingChild3 {

    public static final int ORIENTATION_VERTICAL = 0;//垂直
    public static final int ORIENTATION_HORIZONTAL = 1;//水平

    private NestedScrollingParentHelper mParentHelper = new NestedScrollingParentHelper(this);
    private NestedScrollingChildHelper mChildHelper = new NestedScrollingChildHelper(this);
    private OverScroller mScroller;
    private float mHeaderOffsetHeight, mFooterOffsetHeight;
    private int mReboundOrientation = ORIENTATION_VERTICAL;
    private int mReboundDuration = 800;//回弹动画时间
    private float mTotalOffset;//总计偏移量（不适用scrollX或scrollY，因为fling事件时需要只滑动阻尼部分，但要消耗所有值，否则会一直传递fling值）

    public ReboundViewGroup(Context context) {
        this(context, null);
    }

    public ReboundViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReboundViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            //不能在onStartNestedScroll里做，否则手指滑动和fling都会触发该方法
            //RecyclerView如果是内部item有滑动并触发fling事件的话，会消耗掉ACTION_UP和ACTION_CANCEL事件
            if (!mScroller.isFinished()) {
                mScroller.abortAnimation();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return (axes & (mReboundOrientation == ORIENTATION_VERTICAL ? SCROLL_AXIS_VERTICAL : SCROLL_AXIS_HORIZONTAL)) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        mParentHelper.onNestedScrollAccepted(child, target, axes, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        mParentHelper.onStopNestedScroll(target, type);
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
        if (mReboundOrientation == ORIENTATION_VERTICAL && getScrollY() != 0) {
            mScroller.startScroll(0, getScrollY(), 0, -getScrollY(), mReboundDuration);
            invalidate();
        }
        if (mReboundOrientation == ORIENTATION_HORIZONTAL && getScrollX() != 0) {
            mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, mReboundDuration);
            invalidate();
        }
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
        if (!(target instanceof RecyclerView)) {
            return;
        }
        if (type != ViewCompat.TYPE_TOUCH) {
            return;
        }
        RecyclerView.LayoutManager manager = ((RecyclerView) target).getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            int consumedOffset = mReboundOrientation == ORIENTATION_VERTICAL ? dy : dx;
            //1.列表滑动到最底部向上滑 2.列表从最顶部向下滑中途然后又开始向上滑
            boolean canScrollUp = consumedOffset < 0 &&
                    (((LinearLayoutManager) manager).findFirstCompletelyVisibleItemPosition() == 0 && Math.abs(mTotalOffset) < mFooterOffsetHeight ||
                            mTotalOffset > 0);
            //1.列表滑动到最顶部向下滑 2.列表从最底部向上滑中途然后又开始向下滑
            boolean canScrollDown = consumedOffset > 0 &&
                    ((((LinearLayoutManager) manager).findLastCompletelyVisibleItemPosition() == (manager.getItemCount() - 1) && mTotalOffset < mHeaderOffsetHeight) ||
                            mTotalOffset < 0);
            if (canScrollUp || canScrollDown) {
                consumed[1] = consumedOffset;
                int offset = (int) (consumedOffset * (1 - Math.abs(mTotalOffset / (canScrollUp ? mFooterOffsetHeight : mHeaderOffsetHeight))));
                mTotalOffset += offset;
                if (mReboundOrientation == ORIENTATION_VERTICAL) {
                    scrollBy(0, offset);
                } else {
                    scrollBy(offset, 0);
                }
            }
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            mTotalOffset = mScroller.getCurrY();
            if (mTotalOffset < -mFooterOffsetHeight) {
                mTotalOffset = -mFooterOffsetHeight;
            } else if (mTotalOffset > mHeaderOffsetHeight) {
                mTotalOffset = mHeaderOffsetHeight;
            }
            scrollTo(0, (int) mTotalOffset);
            invalidate();
        }
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        //处理fling事件的回弹
        if (!(target instanceof RecyclerView)) {
            return;
        }
        if (type != ViewCompat.TYPE_NON_TOUCH) {
            return;
        }
        RecyclerView.LayoutManager manager = ((RecyclerView) target).getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            int consumedOffset = mReboundOrientation == ORIENTATION_VERTICAL ? dyUnconsumed : dxUnconsumed;
            boolean canScrollUp = consumedOffset < 0 && ((LinearLayoutManager) manager).findFirstCompletelyVisibleItemPosition() == 0 && Math.abs(mTotalOffset) < mFooterOffsetHeight;
            boolean canScrollDown = consumedOffset > 0 && ((LinearLayoutManager) manager).findLastCompletelyVisibleItemPosition() == (manager.getItemCount() - 1) && mTotalOffset < mHeaderOffsetHeight;
            if (canScrollUp || canScrollDown) {
                consumed[1] = consumedOffset;
                int offset = (int) (consumedOffset * (1 - Math.abs(mTotalOffset / (canScrollUp ? mFooterOffsetHeight : mHeaderOffsetHeight))));
                //不全部消费掉的话，会出现很长时间的慢慢fling事件，很小的值一直传递
                mTotalOffset += consumedOffset;
                if (mReboundOrientation == ORIENTATION_VERTICAL) {
                    scrollBy(0, offset);
                } else {
                    scrollBy(offset, 0);
                }
            }
        }
    }

    @Override
    public void dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type, @NonNull int[] consumed) {
        mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type, consumed);
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
        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }

    public float getHeaderOffsetHeight() {
        return mHeaderOffsetHeight;
    }

    public void setHeaderOffsetHeight(float headerOffsetHeight) {
        mHeaderOffsetHeight = headerOffsetHeight;
    }

    public float getFooterOffsetHeight() {
        return mFooterOffsetHeight;
    }

    public void setFooterOffsetHeight(float footerOffsetHeight) {
        this.mFooterOffsetHeight = footerOffsetHeight;
    }

    public int getReboundDuration() {
        return mReboundDuration;
    }

    public void setReboundDuration(int reboundDuration) {
        mReboundDuration = reboundDuration;
    }

    private void init(Context context, AttributeSet attrs) {
        if (context == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ReboundViewGroup);
        float offset = getResources().getDisplayMetrics().heightPixels * 0.3F;
        mHeaderOffsetHeight = a.getDimension(R.styleable.ReboundViewGroup_rvg_header_offset_height, offset);
        mFooterOffsetHeight = a.getDimension(R.styleable.ReboundViewGroup_rvg_footer_offset_height, offset);
        mReboundDuration = a.getInteger(R.styleable.ReboundViewGroup_rvg_rebound_duration, 800);
        a.recycle();
        mScroller = new OverScroller(context);
    }
}
