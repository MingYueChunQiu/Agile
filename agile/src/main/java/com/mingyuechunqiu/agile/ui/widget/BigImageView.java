package com.mingyuechunqiu.agile.ui.widget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 *      Project:    Agile
 *      Github :    https://github.com/MingYueChunQiu
 *      Author:     XiYuJie
 *      Email:      xiyujieit@163.com
 *      Time:       2020/1/10 10:35
 *      Desc:       大图片显示控件
 *                  继承自View
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
        mTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mScroller != null && !mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
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
                mTracker.computeCurrentVelocity(1000);
                mLastX = mBitmapRect.left;
                mLastY = mBitmapRect.top;
                if (mScroller != null) {
                    mScroller.fling((int) mLastX, (int) mLastY, (int) mTracker.getXVelocity(), (int) mTracker.getYVelocity(),
                            Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
                }
                invalidate();
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

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            int xDistance = (int) (mScroller.getCurrX() - mLastX);
            int yDistance = (int) (mScroller.getCurrY() - mLastY);
            mLastX = mScroller.getCurrX();
            mLastY = mScroller.getCurrY();
            moveXDistance(xDistance);
            moveYDistance(yDistance);
            invalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTracker != null) {
            mTracker.recycle();
            mTracker = null;
        }
    }

    /**
     * 使用文件路径加载图片
     *
     * @param path 文件路径
     */
    public void loadBitmapFile(@Nullable final String path) {
        decodeBitmap(new DecodeBitmapHandler() {
            @Override
            public void onDecodeBitmap(@NonNull BitmapFactory.Options options) {
                try {
                    mDecoder = BitmapRegionDecoder.newInstance(path, false);
                    BitmapFactory.decodeFile(path, options);
                } catch (IOException e) {
                    LogManagerProvider.e("BigImageView:loadBitmapFile", e.getMessage());
                }
            }
        });
    }

    /**
     * 使用文件描述符加载图片
     *
     * @param fileDescriptor 文件描述符
     */
    public void loadBitmapFileDescriptor(@Nullable final FileDescriptor fileDescriptor) {
        decodeBitmap(new DecodeBitmapHandler() {
            @Override
            public void onDecodeBitmap(@NonNull BitmapFactory.Options options) {
                try {
                    mDecoder = BitmapRegionDecoder.newInstance(fileDescriptor, false);
                    BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
                } catch (IOException e) {
                    LogManagerProvider.e("BigImageView:loadBitmapFileDescriptor", e.getMessage());
                }
            }
        });
    }

    /**
     * 使用输入流加载图片
     *
     * @param stream 输入流
     */
    public void loadBitmapStream(@Nullable final InputStream stream) {
        decodeBitmap(new DecodeBitmapHandler() {
            @Override
            public void onDecodeBitmap(@NonNull BitmapFactory.Options options) {
                try {
                    mDecoder = BitmapRegionDecoder.newInstance(stream, false);
                    BitmapFactory.decodeStream(stream, null, options);
                } catch (IOException e) {
                    LogManagerProvider.e("BigImageView:loadBitmapStream", e.getMessage());
                }
            }
        });
    }

    /**
     * 初始化解码图片
     *
     * @param handler 解码图片处理器
     */
    private void decodeBitmap(@NonNull DecodeBitmapHandler handler) {
        if (mDecoder != null) {
            mDecoder.recycle();
            mDecoder = null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        handler.onDecodeBitmap(options);
        mBitmapWidth = options.outWidth;
        mBitmapHeight = options.outHeight;
        requestLayout();
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
        checkBitmapHorizontalRange();
    }

    /**
     * 检查图片水平显示范围
     */
    private void checkBitmapHorizontalRange() {
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
        checkBitmapVerticalRange();
    }

    /**
     * 检查图片垂直显示范围
     */
    private void checkBitmapVerticalRange() {
        if (mBitmapRect.top < 0) {
            mBitmapRect.top = 0;
            mBitmapRect.bottom = getHeight();
        } else if (mBitmapRect.bottom > mBitmapHeight) {
            mBitmapRect.bottom = mBitmapHeight;
            mBitmapRect.top = mBitmapHeight - getHeight();
        }
    }

    /**
     * 解码图片处理器
     */
    private interface DecodeBitmapHandler {

        /**
         * 当解码图片时回调
         *
         * @param options 图片参数
         */
        void onDecodeBitmap(@NonNull BitmapFactory.Options options);
    }
}