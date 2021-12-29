package com.mingyuechunqiu.agile.base.model.repository.remote;

import com.mingyuechunqiu.agile.base.model.framework.callback.remote.DaoNetworkCallback;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 网络Repository层抽象基类
 *              继承自BaseAbstractRemoteRepository
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractNetworkRepository<C extends DaoNetworkCallback> extends BaseAbstractRemoteRepository<C> {
}
