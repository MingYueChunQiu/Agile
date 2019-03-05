package com.mingyuechunqiu.agilemvpframe.base.model;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/16
 *     desc   : 所有业务模型的父接口
 *     version: 1.0
 * </pre>
 */
public interface IBaseModel {

    void start();

    void pause();

    void resume();

    /**
     * 释放资源
     */
    void release();

}
