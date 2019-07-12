package com.mingyuechunqiu.agile.base.model.part.dao;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.base.framework.IBaseListener;

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
public interface IBaseDao<C extends IBaseDao.ModelDaoCallback> {

    void release();

    /**
     * 关联Model层Dao对象回调
     *
     * @param callback 回调对象
     */
    void attachModelDaoCallback(@NonNull C callback);

    /**
     * Model层Dao对象回调
     */
    interface ModelDaoCallback<I extends IBaseListener> {

        /**
         * 执行响应结果
         *
         * @param operation 执行操作
         */
        void onExecuteResult(@NonNull ResultOperation<I> operation);

        /**
         * 结果处理操作
         *
         * @param <I> 操作回调接口类型
         */
        interface ResultOperation<I extends IBaseListener> {

            /**
             * 执行相关回调操作
             *
             * @param listener 回调
             */
            void operate(I listener);
        }
    }
}
