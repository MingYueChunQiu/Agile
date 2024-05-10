package com.mingyuechunqiu.agile.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.R;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *      Email:      xiyujieit@163.com
 *      Time:       2020/3/28 9:41 PM
 *      Desc:       圆角网页控件
 *                  继承自WebView
 *      Version:    1.0
 * </pre>
 */
public class RoundWebView extends WebView {

    private final Path mPath = new Path();
    private final RectF mRectF = new RectF();

    private final float[] mRadiusArray = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};

    public RoundWebView(Context context) {
        this(context, null);
    }

    public RoundWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundWebView); // 读取xml styleable，attrs是xml属性的集合
        float radius = a.getDimension(R.styleable.RoundWebView_rwv_radius, 0);
        float topLeftRadius = a.getDimension(R.styleable.RoundWebView_rwv_top_left_radius, 0);
        float topRightRadius = a.getDimension(R.styleable.RoundWebView_rwv_top_right_radius, 0);
        float bottomLeftRadius = a.getDimension(R.styleable.RoundWebView_rwv_bottom_left_radius, 0);
        float bottomRightRadius = a.getDimension(R.styleable.RoundWebView_rwv_bottom_right_radius, 0);
        a.recycle();

        if (radius > 0) {
            topLeftRadius = topRightRadius = bottomLeftRadius = bottomRightRadius = radius;
        }
        setRoundRadius(topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius);
    }

    /**
     * 设置四个角的圆角半径
     *
     * @param radius 所有圆角的半径
     */
    public void setRoundRadius(float radius) {
        float result = radius > 0 ? radius : 0;
        setRoundRadius(result, result, result, result);
    }

    /**
     * 设置四个角的圆角半径
     *
     * @param leftTopRadius     左上角半径
     * @param rightTopRadius    右上角半径
     * @param rightBottomRadius 右下角半径
     * @param leftBottomRadius  左下角半径
     */
    public void setRoundRadius(float leftTopRadius, float rightTopRadius,
                               float rightBottomRadius, float leftBottomRadius) {
        mRadiusArray[0] = leftTopRadius > 0 ? leftTopRadius : 0;
        mRadiusArray[1] = leftTopRadius > 0 ? leftTopRadius : 0;
        mRadiusArray[2] = rightTopRadius > 0 ? rightTopRadius : 0;
        mRadiusArray[3] = rightTopRadius > 0 ? rightTopRadius : 0;
        mRadiusArray[4] = rightBottomRadius > 0 ? rightBottomRadius : 0;
        mRadiusArray[5] = rightBottomRadius > 0 ? rightBottomRadius : 0;
        mRadiusArray[6] = leftBottomRadius > 0 ? leftBottomRadius : 0;
        mRadiusArray[7] = leftBottomRadius > 0 ? leftBottomRadius : 0;
        invalidate();
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        int scrollX = this.getScrollX();
        int scrollY = this.getScrollY();
        mPath.reset();
        mRectF.set(0, scrollY, scrollX + getWidth(), scrollY + getHeight());
        // 使用半角的方式，性能比较好
        mPath.addRoundRect(mRectF, mRadiusArray, Path.Direction.CW);
        canvas.clipPath(mPath);
        super.onDraw(canvas);
    }
}
