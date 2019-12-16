package com.mingyuechunqiu.agile.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

/**
 * <pre>
 *      Project:    PalmGoldenEagle
 *      Github :    https://github.com/MingYueChunQiu
 *      Author:     XiYuJie
 *      Email:      xiyujie@jinying.com
 *      Time:       2019/12/5 9:29
 *      Desc:       可推动嵌套滚动控件
 *      Version:    1.0
 * </pre>
 */
public class PushNestedScrollView extends NestedScrollView {

    private int mDistance = 0;//记录当前已滚动距离
    private PushScrollCallback mCallback;

    public PushNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public PushNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PushNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(target, dx, dy, consumed, type);
        int distance;//实际移动距离
        //dy向上是正，向下是负，因为是那前一个点位置减去后一个点位置
        if (mCallback != null) {
            int length = mCallback.getPushScrollDistance();
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
                    if (mDistance + dy < 0) {
                        distance = mDistance;
                        mDistance = 0;
                    } else {
                        distance = dy;
                        mDistance += distance;
                    }
                    ViewGroup group = (ViewGroup) getChildAt(0);
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

    public void setPushScrollCallback(PushScrollCallback callback) {
        mCallback = callback;
    }

    /**
     * 助推滚动回调
     */
    public interface PushScrollCallback {

        /**
         * 获取可滚动距离
         *
         * @return
         */
        int getPushScrollDistance();

        /**
         * 每次推滚动时调用
         *
         * @param distance      当次滚动距离
         * @param totalDistance 总计已滚动距离
         */
        void onPushScroll(int distance, int totalDistance);
    }
}
