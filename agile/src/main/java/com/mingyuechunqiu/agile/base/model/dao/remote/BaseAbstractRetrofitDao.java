package com.mingyuechunqiu.agile.base.model.dao.remote;

import com.mingyuechunqiu.agile.base.model.framework.callback.remote.DaoRetrofitCallback;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : Retrofit网络Dao抽象基类
 *              继承自BaseAbstractNetworkDao
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractRetrofitDao<C extends DaoRetrofitCallback> extends BaseAbstractNetworkDao<C> {
}
