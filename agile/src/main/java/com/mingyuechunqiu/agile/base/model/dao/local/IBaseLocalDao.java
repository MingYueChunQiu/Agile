package com.mingyuechunqiu.agile.base.model.dao.local;

import com.mingyuechunqiu.agile.base.model.dao.IBaseDao;
import com.mingyuechunqiu.agile.base.model.dao.framework.callback.DaoCallback;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/3/14
 *     desc   : 本地Dao层接口
 *              继承自IBaseDao
 *     version: 1.0
 * </pre>
 */
public interface IBaseLocalDao<C extends DaoCallback<?>> extends IBaseDao<C> {
}
