package com.mingyuechunqiu.agile.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;

import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;
import com.mingyuechunqiu.agile.frame.Agile;
import com.mingyuechunqiu.agile.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
public final class BitmapUtils {

    private BitmapUtils() {
    }

    /**
     * 将图片保存到本地文件
     *
     * @param bitmap 要保存的图片
     * @param file   图片存储文件
     * @return 如果成功进行保存返回true，否则返回false
     */
    public static boolean saveBitmapToFile(@Nullable Bitmap bitmap, @Nullable File file) {
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
    public static Bitmap getBitmapFromImage(@Nullable Image image) {
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
    public static Bitmap drawWaterMask(@Nullable Bitmap src, @Nullable Bitmap waterMask) {
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
    public static void saveBitmapToLocal(@Nullable String bitmapUrl, @Nullable String filePath, @NonNull OnDownloadBitmapListener listener) {
        if (TextUtils.isEmpty(bitmapUrl) || filePath == null || TextUtils.isEmpty(filePath)) {
            listener.onDownloadBitmapFailed("parameters not set");
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
            listener.onDownloadBitmapSuccess(file);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            LogManagerProvider.d("downloadBitmap", e.getMessage());
            listener.onDownloadBitmapFailed(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            LogManagerProvider.d("downloadBitmap", e.getMessage());
            listener.onDownloadBitmapFailed(e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            IOUtils.closeStream(bis);
            IOUtils.closeStream(bos);
        }
    }

    /**
     * 调用系统相机拍照
     *
     * @param activity    上下文
     * @param saveFile    保存文件
     * @param requestCode 请求码
     */
    public static void selectPictureFromCamera(@Nullable Activity activity, @Nullable File saveFile, int requestCode) {
        if (activity == null || saveFile == null) {
            return;
        }
        // 启动系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageCaptureUri;
        // 判断7.0android系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //临时添加一个拍照权限
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            //通过FileProvider获取uri
            imageCaptureUri = FileProvider.getUriForFile(activity,
                    Agile.getAppContext().getPackageName() + ".fileprovider", saveFile);
        } else {
            imageCaptureUri = Uri.fromFile(saveFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCaptureUri);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 从系统图库选择图片
     *
     * @param activity    上下文
     * @param requestCode 请求码
     */
    public static void selectPictureFromAlbum(@Nullable Activity activity, int requestCode) {
        if (activity == null) {
            return;
        }
        activity.startActivityForResult(new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), requestCode);
    }

    /**
     * 保存图片到本地文件
     *
     * @param context  上下文
     * @param bitmap   需要保存的图片
     * @param fileName 本地文件名
     * @return 如果保存成功返回true，否则返回false
     */
    public static boolean saveBitmapToFile(@Nullable Context context, @Nullable Bitmap bitmap, @Nullable String fileName) {
        if (context == null || bitmap == null || TextUtils.isEmpty(fileName)) {
            return false;
        }
        File file = getSavedLocalFile(context, fileName);
        if (file == null) {
            return false;
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            LogManagerProvider.e("saveBitmapToFile", e.getMessage());
            return false;
        } finally {
            IOUtils.closeStream(fos);
        }
        return true;
    }

    /**
     * 根据多媒体图片资源URI获取图片对象
     *
     * @param context 上下文
     * @param uri     多媒体图片资源URI
     * @return 如果成功返回图片对象，否则返回null
     */
    @Nullable
    public static Bitmap getBitmapFromUri(@Nullable Context context, @Nullable Uri uri) {
        if (context == null || uri == null) {
            return null;
        }
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    context.getContentResolver().openFileDescriptor(uri, "r");
            if (parcelFileDescriptor == null) {
                return null;
            }
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return bitmap;
        } catch (Exception e) {
            LogManagerProvider.e("getBitmapFromUri", e.getMessage());
        }
        return null;
    }

    /**
     * 获取图片旋转角度
     *
     * @param inputStream 输入流
     * @return 返回需要修正的角度
     */
    public static int getBitmapRotatedAngle(@Nullable InputStream inputStream) {
        if (inputStream == null) {
            LogManagerProvider.i("getAdjustedRotationAngle", "inputStream == null");
            return 0;
        }
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(inputStream);
        } catch (IOException e) {
            LogManagerProvider.e("getAdjustedRotationAngle", e.getMessage());
        }
        if (exif == null) {
            LogManagerProvider.i("getAdjustedRotationAngle", "ExifInterface == null");
            return 0;
        }
        // 读取图片中相机方向信息
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        // 计算旋转角度
        int angle = 0;
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                angle = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                angle = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                angle = 270;
                break;
            default:
                break;
        }
        return angle;
    }

    /**
     * 获取图片旋转角度
     *
     * @param filePath 文件路径
     * @return 返回需要修正的角度
     */
    public static int getBitmapRotatedAngle(@Nullable String filePath) {
        if (filePath == null) {
            LogManagerProvider.i("getAdjustedRotationAngle", "filePath == null");
            return 0;
        }
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filePath);
        } catch (IOException e) {
            LogManagerProvider.e("getAdjustedRotationAngle", e.getMessage());
        }
        if (exif == null) {
            LogManagerProvider.i("getAdjustedRotationAngle", "ExifInterface == null");
            return 0;
        }
        // 读取图片中相机方向信息
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        // 计算旋转角度
        int angle = 0;
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                angle = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                angle = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                angle = 270;
                break;
            default:
                break;
        }
        return angle;
    }

    /**
     * 获取调整旋转角度后的图片
     *
     * @param bitmap 需要调整的图片
     * @param angle  调整角度
     * @return 返回修正后的图片
     */
    @Nullable
    public Bitmap getAdjustedRotationBitmap(@Nullable Bitmap bitmap, int angle) {
        if (bitmap == null || angle == 0) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return rotatedBitmap;
    }

    /**
     * 获取本地保存文件父目录
     *
     * @param context 上下文
     * @return 如果获取成功返回File，否则返回null
     */
    @Nullable
    public static File getSavedLocalParentFile(@Nullable Context context) {
        if (context == null) {
            return null;
        }
        File parentFile = null;//父目录文件夹
        boolean hasCreatedParentFile = true;//标记是否创建父目录文件夹成功
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            parentFile = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if (parentFile != null) {
                if (!parentFile.exists()) {
                    hasCreatedParentFile = parentFile.mkdirs();
                }
            } else {
                hasCreatedParentFile = false;
            }
        }
        if (!hasCreatedParentFile) {
            parentFile = new File(context.getCacheDir().getAbsolutePath() + File.separator + "Pictures");
            if (!parentFile.exists()) {
                hasCreatedParentFile = parentFile.mkdirs();
            }
        }
        return hasCreatedParentFile ? parentFile : null;
    }

    /**
     * 获取本地的保存文件
     *
     * @param context  上下文
     * @param fileName 保存文件名称
     * @return 如果获取成功返回File，否则返回null
     */
    @Nullable
    public static File getSavedLocalFile(@Nullable Context context, @Nullable String fileName) {
        if (context == null) {
            return null;
        }
        String name = fileName;
        if (TextUtils.isEmpty(name)) {
            name = "temp";
        }
        File parentFile = getSavedLocalParentFile(context);
        if (parentFile == null) {
            return null;
        }
        return new File(parentFile.getAbsolutePath() + File.separator + name + ".png");
    }

    /**
     * 将图片转换成Base64编码的字符串
     */
    @Nullable
    public static String imageToBase64(@Nullable String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        String result = null;
        try {
            fis = new FileInputStream(filePath);
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count;
            while ((count = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, count);
            }
            //用默认的编码格式进行编码
            result = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            LogManagerProvider.e("imageToBase64", e.getMessage());
        } finally {
            IOUtils.closeStream(fis, baos);
        }
        return result;
    }

    /**
     * 将Base64编码转换为图片
     *
     * @param base64Str 图片Base64字符串
     * @param filePath  保存路径
     * @return 如果成功返回true，否则返回false
     */
    public static boolean base64ToFile(@Nullable String base64Str, @Nullable String filePath) {
        if (TextUtils.isEmpty(base64Str) || TextUtils.isEmpty(filePath)) {
            return false;
        }
        byte[] data = Base64.decode(base64Str, Base64.NO_WRAP);
        for (int i = 0; i < data.length; i++) {
            if (data[i] < 0) {
                //调整异常数据
                data[i] += 256;
            }
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            fos.write(data);
            fos.flush();
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            LogManagerProvider.e("base64ToFile", e.getMessage());
            return false;
        } catch (IOException e) {
            LogManagerProvider.e("base64ToFile", e.getMessage());
            return false;
        } finally {
            IOUtils.closeStream(fos);
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
