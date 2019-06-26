package com.mingyuechunqiu.agile.base.framework;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/23
 *     desc   : 倒计时回调监听器
 *     version: 1.0
 * </pre>
 */
public interface ICountDownListener {

    /**
     * 当倒计时时回调
     *
     * @param count 当前的计数
     */
    void onCountDown(int count);

    /**
     * 当倒计时结束时调用
     */
    void onCountDownComplete();

}
