package com.mingyuechunqiu.agilemvpframe.feature.playermanager.video.player;

import com.mingyuechunqiu.agilemvpframe.feature.playermanager.video.VideoPlayerOption;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/2/21
 *     desc   : 视频播放管理器
 *              实现VideoPlayerable
 *     version: 1.0
 * </pre>
 */
class VideoPlayerManager implements VideoPlayerManagerable {

    private VideoPlayerable mPlayerable;

    VideoPlayerManager(VideoPlayerable playerable) {
        mPlayerable = playerable;
    }

    @Override
    public void start() {
        mPlayerable.start();
    }

    @Override
    public void pause() {
        mPlayerable.pause();
    }

    @Override
    public void resume() {
        mPlayerable.resume();
    }

    @Override
    public void stop() {
        mPlayerable.stop();
    }

    @Override
    public void release() {
        mPlayerable.release();
    }

    @Override
    public void setVolume(float leftVolume, float rightVolume) {
        mPlayerable.setVolume(leftVolume, rightVolume);
    }

    @Override
    public void setVideoSource(String pathOrUrl) {
        mPlayerable.setVideoSource(pathOrUrl);
    }

    @Override
    public VideoPlayerOption getVideoPlayerOption() {
        return mPlayerable.getVideoPlayerOption();
    }

}
