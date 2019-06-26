package com.mingyuechunqiu.agile.base.model.part.dao.remote;

import com.mingyuechunqiu.agile.base.framework.IBaseListener;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 网络Dao层抽象基类
 *              继承自BaseAbstractRemoteDao
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractNetworkDao<I extends IBaseListener> extends BaseAbstractRemoteDao<I> {

    public BaseAbstractNetworkDao(I listener) {
        super(listener);
    }
}
