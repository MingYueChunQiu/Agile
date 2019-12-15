package com.mingyuechunqiu.agile.base.model;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.base.framework.IBaseListener;
import com.mingyuechunqiu.agile.data.bean.BaseParamsInfo;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/16
 *     desc   : 所有业务模型的父接口
 *     version: 1.0
 * </pre>
 */
public interface IBaseModel<I extends IBaseListener> {

    /**
     * 当和监听器关联时调用
     *
     * @param listener 监听器
     */
    void attachListener(@NonNull I listener);

    void start();

    void resume();

    void pause();

    /**
     * 设置请求参数对象，进行请求
     *
     * @param info 请求参数对象
     */
    void requestWithParamsInfo(@NonNull BaseParamsInfo info);

    /**
     * 释放资源
     */
    void release();

}
