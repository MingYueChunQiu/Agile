package com.mingyuechunqiu.agile.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.mingyuechunqiu.agile.R;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/8/7
 *     desc   : 滚动控制ViewPager
 *              继承自ViewPager
 *     version: 1.0
 * </pre>
 */
public class ScrollViewPager extends ViewPager {

    private boolean canScroll;//是否可以滚动

    public ScrollViewPager(@NonNull Context context) {
        this(context, null);
    }

    public ScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScrollViewPager);
        canScroll = a.getBoolean(R.styleable.ScrollViewPager_svp_can_scroll, true);
        a.recycle();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return canScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            performClick();
        }
        return canScroll && super.onTouchEvent(ev);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
