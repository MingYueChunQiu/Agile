package com.mingyuechunqiu.agile.feature.json;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonArray;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2019/1/10
 *     desc   : Json处理帮助类管理器
 *              实现JsonHelperManagerable
 *     version: 1.0
 * </pre>
 */
class JsonManager implements JsonManagerable {

    private JsonHelperable mHelper;

    JsonManager(@Nullable JsonHelperable helper) {
        mHelper = helper;
        if (mHelper == null) {
            mHelper = new GsonHelper();
        }
    }

    @Nullable
    @Override
    public String getJsonString(@Nullable Object o) {
        return mHelper.getJsonString(o);
    }

    @Nullable
    @Override
    public <T> T getJsonObject(@Nullable String json, @NonNull Class<T> c) {
        return mHelper.getJsonObject(json, c);
    }

    @Override
    public void writeJsonStringToFile(@Nullable String json, @Nullable String filePath) {
        mHelper.writeJsonStringToFile(json, filePath);
    }

    @Nullable
    @Override
    public <T> T readJsonFromFile(@Nullable String filePath, @NonNull Class<T> c) {
        return mHelper.readJsonFromFile(filePath, c);
    }

    @Nullable
    @Override
    public <T> List<T> readListFromFile(@Nullable String fileName, @NonNull Class<T> c) {
        return mHelper.readListFromFile(fileName, c);
    }

    @Nullable
    @Override
    public <T> List<T> getListFromJson(@Nullable String json, @NonNull Class<T> c) {
        return mHelper.getListFromJson(json, c);
    }

    @Nullable
    @Override
    public <T> List<T> getListFromJson(@NonNull JsonArray jsonArray, @NonNull Class<T> c) {
        return mHelper.getListFromJson(jsonArray, c);
    }

    @Nullable
    @Override
    public <T> Map<String, Object> getMapFromJson(@Nullable String json, @NonNull Class<T> c) {
        return mHelper.getMapFromJson(json, c);
    }

    /**
     * 设置Json帮助类管理器新的具体操作实例
     *
     * @param helper Json处理帮助类实例
     */
    @Override
    public void setJsonHelper(@NonNull JsonHelperable helper) {
        mHelper = helper;
    }

}
