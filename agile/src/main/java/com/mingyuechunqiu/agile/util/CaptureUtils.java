package com.mingyuechunqiu.agile.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.hardware.display.DisplayManager;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.Surface;

import androidx.annotation.Nullable;

import java.nio.ByteBuffer;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/18
 *     desc   : 屏幕截取工具类
 *     version: 1.0
 * </pre>
 */
public final class CaptureUtils {

    private CaptureUtils() {
    }

    /**
     * 开始屏幕捕捉
     *
     * @param activity    进行屏幕捕捉的界面
     * @param requestCode 屏幕捕捉请求码
     * @return 是否成功请求屏幕捕捉
     */
    public static boolean startCapture(@Nullable Activity activity, int requestCode) {
        if (activity == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            MediaProjectionManager manager = (MediaProjectionManager) activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
            if (manager == null) {
                return false;
            }
            activity.startActivityForResult(manager.createScreenCaptureIntent(), requestCode);
            return true;
        }
        return false;
    }

    /**
     * 获取截屏图片
     *
     * @param context    上下文
     * @param manager    多媒体管理器
     * @param resultCode 请求结果码
     * @param data       传递参数
     * @return 截屏图片
     */
    @Nullable
    public static Bitmap getCaptureImage(@Nullable Context context, @Nullable MediaProjectionManager manager,
                                         int resultCode, @Nullable Intent data) {
        if (context == null || manager == null || data == null ||
                Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return null;
        }
        MediaProjection mediaProjection = manager.getMediaProjection(resultCode, data);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        int densityDpi = metrics.densityDpi;
        Image image;
        try (ImageReader imageReader = ImageReader.newInstance(screenWidth, screenHeight, ImageFormat.JPEG, 1)) {
            mediaProjection.createVirtualDisplay("screenShot", screenWidth, screenHeight
                    , densityDpi, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                    imageReader.getSurface(), null, null);
            SystemClock.sleep(1000);
            image = imageReader.acquireNextImage();
        }
        return BitmapUtils.getBitmapFromImage(image);
    }

    /**
     * 获取多媒体格式
     *
     * @param width  视频宽
     * @param height 视频高
     * @return 若设置成功则返回生成对象，否则返回null
     */
    @Nullable
    public static MediaFormat getVideoMediaFormat(int width, int height) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            MediaFormat mediaFormat = MediaFormat.createAudioFormat("video/acc", width, height);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT,
                        MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
            }
            mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 60000);
            mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 30);
            mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 10);
            return mediaFormat;
        }
        return null;
    }

    /**
     * 获取屏幕捕捉的画面类
     *
     * @param mediaCodec 多媒体编码参数
     * @param format     多媒体格式信息
     * @return 返回捕捉画面
     */
    @Nullable
    public static Surface getVideoSurface(@Nullable MediaCodec mediaCodec, @Nullable MediaFormat format) {
        if (mediaCodec == null || format == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mediaCodec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            Surface surface = mediaCodec.createInputSurface();
            mediaCodec.start();
            return surface;
        }
        return null;
    }

    /**
     * 对各帧上视频进行编码
     *
     * @param index      帧索引
     * @param mediaCodec 多媒体编码参数
     * @param bufferInfo 缓冲信息
     * @return 返回缓冲字节
     */
    @Nullable
    public static ByteBuffer encodeToVideoTrace(int index, @Nullable MediaCodec mediaCodec,
                                                @Nullable MediaCodec.BufferInfo bufferInfo) {
        if (mediaCodec == null || bufferInfo == null ||
                Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return null;
        }
        ByteBuffer encodedData = mediaCodec.getOutputBuffer(index);
        if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
            bufferInfo.size = 0;
        }
        if (bufferInfo.size == 0) {
            encodedData = null;
        }
        if (encodedData != null) {
            encodedData.position(bufferInfo.offset);
            encodedData.limit(bufferInfo.offset + bufferInfo.size);
            return encodedData;
        }
        return null;
    }

}
