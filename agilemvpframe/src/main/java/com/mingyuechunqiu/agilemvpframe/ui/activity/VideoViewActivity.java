package com.mingyuechunqiu.agilemvpframe.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.mingyuechunqiu.agilemvpframe.R;
import com.mingyuechunqiu.agilemvpframe.base.presenter.BaseNetPresenter;
import com.mingyuechunqiu.agilemvpframe.feature.videoviewmanager.Constants;
import com.mingyuechunqiu.agilemvpframe.feature.videoviewmanager.VideoViewManagerFactory;
import com.mingyuechunqiu.agilemvpframe.feature.videoviewmanager.VideoViewManagerable;
import com.mingyuechunqiu.agilemvpframe.feature.videoviewmanager.YCVideoPlayerHelper;
import com.mingyuechunqiu.agilemvpframe.util.ToolbarUtils;

import static com.mingyuechunqiu.agilemvpframe.constants.CommonConstants.BUNDLE_NAVIGATION_TITLE;
import static com.mingyuechunqiu.agilemvpframe.constants.KeyPrefixConstants.KEY_BUNDLE;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/8/11
 *     desc   : 视频播放界面
 *              继承BaseToolbarPresenterActivity
 *     version: 1.0
 * </pre>
 */
public class VideoViewActivity extends BaseToolbarPresenterActivity {

    //视频播放器帮助类实例类型
    public static final String BUNDLE_VIDEO_VIEW_HELPER_TYPE = KEY_BUNDLE + "video_view_helper_type";
    //本地视频地址
    public static final String BUNDLE_LOCAL_VIDEO_RES_ID = KEY_BUNDLE + "local_video_res_id";

    private VideoViewManagerable vvManager;

    @Override
    protected void onResume() {
        super.onResume();
        if (vvManager != null) {
            vvManager.onStart();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (vvManager != null) {
            vvManager.onPause();
        }
    }

    @Override
    public void onBackPressed() {
        if (vvManager.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void attachPresenter() {
        //由于本界面不需要使用present，所以重写父类方法，不进行present相关设置
    }

    @Override
    protected ToolbarUtils.ToolbarBean setToolbarBean() {
        return new ToolbarUtils.ToolbarBean.Builder()
                .setImmerse(true)
                .build();
    }

    @Override
    protected BaseNetPresenter initPresenter() {
        return null;
    }

    @Override
    protected void release() {
        if (vvManager != null) {
            vvManager.onStop();
            vvManager = null;
        }
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.agile_layout_navigation);
        FrameLayout container = findViewById(R.id.fl_navigation_container);
        mToolbar = findViewById(R.id.tb_navigation_bar);
        actvToolbarTitle = findViewById(R.id.tv_navigation_title);
        AppCompatImageView ivBack = findViewById(R.id.iv_navigation_left_icon);
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(new View.OnClickListener() {
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
        initVideoView(container);
    }

    /**
     * 初始化视频播放器
     *
     * @param container 父容器
     */
    private void initVideoView(FrameLayout container) {
        if (getIntent().getExtras() != null) {
            int videoHelperType = getIntent().getExtras().getInt(BUNDLE_VIDEO_VIEW_HELPER_TYPE,
                    Constants.TYPE_ORIGINAL_HELPER);
            if (videoHelperType == Constants.TYPE_ORIGINAL_HELPER) {
                vvManager = VideoViewManagerFactory.newInstance();
            } else if (videoHelperType == Constants.TYPE_THIRD_YC_HELPER) {
                vvManager = VideoViewManagerFactory.newInstance(new YCVideoPlayerHelper());
            }
        }
        container.addView(vvManager.getVideoContainer());
        vvManager.initVideoView(this);
        setLocalVideo();
    }

    /**
     * 播放本地视频
     */
    private void setLocalVideo() {
        if (getIntent().getExtras() != null) {
            int localResId = getIntent().getIntExtra(BUNDLE_LOCAL_VIDEO_RES_ID, -1);
            if (localResId != 0) {
                vvManager.setLocal(this, localResId);
            }
        }
    }
}
