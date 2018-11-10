package com.mingyuechunqiu.agilemvpframe.feature.videoViewManager;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.mingyuechunqiu.agilemvpframe.R;

/**
 * <pre>
 *     author : 明月春秋
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/11/10
 *     desc   : 视频播放实例
 *              继承自BaseVideoView
 *     version: 1.0
 * </pre>
 */
public class VideoViewHelper extends BaseVideoView {

    private VideoView vvVideo;
    private AppCompatImageView acivPlay;

    @Override
    public void initVideoView(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context can not be null!");
        }
        vContainer = View.inflate(context, R.layout.fragment_video_view, null);
        vvVideo = vContainer.findViewById(R.id.vv_video);
        acivThumbnail = vContainer.findViewById(R.id.iv_video_thumbnail);
        acivPlay = vContainer.findViewById(R.id.iv_video_play);
        mpContainer = new MediaController(context);
        vvVideo.setMediaController((MediaController) mpContainer);
        vvVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                isDataLoaded = true;
            }
        });
        vvVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.seekTo(0);
                mp.pause();
                if (acivThumbnail != null) {
                    acivThumbnail.setVisibility(View.VISIBLE);
                }
                if (acivPlay != null) {
                    acivPlay.setVisibility(View.VISIBLE);
                }
            }
        });
        acivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDataLoaded) {
                    acivPlay.setVisibility(View.GONE);
                    vvVideo.start();
                }
            }
        });
    }

    @Override
    public View getVideoContainer() {
        return vContainer;
    }

    @Override
    public View getVideoView() {
        return vvVideo;
    }

    @Override
    public View getMediaPlayerController() {
        return mpContainer;
    }

    @Override
    public void onStart() {
        if (vvVideo == null) {
            return;
        }
        vvVideo.start();
    }

    @Override
    public void onResume() {
        if (vvVideo == null || vvVideo.isPlaying()) {
            return;
        }
        vvVideo.resume();
    }

    @Override
    public void onPause() {
        if (vvVideo == null) {
            return;
        }
        vvVideo.pause();
    }

    @Override
    public void onStop() {
        if (vvVideo == null) {
            return;
        }
        vvVideo.stopPlayback();
        isDataLoaded = false;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void setLocal(Context context, int resId) {
        if (vvVideo == null || context == null || resId == 0) {
            return;
        }
        Uri uri = getUriWithResId(context, resId);
        Glide.with(context).load(uri).into(acivThumbnail);
        vvVideo.setVideoURI(uri);
    }

    @Override
    public void setUrl(Context context, String url) {
        if (vvVideo == null || context == null || TextUtils.isEmpty(url)) {
            return;
        }
        vvVideo.setVideoURI(Uri.parse(url));
    }
}
