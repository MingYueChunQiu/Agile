package com.mingyuechunqiu.agile.base.model.dao.framework.callback.local;

import com.mingyuechunqiu.agile.base.framework.IBaseListener;
import com.mingyuechunqiu.agile.base.model.dao.framework.callback.DaoCallback;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020-01-18 21:26
 *      Desc:       所有本地Dao层回调父接口
 *                  继承自DaoCallback
 *      Version:    1.0
 * </pre>
 */
public interface DaoLocalCallback<I extends IBaseListener> extends DaoCallback<I> {
}
