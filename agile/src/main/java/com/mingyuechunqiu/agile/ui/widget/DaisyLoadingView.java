package com.mingyuechunqiu.agile.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.R;
import com.mingyuechunqiu.agile.util.ScreenUtils;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020/4/8 8:52 PM
 *      Desc:       菊花样式加载控件
 *                  继承自View
 *      Version:    1.0
 * </pre>
 */
public class DaisyLoadingView extends View {

    private static final int LINE_COUNT = 12;
    private static final int DEGREE_PER_LINE = 360 / LINE_COUNT;

    private Paint mPaint;
    private ValueAnimator mAnimator;
    private float mSize;//控件宽高大小
    private @ColorInt
    int mColor;//控件颜色
    private int mAnimateValue = 0;//动画旋转过程中变量
    private ValueAnimator.AnimatorUpdateListener mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mAnimateValue = (int) animation.getAnimatedValue();
            invalidate();
        }
    };


    public DaisyLoadingView(Context context) {
        this(context, null);
    }

    public DaisyLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DaisyLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.DaisyLoadingView, defStyleAttr, 0);
        mSize = array.getDimension(R.styleable.DaisyLoadingView_dlv_size, ScreenUtils.getPxFromDp(getResources(), 32));
        mColor = array.getInt(R.styleable.DaisyLoadingView_dlv_color, Color.BLACK);
        array.recycle();
        initPaint();
    }

    public DaisyLoadingView(Context context, int size, @ColorInt int color) {
        super(context);
        mSize = size;
        mColor = color;
        if (mSize < 0) {
            mSize = 0;
        }
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) mSize, (int) mSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        drawLoading(canvas, mAnimateValue * DEGREE_PER_LINE);
        canvas.restoreToCount(saveCount);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            start();
        } else {
            stop();
        }
    }

    public void start() {
        if (mAnimator == null) {
            mAnimator = ValueAnimator.ofInt(0, LINE_COUNT - 1);
            mAnimator.addUpdateListener(mUpdateListener);
            mAnimator.setDuration(600);
            mAnimator.setRepeatMode(ValueAnimator.RESTART);
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.start();
        } else if (!mAnimator.isStarted()) {
            mAnimator.start();
        }
    }

    public void stop() {
        if (mAnimator != null) {
            mAnimator.removeUpdateListener(mUpdateListener);
            mAnimator.removeAllUpdateListeners();
            mAnimator.cancel();
            mAnimator = null;
        }
    }

    @ColorInt
    public int getColor() {
        return mColor;
    }

    public void setColor(@ColorInt int color) {
        mColor = color;
        mPaint.setColor(color);
        invalidate();
    }

    public float getSize() {
        return mSize;
    }

    public void setSize(float size) {
        mSize = size;
        if (mSize < 0) {
            mSize = 0;
        }
        requestLayout();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void drawLoading(Canvas canvas, int rotateDegrees) {
        float width = mSize / 12, height = mSize / 6;
        mPaint.setStrokeWidth(width);

        canvas.rotate(rotateDegrees, mSize / 2, mSize / 2);
        canvas.translate(mSize / 2, mSize / 2);

        for (int i = 0; i < LINE_COUNT; i++) {
            canvas.rotate(DEGREE_PER_LINE);
            mPaint.setAlpha((int) (255F * (i + 1) / LINE_COUNT));
            canvas.translate(0, -mSize / 2 + width / 2);
            canvas.drawLine(0, 0, 0, height, mPaint);
            canvas.translate(0, mSize / 2 - width / 2);
        }
    }
}
