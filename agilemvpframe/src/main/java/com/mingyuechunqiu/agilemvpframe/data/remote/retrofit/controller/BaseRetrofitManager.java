package com.mingyuechunqiu.agilemvpframe.data.remote.retrofit.controller;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2017/12/26
 *     desc   : Retrofit的管理器，用于获取全局唯一Retrofit，执行网络请求
 *     version: 1.0
 * </pre>
 */
public class BaseRetrofitManager {

    protected static final int DEFAULT_TIMEOUT = 15;//默认服务器连接、读取超时时间（秒数）

    protected static int sTimeout;
    protected static OkHttpClient sOkHttpClient;

    public static int getTimeout() {
        return sTimeout;
    }

    public static void setTimeout(int timeout) {
        if (timeout < 0) {
            return;
        }
        sTimeout = timeout;
        //根据新超时时间，创建新OkHttpClient
        if (sOkHttpClient != null) {
            sOkHttpClient = null;
        }
        sOkHttpClient = getOkHttpClient();
    }

    /**
     * 获取OkHttpClient
     *
     * @return 返回OkHttpClient全局唯一实例对象
     */
    protected static OkHttpClient getOkHttpClient() {
        if (sTimeout <= 0) {
            sTimeout = DEFAULT_TIMEOUT;
        }
        if (sOkHttpClient == null) {
            synchronized (BaseRetrofitManager.class) {
                if (sOkHttpClient == null) {
                    sOkHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(sTimeout, TimeUnit.SECONDS)
                            .readTimeout(sTimeout, TimeUnit.SECONDS)
                            .writeTimeout(sTimeout, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return sOkHttpClient;
    }

}
