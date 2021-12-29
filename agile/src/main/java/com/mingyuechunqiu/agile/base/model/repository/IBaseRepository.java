package com.mingyuechunqiu.agile.base.model.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.bridge.call.ICallExecutor;
import com.mingyuechunqiu.agile.base.model.framework.callback.DaoCallback;

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
public interface IBaseRepository<C extends DaoCallback> extends ICallExecutor {

    /**
     * 关联Model层回调
     *
     * @param callback Model层回调
     */
    void attachModelCallback(@NonNull C callback);

    void releaseOnDetach();

    @Nullable
    C getModelCallback();
}
