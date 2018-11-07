package com.mingyuechunqiu.agilemvpframe.base.model;

import com.mingyuechunqiu.agilemvpframe.base.framework.IBaseListener;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/17
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseNetModel<I extends IBaseListener> implements BaseModel {

    protected final String TAG = getClass().getSimpleName();//日志标签
    protected final String TAG_FAILURE = getClass().getSimpleName() + " failure";//打印错误日志标签

    protected I mListener;

    public BaseNetModel(I listener) {
        mListener = listener;
    }

    /**
     * 释放资源方法
     */
    public void release() {
        mListener = null;
        destroy();
    }

    /**
     * 由子类重写进行网络请求
     */
    protected abstract void getRequest();

}
