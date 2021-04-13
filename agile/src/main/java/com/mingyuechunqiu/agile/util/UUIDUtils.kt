package com.mingyuechunqiu.agile.util

import java.util.*

/**
 * <pre>
 *      Project:    FineNote
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020/5/29 9:42 PM
 *      Desc:       随机数工具类
 *      Version:    1.0
 * </pre>
 */
object UUIDUtils {

    fun getUUID(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }
}