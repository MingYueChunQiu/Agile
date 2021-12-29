package com.mingyuechunqiu.agile.base.model.repository

import com.mingyuechunqiu.agile.base.model.framework.callback.DaoCallback

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
abstract class BaseAbstractRepository<C : DaoCallback> :
    IBaseRepository<C> {

    private var mModelCallback: C? = null

    override fun attachModelCallback(callback: C) {
        mModelCallback = callback
        onAttachModelCallback(callback)
    }

    override fun releaseOnDetach() {
        preRelease()
        release()
        postRelease()
    }

    override fun getModelCallback(): C? {
        return mModelCallback
    }

    /**
     * 当和Model层回调关联时调用
     *
     * @param callback 回调对象
     */
    protected open fun onAttachModelCallback(callback: C) {}

    protected open fun preRelease() {}

    protected open fun postRelease() {}

    /**
     * 释放资源
     */
    protected abstract fun release()
}