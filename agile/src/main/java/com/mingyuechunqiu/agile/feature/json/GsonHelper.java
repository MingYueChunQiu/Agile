package com.mingyuechunqiu.agile.feature.json;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;
import com.mingyuechunqiu.agile.io.FileHelper;
import com.mingyuechunqiu.agile.io.IOHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2019/1/10
 *     desc   : Json处理帮助类，使用Gson处理
 *              实现JsonHelperable
 *     version: 1.0
 * </pre>
 */
final class GsonHelper implements IJsonHelper {

    private static final String TAG = "GsonHelper";
    private volatile Gson mGson;

    @Nullable
    @Override
    public String getJsonString(@Nullable Object o) {
        if (o == null) {
            return null;
        }
        checkOrCreateGson();
        return mGson.toJson(o);
    }

    @Nullable
    @Override
    public <T> T getJsonObject(@Nullable String json, @NonNull Class<T> c) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        checkOrCreateGson();
        return mGson.fromJson(json, c);
    }

    @Override
    public void writeJsonStringToFile(@Nullable String json, @Nullable String filePath) {
        if (TextUtils.isEmpty(json) || TextUtils.isEmpty(filePath) || !FileHelper.INSTANCE.checkIsFileOrCreate(filePath)) {
            return;
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOHelper.closeStreams(writer);
        }
    }

    @Nullable
    @Override
    public <T> T readJsonFromFile(@Nullable String filePath, @NonNull Class<T> c) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            StringBuilder sbJson = new StringBuilder();
            String text;
            while ((text = reader.readLine()) != null) {
                sbJson.append(text);
            }
            return getJsonObject(sbJson.toString(), c);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOHelper.closeStreams(reader);
        }
        return null;
    }

    @NonNull
    @Override
    public <T> List<T> readListFromFile(@Nullable String filePath, @NonNull Class<T> c) {
        List<T> list = new ArrayList<>();
        if (TextUtils.isEmpty(filePath)) {
            return list;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            StringBuilder sbJson = new StringBuilder();
            String text;
            while ((text = reader.readLine()) != null) {
                sbJson.append(text);
            }
            JsonArray jsonArray = new JsonParser().parse(sbJson.toString()).getAsJsonArray();
            if (jsonArray == null) {
                return list;
            }
            return getListFromJson(jsonArray, c);
        } catch (FileNotFoundException e) {
            LogManagerProvider.e(TAG, "readListFromFile: " + e.getMessage());
        } catch (IOException e) {
            LogManagerProvider.e(TAG, "readListFromFile: " + e.getMessage());
        } finally {
            IOHelper.closeStreams(reader);
        }
        return list;
    }

    @NonNull
    @Override
    public <T> List<T> getListFromJson(@Nullable String json, @NonNull Class<T> c) {
        List<T> list = new ArrayList<>();
        if (json == null || json.isEmpty()) {
            return list;
        }
        JsonElement jsonElement = new JsonParser().parse(json);
        if (jsonElement instanceof JsonArray) {
            return getListFromJson(jsonElement.getAsJsonArray(), c);
        }
        return list;
    }

    @NonNull
    @Override
    public <T> List<T> getListFromJson(@NonNull JsonArray jsonArray, @NonNull Class<T> c) {
        List<T> list = new ArrayList<>();
        checkOrCreateGson();
        for (JsonElement jsonElement : jsonArray) {
            list.add(mGson.fromJson(jsonElement, c));
        }
        return list;
    }

    @NonNull
    @Override
    public <T> Map<String, Object> getMapFromJson(@Nullable String json, @NonNull Class<T> c) {
        Map<String, Object> map = new HashMap<>();
        if (json == null || json.isEmpty()) {
            return map;
        }
        JsonElement element = new JsonParser().parse(json);
        JsonObject jsonObject;
        if (element.isJsonObject()) {
            jsonObject = element.getAsJsonObject();
        } else {
            return map;
        }
        checkOrCreateGson();
        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            String key = entry.getKey();
            JsonElement jsonElement = entry.getValue();
            if (jsonElement instanceof JsonArray) {
                List<T> list = getListFromJson(jsonElement.getAsJsonArray(), c);
                map.put(key, list);
            } else if (jsonElement instanceof JsonObject) {
                map.put(key, mGson.fromJson(jsonElement, c));
            }
        }
        return map;
    }

    private void checkOrCreateGson() {
        if (mGson == null) {
            synchronized (this) {
                if (mGson == null) {
                    mGson = new Gson();
                }
            }
        }
    }

}
