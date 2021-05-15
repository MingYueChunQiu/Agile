package com.mingyuechunqiu.agile.base.view;

import android.content.Context;

import androidx.annotation.Nullable;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/06/14
 *     desc   : 所有view视图接口的父接口
 *     version: 1.0
 * </pre>
 */
public interface IBaseView {

    /**
     * 获取当前界面的上下文
     *
     * @return 返回上下文
     */
    @Nullable
    Context getCurrentContext();
}
