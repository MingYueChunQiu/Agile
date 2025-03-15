package com.mingyuechunqiu.agile.feature.helper

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable

/**
 * <pre>
 *      Project:    FileRecovery
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2024/10/2 13:17
 *      Desc:       参数获取兼容辅助类
 *      Version:    1.0
 * </pre>
 */
inline fun <reified T : Parcelable> Bundle.getParcelableData(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) getParcelable(
        key, T::class.java
    ) else @Suppress("DEPRECATION") getParcelable(key)
}

inline fun <reified T : Parcelable> Bundle.getParcelableArrayListData(key: String): ArrayList<T>? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) getParcelableArrayList(
        key, T::class.java
    ) else @Suppress("DEPRECATION") getParcelableArrayList(key)
}

inline fun <reified T : Parcelable> Intent.getParcelableData(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) getParcelableExtra(
        key, T::class.java
    ) else @Suppress("DEPRECATION") getParcelableExtra(key)
}

inline fun <reified T : Parcelable> Intent.getParcelableArrayListData(key: String): ArrayList<T>? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) getParcelableArrayListExtra(
        key, T::class.java
    ) else @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
}