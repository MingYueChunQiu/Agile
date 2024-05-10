package com.mingyuechunqiu.agile.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mingyuechunqiu.agile.R;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *      Email:      xiyujieit@163.com
 *      Time:       2020/3/22 11:18 PM
 *      Desc:       冲突处理列表
 *                  继承自RecyclerView
 *      Version:    1.0
 * </pre>
 */
public class ConflictRecyclerView extends RecyclerView {

    private boolean preventHorizontalConflict, preventVerticalConflict;//标记是否阻止水平/垂直冲突
    private boolean horizontalScroll;//标记是否水平滑动
    private boolean verticalScroll;//标记是否垂直滑动
    private float mLastX, mLastY;//上次滑动X、Y轴坐标位置

    public ConflictRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public ConflictRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConflictRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConflictRecyclerView);
        preventHorizontalConflict = a.getBoolean(R.styleable.ConflictRecyclerView_crv_prevent_horizontal_conflict, false);
        preventVerticalConflict = a.getBoolean(R.styleable.ConflictRecyclerView_crv_prevent_vertical_conflict, false);
        a.recycle();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = ev.getX();
                mLastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                judgeScrollOrientation(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            default:
                judgeScrollOrientation(ev);
                mLastX = mLastY = 0;
                break;
        }
        getParent().requestDisallowInterceptTouchEvent((preventHorizontalConflict && horizontalScroll) ||
                (preventVerticalConflict && verticalScroll));
        return super.dispatchTouchEvent(ev);
    }

    public boolean isPreventHorizontalConflict() {
        return preventHorizontalConflict;
    }

    public void setPreventHorizontalConflict(boolean preventHorizontalConflict) {
        this.preventHorizontalConflict = preventHorizontalConflict;
    }

    public boolean isPreventVerticalConflict() {
        return preventVerticalConflict;
    }

    public void setPreventVerticalConflict(boolean preventVerticalConflict) {
        this.preventVerticalConflict = preventVerticalConflict;
    }

    /**
     * 判断滚动方向
     *
     * @param ev 触摸信息对象
     */
    private void judgeScrollOrientation(@NonNull MotionEvent ev) {
        float xDistance = Math.abs(ev.getX() - mLastX);
        float yDistance = Math.abs(ev.getY() - mLastY);
        if (xDistance > yDistance) {
            horizontalScroll = true;
            verticalScroll = false;
        } else if (yDistance > xDistance) {
            verticalScroll = true;
            horizontalScroll = false;
        } else {
            horizontalScroll = false;
            verticalScroll = false;
        }
    }
}
