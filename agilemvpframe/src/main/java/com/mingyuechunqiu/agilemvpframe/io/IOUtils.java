package com.mingyuechunqiu.agilemvpframe.io;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mingyuechunqiu.agilemvpframe.util.LogUtils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2019/1/10
 *     desc   : IO工具类
 *     version: 1.0
 * </pre>
 */
public class IOUtils {

    private static final String TAG = "IOUtils";

    /**
     * 检测是否是文件
     *
     * @param filePath 文件路径
     * @return 如果是文件返回true，否则返回false
     */
    public static boolean checkIsFile(@Nullable String filePath) {
        File file = getFileByPath(filePath);
        return file != null && file.isFile();
    }

    /**
     * 检查传入的文件名，判断文件是否存在，如果文件不存在，试图创建文件
     *
     * @param filePath 文件路径
     * @return 如果是文件或者创建文件成功返回true，否则返回false
     */
    public static boolean checkIsFileOrCreate(@Nullable String filePath) {
        File file = getFileByPath(filePath);
        if (file != null) {
            if (file.isFile()) {
                return true;
            } else if (!file.exists()) {
                try {
                    return file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtils.d(TAG, e.getMessage());
                }
            }
        }
        return false;
    }

    /**
     * 根据文件路径，检测是否是目录
     *
     * @param filePath 文件路径
     * @return 如果是目录返回true，否则返回false
     */
    public static boolean checkIsDirectory(@Nullable String filePath) {
        File file = getFileByPath(filePath);
        return file != null && file.isDirectory();
    }

    /**
     * 根据文件路径，检测是否是目录，如果文件不存在，试图创建目录
     *
     * @param filePath 文件路径
     * @return 如果是目录或者创建目录成功返回true，否则返回false
     */
    public static boolean checkIsDirectoryOrCreate(@Nullable String filePath) {
        File file = getFileByPath(filePath);
        if (file != null) {
            if (file.isDirectory()) {
                return true;
            } else if (!file.exists()) {
                return file.mkdir();
            }
        }
        return false;
    }

    /**
     * 关闭流的操作，供子类调用
     *
     * @param stream 需要关闭的流
     */
    public static void closeStream(@Nullable Closeable stream) {
        if (stream == null) {
            return;
        }
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据路径获取文件
     *
     * @param filePath 路径地址
     * @return 如果成功获取文件返回file，否则返回null
     */
    @Nullable
    private static File getFileByPath(@Nullable String filePath) {
        return TextUtils.isEmpty(filePath) ? null : new File(filePath);
    }
}
