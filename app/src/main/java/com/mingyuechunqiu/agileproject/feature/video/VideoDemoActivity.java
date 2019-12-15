package com.mingyuechunqiu.agileproject.feature.video;

import android.Manifest;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.material.card.MaterialCardView;
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;
import com.mingyuechunqiu.agile.feature.playermanager.video.Constants;
import com.mingyuechunqiu.agile.feature.playermanager.video.VideoPlayerOption;
import com.mingyuechunqiu.agile.feature.playermanager.video.player.VideoPlayerManagerFactory;
import com.mingyuechunqiu.agile.feature.playermanager.video.player.VideoPlayerManagerable;
import com.mingyuechunqiu.agile.receiver.NetworkConnectedTypeReceiver;
import com.mingyuechunqiu.agileproject.R;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

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
public class VideoDemoActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

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
        final String url = "http://live.ehailuo.com/ehello_123/behind.m3u8?auth_key=1582180883-0-0-e99fa77b60031f432b927c57148c4dd0";
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
                    new VideoPlayerOption.Builder()
                            .setSurfaceType(Constants.SURFACE_TYPE.TYPE_TEXTURE_VIEW)
                            .setScreenType(Constants.SCREEN_TYPE.TYPE_PROPORTION_CLIP)
//                            .setBackgroundDrawable(new ColorDrawable(Color.BLACK))
                            //  .setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.scenery)))
                            .setPlaceholderDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.scenery)))
                            .build());
            MaterialCardView cardView = managerable.getVideoPlayerOption().getContainerable().getContainerView();
            cardView.setRadius(20);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) managerable.getVideoPlayerOption().getContainerable().getTitleView()
                    .getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
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
        LogManagerProvider.d("份", "这是一次测试");
        if (!EasyPermissions.hasPermissions(this, permissions)) {
            EasyPermissions.requestPermissions(this, "申请", 1, permissions);
        }
        receiver = new NetworkConnectedTypeReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, intentFilter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    list.get(i).setVideoSource(url);
                }
                LogManagerProvider.saveErrorToLocal("分为", "这是测试是否能写入本地文件的", "demo", getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "ew.txt");
            }
        }, 3000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        new AppSettingsDialog.Builder(this)
                .setTitle("申请")
                .build();
    }
}
