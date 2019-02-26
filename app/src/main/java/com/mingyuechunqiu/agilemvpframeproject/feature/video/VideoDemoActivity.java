package com.mingyuechunqiu.agilemvpframeproject.feature.video;

import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.card.MaterialCardView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mingyuechunqiu.agilemvpframe.feature.playermanager.video.Constants;
import com.mingyuechunqiu.agilemvpframe.feature.playermanager.video.VideoPlayerOption;
import com.mingyuechunqiu.agilemvpframe.feature.playermanager.video.player.VideoPlayerManagerFactory;
import com.mingyuechunqiu.agilemvpframe.feature.playermanager.video.player.VideoPlayerManagerable;
import com.mingyuechunqiu.agilemvpframe.receiver.NetworkConnectedTypeReceiver;
import com.mingyuechunqiu.agilemvpframeproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/2/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class VideoDemoActivity extends AppCompatActivity {

    List<VideoPlayerManagerable> list;
    private NetworkConnectedTypeReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_demo);
//        RecyclerView rvList = findViewById(R.id.rv_video_demo_list);
//        rvList.setLayoutManager(new LinearLayoutManager(this));
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            list.add("http://live.ehailuo.com/ehello_123/behind.m3u8?auth_key=1582180883-0-0-e99fa77b60031f432b927c57148c4dd0");
//        }
//        rvList.setAdapter(new VideoDemoAdapter(R.layout.activity_video, list));
        LinearLayoutCompat llContainer = findViewById(R.id.ll_video_demo_container);
        String url = "http://live.ehailuo.com/ehello_123/behind.m3u8?auth_key=1582180883-0-0-e99fa77b60031f432b927c57148c4dd0";
//        FrameLayout flContainer = findViewById(R.id.fl_video_demo_container);
//        VideoPlayerManagerFactory.newInstance(this, flContainer,
//                new VideoPlayerOption.Builder().setVideoSource(url).build());
//        FrameLayout flContainer2 = findViewById(R.id.fl_video_demo_container2);
//        VideoPlayerManagerFactory.newInstance(this, flContainer2,
//                new VideoPlayerOption.Builder().setVideoSource(url).build());
//        FrameLayout flContainer3 = findViewById(R.id.fl_video_demo_container3);
//        VideoPlayerManagerFactory.newInstance(this, flContainer3,
//                new VideoPlayerOption.Builder().setVideoSource(url).build());
//        FrameLayout flContainer4 = findViewById(R.id.fl_video_demo_container4);
//        VideoPlayerManagerFactory.newInstance(this, flContainer4,
//                new VideoPlayerOption.Builder().setVideoSource(url).build());
        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FrameLayout frameLayout = new FrameLayout(this);
            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 800);
            frameLayout.setLayoutParams(params);
            llContainer.addView(frameLayout);
            VideoPlayerManagerable managerable = VideoPlayerManagerFactory.newInstance(this, frameLayout,
                    new VideoPlayerOption.Builder().setVideoSource(url)
                            .setSurfaceType(Constants.SURFACE_TYPE.TYPE_TEXTURE_VIEW)
//                            .setBackgroundDrawable(new ColorDrawable(Color.BLACK))
                            .setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.scenery)))
//                          .setPlaceholderDrawable(new ColorDrawable(Color.BLACK))
                            .build());
            MaterialCardView cardView = managerable.getVideoPlayerOption().getContainerable().getContainerView();
            cardView.setRadius(20);
            list.add(managerable);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                cardView.setOutlineProvider(new ViewOutlineProvider() {
//                    @Override
//                    public void getOutline(View view, Outline outline) {
//                        outline.setRoundRect(new Rect(0, 0, view.getWidth(), view.getHeight()),
//                                ScreenUtils.getPxFromDp(getResources(), 20));
//                    }
//                });
//            }
        }
        receiver = new NetworkConnectedTypeReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (VideoPlayerManagerable m : list) {
            m.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (VideoPlayerManagerable m : list) {
            m.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
