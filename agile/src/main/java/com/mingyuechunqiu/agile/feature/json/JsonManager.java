package com.mingyuechunqiu.agile.feature.json;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonArray;
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
final class JsonManager implements IJsonManager {

    private IJsonHelper mHelper;

    JsonManager(@Nullable IJsonHelper helper) {
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

    @NonNull
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
    public void setJsonHelper(@NonNull IJsonHelper helper) {
        mHelper = helper;
    }

    @Nullable
    @Override
    public String getJsonFromRawFile(@NonNull Context context, int id) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().openRawResource(id));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = bufReader.readLine()) != null)
                result.append(line);
            return result.toString();
        } catch (IOException e) {
            LogManagerProvider.e("getJsonFromRawFile", e.getMessage());
        }
        return null;
    }

    @Nullable
    @Override
    public String getJsonFromAssetFile(@NonNull Context context, @NonNull String fileName) {
        AssetManager manager = context.getAssets();
        try {
            InputStreamReader inputReader = new InputStreamReader(manager.open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = bufReader.readLine()) != null)
                result.append(line);
            return result.toString();
        } catch (IOException e) {
            LogManagerProvider.e("getJsonFromAssetFile", e.getMessage());
        }
        return null;
    }

}
