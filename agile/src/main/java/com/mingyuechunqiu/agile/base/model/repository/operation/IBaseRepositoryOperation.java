package com.mingyuechunqiu.agile.base.model.repository.operation;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : Repository层操作的父接口
 *     version: 1.0
 * </pre>
 */
public interface IBaseRepositoryOperation<T> {

    /**
     * 释放资源
     */
    void releaseOnDetach();

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

    /**
     * 获取实际工作对象
     *
     * @return 返回用户操作的实际类型对象
     */
    @NonNull
    T getWork();
}
