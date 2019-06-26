package com.mingyuechunqiu.agile.feature.playermanager.video.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.mingyuechunqiu.agile.feature.playermanager.video.VideoPlayerOption;
import com.mingyuechunqiu.agile.feature.playermanager.video.ui.VideoContainer;

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
public class VideoPlayerManagerFactory {

    /**
     * 使用默认的布局和视频播放类来配置管理器
     *
     * @param context 上下文
     * @param parent  视频容器父布局
     * @param option  视频播放配置信息对象
     * @return 返回VideoPlayerManagerable
     */
    @NonNull
    public static VideoPlayerManagerable newInstance(@NonNull Context context, @NonNull ViewGroup parent,
                                                     @NonNull VideoPlayerOption option) {
        option.setContainerable(new VideoContainer(context, parent, option));
        return newInstance(new VideoPlayer(option));
    }

    /**
     * 传入用户自定义视频播放类来配置管理器
     *
     * @param playerable 实现视频播放接口的类
     * @return 返回VideoPlayerManagerable
     */
    public static VideoPlayerManagerable newInstance(@NonNull VideoPlayerable playerable) {
        return new VideoPlayerManager(playerable);
    }

}
