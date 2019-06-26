package com.mingyuechunqiu.agile.feature.playermanager.video.player;

import com.mingyuechunqiu.agile.feature.playermanager.video.VideoPlayerOption;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/2/21
 *     desc   : 视频播放接口
 *     version: 1.0
 * </pre>
 */
public interface VideoPlayerable {

    void start();

    void pause();

    void resume();

    void stop();

    void release();

    /**
     * 设置左右音量
     *
     * @param leftVolume  左音量
     * @param rightVolume 右音量
     */
    void setVolume(float leftVolume, float rightVolume);

    /**
     * 设置视频来源
     *
     * @param pathOrUrl 本地路径或URL
     */
    void setVideoSource(String pathOrUrl);

    /**
     * 获取配置信息对象
     *
     * @return 返回配置信息对象实例
     */
    VideoPlayerOption getVideoPlayerOption();

}
