package com.mingyuechunqiu.agile.feature.helper.ui.key.dispatcher

import com.mingyuechunqiu.agile.frame.ui.IAgilePage

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/15 1:53 下午
 *      Desc:       按键事件分发者界面
 *                  继承自IAgilePage
 *      Version:    1.0
 * </pre>
 */
interface IKeyEventDispatcherPage : IAgilePage{

    fun getKeyEventDispatcherHelper(): IKeyEventDispatcherHelper
}