package com.mingyuechunqiu.agile.base.model.dao.framework.callback.remote;

import com.mingyuechunqiu.agile.base.framework.IBaseListener;
import com.mingyuechunqiu.agile.base.model.dao.framework.callback.DaoCallback;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020-01-18 21:27
 *      Desc:       所有远程Dao层回调父接口
 *                  继承自DaoCallback
 *      Version:    1.0
 * </pre>
 */
public interface DaoRemoteCallback<I extends IBaseListener> extends DaoCallback<I> {
}
