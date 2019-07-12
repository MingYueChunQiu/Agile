package com.mingyuechunqiu.agile.feature.playermanager.video.player;

import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.View;

import com.mingyuechunqiu.agile.feature.playermanager.video.Constants;
import com.mingyuechunqiu.agile.feature.playermanager.video.VideoPlayerOption;

import java.io.IOException;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/2/21
 *     desc   : 视频播放类
 *              实现VideoPlayerable, SurfaceHolder.Callback
 *     version: 1.0
 * </pre>
 */
class VideoPlayer implements VideoPlayerable, SurfaceHolder.Callback, TextureView.SurfaceTextureListener {

    private MediaPlayer mPlayer;
    private VideoPlayerOption mOption;
    private boolean hasSurfaceCreated;//标记播放界面是否已经创建完毕
    private boolean hasPrepared;//标记是否已经准备好资源

    VideoPlayer(@NonNull VideoPlayerOption option) {
        initMediaPlayer();
        mOption = option;
        if (mOption.getSurfaceType() == Constants.SURFACE_TYPE.TYPE_SURFACE_VIEW) {
            mOption.getContainerable().getSurfaceView().getHolder().addCallback(this);
        } else {
            mOption.getContainerable().getTextureView().setSurfaceTextureListener(this);
        }
        if (!checkObjectIsNull(mOption.getContainerable().getPlaceholderView())) {
            mOption.getContainerable().getPlaceholderView().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void start() {
        if (checkObjectIsNull(mPlayer) || !hasSurfaceCreated || mPlayer.isPlaying()) {
            return;
        }
        startPlayVideo();
    }

    @Override
    public void pause() {
        if (checkObjectIsNull(mPlayer) || !hasPrepared || !mPlayer.isPlaying()) {
            return;
        }
        mPlayer.pause();
    }

    @Override
    public void resume() {
        if (checkObjectIsNull(mPlayer) || !hasPrepared) {
            return;
        }
        playVideo();
    }

    @Override
    public void stop() {
        if (checkObjectIsNull(mPlayer) || !hasPrepared) {
            return;
        }
        mPlayer.stop();
    }

    @Override
    public void release() {
        releaseMediaPlayer();
        mOption = null;
    }

    @Override
    public void setVolume(float leftVolume, float rightVolume) {
        if (checkObjectIsNull(mPlayer)) {
            return;
        }
        mPlayer.setVolume(leftVolume, rightVolume);
    }

    @Override
    public void setVideoSource(String pathOrUrl) {
        if (TextUtils.isEmpty(pathOrUrl) || checkObjectIsNull(mPlayer)) {
            return;
        }
        mOption.setVideoSource(pathOrUrl);
        if (hasSurfaceCreated) {
            if (hasPrepared) {
                mPlayer.stop();
            }
            prepareVideoSource();
        }
    }

    @Override
    public VideoPlayerOption getVideoPlayerOption() {
        return mOption;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initAndStarPlayVideo(holder, null);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseMediaPlayer();
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        initAndStarPlayVideo(null, surface);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        releaseMediaPlayer();
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    /**
     * 检测对象是否为空
     *
     * @param o 对象
     * @return 如果为空返回true，否则返回false
     */
    private boolean checkObjectIsNull(Object o) {
        return o == null;
    }

    /**
     * 当图层创建好时初始化资源并开始播放视频
     *
     * @param holder  Surface持有者
     * @param surface 图层
     */
    private void initAndStarPlayVideo(SurfaceHolder holder, SurfaceTexture surface) {
        hasSurfaceCreated = true;
        if (checkObjectIsNull(mPlayer)) {
            if (mOption == null) {
                return;
            } else {
                initMediaPlayer();
                if (!TextUtils.isEmpty(mOption.getVideoSource())) {
                    showLoadingView();
                }
            }
        }
        if (holder != null) {
            mPlayer.setDisplay(holder);
        } else {
            mPlayer.setSurface(new Surface(surface));
        }
        startPlayVideo();
    }

    /**
     * 初始化播放器
     */
    private void initMediaPlayer() {
        mPlayer = new MediaPlayer();
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                hasPrepared = true;
                playVideo();
//                Log.d("份", mp.getVideoWidth() + " " + mp.getVideoHeight() + " "
//                        + mOption.getContainerable().getContainerView().getWidth() + " " +
//                        mOption.getContainerable().getContainerView().getHeight());
//                float scaleX = mOption.getContainerable().getContainerView().getWidth() * 1.0f / mp.getVideoWidth();
//                float scaleY = mOption.getContainerable().getContainerView().getHeight() * 1.0f / mp.getVideoHeight();
//                float scale = scaleX > scaleY ? scaleX : scaleY;
//                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//                        mOption.getContainerable().getContainerView().getWidth(),
//                        (int) (mOption.getContainerable().getContainerView().getHeight() * scale)
//                );
//                Log.d("份", params.width + " " + params.height);
//                mOption.getContainerable().getContainerView().setLayoutParams(params);
//                mOption.getContainerable().getContainerView().setTranslationY(-(params.height -
//                        mOption.getContainerable().getContainerView().getHeight()) * 1.0f / 2);
            }
        });
        mPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                if (mOption.getScreenType() == Constants.SCREEN_TYPE.TYPE_PROPORTION_CLIP) {
                    if (mOption.getSurfaceType() == Constants.SURFACE_TYPE.TYPE_TEXTURE_VIEW) {
                        updateTextureViewSizeCenterCrop();
                    }
                }
            }
        });
    }

    /**
     * 准备视频资源
     */
    private void prepareVideoSource() {
        if (!TextUtils.isEmpty(mOption.getVideoSource())) {
            showLoadingView();
            try {
                mPlayer.setDataSource(mOption.getVideoSource());
                mPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
                hideLoadingView();
            }
        }
    }

    /**
     * 开始播放视频
     */
    private void startPlayVideo() {
        if (hasPrepared) {
            playVideo();
        } else {
            prepareVideoSource();
        }
    }

    /**
     * 播放视频
     */
    private void playVideo() {
        mPlayer.start();
        //TextureView在播放时默认是白色透明的，设置了占位图但如果在start（）前调用，
        //会先隐藏占位图出现一个白屏再开始播放，所以要调整顺序
        hideLoadingView();
        if (!checkObjectIsNull(mOption.getContainerable().getPlaceholderView())) {
            mOption.getContainerable().getPlaceholderView().setVisibility(View.GONE);
        }
    }

    /**
     * 释放播放器
     */
    private void releaseMediaPlayer() {
        if (checkObjectIsNull(mPlayer)) {
            return;
        }
        mPlayer.stop();
        mPlayer.reset();
        mPlayer.release();
        mPlayer = null;
        hasPrepared = false;
        hasSurfaceCreated = false;
    }

    //重新计算video的显示位置，裁剪后全屏显示
    private void updateTextureViewSizeCenterCrop() {
        int videoHeight = mPlayer.getVideoHeight();
        int videoWidth = mPlayer.getVideoWidth();
        int width = mOption.getContainerable().getTextureView().getWidth();
        int height = mOption.getContainerable().getTextureView().getHeight();
        float sx = width * 1.0f / videoWidth;
        float sy = height * 1.0f / videoHeight;

        Matrix matrix = new Matrix();
        float maxScale = Math.max(sx, sy);

        //第1步:把视频区移动到View区,使两者中心点重合.
        matrix.preTranslate((width - videoWidth) * 1.0f / 2, (height - videoHeight) * 1.0f / 2);

        //第2步:因为默认视频是fitXY的形式显示的,所以首先要缩放还原回来.
        matrix.preScale(videoWidth * 1.0f / width, videoHeight * 1.0f / height);

        //第3步,等比例放大或缩小,直到视频区的一边超过View一边, 另一边与View的另一边相等. 因为超过的部分超出了View的范围,所以是不会显示的,相当于裁剪了.
        matrix.postScale(maxScale, maxScale, width * 1.0f / 2, height * 1.0f / 2);//后两个参数坐标是以整个View的坐标系以参考的
        mOption.getContainerable().getTextureView().setTransform(matrix);
        mOption.getContainerable().getTextureView().postInvalidate();
    }

    /**
     * 显示等待加载控件
     */
    private void showLoadingView() {
        if (checkLoadingViewForbidSetVisibility(View.VISIBLE)) {
            return;
        }
        mOption.getContainerable().getLoadingView().setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏等待加载控件
     */
    private void hideLoadingView() {
        if (checkLoadingViewForbidSetVisibility(View.GONE)) {
            return;
        }
        mOption.getContainerable().getLoadingView().setVisibility(View.GONE);
    }

    /**
     * 检查等待加载控件是否无效
     *
     * @return 如果无效返回true，否则返回false
     */
    private boolean checkLoadingViewForbidSetVisibility(int visibility) {
        return checkObjectIsNull(mOption) || checkObjectIsNull(mOption.getContainerable()) ||
                checkObjectIsNull(mOption.getContainerable().getLoadingView()) ||
                mOption.getContainerable().getLoadingView().getVisibility() == visibility;
    }
}
