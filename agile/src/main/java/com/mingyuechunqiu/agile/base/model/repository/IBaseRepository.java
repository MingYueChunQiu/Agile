package com.mingyuechunqiu.agile.base.model.repository;

import com.mingyuechunqiu.agile.base.bridge.call.ICallExecutor;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/3/14
 *     desc   : 所有Repository层父接口
 *              继承自ICallExecutor
 *     version: 1.0
 * </pre>
 */
public interface IBaseRepository extends ICallExecutor {

    void releaseOnDetach();
}
