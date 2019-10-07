package com.mingyuechunqiu.agile.feature.json;

import androidx.annotation.NonNull;

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

    JsonManager(JsonHelperable helper) {
        mHelper = helper;
        if (mHelper == null) {
            mHelper = new GsonHelper();
        }
    }

    @Override
    public String getJsonString(Object o) {
        return mHelper.getJsonString(o);
    }

    @Override
    public <T> T getJsonObject(String json, @NonNull Class<T> c) {
        return mHelper.getJsonObject(json, c);
    }

    @Override
    public void writeJsonStringToFile(String json, String filePath) {
        mHelper.writeJsonStringToFile(json, filePath);
    }

    @Override
    public <T> T readJsonFromFile(String filePath, @NonNull Class<T> c) {
        return mHelper.readJsonFromFile(filePath, c);
    }

    @Override
    public <T> List<T> readListFromFile(String fileName, @NonNull Class<T> c) {
        return mHelper.readListFromFile(fileName, c);
    }

    @Override
    public <T> List<T> getListFromJson(String json, @NonNull Class<T> c) {
        return mHelper.getListFromJson(json, c);
    }

    @Override
    public <T> List<T> getListFromJson(@NonNull JsonArray jsonArray, @NonNull Class<T> c) {
        return mHelper.getListFromJson(jsonArray, c);
    }

    @Override
    public <T> Map<String, Object> getMapFromJson(String json, @NonNull Class<T> c) {
        return mHelper.getMapFromJson(json, c);
    }

    /**
     * 设置Json帮助类管理器新的具体操作实例
     *
     * @param helper Json处理帮助类实例
     */
    public void setJsonHelper(JsonHelperable helper) {
        if (helper == null) {
            return;
        }
        mHelper = helper;
    }
}
