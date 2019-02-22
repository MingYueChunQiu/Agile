package com.mingyuechunqiu.agilemvpframeproject.feature.video;

import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mingyuechunqiu.agilemvpframe.feature.playermanager.video.VideoPlayerOption;
import com.mingyuechunqiu.agilemvpframe.feature.playermanager.video.player.VideoPlayerManagerFactory;
import com.mingyuechunqiu.agilemvpframeproject.R;

import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/2/22
 *     desc   : 视频播放适配器
 *     version: 1.0
 * </pre>
 */
public class VideoDemoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public VideoDemoAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        FrameLayout flContainer = helper.getView(R.id.fl_video);
        VideoPlayerManagerFactory.newInstance(mContext, flContainer,
                new VideoPlayerOption.Builder().setVideoSource(item).build());
    }
}
