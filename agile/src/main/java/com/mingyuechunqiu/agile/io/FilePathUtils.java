package com.mingyuechunqiu.agile.io;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/3/28
 *     desc   : 文件路径工具类
 *     version: 1.0
 * </pre>
 */
public class FilePathUtils {

    /**
     * 获取文件路径URI
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param fileType 文件类型名
     * @return 返回文件路径URI
     */
    @Nullable
    public static Uri getCacheFilePath(@NonNull Context context, @NonNull String fileName, @NonNull String fileType) {
        String filePath = getCacheFilePath(context, fileName, fileType, null);
        if (TextUtils.isEmpty(filePath)) {
            return null;
        } else {
            return Uri.fromFile(new File(filePath));
        }
    }

    /**
     * 获取文件路径
     *
     * @param context    上下文
     * @param fileName   文件名
     * @param fileType   文件类型名
     * @param moduleType 模块名称
     * @return 返回文件路径
     */
    @Nullable
    public static String getCacheFilePath(@NonNull Context context, @NonNull String fileName,
                                          @NonNull String fileType, String moduleType) {
        String moduleName = moduleType;
        if (TextUtils.isEmpty(moduleName)) {
            moduleName = "Main";
        }
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = context.getExternalCacheDir();
            if (file != null) {
                File parent = new File(file.getAbsolutePath() +
                        File.separator + moduleName + File.separator + fileType);
                boolean isCreated = true;
                if (!parent.exists()) {
                    isCreated = parent.mkdirs();
                }
                if (isCreated) {
                    return parent.getAbsolutePath() + File.separator + fileName;
                }
            }
        }
        File parent = new File(context.getCacheDir().getAbsolutePath() +
                File.separator + moduleName + File.separator + fileType);
        boolean isCreated = true;
        if (!parent.exists()) {
            isCreated = parent.mkdirs();
        }
        if (isCreated) {
            return parent.getAbsolutePath() + File.separator + fileName;
        } else {
            return null;
        }
    }
}
