package com.mingyuechunqiu.agilemvpframe.feature.videoviewmanager;

import android.content.Context;
import android.view.View;

/**
 * <pre>
 *     author : 明月春秋
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/11/10
 *     desc   : 视频播放接口
 *     version: 1.0
 * </pre>
 */
public interface VideoViewable {

    void initVideoView(Context context);

    View getVideoContainer();

    View getVideoView();

    View getVideoController();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    boolean onBackPressed();

    void setLocal(Context context, int resId);

    void setUrl(Context context, String url);
}
