package com.mingyuechunqiu.agilemvpframe.feature.videoViewManager;

import android.content.Context;
import android.view.View;

/**
 * <pre>
 *     author : 明月春秋
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/11/10
 *     desc   : 视频播放管理器
 *              实现VideoViewable
 *     version: 1.0
 * </pre>
 */
public class VideoViewManager implements VideoViewable {

    private VideoViewable mVideoViewable;

    public static VideoViewManager getInstance() {
        return new VideoViewManager(new VideoViewHelper());
    }

    public static VideoViewManager getInstance(VideoViewable videoViewable) {
        return new VideoViewManager(videoViewable);
    }

    private VideoViewManager(VideoViewable videoViewable) {
        if (videoViewable == null) {
            throw new IllegalArgumentException("VideoViewable can not be null!");
        }
        mVideoViewable = videoViewable;
    }

    @Override
    public void initVideoView(Context context) {
        mVideoViewable.initVideoView(context);
    }

    @Override
    public View getVideoContainer() {
        return mVideoViewable.getVideoContainer();
    }

    @Override
    public View getVideoView() {
        return mVideoViewable.getVideoView();
    }

    @Override
    public View getVideoController() {
        return mVideoViewable.getVideoController();
    }

    @Override
    public void onStart() {
        mVideoViewable.onStart();
    }

    @Override
    public void onResume() {
        mVideoViewable.onResume();
    }

    @Override
    public void onPause() {
        mVideoViewable.onPause();
    }

    @Override
    public void onStop() {
        mVideoViewable.onStop();
    }

    @Override
    public boolean onBackPressed() {
        return mVideoViewable.onBackPressed();
    }

    @Override
    public void setLocal(Context context, int resId) {
        mVideoViewable.setLocal(context, resId);
    }

    @Override
    public void setUrl(Context context, String url) {
        mVideoViewable.setUrl(context, url);
    }
}
