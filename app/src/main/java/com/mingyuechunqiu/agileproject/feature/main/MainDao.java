package com.mingyuechunqiu.agileproject.feature.main;

import com.mingyuechunqiu.agile.base.model.dao.framework.callback.remote.DaoRetrofitCallback;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020-01-19 21:19
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
public class MainDao extends MainContract.Dao<DaoRetrofitCallback<MainContract.Listener>> {
    @Override
    protected void release() {

    }
}
