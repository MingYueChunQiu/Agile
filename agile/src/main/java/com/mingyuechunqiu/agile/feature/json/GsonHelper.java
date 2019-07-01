package com.mingyuechunqiu.agile.feature.json;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mingyuechunqiu.agile.io.IOUtils;

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
class GsonHelper implements JsonHelperable {

    private volatile Gson mGson;

    @Nullable
    @Override
    public String getJsonString(Object o) {
        checkOrCreateGson();
        return o == null ? null : mGson.toJson(o);
    }

    @Override
    public <T> T getJsonObject(String json, @NonNull Class<T> c) {
        checkOrCreateGson();
        return mGson.fromJson(json, c);
    }

    @Override
    public void writeJsonStringToFile(String json, String filePath) {
        if (TextUtils.isEmpty(json) || TextUtils.isEmpty(filePath) || !IOUtils.checkIsFileOrCreate(filePath)) {
            return;
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(writer);
        }
    }

    @Nullable
    @Override
    public <T> T readJsonFromFile(String filePath, @NonNull Class<T> c) {
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
            IOUtils.closeStream(reader);
        }
        return null;
    }

    @Nullable
    @Override
    public <T> List<T> readListFromFile(String fileName, @NonNull Class<T> c) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            StringBuilder sbJson = new StringBuilder();
            String text;
            while ((text = reader.readLine()) != null) {
                sbJson.append(text);
            }
            JsonArray jsonArray = new JsonParser().parse(sbJson.toString()).getAsJsonArray();
            if (jsonArray == null) {
                return null;
            }
            return getListFromJson(jsonArray, c);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(reader);
        }
        return null;
    }

    @Override
    public <T> List<T> getListFromJson(String json, @NonNull Class<T> c) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        JsonElement jsonElement = new JsonParser().parse(json);
        if (jsonElement instanceof JsonArray) {
            return getListFromJson(jsonElement.getAsJsonArray(), c);
        }
        return null;
    }

    @Override
    public <T> List<T> getListFromJson(@NonNull JsonArray jsonArray, @NonNull Class<T> c) {
        List<T> list = new ArrayList<>();
        checkOrCreateGson();
        for (JsonElement jsonElement : jsonArray) {
            list.add(mGson.fromJson(jsonElement, c));
        }
        return list;
    }

    @Override
    public <T> Map<String, Object> getMapFromJson(String json, @NonNull Class<T> c) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        JsonElement element = new JsonParser().parse(json);
        JsonObject jsonObject;
        if (element.isJsonObject()) {
            jsonObject = element.getAsJsonObject();
        } else {
            return null;
        }
        checkOrCreateGson();
        Map<String, Object> map = new HashMap<>();
        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            String key = entry.getKey();
            JsonElement jsonElement = entry.getValue();
            if (jsonElement instanceof JsonArray) {
                List<T> list = getListFromJson(jsonElement.getAsJsonArray(), c);
                if (list != null) {
                    map.put(key, list);
                }
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
