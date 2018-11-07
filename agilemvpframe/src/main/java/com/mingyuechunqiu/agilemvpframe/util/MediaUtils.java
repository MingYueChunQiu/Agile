package com.mingyuechunqiu.agilemvpframe.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.lang.reflect.Field;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/06/13
 *     desc   : 多媒体工具类
 *     version: 1.0
 * </pre>
 */
public class MediaUtils {

    /**
     * 启动选择本地图片界面
     *
     * @param activity    启动界面
     * @param requestCode 启动请求码
     */
    public static void startPickImage(@NonNull Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), requestCode);
    }

    /**
     * 启动选择本地图片界面
     *
     * @param fragment    启动界面
     * @param requestCode 启动请求码
     */
    public static void startPickImage(@NonNull Fragment fragment, int requestCode) {
        fragment.startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), requestCode);
    }

    /**
     * 启动选择本地视频界面
     *
     * @param activity    启动界面
     * @param requestCode 启动请求码
     */
    public static void startPickVideo(@NonNull Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI), requestCode);
    }

    /**
     * 启动选择本地视频界面
     *
     * @param fragment    启动界面
     * @param requestCode 启动请求码
     */
    public static void startPickVideo(@NonNull Fragment fragment, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        //必须加这句话，否则会连图片也一起查找到
        intent.setType("video/*");
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 查询系统数据库地址中视频信息
     *
     * @param resolver Android组件
     * @param uri      视频本地地址
     * @return 如果成功获取数据，则返回VideoInfo，否则返回null
     */
    public static VideoInfo queryVideoInfo(@NonNull ContentResolver resolver, Uri uri) {
        Cursor cursor = resolver.query(uri, null, null, null, null);
        if (cursor == null) {
            return null;
        }
        if (cursor.moveToFirst()) {
            VideoInfo info = new VideoInfo();
            info.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)));
            info.setVideoPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
            info.setThumbnail(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
            info.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)));
            info.setSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)));
            cursor.close();
            return info;
        }
        return null;
    }

    /**
     * 根据缩略图路径获取缩略图
     *
     * @param path 缩略图路径
     * @return 返回生成的缩略图
     */
    public static Bitmap getThumbnail(String path) {
        return ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MINI_KIND);
    }

    /**
     * 设置视频播放声音
     *
     * @param volume 声音音量（0--1）
     * @param o      播放的对象
     */
    public static void setVolume(float volume, Object o) {
        if (volume < 0 || volume > 1 || o == null) {
            return;
        }
        try {
            Class c = Class.forName("android.widget.VideoView");
            Field field = c.getDeclaredField("mMediaPlayer");
            field.setAccessible(true);
            MediaPlayer mediaPlayer = (MediaPlayer) field.get(o);
            mediaPlayer.setVolume(volume, volume);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 视频信息类
     */
    public static class VideoInfo {
        private String title;
        private String videoPath;
        private String thumbnail;
        private int duration;
        private long size;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVideoPath() {
            return videoPath;
        }

        public void setVideoPath(String videoPath) {
            this.videoPath = videoPath;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }
    }
}
