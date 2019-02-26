package com.mingyuechunqiu.agilemvpframeproject.feature.video;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.card.MaterialCardView;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.mingyuechunqiu.agilemvpframe.feature.playermanager.video.VideoPlayerOption;
import com.mingyuechunqiu.agilemvpframe.feature.playermanager.video.player.VideoPlayerManagerFactory;
import com.mingyuechunqiu.agilemvpframe.feature.playermanager.video.player.VideoPlayerManagerable;
import com.mingyuechunqiu.agilemvpframeproject.R;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/2/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class VideoActivity extends AppCompatActivity {
    VideoPlayerManagerable managerable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        FrameLayout flContainer = findViewById(R.id.fl_video);
        managerable = VideoPlayerManagerFactory.newInstance(this, flContainer,
                new VideoPlayerOption.Builder().setVideoSource("http://live.ehailuo.com/ehello_123/behind.m3u8?auth_key=1582180883-0-0-e99fa77b60031f432b927c57148c4dd0")
                        .setPlaceholderDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))).build());
        MaterialCardView cardView = managerable.getVideoPlayerOption().getContainerable().getContainerView();
        cardView.setRadius(20);
//        managerable.setVideoSource("http://live.ehailuo.com/ehello_123/behind.m3u8?auth_key=1582180883-0-0-e99fa77b60031f432b927c57148c4dd0");
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        managerable.pause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        managerable.start();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        managerable.releaseView();
//    }
}
