package com.mingyuechunqiu.agile.util;

import androidx.annotation.ColorInt;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *      Email:      xiyujieit@163.com
 *      Time:       2020/3/28 9:29 PM
 *      Desc:       颜色工具类
 *      Version:    1.0
 * </pre>
 */
public final class ColorUtils {

    private ColorUtils() {
    }

    /**
     * 获取不同进度下的渐变颜色
     *
     * @param fraction 进度因子
     * @return 返回对应进度下的颜色
     */
    private int getGradualColor(@ColorInt int startColor, @ColorInt int endColor, float fraction) {
        /*
         * 起始颜色ARGB颜色通道拆分
         */
        float startA = ((startColor >> 24) & 0xff) / 255.0f;
        float startR = ((startColor >> 16) & 0xff) / 255.0f;
        float startG = ((startColor >> 8) & 0xff) / 255.0f;
        float startB = (startColor & 0xff) / 255.0f;
        /*
         * 结束颜色ARGB颜色通道拆分
         */
        float endA = ((endColor >> 24) & 0xff) / 255.0f;
        float endR = ((endColor >> 16) & 0xff) / 255.0f;
        float endG = ((endColor >> 8) & 0xff) / 255.0f;
        float endB = (endColor & 0xff) / 255.0f;


        // convert from sRGB to linear
        startR = (float) Math.pow(startR, 2.2);
        startG = (float) Math.pow(startG, 2.2);
        startB = (float) Math.pow(startB, 2.2);

        endR = (float) Math.pow(endR, 2.2);
        endG = (float) Math.pow(endG, 2.2);
        endB = (float) Math.pow(endB, 2.2);
        /*
         *根据动画时间因子，计算出中间的过渡颜色
         */
        // compute the interpolated color in linear space
        float a = startA + fraction * (endA - startA);
        float r = startR + fraction * (endR - startR);
        float g = startG + fraction * (endG - startG);
        float b = startB + fraction * (endB - startB);

        // convert back to sRGB in the [0..255] range
        a = a * 255.0f;
        r = (float) Math.pow(r, 1.0 / 2.2) * 255.0f;
        g = (float) Math.pow(g, 1.0 / 2.2) * 255.0f;
        b = (float) Math.pow(b, 1.0 / 2.2) * 255.0f;
        /*
         *重新将分离的颜色通道组合返回过渡颜色
         */
        return Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b);
    }
}
