package com.mingyuechunqiu.agilemvpframe.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.Image;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mingyuechunqiu.agilemvpframe.feature.logmanager.LogManagerProvider;
import com.mingyuechunqiu.agilemvpframe.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
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
     * @return 如果成功进行保存返回true，否则返回false
     */
    public static boolean saveBitmapToFile(Bitmap bitmap, File file) {
        if (bitmap == null || file == null) {
            return false;
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 从image中获取图片
     *
     * @param image 存储图像的image
     * @return 提取图片成功返回Bitmap，否则返回null
     */
    @Nullable
    public static Bitmap getBitmapFromImage(Image image) {
        if (image == null) {
            return null;
        }
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
     * @return 带水印的图片创建成功返回Bitmap，否则返回null
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

    /**
     * 保存图片到本地
     *
     * @param bitmapUrl 图片地址
     * @param filePath  保存文件路径
     * @param listener  下载图片监听器
     */
    public static void saveBitmapToLocal(String bitmapUrl, String filePath, OnDownloadBitmapListener listener) {
        if (TextUtils.isEmpty(bitmapUrl) || TextUtils.isEmpty(filePath)) {
            if (listener != null) {
                listener.onDownloadBitmapFailed("parameters not set");
            }
            return;
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(bitmapUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            File file = new File(filePath);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                bis = new BufferedInputStream(connection.getInputStream());
                bos = new BufferedOutputStream(new FileOutputStream(file));
                byte[] bytes = new byte[1024];
                int length;
                while ((length = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, length);
                }
            }
            if (listener != null) {
                listener.onDownloadBitmapSuccess(file);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            LogManagerProvider.d("downloadBitmap", e.getMessage());
            if (listener != null) {
                listener.onDownloadBitmapFailed(e.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogManagerProvider.d("downloadBitmap", e.getMessage());
            if (listener != null) {
                listener.onDownloadBitmapFailed(e.getMessage());
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            IOUtils.closeStream(bis);
            IOUtils.closeStream(bos);
        }
    }

    /**
     * 下载图片监听器
     */
    public interface OnDownloadBitmapListener {

        /**
         * 当下载成功时回调
         *
         * @param file 下载图片所保存文件
         */
        void onDownloadBitmapSuccess(File file);

        /**
         * 当下载失败时回调
         *
         * @param msg 失败信息
         */
        void onDownloadBitmapFailed(String msg);
    }
}
