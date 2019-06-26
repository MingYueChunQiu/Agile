package com.mingyuechunqiu.agile.framework.engine;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import java.io.File;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/28
 *     desc   : 图片引擎接口
 *     version: 1.0
 * </pre>
 */
public interface IImageEngine {

    /**
     * 显示网络图片
     *
     * @param context          上下文
     * @param imageView        显示控件
     * @param url              图片地址
     * @param placeholderResId 占位图
     * @param errorResId       错误图
     */
    void showImage(Context context, ImageView imageView, String url, @DrawableRes int placeholderResId,
                   @DrawableRes int errorResId);

    /**
     * 显示网络图片
     *
     * @param fragment         碎片界面
     * @param imageView        显示控件
     * @param url              图片地址
     * @param placeholderResId 占位图
     * @param errorResId       错误图
     */
    void showImage(Fragment fragment, ImageView imageView, String url, @DrawableRes int placeholderResId,
                   @DrawableRes int errorResId);

    /**
     * 显示网络图片
     *
     * @param context     上下文
     * @param imageView   显示控件
     * @param url         图片地址
     * @param placeholder 占位图
     * @param errorRes    错误图
     */
    void showImage(Context context, ImageView imageView, String url, Drawable placeholder, Drawable errorRes);

    /**
     * 显示网络图片
     *
     * @param fragment    碎片
     * @param imageView   显示控件
     * @param url         图片地址
     * @param placeholder 占位图
     * @param errorRes    错误图
     */
    void showImage(Fragment fragment, ImageView imageView, String url, Drawable placeholder, Drawable errorRes);

    /**
     * 显示本地文件图片
     *
     * @param context          上下文
     * @param imageView        显示控件
     * @param file             本地图片文件
     * @param placeholderResId 占位图
     * @param errorResId       错误图
     */
    void showImage(Context context, ImageView imageView, File file, @DrawableRes int placeholderResId,
                   @DrawableRes int errorResId);

    /**
     * 显示本地文件图片
     *
     * @param fragment         碎片界面
     * @param imageView        显示控件
     * @param file             本地图片文件
     * @param placeholderResId 占位图
     * @param errorResId       错误图
     */
    void showImage(Fragment fragment, ImageView imageView, File file, @DrawableRes int placeholderResId,
                   @DrawableRes int errorResId);

    /**
     * 显示本地文件图片
     *
     * @param context     上下文
     * @param imageView   显示控件
     * @param file        本地图片文件
     * @param placeholder 占位图
     * @param errorRes    错误图
     */
    void showImage(Context context, ImageView imageView, File file, Drawable placeholder, Drawable errorRes);

    /**
     * 显示本地文件图片
     *
     * @param fragment    碎片界面
     * @param imageView   显示控件
     * @param file        本地图片文件
     * @param placeholder 占位图
     * @param errorRes    错误图
     */
    void showImage(Fragment fragment, ImageView imageView, File file, Drawable placeholder, Drawable errorRes);

    /**
     * 显示图片
     *
     * @param context          上下文
     * @param imageView        显示控件
     * @param drawable         显示的图片
     * @param placeholderResId 占位图
     * @param errorResId       错误图
     */
    void showImage(Context context, ImageView imageView, Drawable drawable,
                   @DrawableRes int placeholderResId, @DrawableRes int errorResId);

    /**
     * 显示图片
     *
     * @param fragment         碎片界面
     * @param imageView        显示控件
     * @param drawable         显示的图片
     * @param placeholderResId 占位图
     * @param errorResId       错误图
     */
    void showImage(Fragment fragment, ImageView imageView, Drawable drawable,
                   @DrawableRes int placeholderResId, @DrawableRes int errorResId);

    /**
     * 显示图片
     *
     * @param context     上下文
     * @param imageView   显示控件
     * @param drawable    显示的图片
     * @param placeholder 占位图
     * @param errorRes    错误图
     */
    void showImage(Context context, ImageView imageView, Drawable drawable, Drawable placeholder,
                   Drawable errorRes);

    /**
     * 显示图片
     *
     * @param fragment    碎片界面
     * @param imageView   显示控件
     * @param drawable    显示的图片
     * @param placeholder 占位图
     * @param errorRes    错误图
     */
    void showImage(Fragment fragment, ImageView imageView, Drawable drawable, Drawable placeholder,
                   Drawable errorRes);

    /**
     * 显示圆角图片
     *
     * @param context       上下文
     * @param imageView     显示控件
     * @param drawableModel 显示图片对象
     * @param radiusDp      圆角半径（单位为dp）
     */
    void showRoundCornersImage(Context context, ImageView imageView, Object drawableModel, int radiusDp);

    /**
     * 显示圆角图片
     *
     * @param fragment      碎片界面
     * @param imageView     显示控件
     * @param drawableModel 显示图片对象
     * @param radiusDp      圆角半径（单位为dp）
     */
    void showRoundCornersImage(Fragment fragment, ImageView imageView, Object drawableModel, int radiusDp);
}
