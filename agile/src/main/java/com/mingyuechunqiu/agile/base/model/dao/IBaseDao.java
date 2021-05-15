package com.mingyuechunqiu.agile.base.model.dao;

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
 *     desc   : Dao层父接口
 *     version: 1.0
 * </pre>
 */
public interface IBaseDao<C extends DaoCallback> extends ICallExecutor {

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
