package com.mingyuechunqiu.agilemvpframe.feature.videoviewmanager;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.mingyuechunqiu.agilemvpframe.R;

import org.yczbj.ycvideoplayerlib.constant.ConstantKeys;
import org.yczbj.ycvideoplayerlib.controller.VideoPlayerController;
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;

/**
 * <pre>
 *     author : 明月春秋
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/11/10
 *     desc   : 第三方库YCVideoPlayerHelper的使用实例
 *     version: 1.0
 * </pre>
 */
public class YCVideoPlayerHelper extends BaseVideoViewHelper {

    private VideoPlayer vpVideo;

    @Override
    public void initVideoView(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context can not be null!");
        }
        vContainer = View.inflate(context, R.layout.agile_fragment_yc_video_player, null);
        vpVideo = vContainer.findViewById(R.id.vv_yc_video);
        acivThumbnail = vContainer.findViewById(R.id.iv_yc_video_thumbnail);
        vpVideo.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_NATIVE);
        mpContainer = new VideoPlayerController(context);
        VideoPlayerController controller = (VideoPlayerController) mpContainer;
        controller.setTitle(context.getResources().getString(R.string.app_name));
        controller.findViewById(R.id.share).setVisibility(View.GONE);
        vpVideo.setController(controller);
    }

    @Override
    public View getVideoContainer() {
        return vContainer;
    }

    @Override
    public View getVideoView() {
        return vpVideo;
    }

    @Override
    public View getVideoController() {
        return mpContainer;
    }

    @Override
    public void onStart() {
        if (vpVideo == null) {
            return;
        }
        vpVideo.start();
    }

    @Override
    public void onResume() {
        VideoPlayerManager.instance().resumeVideoPlayer();
    }

    @Override
    public void onPause() {
        if (vpVideo == null || vpVideo.isPlaying()) {
            return;
        }
        vpVideo.pause();
    }

    @Override
    public void onStop() {
        VideoPlayerManager.instance().releaseVideoPlayer();
    }

    @Override
    public boolean onBackPressed() {
        return VideoPlayerManager.instance().onBackPressed();
    }

    @Override
    public void setLocal(Context context, int resId) {
        if (vpVideo == null || context == null || resId == 0) {
            return;
        }
        vpVideo.setUp(getUriWithResId(context, resId).toString(), null);
    }

    @Override
    public void setUrl(Context context, String url) {
        if (vpVideo == null || context == null || TextUtils.isEmpty(url)) {
            return;
        }
        vpVideo.setUp(url, null);
    }
}
