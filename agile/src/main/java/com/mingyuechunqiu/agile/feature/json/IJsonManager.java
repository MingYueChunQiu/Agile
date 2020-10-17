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

    @Nullable
    String getJsonFromRawFile(@NonNull Context context, @RawRes int id);

    @Nullable
    String getJsonFromAssetFile(@NonNull Context context, @NonNull String fileName);
}
