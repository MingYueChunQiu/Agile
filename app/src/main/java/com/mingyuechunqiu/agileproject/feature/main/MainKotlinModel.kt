package com.mingyuechunqiu.agileproject.feature.main

import com.mingyuechunqiu.agile.data.bean.BaseParamsInfo

/**
 * <pre>
 * Project:    Agile
 *
 * Author:     xiyujie
 * Github:     https://github.com/MingYueChunQiu
 * Email:      xiyujieit@163.com
 * Time:       2020-01-19 21:17
 * Desc:
 * Version:    1.0
</pre> *
 */
internal class MainKotlinModel(listener: MainContract.Listener?) : MainContract.Model<MainContract.Listener?>(listener) {

    private var mDao: MainContract.Dao<*>? = null

    override fun doRequest(info: BaseParamsInfo) {
        if (mDao == null) {
            mDao = MainDao()
            addDao(mDao)
        }
    }

    override fun release() {}
}