package com.mingyuechunqiu.agile.io

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       1/16/21 1:36 PM
 *      Desc:       文件辅助类（单例）
 *      Version:    1.0
 * </pre>
 */
object FileHelper {

    /**
     * 根据文件获取其Uri
     * @param context 当前调用所在上下文
     * @param file 需要转换的文件
     * @param blockWithNewApi 高版本Api调用时方法
     */
    fun getUriFromFile(context: Context, file: File, blockWithNewApi: (() -> Unit)? = null): Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            blockWithNewApi?.invoke()
            FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
        } else {
            Uri.fromFile(file)
        }
    }
}