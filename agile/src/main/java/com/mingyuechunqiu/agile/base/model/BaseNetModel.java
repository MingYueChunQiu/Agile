package com.mingyuechunqiu.agile.base.model;

import android.support.annotation.StringRes;

import com.mingyuechunqiu.agile.R;
import com.mingyuechunqiu.agile.base.framework.IBaseListener;
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;

import retrofit2.Response;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/17
 *     desc   : 所有网络Model层的抽象基类
 *              继承自BaseAbstractModel
 *     version: 1.0
 * </pre>
 */
public abstract class BaseNetModel<I extends IBaseListener> extends BaseAbstractModel<I> {

    public BaseNetModel(I listener) {
        super(listener);
    }

    @Override
    public void release() {
        releaseNetResources();
        super.release();
    }

    /**
     * 释放网络相关资源
     */
    public void releaseNetResources() {
        releaseNetworkResources();
    }

    /**
     * 检测Retrofit的网络响应是否为空
     *
     * @param response 网络响应
     * @return 如果网络响应为空返回true，否则返回false
     */
    protected boolean checkRetrofitResponseIsNull(Response response) {
        if (response == null || response.body() == null) {
            onNetworkResponseFailed(new IllegalStateException("服务器响应异常，请重试！"),
                    R.string.agile_error_service_response);
            return true;
        }
        return false;
    }

    /**
     * 处理Retrofit网络响应失败事件
     *
     * @param t                抛出的异常
     * @param errorStringResId 错误提示字符串资源ID
     */
    protected void onNetworkResponseFailed(Throwable t, @StringRes int errorStringResId) {
        LogManagerProvider.d(TAG_FAILURE, t.getMessage());
        if (mListener != null) {
            mListener.onFailure(errorStringResId);
        }
    }

    /**
     * 释放网络相关资源，由子类重写实现自己相关逻辑
     */
    protected abstract void releaseNetworkResources();
}
