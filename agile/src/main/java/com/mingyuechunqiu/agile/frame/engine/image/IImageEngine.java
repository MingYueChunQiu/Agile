package com.mingyuechunqiu.agile.frame.engine.image;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.File;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/28
 *     desc   : 框架图片引擎接口
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
    void showImage(@NonNull Context context, @NonNull ImageView imageView, @NonNull String url,
                   @DrawableRes int placeholderResId, @DrawableRes int errorResId);

    /**
     * 显示网络图片
     *
     * @param fragment         界面
     * @param imageView        显示控件
     * @param url              图片地址
     * @param placeholderResId 占位图
     * @param errorResId       错误图
     */
    void showImage(@NonNull Fragment fragment, @NonNull ImageView imageView, @NonNull String url,
                   @DrawableRes int placeholderResId, @DrawableRes int errorResId);

    /**
     * 显示网络图片
     *
     * @param activity         界面
     * @param imageView        显示控件
     * @param url              图片地址
     * @param placeholderResId 占位图
     * @param errorResId       错误图
     */
    void showImage(@NonNull Activity activity, @NonNull ImageView imageView, @NonNull String url,
                   @DrawableRes int placeholderResId, @DrawableRes int errorResId);

    /**
     * 显示网络图片
     *
     * @param context          上下文
     * @param imageView        显示控件
     * @param resId            图片资源ID
     * @param placeholderResId 占位图
     * @param errorResId       错误图
     */
    void showImage(@NonNull Context context, @NonNull ImageView imageView, @DrawableRes int resId,
                   @DrawableRes int placeholderResId, @DrawableRes int errorResId);

    /**
     * 显示网络图片
     *
     * @param fragment         界面
     * @param imageView        显示控件
     * @param resId            图片资源ID
     * @param placeholderResId 占位图
     * @param errorResId       错误图
     */
    void showImage(@NonNull Fragment fragment, @NonNull ImageView imageView, @DrawableRes int resId,
                   @DrawableRes int placeholderResId, @DrawableRes int errorResId);

    /**
     * 显示网络图片
     *
     * @param activity         界面
     * @param imageView        显示控件
     * @param resId            图片资源ID
     * @param placeholderResId 占位图
     * @param errorResId       错误图
     */
    void showImage(@NonNull Activity activity, @NonNull ImageView imageView, @DrawableRes int resId,
                   @DrawableRes int placeholderResId, @DrawableRes int errorResId);

    /**
     * 显示网络图片
     *
     * @param context          上下文
     * @param imageView        显示控件
     * @param file             本地文件
     * @param placeholderResId 占位图
     * @param errorResId       错误图
     */
    void showImage(@NonNull Context context, @NonNull ImageView imageView, @NonNull File file,
                   @DrawableRes int placeholderResId, @DrawableRes int errorResId);

    /**
     * 显示网络图片
     *
     * @param fragment         界面
     * @param imageView        显示控件
     * @param file             本地文件
     * @param placeholderResId 占位图
     * @param errorResId       错误图
     */
    void showImage(@NonNull Fragment fragment, @NonNull ImageView imageView, @NonNull File file,
                   @DrawableRes int placeholderResId, @DrawableRes int errorResId);

    /**
     * 显示网络图片
     *
     * @param activity         界面
     * @param imageView        显示控件
     * @param file             本地文件
     * @param placeholderResId 占位图
     * @param errorResId       错误图
     */
    void showImage(@NonNull Activity activity, @NonNull ImageView imageView, @NonNull File file,
                   @DrawableRes int placeholderResId, @DrawableRes int errorResId);

}
