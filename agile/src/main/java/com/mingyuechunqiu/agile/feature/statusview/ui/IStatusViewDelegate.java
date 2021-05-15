package com.mingyuechunqiu.agile.feature.statusview.ui;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-11-24 19:54
 *      Desc:       状态视图功能代理接口
 *      Version:    1.0
 * </pre>
 */
public interface IStatusViewDelegate {

    void applyOption(@NonNull View view);

    void release();
}
