package com.mingyuechunqiu.agilemvpframe.base.model;

import android.support.annotation.StringRes;

import com.mingyuechunqiu.agilemvpframe.R;
import com.mingyuechunqiu.agilemvpframe.base.framework.IBaseListener;
import com.mingyuechunqiu.agilemvpframe.util.LogUtils;

import retrofit2.Response;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
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
        releaseNetworkRequests();
        super.release();
    }

    /**
     * 检测Retrofit的网络响应是否为空
     *
     * @param response 网络响应
     * @return 如果网络响应为空返回true，否则返回false
     */
    protected boolean checkRetrofitResponseIsNull(Response response) {
        if (response == null || response.body() == null) {
            LogUtils.d(TAG_FAILURE, "服务器响应异常，请重试！");
            if (mListener != null) {
                mListener.onFailure(R.string.error_service_response);
            }
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
    protected void onRetrofitResponseFailed(Throwable t, @StringRes int errorStringResId) {
        LogUtils.d(TAG_FAILURE, t.getMessage());
        if (mListener != null) {
            mListener.onFailure(errorStringResId);
        }
    }

    /**
     * 释放网络请求
     */
    protected abstract void releaseNetworkRequests();
}
