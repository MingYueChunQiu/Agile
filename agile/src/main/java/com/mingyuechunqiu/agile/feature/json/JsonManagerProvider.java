package com.mingyuechunqiu.agile.feature.json;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/2/21
 *     desc   : Json管理器提供类
 *     version: 1.0
 * </pre>
 */
public final class JsonManagerProvider {

    private static volatile IJsonManager sInstance;

    private JsonManagerProvider() {
    }

    /**
     * 获取Json帮助类管理器实例（默认使用Gson处理Json）
     *
     * @return 返回Json帮助类管理器实例
     */
    @NonNull
    public static IJsonManager getInstance() {
        return getInstance(new GsonHelper());
    }

    /**
     * 获取Json帮助类管理器实例
     *
     * @param helper Json处理帮助类
     * @return 返回Json帮助类管理器实例
     */
    @NonNull
    public static IJsonManager getInstance(@NonNull IJsonHelper helper) {
        if (sInstance == null) {
            synchronized (JsonManagerProvider.class) {
                if (sInstance == null) {
                    sInstance = new JsonManager(helper);
                }
            }
        }
        return sInstance;
    }
}
