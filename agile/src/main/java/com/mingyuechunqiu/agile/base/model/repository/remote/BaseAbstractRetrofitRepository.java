package com.mingyuechunqiu.agile.base.model.repository.remote;

import com.mingyuechunqiu.agile.base.model.framework.callback.remote.DaoRetrofitCallback;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : Retrofit网络Repository抽象基类
 *              继承自BaseAbstractNetworkRepository
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractRetrofitRepository<C extends DaoRetrofitCallback> extends BaseAbstractNetworkRepository<C> {
}
