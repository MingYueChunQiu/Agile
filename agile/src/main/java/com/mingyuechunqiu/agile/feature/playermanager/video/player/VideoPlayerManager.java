package com.mingyuechunqiu.agile.feature.playermanager.video.player;

import com.mingyuechunqiu.agile.feature.playermanager.video.VideoPlayerOption;

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

    private final VideoPlayerable mPlayer;

    VideoPlayerManager(VideoPlayerable player) {
        mPlayer = player;
    }

    @Override
    public void start() {
        mPlayer.start();
    }

    @Override
    public void pause() {
        mPlayer.pause();
    }

    @Override
    public void resume() {
        mPlayer.resume();
    }

    @Override
    public void stop() {
        mPlayer.stop();
    }

    @Override
    public void release() {
        mPlayer.release();
    }

    @Override
    public void setVolume(float leftVolume, float rightVolume) {
        mPlayer.setVolume(leftVolume, rightVolume);
    }

    @Override
    public void setVideoSource(String pathOrUrl) {
        mPlayer.setVideoSource(pathOrUrl);
    }

    @Override
    public VideoPlayerOption getVideoPlayerOption() {
        return mPlayer.getVideoPlayerOption();
    }

}
