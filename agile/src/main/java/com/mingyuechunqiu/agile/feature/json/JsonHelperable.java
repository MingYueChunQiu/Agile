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
 *     desc   : 所有的Json处理帮助类接口
 *     version: 1.0
 * </pre>
 */
public interface JsonHelperable {

    /**
     * 获取对象转换成的json字符串
     *
     * @param o java对象
     * @return 如果转换成功返回字符串，否则返回null
     */
    @Nullable
    String getJsonString(@Nullable Object o);

    /**
     * 根据json字符串获取json对象
     *
     * @param json json字符串
     * @param c    json对象类型
     * @param <T>  json对象泛型约束
     * @return 返回json对象
     */
    @Nullable
    <T> T getJsonObject(@Nullable String json, @NonNull Class<T> c);

    /**
     * 将字符串写入文件
     *
     * @param json     Json字符串
     * @param filePath 文件路径
     */
    void writeJsonStringToFile(@Nullable String json, @Nullable String filePath);

    /**
     * 从文件中读取json
     *
     * @param filePath 文件名
     * @param c        json对象类型
     * @param <T>      json对象泛型约束
     * @return 读取转换成功返回json对象，否则返回null
     */
    @Nullable
    <T> T readJsonFromFile(@Nullable String filePath, @NonNull Class<T> c);

    /**
     * 从文件中读取json字符串
     *
     * @param fileName 文件路径
     * @param c        json对象类字节码类
     * @param <T>      json对象类型
     * @return 转换成功返回json对象，否则返回null
     */
    @Nullable
    <T> List<T> readListFromFile(@Nullable String fileName, @NonNull Class<T> c);

    /**
     * 根据Json字符串获取列表
     *
     * @param json json字符串
     * @param c    列表的对象类型
     * @param <T>  列表元素泛型类型
     * @return 如果转换成功返回List对象，否则返回null
     */
    @Nullable
    <T> List<T> getListFromJson(@Nullable String json, @NonNull Class<T> c);

    /**
     * 将json字符串转换成list集合
     *
     * @param jsonArray json数组
     * @param c         值的类型
     * @param <T>       值的泛型类型
     * @return 返回list集合
     */
    @NonNull
    <T> List<T> getListFromJson(@NonNull JsonArray jsonArray, @NonNull Class<T> c);

    /**
     * 将json字符串转换成map集合
     *
     * @param json json格式的字符串
     * @param c    map值的类型
     * @param <T>  值的泛型类型
     * @return 返回map集合
     */
    @Nullable
    <T> Map<String, Object> getMapFromJson(@Nullable String json, @NonNull Class<T> c);
}
