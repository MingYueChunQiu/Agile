package com.mingyuechunqiu.agile.base.model.dao;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.base.model.dao.framework.callback.DaoCallback;

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
public interface IBaseDao<C extends DaoCallback<?>> {

    void releaseOnDetach();

    /**
     * 关联Model层Dao对象回调
     *
     * @param callback 回调对象
     */
    void attachDaoCallback(@NonNull C callback);
}
