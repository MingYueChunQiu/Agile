package com.mingyuechunqiu.agile.base.model.dao.framework.callback;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.base.framework.IBaseListener;
import com.mingyuechunqiu.agile.base.model.dao.framework.result.DaoResultHandler;

/**
 * <pre>
 *      Project:    Agile
 *      Github :    https://github.com/MingYueChunQiu
 *      Author:     XiYuJie
 *      Email:      xiyujie@jinying.com
 *      Time:       2020/1/15 13:49
 *      Desc:       所有Dao层回调父接口
 *      Version:    1.0
 * </pre>
 */
public interface DaoCallback<I extends IBaseListener> {

    /**
     * 执行Dao层结果
     *
     * @param handler Dao层结果处理器
     */
    void onExecuteDaoResult(@NonNull DaoResultHandler<I> handler);
}
