package com.mingyuechunqiu.agile.frame.ui

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2023/3/24 23:11
 *      Desc:       Agile库页面通用API
 *      Version:    1.0
 * </pre>
 */
/**
 * 生成页面标签
 *
 * @param page 页面
 * @return 返回标签字符串
 */
fun createPageTag(page: IAgilePage): String {
    return page::class.java.name + "@" + Integer.toHexString(page.hashCode())
}