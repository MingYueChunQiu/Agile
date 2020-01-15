package com.mingyuechunqiu.agile.base.model.dao.framework.result;

import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.framework.IBaseListener;

/**
 * <pre>
 *      Project:    Agile
 *      Github :    https://github.com/MingYueChunQiu
 *      Author:     XiYuJie
 *      Email:      xiyujie@jinying.com
 *      Time:       2020/1/15 13:46
 *      Desc:       Dao层结果处理器
 *      Version:    1.0
 * </pre>
 */
public interface DaoResultHandler<I extends IBaseListener> {

    /**
     * 处理Dao层结果
     *
     * @param listener 回调接口
     */
    void handleDaoResult(@Nullable I listener);
}
