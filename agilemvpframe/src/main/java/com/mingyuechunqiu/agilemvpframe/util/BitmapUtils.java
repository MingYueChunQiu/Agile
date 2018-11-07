package com.mingyuechunqiu.agilemvpframe.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.Image;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/18
 *     desc   : 图片工具类
 *     version: 1.0
 * </pre>
 */
public class BitmapUtils {

    /**
     * 将图片保存到本地文件
     *
     * @param bitmap 要保存的图片
     * @param file   图片存储文件
     */
    public static void saveBitmapToFile(Bitmap bitmap, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从image中获取图片
     *
     * @param image 存储图像的image
     * @return 提取出的图片
     */
    @Nullable
    public static Bitmap getBitmapFromImage(Image image) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            int width = image.getWidth();
            int height = image.getHeight();
            Image.Plane[] planes = image.getPlanes();
            ByteBuffer byteBuffer = planes[0].getBuffer();
            int pixelStride = planes[0].getPixelStride();
            int rowStride = planes[0].getRowStride();
            int rowPadding = rowStride - width * pixelStride;
            Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
            bitmap.copyPixelsFromBuffer(byteBuffer);
            image.close();
            return bitmap;
        }
        return null;
    }

    /**
     * 绘制带水印的图片
     *
     * @param src       原始图片
     * @param waterMask 水印图片
     * @return 返回带水印的图片
     */
    public static Bitmap drawWaterMask(Bitmap src, Bitmap waterMask) {
        if (src == null || waterMask == null) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(src, 0, 0, null);
        Bitmap newWaterMask = Bitmap.createScaledBitmap(waterMask, waterMask.getWidth() * 2,
                waterMask.getHeight() * 2, false);
        canvas.drawBitmap(newWaterMask, width - newWaterMask.getWidth(),
                height - newWaterMask.getHeight(), null);
        return newBitmap;
    }

}
