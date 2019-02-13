package com.mingyuechunqiu.agilemvpframe.base.framework;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/17
 *     desc   : 所有回调信息的父接口
 *     version: 1.0
 * </pre>
 */
public interface IBaseListener {

    /**
     * 错误时回调
     *
     * @param msg 错误信息
     */
    void onFailure(String msg);

    /**
     * 错误时回调
     *
     * @param stringResourceId 错误信息资源ID
     */
    void onFailure(int stringResourceId);

}
