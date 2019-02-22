package com.mingyuechunqiu.agilemvpframe.feature.videoviewmanager;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/2/21
 *     desc   : 视频播放管理器接口
 *              继承自VideoViewable
 *     version: 1.0
 * </pre>
 */
public interface VideoViewManagerable extends VideoViewable {

    /**
     * 设置视频播放类
     *
     * @param videoViewable 视频播放类
     */
    void setVideoViewable(VideoViewable videoViewable);

    /**
     * 获取视频播放类
     *
     * @return 返回视频播放对象
     */
    VideoViewable getVideoViewable();

}
