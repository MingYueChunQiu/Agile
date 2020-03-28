package com.mingyuechunqiu.agile.ui.behaviour;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *      Project:    Demo
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020/3/24 10:45 PM
 *      Desc:       头部推动行为
 *                  继承自CoordinatorLayout.Behavior
 *      Version:    1.0
 * </pre>
 */
public class HeaderPullBehaviour extends CoordinatorLayout.Behavior<View> {

    public HeaderPullBehaviour() {
    }

    public HeaderPullBehaviour(Context context, AttributeSet attrs) {
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        if (target instanceof RecyclerView) {
            RecyclerView.LayoutManager manager = ((RecyclerView) target).getLayoutManager();
            if (manager instanceof LinearLayoutManager) {
                int pos = ((LinearLayoutManager) manager).findFirstCompletelyVisibleItemPosition();
                //dy大于0表示想上滚动，小于0表示向下滚动

                //标记可以想下滚动（必须在RecyclerView滑到第一个Item且child已经滑出屏幕的情况下）
                boolean canPullDown = dy < 0 && pos == 0 && child.getTranslationY() < 0;
                //标记可以向上滚动（必须在child的超出屏幕Y轴滚动距离不超出child的高度）
                boolean canPullUp = dy > 0 && child.getTranslationY() <= 0 && child.getTranslationY() > -child.getHeight();
                if (canPullDown || canPullUp) {
                    float scrollY = child.getTranslationY() - dy;
                    //避免精度损失
                    if (scrollY > 0) {
                        scrollY = 0;
                    } else if (scrollY <- child.getHeight()) {
                        scrollY = -child.getHeight();
                    }
                    consumed[1] = dy;
                    child.setTranslationY(scrollY);
                }
            }
        }
    }
}
