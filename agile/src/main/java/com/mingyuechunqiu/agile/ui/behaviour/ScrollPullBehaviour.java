package com.mingyuechunqiu.agile.ui.behaviour;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *      Project:    Demo
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020/3/24 11:26 PM
 *      Desc:       可滚动推动行为
 *                  继承自CoordinatorLayout.Behavior
 *      Version:    1.0
 * </pre>
 */
public class ScrollPullBehaviour extends CoordinatorLayout.Behavior<RecyclerView> {

    /**
     * Default constructor for instantiating Behaviors.
     */
    public ScrollPullBehaviour() {
    }

    public ScrollPullBehaviour(Context context, AttributeSet attrs) {
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull View dependency) {
        return true;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull View dependency) {
        //计算列表坐上角的Y点的具体位置
        float offsetY = dependency.getTranslationY() + dependency.getHeight();
        if (offsetY < 0) {
            offsetY = 0;
        }
        child.setY(offsetY);
        return true;
    }
}
