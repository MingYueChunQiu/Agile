package com.mingyuechunqiu.agile.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.R;

/**
 * <pre>
 *      Project:    Agile
 *
 *      author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-05-18 13:48
 *      Desc:       按下状态容器控件
 *                  继承自FrameLayout
 *      Version:    1.0
 * </pre>
 */
public class PressStateViewGroup extends FrameLayout {

    private static final float DEFAULT_SCALE_RATIO = 0.9F;//默认缩放比例
    private static final float DEFAULT_RESTORE_RATIO = 1.0F;//默认动画执行完后还原比例
    private static final int DEFAULT_SCALE_ANIMATOR_DURATION = 200;//默认缩放动画时长
    private static final int DEFAULT_RESTORE_ANIMATOR_DURATION = 200;//默认还原动画时长

    private boolean canPress;//是否可以显示按压效果
    private float mScaleX, mScaleY;//按压缩放比例（大于0）
    private float mRestoreX, mRestoreY;//缩放后还原比例（大于0）
    private int mScaleDuration, mRestoreDuration;//缩放动画时长，还原动画时长

    public PressStateViewGroup(@NonNull Context context) {
        this(context, null);
    }

    public PressStateViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PressStateViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PressStateViewGroup);
        canPress = a.getBoolean(R.styleable.PressStateViewGroup_psvg_can_press, false);
        mScaleX = a.getFloat(R.styleable.PressStateViewGroup_psvg_scale_x, DEFAULT_SCALE_RATIO);
        mScaleY = a.getFloat(R.styleable.PressStateViewGroup_psvg_scale_y, DEFAULT_SCALE_RATIO);
        mRestoreX = a.getFloat(R.styleable.PressStateViewGroup_psvg_restore_x, DEFAULT_RESTORE_RATIO);
        mRestoreY = a.getFloat(R.styleable.PressStateViewGroup_psvg_restore_y, DEFAULT_RESTORE_RATIO);
        mScaleDuration = a.getInt(R.styleable.PressStateViewGroup_psvg_scale_animator_duration, DEFAULT_SCALE_ANIMATOR_DURATION);
        mRestoreDuration = a.getInt(R.styleable.PressStateViewGroup_psvg_restore_animator_duration, DEFAULT_RESTORE_ANIMATOR_DURATION);
        a.recycle();
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull MotionEvent ev) {
        // 必须onInterceptTouchEvent返回false，且子控件onTouchEvent返回true，
        // 才能在onInterceptTouchEvent里接收到ACTION_DOWN之外其他事件
        if (canPress) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    performAnimator(mScaleX, mScaleY, mScaleDuration);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    performAnimator(mRestoreX, mRestoreY, mRestoreDuration);
                    break;
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        // 防止子控件onTouchEvent返回为false，导致接收不到ACTION_DOWN之外其他事件，使得无法执行还原动画
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_UP:
                performClick();
            case MotionEvent.ACTION_CANCEL:
                performAnimator(mRestoreX, mRestoreY, mRestoreDuration);
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    /**
     * 执行缩放动画
     *
     * @param scaleX   X轴缩放比例
     * @param scaleY   Y轴缩放比例
     * @param duration 动画时长
     */
    private void performAnimator(float scaleX, float scaleY, int duration) {
        animate().scaleX(scaleX).scaleY(scaleY).setDuration(duration).start();
    }
}
