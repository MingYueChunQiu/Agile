package com.mingyuechunqiu.agile.ui.widget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import java.io.IOException;

/**
 * <pre>
 *      Project:    Agile
 *      Github :    https://github.com/MingYueChunQiu
 *      Author:     XiYuJie
 *      Email:      xiyujieit@163.com
 *      Time:       2020/1/10 10:35
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
public class BigImageView extends View {

    private Rect mBitmapRect = new Rect();//显示矩形
    private BitmapRegionDecoder mDecoder;
    private final BitmapFactory.Options mOptions = new BitmapFactory.Options();
    private int mBitmapWidth, mBitmapHeight;//图标原始宽高
    private float mLastX, mLastY;//上一次按下的X,Y坐标
    private VelocityTracker mTracker;
    private Scroller mScroller;

    public BigImageView(Context context) {
        this(context, null);
    }

    public BigImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mBitmapRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mDecoder.decodeRegion(mBitmapRect, mOptions), 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTracker == null) {
            mTracker = VelocityTracker.obtain();
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getX();
                mLastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int xDistance = (int) (event.getX() - mLastX);
                int yDistance = (int) (event.getY() - mLastY);
                mLastX = event.getX();
                mLastY = event.getY();
                moveXDistance(xDistance);
                moveYDistance(yDistance);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mTracker.addMovement(event);
                mTracker.computeCurrentVelocity(1000);
                int xFling = (int) (mTracker.getXVelocity() * 0.2F);
                int yFling = (int) (mTracker.getYVelocity() * 0.2F);
                Log.d("分", mTracker.getXVelocity() + " " + mTracker.getYVelocity());
                mScroller.fling(0, 0, (int) mTracker.getXVelocity(), (int) mTracker.getYVelocity(),
                        Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
                invalidate();
//                ObjectAnimator animator = ObjectAnimator.ofInt(mBitmapRect.left, mBitmapRect.left )
                mTracker.clear();
                performClick();
                break;
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

//    @Override
//    public void computeScroll() {
//        super.computeScroll();
//        if (mScroller.computeScrollOffset()) {
//            mBitmapRect.set(mScroller.getCurrX(), mScroller.getCurrY(), mScroller.getCurrX() + mBitmapRect.width(),
//                    mScroller.getCurrY() + mBitmapRect.height());
//            invalidate();
//        }
//    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTracker != null) {
            mTracker.recycle();
            mTracker = null;
        }
    }

    public void loadFile(@Nullable String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        if (mDecoder != null) {
            mDecoder.recycle();
            mDecoder = null;
        }
        try {
            mDecoder = BitmapRegionDecoder.newInstance(path, false);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            mBitmapWidth = options.outWidth;
            mBitmapHeight = options.outHeight;
            requestLayout();
        } catch (IOException e) {

        }
    }

    /**
     * 移动X轴距离
     *
     * @param distance 移动距离
     */
    private void moveXDistance(int distance) {
        if (mBitmapWidth <= getWidth()) {
            return;
        }
        mBitmapRect.offset(-distance, 0);
        if (mBitmapRect.left < 0) {
            mBitmapRect.left = 0;
            mBitmapRect.right = getWidth();
        } else if (mBitmapRect.right > mBitmapWidth) {
            mBitmapRect.right = mBitmapWidth;
            mBitmapRect.left = mBitmapWidth - getWidth();
        }
    }

    /**
     * 移动Y轴距离
     *
     * @param distance 移动距离
     */
    private void moveYDistance(int distance) {
        if (mBitmapHeight <= getHeight()) {
            return;
        }
        mBitmapRect.offset(0, -distance);
        if (mBitmapRect.top < 0) {
            mBitmapRect.top = 0;
            mBitmapRect.bottom = getHeight();
        } else if (mBitmapRect.bottom > mBitmapHeight) {
            mBitmapRect.bottom = mBitmapHeight;
            mBitmapRect.top = mBitmapHeight - getHeight();
        }
    }
}
