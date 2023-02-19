package com.mingyuechunqiu.agile.frame.ui.fragment

import com.mingyuechunqiu.agile.frame.ui.IAgilePage

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2023/2/19 14:04
 *      Desc:       所有Fragment视图界面接口
 *                  继承自IAgilePage
 *      Version:    1.0
 * </pre>
 */
interface IAgileFragmentViewPage : IAgilePage {

    /**
     * 当前是否是空视图
     *
     * @return 如果所属fragment没创建视图返回true，否则返回false
     */
    fun isEmptyView(): Boolean
}