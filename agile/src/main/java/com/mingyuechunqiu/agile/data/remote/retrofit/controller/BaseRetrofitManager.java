package com.mingyuechunqiu.agile.data.remote.retrofit.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.frame.Agile;
import com.mingyuechunqiu.agile.frame.AgileFrameConfigure;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *       author :    MingYueChunQiu
 *       Github :    <a href="https://github.com/MingYueChunQiu">...</a>
 *       e-mail :    xiyujieit@163.com
 *       time   :    2017/12/26
 *       desc   :    Retrofit的管理器，用于获取全局唯一Retrofit，执行网络请求
 *       version:    1.0
 * </pre>
 */
public abstract class BaseRetrofitManager {

    public static final long DEFAULT_TIMEOUT = 10 * 1000;//默认服务器连接、读取超时时间（毫秒数）

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
                .build();
    }

    /**
     * 获取默认OkHttpClient
     *
     * @return 返回OkHttpClient实例对象
     */
    @NonNull
    protected OkHttpClient getDefaultOkHttpClient() {
        OkHttpClient.Builder builder = initOkHttpClientBuilder(initDefaultOkHttpClientBuilder());
        if (builder == null) {
            builder = initDefaultOkHttpClientBuilder();
        }
        return builder.build();
    }

    @Nullable
    protected OkHttpClient.Builder initOkHttpClientBuilder(@NonNull OkHttpClient.Builder builder) {
        return builder;
    }

    /**
     * 获取基本URL
     *
     * @return 返回URL字符串
     */
    protected String getBaseUrl() {
        return beInTest() ? getTestBaseUrl() : getFormalBaseUrl();
    }

    /**
     * 是否处于测试中（默认返回false）
     *
     * @return 是返回true，否则返回false
     */
    protected boolean beInTest() {
        return false;
    }

    /**
     * 获取正式基本URL
     *
     * @return 返回URL字符串
     */
    protected abstract String getFormalBaseUrl();

    /**
     * 获取测试基本URL
     *
     * @return 返回URL字符串
     */
    protected abstract String getTestBaseUrl();

    /**
     * 初始化OkHttpClient建造者
     *
     * @return 返回建造者
     */
    @NonNull
    private OkHttpClient.Builder initDefaultOkHttpClientBuilder() {
        AgileFrameConfigure configure = Agile.getConfigure();
        return new OkHttpClient.Builder()
                .connectTimeout(configure.getNetworkConfig().getConnectNetTimeout() <= 0 ?
                        DEFAULT_TIMEOUT : configure.getNetworkConfig().getConnectNetTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(configure.getNetworkConfig().getReadNetTimeout() <= 0 ?
                        DEFAULT_TIMEOUT : configure.getNetworkConfig().getReadNetTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(configure.getNetworkConfig().getWriteNetTimeout() <= 0 ?
                        DEFAULT_TIMEOUT : configure.getNetworkConfig().getWriteNetTimeout(), TimeUnit.MILLISECONDS);
    }
}
