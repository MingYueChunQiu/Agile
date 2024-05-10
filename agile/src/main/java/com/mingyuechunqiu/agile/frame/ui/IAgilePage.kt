package com.mingyuechunqiu.agile.frame.ui

import androidx.lifecycle.LifecycleOwner

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *      Email:      xiyujieit@163.com
 *      Time:       4/8/21 11:09 PM
 *      Desc:       所有框架界面父接口
 *                  继承自LifecycleOwner
 *      Version:    1.0
 * </pre>
 */
interface IAgilePage : LifecycleOwner {

    /**
     * 获取界面标签，确保唯一
     */
    fun getPageTag(): String
}