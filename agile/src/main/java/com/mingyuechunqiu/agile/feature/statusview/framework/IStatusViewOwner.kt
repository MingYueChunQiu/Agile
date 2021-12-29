package com.mingyuechunqiu.agile.feature.statusview.framework

import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/19/21 10:53 PM
 *      Desc:       状态视图拥有者接口
 *                  继承自IStatusViewable
 *      Version:    1.0
 * </pre>
 */
interface IStatusViewOwner : IStatusViewProcessor {

    fun getStatusViewManager(): IStatusViewManager
}