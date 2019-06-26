package com.mingyuechunqiu.agileproject.feature.video;

import android.animation.ValueAnimator;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.card.MaterialCardView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.FrameLayout;

import com.mingyuechunqiu.agile.feature.playermanager.video.VideoPlayerOption;
import com.mingyuechunqiu.agile.feature.playermanager.video.player.VideoPlayerManagerFactory;
import com.mingyuechunqiu.agile.feature.playermanager.video.player.VideoPlayerManagerable;
import com.mingyuechunqiu.agileproject.R;

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
        AppCompatButton btnBig = findViewById(R.id.btn_big);
        final FrameLayout flSmall = findViewById(R.id.fl_small);
        final FrameLayout flBig = findViewById(R.id.fl_big);
        btnBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = flSmall.getChildAt(0);
                flSmall.removeViewAt(0);
                flBig.addView(view);
                ValueAnimator animator = ValueAnimator.ofInt(flSmall.getHeight(), 0).setDuration(500);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        flSmall.getLayoutParams().height = (int) animation.getAnimatedValue();
                        flSmall.requestLayout();
                    }
                });
//                animator.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        flSmall.getLayoutParams().height = 0;
//                    }
//                });
                animator.start();
            }
        });
        managerable = VideoPlayerManagerFactory.newInstance(this, flSmall,
                new VideoPlayerOption.Builder().setVideoSource("http://live.ehailuo.com/ehello_123/behind.m3u8?auth_key=1582180883-0-0-e99fa77b60031f432b927c57148c4dd0")
                        .setPlaceholderDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))).build());
        MaterialCardView cardView = managerable.getVideoPlayerOption().getContainerable().getContainerView();
        cardView.setRadius(20);
//        managerable.setVideoSource("http://live.ehailuo.com/ehello_123/behind.m3u8?auth_key=1582180883-0-0-e99fa77b60031f432b927c57148c4dd0");
    }

    @Override
    protected void onPause() {
        super.onPause();
        managerable.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        managerable.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        managerable.release();
    }
}
