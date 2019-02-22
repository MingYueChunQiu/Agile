package com.mingyuechunqiu.agilemvpframe.feature.videoviewmanager;

import android.support.annotation.NonNull;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/2/21
 *     desc   : 视频播放管理器工厂类
 *     version: 1.0
 * </pre>
 */
public class VideoViewManagerFactory {

    /**
     * 获取视频播放管理器实例（默认使用VideoViewHelper）
     *
     * @return 返回视频播放管理器实例
     */
    @NonNull
    public static VideoViewManagerable newInstance() {
        return newInstance(new VideoViewHelper());
    }

    /**
     * 获取视频播放管理器实例
     *
     * @param videoViewable 视频帮助类
     * @return 返回视频播放管理器实例
     */
    @NonNull
    public static VideoViewManagerable newInstance(VideoViewable videoViewable) {
        return new VideoViewManager(videoViewable);
    }

}
