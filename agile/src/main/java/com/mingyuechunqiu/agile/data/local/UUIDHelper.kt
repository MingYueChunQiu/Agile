package com.mingyuechunqiu.agile.data.local

import java.util.UUID

/**
 * <pre>
 *      Project:    FineNote
 *
 *      Author:     xiyujie
 *      Github:     <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *      Email:      xiyujieit@163.com
 *      Time:       2020/5/29 9:42 PM
 *      Desc:       随机数工具类
 *      Version:    1.0
 * </pre>
 */
object UUIDHelper {

    fun getUUID(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }
}