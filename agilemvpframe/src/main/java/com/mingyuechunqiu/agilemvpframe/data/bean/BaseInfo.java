package com.mingyuechunqiu.agilemvpframe.data.bean;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/17
 *     desc   : 所有网络请求参数的基类
 *     version: 1.0
 * </pre>
 */
public class BaseInfo {

    private int requestType;//网络请求类型

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }
}
