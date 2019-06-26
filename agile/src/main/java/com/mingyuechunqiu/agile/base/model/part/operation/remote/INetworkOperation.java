package com.mingyuechunqiu.agile.base.model.part.operation.remote;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 网络调用操作接口
 *     version: 1.0
 * </pre>
 */
public interface INetworkOperation {

    /**
     * 是否网络操作已经被取消
     *
     * @return 如果已经被取消返回true，否则返回false
     */
    boolean isCanceled();

    /**
     * 取消网络操作
     */
    void cancel();
}
