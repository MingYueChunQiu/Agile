package com.mingyuechunqiu.agile.feature.statusview.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-11-24 19:54
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
interface IStatusViewDelegate {

    @NonNull
    StatusViewOption getStatusViewOption();

    void setModeType(@NonNull StatusViewConstants.ModeType type);

    @NonNull
    StatusViewConstants.ModeType getModeType();

    void setStatusType(@NonNull StatusViewConstants.StatusType type);

    @NonNull
    StatusViewConstants.StatusType getStatusType();

    void applyOption(@Nullable View vContainer, @Nullable View vProgress, @Nullable ImageView ivIcon, @Nullable TextView tvContent, @Nullable TextView tvReload);

    void release();
}
