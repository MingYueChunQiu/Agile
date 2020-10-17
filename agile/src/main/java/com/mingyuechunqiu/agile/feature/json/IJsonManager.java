package com.mingyuechunqiu.agile.feature.json;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/2/21
 *     desc   : Json处理帮助类管理器接口
 *              继承自JsonHelperable
 *     version: 1.0
 * </pre>
 */
public interface IJsonManager extends IJsonHelper {

    void setJsonHelper(@NonNull IJsonHelper helper);

    /**
     * 从原始资源文件获取Json
     *
     * @param context 上下文
     * @param id      资源ID
     * @return 如果获取成功，返回json字符串，否则返回null
     */
    @Nullable
    String getJsonFromRawFile(@NonNull Context context, @RawRes int id);

    /**
     * 从资产文件获取Json
     *
     * @param context  上下文
     * @param fileName 文件名称
     * @return 如果获取成功，返回json字符串，否则返回null
     */
    @Nullable
    String getJsonFromAssetFile(@NonNull Context context, @NonNull String fileName);
}
