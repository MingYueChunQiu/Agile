package com.mingyuechunqiu.agile.feature.statusview.bean

import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/16 8:10 下午
 *      Desc:       状态视图状态信息类
 *      Version:    1.0
 * </pre>
 */
data class StatusViewStateInfo(
    val mode: StatusViewConstants.StatusViewMode,
    val type: StatusViewConstants.StatusViewType,
    val option: StatusViewOption
)