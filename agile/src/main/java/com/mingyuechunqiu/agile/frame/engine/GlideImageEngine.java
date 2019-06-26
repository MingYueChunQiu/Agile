package com.mingyuechunqiu.agile.frame.engine;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mingyuechunqiu.agile.framework.engine.IImageEngine;
import com.mingyuechunqiu.agile.util.ScreenUtils;

import java.io.File;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/28
 *     desc   : Glide图片引擎
 *              实现IImageEngine
 *     version: 1.0
 * </pre>
 */
public class GlideImageEngine implements IImageEngine {

    @Override
    public void showImage(Context context, ImageView imageView, String url, int placeholderResId, int errorResId) {
        if (context == null || imageView == null || TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(context).load(url).apply(new RequestOptions()
                .placeholder(placeholderResId).error(errorResId))
                .into(imageView);
    }

    @Override
    public void showImage(Fragment fragment, ImageView imageView, String url, int placeholderResId, int errorResId) {
        if (fragment == null || imageView == null || TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(fragment).load(url).apply(new RequestOptions()
                .placeholder(placeholderResId).error(errorResId))
                .into(imageView);
    }

    @Override
    public void showImage(Context context, ImageView imageView, String url, Drawable placeholder, Drawable errorRes) {
        if (context == null || imageView == null || TextUtils.isEmpty(url) || placeholder == null ||
                errorRes == null) {
            return;
        }
        Glide.with(context).load(url).apply(new RequestOptions()
                .placeholder(placeholder).error(errorRes))
                .into(imageView);
    }

    @Override
    public void showImage(Fragment fragment, ImageView imageView, String url, Drawable placeholder, Drawable errorRes) {
        if (fragment == null || imageView == null || TextUtils.isEmpty(url) || placeholder == null ||
                errorRes == null) {
            return;
        }
        Glide.with(fragment).load(url).apply(new RequestOptions()
                .placeholder(placeholder).error(errorRes))
                .into(imageView);
    }

    @Override
    public void showImage(Context context, ImageView imageView, File file, int placeholderResId, int errorResId) {
        if (context == null || imageView == null || file == null || !file.exists()) {
            return;
        }
        Glide.with(context).load(file).apply(new RequestOptions()
                .placeholder(placeholderResId).error(errorResId))
                .into(imageView);
    }

    @Override
    public void showImage(Fragment fragment, ImageView imageView, File file, int placeholderResId, int errorResId) {
        if (fragment == null || imageView == null || file == null || !file.exists()) {
            return;
        }
        Glide.with(fragment).load(file).apply(new RequestOptions()
                .placeholder(placeholderResId).error(errorResId))
                .into(imageView);
    }

    @Override
    public void showImage(Context context, ImageView imageView, File file, Drawable placeholder, Drawable errorRes) {
        if (context == null || imageView == null || file == null || !file.exists() ||
                placeholder == null || errorRes == null) {
            return;
        }
        Glide.with(context).load(file).apply(new RequestOptions()
                .placeholder(placeholder).error(errorRes))
                .into(imageView);
    }

    @Override
    public void showImage(Fragment fragment, ImageView imageView, File file, Drawable placeholder, Drawable errorRes) {
        if (fragment == null || imageView == null || file == null || !file.exists() ||
                placeholder == null || errorRes == null) {
            return;
        }
        Glide.with(fragment).load(file).apply(new RequestOptions()
                .placeholder(placeholder).error(errorRes))
                .into(imageView);
    }

    @Override
    public void showImage(Context context, ImageView imageView, Drawable drawable, int placeholderResId, int errorResId) {
        if (context == null || imageView == null || drawable == null) {
            return;
        }
        Glide.with(context).load(drawable).apply(new RequestOptions()
                .placeholder(placeholderResId).error(errorResId))
                .into(imageView);
    }

    @Override
    public void showImage(Fragment fragment, ImageView imageView, Drawable drawable, int placeholderResId, int errorResId) {
        if (fragment == null || imageView == null || drawable == null) {
            return;
        }
        Glide.with(fragment).load(drawable).apply(new RequestOptions()
                .placeholder(placeholderResId).error(errorResId))
                .into(imageView);
    }

    @Override
    public void showImage(Context context, ImageView imageView, Drawable drawable, Drawable placeholder, Drawable errorRes) {
        if (context == null || imageView == null || drawable == null || placeholder == null || errorRes == null) {
            return;
        }
        Glide.with(context).load(drawable).apply(new RequestOptions()
                .placeholder(placeholder).error(errorRes))
                .into(imageView);
    }

    @Override
    public void showImage(Fragment fragment, ImageView imageView, Drawable drawable, Drawable placeholder, Drawable errorRes) {
        if (fragment == null || imageView == null || drawable == null || placeholder == null || errorRes == null) {
            return;
        }
        Glide.with(fragment).load(drawable).apply(new RequestOptions()
                .placeholder(placeholder).error(errorRes))
                .into(imageView);
    }

    @Override
    public void showRoundCornersImage(Context context, ImageView imageView, Object drawableModel, int radiusDp) {
        if (context == null || imageView == null || drawableModel == null) {
            return;
        }
        Glide.with(context).load(drawableModel)
                .apply(new RequestOptions()
                        .centerCrop()//这句必须有，否则图片会偏移
                        .transform(new RoundedCorners((int) ScreenUtils.getPxFromDp(
                                context.getResources(), radiusDp))))
                .into(imageView);
    }

    @Override
    public void showRoundCornersImage(Fragment fragment, ImageView imageView, Object drawableModel, int radiusDp) {
        if (fragment == null || imageView == null || drawableModel == null) {
            return;
        }
        Glide.with(fragment).load(drawableModel)
                .apply(new RequestOptions()
                        .centerCrop()//这句必须有，否则图片会偏移
                        .transform(new RoundedCorners((int) ScreenUtils.getPxFromDp(
                                fragment.getResources(), radiusDp))))
                .into(imageView);
    }
}
