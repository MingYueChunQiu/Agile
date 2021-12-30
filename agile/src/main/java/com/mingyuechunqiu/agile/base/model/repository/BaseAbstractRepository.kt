package com.mingyuechunqiu.agile.base.model.repository

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/18/21 8:01 PM
 *      Desc:       Repository层抽象基类
 *                  实现IBaseRepository
 *      Version:    1.0
 * </pre>
 */
abstract class BaseAbstractRepository : IBaseRepository {

    override fun releaseOnDetach() {
        preRelease()
        release()
        postRelease()
    }

    protected open fun preRelease() {}

    protected open fun postRelease() {}

    /**
     * 释放资源
     */
    protected abstract fun release()
}