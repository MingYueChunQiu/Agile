package com.mingyuechunqiu.agilemvpframeproject.data.remote.retrofit.controller;

import com.mingyuechunqiu.agilemvpframe.data.remote.retrofit.controller.BaseRetrofitManager;
import com.mingyuechunqiu.agilemvpframeproject.constants.URLConstants;
import com.mingyuechunqiu.agilemvpframeproject.data.remote.retrofit.service.APIService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2017/12/26
 *     desc   : Retrofit的管理器，用于获取全局唯一Retrofit，执行网络请求
 *     version: 1.0
 * </pre>
 */
public class RetrofitManager extends BaseRetrofitManager {

    private static APIService sApiService;

    /**
     * 获取网络请求接口服务
     *
     * @return Retrofit的服务接口
     */
    public static APIService getAPIService() {
        if (sApiService == null) {
            synchronized (RetrofitManager.class) {
                if (sApiService == null) {
                    sApiService = new Retrofit.Builder()
                            .baseUrl(URLConstants.URL_BASE)
                            .client(getOkHttpClient())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build().create(APIService.class);
                }
            }
        }
        return sApiService;
    }

}
