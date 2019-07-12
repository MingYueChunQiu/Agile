package com.mingyuechunqiu.agile.feature.json;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/2/21
 *     desc   : Json处理帮助类管理器工厂
 *     version: 1.0
 * </pre>
 */
public class JsonHelperManagerFactory {

    /**
     * 获取Json帮助类管理器实例（默认使用Gson处理Json）
     *
     * @return 返回Json帮助类管理器实例
     */
    @NonNull
    public static JsonHelperManagerable getInstance() {
        return getInstance(new GsonHelper());
    }

    /**
     * 获取Json帮助类管理器实例
     *
     * @param helper Json处理帮助类
     * @return 返回Json帮助类管理器实例
     */
    @NonNull
    public static JsonHelperManagerable getInstance(JsonHelperable helper) {
        return new JsonHelperManager(helper);
    }

}
