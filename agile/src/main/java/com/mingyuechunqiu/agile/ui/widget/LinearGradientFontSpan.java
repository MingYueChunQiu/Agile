package com.mingyuechunqiu.agile.ui.widget;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.style.ReplacementSpan;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *      Email:      xiyujieit@163.com
 *      Time:       2020/3/22 11:19 PM
 *      Desc:       线性渐变文字扩展
 *                  继承自ReplacementSpan
 *      Version:    1.0
 * </pre>
 */
public class LinearGradientFontSpan extends ReplacementSpan {
    private int mSize;
    private final int mStartColor;
    private final int mEndColor;


    public LinearGradientFontSpan(int startColor, int endColor) {
        this.mStartColor = startColor;
        this.mEndColor = endColor;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        mSize = (int) (paint.measureText(text, start, end));

        //这段不可以去掉，字体高度没设置，可能出现draw（）方法没有被调用，如果你调用SpannableStringBuilder后append一个字符串，效果也是会出来，下面这段就不需要了
        // 原因详见https://stackoverflow.com/questions/20069537/replacementspans-draw-method-isnt-called
        Paint.FontMetricsInt metrics = paint.getFontMetricsInt();
        if (fm != null) {
            fm.top = metrics.top;
            fm.ascent = metrics.ascent;
            fm.descent = metrics.descent;
            fm.bottom = metrics.bottom;
        }

        return mSize;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        LinearGradient lg = new LinearGradient(0, 0, mSize, paint.descent() - paint.ascent(),
                mStartColor,
                mEndColor,
                Shader.TileMode.REPEAT);
        paint.setShader(lg);
        canvas.drawText(text, start, end, x, y, paint);//绘制文字
    }

}
