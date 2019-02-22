package com.mingyuechunqiu.agilemvpframe.feature.videoviewmanager;

import android.content.Context;
import android.view.View;

/**
 * <pre>
 *     author : 明月春秋
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/11/10
 *     desc   : 视频播放管理器
 *              实现VideoViewManagerable
 *     version: 1.0
 * </pre>
 */
class VideoViewManager implements VideoViewManagerable {

    private VideoViewable mVideoViewable;

    VideoViewManager(VideoViewable videoViewable) {
        mVideoViewable = videoViewable;
        if (mVideoViewable == null) {
            mVideoViewable = new VideoViewHelper();
        }
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

    /**
     * 设置视频播放管理器新的具体操作实例
     *
     * @param videoViewable 视频帮助类实例
     */
    @Override
    public void setVideoViewable(VideoViewable videoViewable) {
        if (videoViewable == null) {
            return;
        }
        mVideoViewable = videoViewable;
    }

    @Override
    public VideoViewable getVideoViewable() {
        return mVideoViewable;
    }
}
