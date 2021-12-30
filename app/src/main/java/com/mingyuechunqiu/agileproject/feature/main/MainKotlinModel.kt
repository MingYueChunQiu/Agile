package com.mingyuechunqiu.agileproject.feature.main

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
internal class MainKotlinModel() : MainContract.Model() {

    private val mDao: MainContract.Repository by lazy {
        val repository = MainRepository()
        addRepository(repository)
        repository
    }

    override fun release() {}
}