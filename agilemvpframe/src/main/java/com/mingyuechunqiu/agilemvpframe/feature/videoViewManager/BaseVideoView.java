package com.mingyuechunqiu.agilemvpframe.feature.videoViewManager;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

/**
 * <pre>
 *     author : 明月春秋
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/11/10
 *     desc   : 视频播放基础功能类
 *              实现VideoViewable
 *     version: 1.0
 * </pre>
 */
public abstract class BaseVideoView implements VideoViewable {

    protected View vContainer;
    protected View mpContainer;
    protected AppCompatImageView acivThumbnail;

    protected boolean isDataLoaded;//标记数据是否加载好了

    /**
     * 根据资源ID获取URI
     *
     * @param context 上下文
     * @param resId   资源ID
     * @return 返回生成的URI
     */
    protected Uri getUriWithResId(Context context, int resId) {
        if (context == null || resId == 0) {
            return null;
        }
        return Uri.parse("android.resource://" + context.getPackageName() + "/raw/" + resId);
    }

}
