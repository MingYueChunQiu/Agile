package com.mingyuechunqiu.agileproject.data.remote.retrofit.controller;

import com.mingyuechunqiu.agile.data.remote.retrofit.controller.BaseRetrofitManager;
import com.mingyuechunqiu.agileproject.constants.URLConstants;
import com.mingyuechunqiu.agileproject.data.remote.retrofit.service.APIService;

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

    private static volatile RetrofitManager sManager;
    private static volatile APIService sApiService;

    private RetrofitManager() {
    }

    public static RetrofitManager getInstance() {
        if (sManager == null) {
            synchronized (RetrofitManager.class) {
                if (sManager == null) {
                    sManager = new RetrofitManager();
                }
            }
        }
        return sManager;
    }

    /**
     * 获取网络请求接口服务
     *
     * @return Retrofit的服务接口
     */
    public static APIService getAPIService() {
        getInstance();
        if (sApiService == null) {
            synchronized (RetrofitManager.class) {
                if (sApiService == null) {
                    sApiService = sManager.getDefaultRetrofit().create(APIService.class);
                }
            }
        }
        return sApiService;
    }

    @Override
    protected String getFormalBaseUrl() {
        return URLConstants.URL_BASE;
    }

    @Override
    protected String getTestBaseUrl() {
        return null;
    }
}
