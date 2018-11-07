package com.mingyuechunqiu.agilemvpframe.ui.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.mingyuechunqiu.agilemvpframe.R;
import com.mingyuechunqiu.agilemvpframe.base.presenter.BaseDialogPresenter;
import com.mingyuechunqiu.agilemvpframe.util.ToolbarUtils;

import static com.mingyuechunqiu.agilemvpframe.constants.CommonConstants.BUNDLE_NAVIGATION_TITLE;
import static com.mingyuechunqiu.agilemvpframe.constants.KeyPrefixConstants.KEY_BUNDLE;
import static com.mingyuechunqiu.agilemvpframe.util.ToolbarUtils.NO_RESOURCE_ID;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/8/11
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class VideoViewActivity extends BaseToolbarPresenterActivity {

    //本地视频地址
    public static final String BUNDLE_LOCAL_VIDEO_RES_ID = KEY_BUNDLE + "local_video_res_id";

    private VideoView vvVideo;
    private AppCompatImageView acivThumbnail, acivPlay;

    private boolean isDataLoaded;//标记数据是否加载好了

    @Override
    protected void onResume() {
        super.onResume();
        if (!vvVideo.isPlaying()) {
            vvVideo.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (vvVideo.canPause()) {
            vvVideo.pause();
        }
    }

    @Override
    protected void attachPresenter() {
        //由于本界面不需要使用present，所以重写父类方法，不进行present相关设置
    }

    @Override
    protected ToolbarUtils.ToolbarBean setToolbarBean() {
        return new ToolbarUtils.ToolbarBean.Builder()
                .setImmerse(true)
                .setHasCustomTitle(true)
                .setNavigationIconResId(NO_RESOURCE_ID)
                .build();
    }

    @Override
    protected BaseDialogPresenter initPresenter() {
        return null;
    }

    @Override
    protected void release() {
        vvVideo.stopPlayback();
        isDataLoaded = false;
    }

    @Override
    protected void initView() {
        setContentView(R.layout.layout_navigation);
        FrameLayout container = findViewById(R.id.fl_navigation_container);
        View view = getLayoutInflater().inflate(R.layout.fragment_video_view, container, false);
        mToolbar = findViewById(R.id.tb_navigation_bar);
        actvToolbarTitle = findViewById(R.id.tv_navigation_title);
        AppCompatImageView acivBack = findViewById(R.id.iv_navigation_left_icon);
        acivBack.setVisibility(View.VISIBLE);
        acivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(mToolbar);
        String title = getIntent().getStringExtra(BUNDLE_NAVIGATION_TITLE);
        if (!TextUtils.isEmpty(title)) {
            actvToolbarTitle.setText(title);
        }
        container.addView(view);

        vvVideo = view.findViewById(R.id.vv_video);
        acivThumbnail = view.findViewById(R.id.iv_video_thumbnail);
        acivPlay = view.findViewById(R.id.iv_video_play);
        acivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDataLoaded) {
                    acivPlay.setVisibility(View.GONE);
                    vvVideo.start();
                }
            }
        });
        initVideoView();
        playLocalVideo();
    }

    /**
     * 初始化视频多媒体控件
     */
    private void initVideoView() {
        MediaController controller = new MediaController(this);
        vvVideo.setMediaController(controller);
        vvVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                isDataLoaded = true;
            }
        });
        vvVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.seekTo(0);
                mp.pause();
                acivThumbnail.setVisibility(View.VISIBLE);
                acivPlay.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 播放本地视频
     */
    private void playLocalVideo() {
        if (getIntent().getExtras() != null) {
            int localResId = getIntent().getIntExtra(BUNDLE_LOCAL_VIDEO_RES_ID, -1);
            if (localResId > -1) {
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + localResId);
                Glide.with(this).load(uri).into(acivThumbnail);
                vvVideo.setVideoURI(uri);
            }
        }
    }
}
