package com.mingyuechunqiu.agile.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.R;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *      Email:      xiyujieit@163.com
 *      Time:       1/16/21 1:20 PM
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
public final class CircleProgressView extends View {

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final PorterDuffXfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);

    @ColorInt
    private int mBackgroundColor;
    @ColorInt
    private int mProgressColor;
    @ColorInt
    private int mProgressTextColor;
    @Dimension
    private float mProgressTextSize;
    @Dimension
    private float mProgressTextVerticalPadding;
    @Dimension
    private float mProgressTextHorizontalPadding;
    @Nullable
    private String mProgressSuffix;
    @IntRange(from = 0)
    private int mMinProgress;
    @IntRange(from = 0)
    private int mMaxProgress;
    @IntRange(from = 0)
    private int mProgress;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        mBackgroundColor = a.getColor(R.styleable.CircleProgressView_cpv_background_color, Color.WHITE);
        mProgressColor = a.getColor(R.styleable.CircleProgressView_cpv_progress_color, Color.RED);
        mProgressTextColor = a.getColor(R.styleable.CircleProgressView_cpv_progress_text_color, Color.BLACK);
        mProgressTextSize = a.getDimension(R.styleable.CircleProgressView_cpv_progress_text_size,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14F, getResources().getDisplayMetrics()));
        mProgressTextVerticalPadding = a.getDimension(R.styleable.CircleProgressView_cpv_progress_text_vertical_padding,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 4F, getResources().getDisplayMetrics()));
        mProgressTextHorizontalPadding = a.getDimension(R.styleable.CircleProgressView_cpv_progress_text_horizontal_padding,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 4F, getResources().getDisplayMetrics()));
        mProgressSuffix = a.getString(R.styleable.CircleProgressView_cpv_progress_suffix);
        mMinProgress = a.getInt(R.styleable.CircleProgressView_cpv_min_progress, 0);
        mMaxProgress = a.getInt(R.styleable.CircleProgressView_cpv_max_progress, 100);
        mProgress = a.getInt(R.styleable.CircleProgressView_cpv_progress, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.max(getMeasuredWidth(widthMeasureSpec), getMeasuredHeight(heightMeasureSpec));
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() - getCompatPaddingStart() - getCompatPaddingEnd();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        float radius = Math.min(width, height) * 1.0F / 2;
        int centerX = width / 2;
        int centerY = height / 2;
        mPaint.reset();
        int layerId = canvas.saveLayer(getCompatPaddingStart(), getPaddingTop(), width, height, null, Canvas.ALL_SAVE_FLAG);
        mPaint.setColor(mBackgroundColor);
        canvas.drawCircle(centerX, centerY, radius, mPaint);
        mPaint.setXfermode(mXfermode);
        mPaint.setColor(mProgressColor);
        canvas.drawRect(getCompatPaddingStart(), height - height * (mProgress * 1.0F / mMaxProgress), getCompatPaddingStart() + width, height, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);

        drawProgress(canvas, centerY);
    }

    @ColorInt
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setBackgroundColor(@ColorInt int backgroundColor) {
        this.mBackgroundColor = backgroundColor;
        invalidate();
    }

    @ColorInt
    public int getProgressColor() {
        return mProgressColor;
    }

    public void setProgressColor(@ColorInt int progressColor) {
        this.mProgressColor = progressColor;
        invalidate();
    }

    @ColorInt
    public int getProgressTextColor() {
        return mProgressTextColor;
    }

    public void setProgressTextColor(@ColorInt int progressTextColor) {
        this.mProgressTextColor = progressTextColor;
        invalidate();
    }

    @Dimension
    public float getProgressTextSize() {
        return mProgressTextSize;
    }

    public void setProgressTextSize(@Dimension float progressTextSize) {
        this.mProgressTextSize = progressTextSize;
        invalidate();
    }

    public float getProgressTextVerticalPadding() {
        return mProgressTextVerticalPadding;
    }

    public void setProgressTextVerticalPadding(@Dimension float verticalPadding) {
        mProgressTextVerticalPadding = verticalPadding;
        invalidate();
    }

    public float getProgressTextHorizontalPadding() {
        return mProgressTextHorizontalPadding;
    }

    public void setProgressTextHorizontalPadding(@Dimension float horizontalPadding) {
        mProgressTextHorizontalPadding = horizontalPadding;
        invalidate();
    }

    @Nullable
    public String getProgressSuffix() {
        return mProgressSuffix;
    }

    public void setProgressSuffix(@Nullable String progressSuffix) {
        this.mProgressSuffix = progressSuffix;
        invalidate();
    }

    public int getMinProgress() {
        return mMinProgress;
    }

    public void setMinProgress(@IntRange(from = 0) int minProgress) {
        this.mMinProgress = minProgress;
        invalidate();
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    public void setMaxProgress(@IntRange(from = 0) int maxProgress) {
        this.mMaxProgress = maxProgress;
        invalidate();
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(@IntRange(from = 0) int progress) {
        this.mProgress = progress;
        invalidate();
    }

    @NonNull
    private String getProgressText() {
        return (int) (mProgress * 1.0F / mMaxProgress * 100) + (TextUtils.isEmpty(mProgressSuffix) ? "" : mProgressSuffix);
    }

    private int getMeasuredWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        float width;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                width = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
            default:
                mPaint.setTextSize(mProgressTextSize);
                width = mPaint.measureText(getProgressText());
                width += getCompatPaddingStart() + getCompatPaddingEnd() + mProgressTextHorizontalPadding * 2;
                break;
        }
        return (int) (width);
    }

    private int getMeasuredHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        float height;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                height = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
            default:
                mPaint.setTextSize(mProgressTextSize);
                Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
                height = fontMetrics.bottom - fontMetrics.ascent;
                height += getPaddingTop() + getPaddingBottom() + mProgressTextVerticalPadding * 2;
                break;
        }
        return (int) height;
    }

    private void drawProgress(@NonNull Canvas canvas, int centerY) {
        String progressText = getProgressText();
        mPaint.setColor(mProgressTextColor);
        mPaint.setTextSize(mProgressTextSize);
        float textWidth = mPaint.measureText(progressText);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float bottom = centerY + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        canvas.drawText(progressText, (getWidth() - textWidth) / 2, bottom, mPaint);
    }

    private int getCompatPaddingStart() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 ? getPaddingStart() : getPaddingLeft();
    }

    private int getCompatPaddingEnd() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 ? getPaddingEnd() : getPaddingRight();
    }
}
