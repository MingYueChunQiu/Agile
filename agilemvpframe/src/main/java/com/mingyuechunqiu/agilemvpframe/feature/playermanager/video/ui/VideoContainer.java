package com.mingyuechunqiu.agilemvpframe.feature.playermanager.video.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingyuechunqiu.agilemvpframe.R;
import com.mingyuechunqiu.agilemvpframe.feature.playermanager.video.Constants;
import com.mingyuechunqiu.agilemvpframe.feature.playermanager.video.VideoPlayerOption;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/2/21
 *     desc   : 视频容器默认实现类
 *              实现VideoContainerable
 *     version: 1.0
 * </pre>
 */
public class VideoContainer implements VideoContainerable {

    private MaterialCardView mcvContainer;//父容器层
    private View vBackground;//背景层（因为对于TextureView只有它的直接父类设置背景才能有效果）
    private SurfaceView svScreen;
    private TextureView ttvScreen;
    private ImageView ivPlaceholder;//占位图层
    private TextView tvTitle;//标题
    private View vLoading;//等待加载控件
    private VideoPlayerOption mOption;//视频播放配置信息对象

    public VideoContainer(@NonNull Context context, @NonNull ViewGroup parent, @NonNull VideoPlayerOption option) {
        View vContainer = LayoutInflater.from(context).inflate(R.layout.agile_layout_video_container, parent);
        mOption = option;
        mcvContainer = vContainer.findViewById(R.id.mcv_agile_video_container);
        vBackground = vContainer.findViewById(R.id.rl_agile_video_container_background);
        if (mOption.getBackgroundDrawable() != null) {
            vBackground.setBackground(option.getBackgroundDrawable());
        }
        if (mOption.getSurfaceType() == null || mOption.getSurfaceType() == Constants.SURFACE_TYPE.TYPE_NOT_SET ||
                mOption.getSurfaceType() == Constants.SURFACE_TYPE.TYPE_SURFACE_VIEW) {
            ViewStub vsScreen = vContainer.findViewById(R.id.vs_agile_video_container_surface_view);
            vsScreen.setVisibility(View.VISIBLE);
            svScreen = vContainer.findViewById(R.id.sv_agile_video_container_screen);
            mOption.setSurfaceType(Constants.SURFACE_TYPE.TYPE_SURFACE_VIEW);
        } else {
            ViewStub vsScreen = vContainer.findViewById(R.id.vs_agile_video_container_texture_view);
            vsScreen.setVisibility(View.VISIBLE);
            ttvScreen = vContainer.findViewById(R.id.ttv_agile_video_container_screen);
            mOption.setSurfaceType(Constants.SURFACE_TYPE.TYPE_TEXTURE_VIEW);
        }
        ivPlaceholder = vContainer.findViewById(R.id.iv_agile_video_container_placeholder);
        if (mOption.getPlaceholderDrawable() != null) {
            ivPlaceholder.setImageDrawable(mOption.getPlaceholderDrawable());
        }
        tvTitle = vContainer.findViewById(R.id.tv_agile_video_container_title);
        vLoading = vContainer.findViewById(R.id.pb_agile_video_container_loading);
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T extends View> T getContainerView() {
        return (T) mcvContainer;
    }

    @Override
    public void setBackgroundDrawable(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        vBackground.setBackground(drawable);
        if (mOption != null) {
            mOption.setBackgroundDrawable(drawable);
        }
    }

    @Override
    public void setPlaceholderDrawable(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        ivPlaceholder.setImageDrawable(drawable);
        if (mOption != null) {
            mOption.setPlaceholderDrawable(drawable);
        }
    }

    @Override
    public Constants.SURFACE_TYPE getSurfaceType() {
        return svScreen != null ? Constants.SURFACE_TYPE.TYPE_SURFACE_VIEW : Constants.SURFACE_TYPE.TYPE_TEXTURE_VIEW;
    }

    @Override
    public SurfaceView getSurfaceView() {
        return svScreen;
    }

    @Override
    public TextureView getTextureView() {
        return ttvScreen;
    }

    @Override
    public ImageView getPlaceholderView() {
        return ivPlaceholder;
    }

    @Override
    public TextView getTitleView() {
        return tvTitle;
    }

    @Override
    public View getLoadingView() {
        return vLoading;
    }
}
