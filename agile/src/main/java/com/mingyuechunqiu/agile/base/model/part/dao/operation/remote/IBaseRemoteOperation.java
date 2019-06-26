package com.mingyuechunqiu.agile.base.model.part.dao.operation.remote;

import com.mingyuechunqiu.agile.base.model.part.dao.operation.IBaseDaoOperation;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 远程调用操作父接口
 *              继承自IDaoOperation
 *     version: 1.0
 * </pre>
 */
public interface IBaseRemoteOperation extends IBaseDaoOperation {

    /**
     * 是否远程操作已经被取消
     *
     * @return 如果已经被取消返回true，否则返回false
     */
    boolean isCanceled();

    /**
     * 取消远程操作
     */
    void cancel();
}
