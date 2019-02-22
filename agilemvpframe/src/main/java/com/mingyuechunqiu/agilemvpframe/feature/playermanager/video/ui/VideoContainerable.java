package com.mingyuechunqiu.agilemvpframe.feature.playermanager.video.ui;

import android.graphics.drawable.Drawable;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingyuechunqiu.agilemvpframe.feature.playermanager.video.Constants;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/2/21
 *     desc   : 视频容器接口
 *     version: 1.0
 * </pre>
 */
public interface VideoContainerable {

    <T extends View> T getContainerView();

    /**
     * 设置背景图片
     *
     * @param drawable 图片
     */
    void setBackgroundDrawable(Drawable drawable);

    /**
     * 设置占位图
     *
     * @param drawable 占位图
     */
    void setPlaceholderDrawable(Drawable drawable);

    /**
     * 获取图层类型
     *
     * @return 返回图层类型枚举类
     */
    Constants.SURFACE_TYPE getSurfaceType();

    /**
     * 获取视频播放图层控件
     *
     * @return 返回视图层控件
     */
    SurfaceView getSurfaceView();

    /**
     * 获取视频播放图层控件
     *
     * @return 返回视图层控件
     */
    TextureView getTextureView();

    /**
     * 获取占位图控件
     *
     * @return 返回图片控件
     */
    ImageView getPlaceholderView();

    /**
     * 获取标题控件
     *
     * @return 返回文本控件
     */
    TextView getTitleView();

    /**
     * 获取加载等待控件
     *
     * @return 返回控件
     */
    View getLoadingView();
}
