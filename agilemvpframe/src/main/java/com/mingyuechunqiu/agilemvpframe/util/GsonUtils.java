package com.mingyuechunqiu.agilemvpframe.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mingyuechunqiu.agilemvpframe.io.BaseFile;

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
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/9/7
 *     desc   : json转换工具类
 *     version: 1.0
 * </pre>
 */
public class GsonUtils extends BaseFile {

    /**
     * 获取对象转换成的json字符串
     *
     * @param o java对象
     * @return 如果转换成功返回字符串，否则返回null
     */
    public static String getJsonString(Object o) {
        if (o == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    /**
     * 将字符串写入文件
     *
     * @param s        字符串
     * @param fileName 文件名
     */
    public static void writeStringToFile(String s, String fileName) {
        if (TextUtils.isEmpty(s) || TextUtils.isEmpty(fileName) || !checkFile(fileName)) {
            return;
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(writer);
        }
    }

    /**
     * 从文件中读取json
     *
     * @param fileName 文件名
     * @param c        json对象类型
     * @param <T>      json对象泛型约束
     * @return 读取转换成功返回json对象，否则返回null
     */
    @Nullable
    public static <T> T readJsonFromFile(String fileName, Class<T> c) {
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
            return getJsonObject(sbJson.toString(), c);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(reader);
        }
        return null;
    }

    /**
     * 从文件中读取json字符串
     *
     * @param fileName 文件路径
     * @param c        json对象类字节码类
     * @param <T>      json对象类型
     * @return 转换成功返回json对象，否则返回null
     */
    @Nullable
    public static <T> List<T> readJsonArrayFromFile(String fileName, Class<T> c) {
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
            closeStream(reader);
        }
        return null;
    }

    /**
     * 将json字符串转换成list集合
     *
     * @param jsonArray json数组
     * @param c         值的类型
     * @param <T>       值的泛型类型
     * @return 返回list集合
     */
    public static <T> List<T> getListFromJson(@NonNull JsonArray jsonArray, @NonNull Class<T> c) {
        List<T> list = new ArrayList<>();
        Gson gson = new Gson();
        for (JsonElement jsonElement : jsonArray) {
            list.add(gson.fromJson(jsonElement, c));
        }
        return list;
    }

    /**
     * 将json字符串转换成map集合
     *
     * @param json json格式的字符串
     * @param c    map值的类型
     * @param <T>  值的泛型类型
     * @return 返回map集合
     */
    @Nullable
    public static <T> Map<String, Object> getMapFromJson(@NonNull String json, @NonNull Class<T> c) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        if (jsonObject == null) {
            return null;
        }
        Gson gson = new Gson();
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
                map.put(key, gson.fromJson(jsonElement, c));
            }
        }
        return map;
    }

    /**
     * 根据json字符串获取json对象
     *
     * @param json json字符串
     * @param c    json对象类型
     * @param <T>  json对象泛型约束
     * @return 返回json对象
     */
    public static <T> T getJsonObject(String json, Class<T> c) {
        Gson gson = new Gson();
        return gson.fromJson(json, c);
    }
}
