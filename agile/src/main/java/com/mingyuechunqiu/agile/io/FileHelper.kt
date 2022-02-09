package com.mingyuechunqiu.agile.io

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider
import com.mingyuechunqiu.agile.util.ThreadPoolUtils
import java.io.*

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

    private const val TAG = "FileHelper"

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

    /**
     * 检测是否是文件
     *
     * @param filePath 文件路径
     * @return 如果是文件返回true，否则返回false
     */
    fun checkIsFile(filePath: String?): Boolean {
        val file = getFileByPath(filePath)
        return file?.isFile == true
    }

    /**
     * 检查传入的文件名，判断文件是否存在，如果文件不存在，试图创建文件
     *
     * @param filePath 文件路径
     * @return 如果是文件或者创建文件成功返回true，否则返回false
     */
    fun checkIsFileOrCreate(filePath: String?): Boolean {
        val file = getFileByPath(filePath)
        if (file != null) {
            if (file.isFile) {
                return true
            } else if (!file.exists()) {
                try {
                    return file.createNewFile()
                } catch (e: IOException) {
                    LogManagerProvider.d(TAG, "checkIsFileOrCreate: ${e.message}")
                }
            }
        }
        return false
    }

    /**
     * 根据文件路径，检测是否是目录
     *
     * @param filePath 文件路径
     * @return 如果是目录返回true，否则返回false
     */
    fun checkIsDirectory(filePath: String?): Boolean {
        val file = getFileByPath(filePath)
        return file?.isDirectory == true
    }

    /**
     * 根据文件路径，检测是否是目录，如果文件不存在，试图创建目录
     *
     * @param filePath 文件路径
     * @return 如果是目录或者创建目录成功返回true，否则返回false
     */
    fun checkIsDirectoryOrCreate(filePath: String?): Boolean {
        val file = getFileByPath(filePath)
        if (file != null) {
            if (file.isDirectory) {
                return true
            } else if (!file.exists()) {
                return file.mkdirs()
            }
        }
        return false
    }

    /**
     * 向指定文件写入字符串
     *
     * @param title    标题
     * @param msg      信息
     * @param filePath 文件路径
     */
    fun writeStringToLocalFile(title: String, msg: String, filePath: String) {
        if (!checkIsFileOrCreate(filePath)) {
            return
        }
        ThreadPoolUtils.executeAction {
            try {
                BufferedWriter(OutputStreamWriter(FileOutputStream(File(filePath)))).use {
                    it.write(title)
                    it.newLine()
                    it.write(msg)
                    it.flush()
                }
            } catch (e: Throwable) {
                LogManagerProvider.e(TAG, "writeStringToLocalFile: ${e.message}")
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return 如果删除成功返回true， 否则返回false
     */
    fun deleteFile(filePath: String?): Boolean {
        if (filePath.isNullOrEmpty()) {
            return false
        }
        val file = File(filePath)
        return if (file.exists()) {
            file.delete()
        } else false
    }

    /**
     * 删除目录及内部所有文件
     *
     * @param filePath 文件路径
     * @return 如果删除成功返回true，否则返回false
     */
    fun deleteDirectory(filePath: String?): Boolean {
        return if (filePath.isNullOrEmpty()) false else deleteDirectory(File(filePath))
    }

    /**
     * 删除目录及内部所有文件
     *
     * @param file 文件参数
     * @return 如果删除成功返回true，否则返回false
     */
    fun deleteDirectory(file: File?): Boolean {
        if (file == null || !file.exists()) {
            return false
        }
        if (file.isFile) {
            return file.delete()
        }
        if (!file.isDirectory) {
            return false
        }
        var flag = true
        file.listFiles()?.forEach { f ->
            flag = flag and deleteDirectory(f)
        }
        return flag and file.delete()
    }

    /**
     * 根据路径获取文件
     *
     * @param filePath 路径地址
     * @return 如果成功获取文件返回file，否则返回null
     */
    private fun getFileByPath(filePath: String?): File? {
        return if (filePath.isNullOrEmpty()) null else File(filePath)
    }
}