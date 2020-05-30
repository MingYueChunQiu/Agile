package com.mingyuechunqiu.agile.base.model.dao.operation.local;

import com.mingyuechunqiu.agile.base.model.dao.operation.IBaseDaoOperation;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 本地操作父接口
 *              继承自IBaseDaoOperation
 *     version: 1.0
 * </pre>
 */
public interface IBaseLocalDaoOperation<T> extends IBaseDaoOperation<T> {

    /**
     * 是否操作已经被取消
     *
     * @return 如果已经被取消返回true，否则返回false
     */
    boolean isCanceled();

    /**
     * 取消操作
     */
    void cancel();
}
