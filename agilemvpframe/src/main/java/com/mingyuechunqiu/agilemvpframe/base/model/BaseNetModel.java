package com.mingyuechunqiu.agilemvpframe.base.model;

import com.mingyuechunqiu.agilemvpframe.R;
import com.mingyuechunqiu.agilemvpframe.base.framework.IBaseListener;
import com.mingyuechunqiu.agilemvpframe.data.bean.BaseInfo;

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
     * 设置网络请求参数对象，进行网络请求
     *
     * @param info 网络请求参数对象
     */
    public void setParamsInfo(BaseInfo info) {
        if (info == null) {
            if (mListener == null) {
                throw new IllegalArgumentException("Listener has not been set!");
            }
            mListener.onFailure(R.string.error_set_net_params);
            return;
        }
        getRequest(info);
    }

    /**
     * 由子类重写进行网络请求
     *
     * @param info 网络请求参数对象
     */
    protected abstract void getRequest(BaseInfo info);

}
