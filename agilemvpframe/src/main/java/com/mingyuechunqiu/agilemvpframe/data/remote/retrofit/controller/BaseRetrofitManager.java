package com.mingyuechunqiu.agilemvpframe.data.remote.retrofit.controller;

import com.mingyuechunqiu.agilemvpframe.agile.AgileMVPFrame;
import com.mingyuechunqiu.agilemvpframe.agile.AgileMVPFrameConfigure;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2017/12/26
 *     desc   : Retrofit的管理器，用于获取全局唯一Retrofit，执行网络请求
 *     version: 1.0
 * </pre>
 */
public abstract class BaseRetrofitManager {

    public static final int DEFAULT_TIMEOUT = 10;//默认服务器连接、读取超时时间（秒数）

    /**
     * 获取默认Retrofit实例
     *
     * @return 返回Retrofit实例对象
     */
    public Retrofit getDefaultRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(getDefaultOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**
     * 获取默认OkHttpClient
     *
     * @return 返回OkHttpClient实例对象
     */
    protected OkHttpClient getDefaultOkHttpClient() {
        AgileMVPFrameConfigure configure = AgileMVPFrame.getConfigure();
        return new OkHttpClient.Builder()
                .connectTimeout(configure.getNetworkConfig().getConnectNetTimeout() <= 0 ?
                        DEFAULT_TIMEOUT : configure.getNetworkConfig().getConnectNetTimeout(), TimeUnit.SECONDS)
                .readTimeout(configure.getNetworkConfig().getReadNetTimeout() <= 0 ?
                        DEFAULT_TIMEOUT : configure.getNetworkConfig().getReadNetTimeout(), TimeUnit.SECONDS)
                .writeTimeout(configure.getNetworkConfig().getWriteNetTimeout() <= 0 ?
                        DEFAULT_TIMEOUT : configure.getNetworkConfig().getWriteNetTimeout(), TimeUnit.SECONDS)
                .build();
    }

    /**
     * 获取基本URL
     *
     * @return 返回URL字符串
     */
    protected abstract String getBaseUrl();
}
