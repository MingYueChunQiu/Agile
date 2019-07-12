package com.mingyuechunqiu.agile.feature.playermanager.video;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.feature.playermanager.video.ui.VideoContainerable;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/2/21
 *     desc   : 视频播放配置信息类
 *     version: 1.0
 * </pre>
 */
public class VideoPlayerOption {

    private String title;//标题
    private Drawable placeholderDrawable;//占位图
    private VideoContainerable containerable;//视频容器
    private Constants.SURFACE_TYPE surfaceType;//图层类型
    private Constants.SCREEN_TYPE screenType;//界面类型
    private Drawable backgroundDrawable;//视频播放背景颜色
    private String videoSource;//播放来源（ 包括本地路径和URL）

    public VideoPlayerOption() {
        surfaceType = Constants.SURFACE_TYPE.TYPE_NOT_SET;
        screenType = Constants.SCREEN_TYPE.TYPE_FILL;
        backgroundDrawable = new ColorDrawable(Color.BLACK);//默认黑色背景
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getPlaceholderDrawable() {
        return placeholderDrawable;
    }

    public void setPlaceholderDrawable(Drawable placeholderDrawable) {
        this.placeholderDrawable = placeholderDrawable;
    }

    public VideoContainerable getContainerable() {
        if (containerable == null) {
            throw new NullPointerException("VideoContainerable be null!");
        }
        return containerable;
    }

    public void setContainerable(@NonNull VideoContainerable containerable) {
        this.containerable = containerable;
    }

    public Constants.SURFACE_TYPE getSurfaceType() {
        return surfaceType;
    }

    public void setSurfaceType(Constants.SURFACE_TYPE surfaceType) {
        this.surfaceType = surfaceType;
    }

    public Constants.SCREEN_TYPE getScreenType() {
        return screenType;
    }

    public void setScreenType(Constants.SCREEN_TYPE screenType) {
        this.screenType = screenType;
    }

    public Drawable getBackgroundDrawable() {
        return backgroundDrawable;
    }

    public void setBackgroundDrawable(Drawable backgroundDrawable) {
        this.backgroundDrawable = backgroundDrawable;
    }

    public String getVideoSource() {
        return videoSource;
    }

    public void setVideoSource(String videoSource) {
        this.videoSource = videoSource;
    }

    /**
     * 链式调用
     */
    public static class Builder {

        private VideoPlayerOption mOption;

        public Builder() {
            mOption = new VideoPlayerOption();
        }

        public VideoPlayerOption build() {
            return mOption;
        }

        public String getTitle() {
            return mOption.title;
        }

        public Builder setTitle(String title) {
            mOption.title = title;
            return this;
        }

        public Drawable getPlaceholderDrawable() {
            return mOption.placeholderDrawable;
        }

        public Builder setPlaceholderDrawable(Drawable placeholderDrawable) {
            mOption.placeholderDrawable = placeholderDrawable;
            return this;
        }

        public VideoContainerable getContainerable() {
            return mOption.getContainerable();
        }

        public Builder setContainerable(@NonNull VideoContainerable containerable) {
            mOption.containerable = containerable;
            return this;
        }

        public Constants.SURFACE_TYPE getSurfaceType() {
            return mOption.surfaceType;
        }

        public Builder setSurfaceType(Constants.SURFACE_TYPE surfaceType) {
            mOption.surfaceType = surfaceType;
            return this;
        }

        public Constants.SCREEN_TYPE getScreenType() {
            return mOption.screenType;
        }

        public Builder setScreenType(Constants.SCREEN_TYPE screenType) {
            mOption.screenType = screenType;
            return this;
        }

        public Drawable getBackgroundDrawable() {
            return mOption.backgroundDrawable;
        }

        public Builder setBackgroundDrawable(Drawable backgroundDrawable) {
            mOption.backgroundDrawable = backgroundDrawable;
            return this;
        }

        public String getVideoSource() {
            return mOption.videoSource;
        }

        public Builder setVideoSource(String videoSource) {
            mOption.videoSource = videoSource;
            return this;
        }
    }
}
