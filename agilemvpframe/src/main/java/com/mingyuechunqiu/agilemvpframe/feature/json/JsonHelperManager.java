package com.mingyuechunqiu.agilemvpframe.feature.json;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2019/1/10
 *     desc   : Json帮助类管理器
 *              实现JsonHelperable
 *     version: 1.0
 * </pre>
 */
public class JsonHelperManager implements JsonHelperable {

    private JsonHelperable mHelper;

    private JsonHelperManager(JsonHelperable helper) {
        mHelper = helper;
        if (mHelper == null) {
            mHelper = new GsonHelper();
        }
    }

    /**
     * 获取Json帮助类管理器实例（默认使用Gson处理Json）
     *
     * @return 返回Json帮助类管理器实例
     */
    @NonNull
    public static JsonHelperManager getInstance() {
        return getInstance(new GsonHelper());
    }

    /**
     * 获取Json帮助类管理器实例
     *
     * @param helper Json处理帮助类
     * @return 返回Json帮助类管理器实例
     */
    @NonNull
    public static JsonHelperManager getInstance(JsonHelperable helper) {
        return new JsonHelperManager(helper);
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
