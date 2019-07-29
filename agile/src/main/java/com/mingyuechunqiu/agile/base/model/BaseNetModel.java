package com.mingyuechunqiu.agile.base.model;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.mingyuechunqiu.agile.R;
import com.mingyuechunqiu.agile.base.framework.IBaseListener;
import com.mingyuechunqiu.agile.base.model.part.dao.remote.BaseAbstractRetrofitDao;
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
public abstract class BaseNetModel<I extends IBaseListener> extends BaseAbstractModel<I> implements BaseAbstractRetrofitDao.ModelDaoRetrofitCallback<I> {

    public BaseNetModel(I listener) {
        super(listener);
    }

    @Override
    public void release() {
        releaseNetResources();
        super.release();
    }

    /**
     * 检测Retrofit的网络响应是否为空
     *
     * @param response 网络响应
     * @return 如果网络响应为空返回true，否则返回false
     */
    @Override
    public boolean checkRetrofitResponseIsNull(Response response) {
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
    @Override
    public void onNetworkResponseFailed(Throwable t, @StringRes int errorStringResId) {
        if (t != null) {
            LogManagerProvider.d(TAG_FAILURE, t.getMessage());
        }
        if (mListener != null) {
            mListener.onFailure(errorStringResId);
        }
    }

    @Override
    public void onExecuteResult(@NonNull ResultOperation<I> operation) {
        operation.operate(mListener);
    }

    /**
     * 根据网络响应返回码，进行不同处理
     *
     * @param code     网络响应返回码
     * @param errorMsg 网络请求错误信息
     * @return 返回true表示响应成功，否则返回false失败
     */
    @Override
    public boolean handleNetworkResponseCode(int code, String errorMsg) {
        return true;
    }

    /**
     * 释放网络相关资源
     */
    public void releaseNetResources() {
        releaseNetworkResources();
    }

    /**
     * 释放网络相关资源，由子类重写实现自己相关逻辑
     */
    protected abstract void releaseNetworkResources();
}
