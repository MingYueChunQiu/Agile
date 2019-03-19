package com.mingyuechunqiu.agilemvpframe.base.model;

import com.mingyuechunqiu.agilemvpframe.base.framework.IBaseListener;

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
    void attachListener(I listener);

    void start();

    void pause();

    void resume();

    /**
     * 释放资源
     */
    void release();

}
