package com.mingyuechunqiu.agilemvpframe.base.model;

import android.support.annotation.StringRes;

import com.mingyuechunqiu.agilemvpframe.R;
import com.mingyuechunqiu.agilemvpframe.base.framework.IBaseListener;
import com.mingyuechunqiu.agilemvpframe.feature.logmanager.LogManagerProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
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

    protected List<Call> mRetrofitCallList;

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
        if (mRetrofitCallList == null) {
            return;
        }
        for (Call c : mRetrofitCallList) {
            if (c != null && !c.isCanceled()) {
                c.cancel();
            }
        }
        mRetrofitCallList.clear();
        mRetrofitCallList = null;
    }

    /**
     * 添加Retrofit回调
     *
     * @param call 回调对象
     */
    protected void addRetrofitCall(Call call) {
        if (call == null || call.isCanceled()) {
            return;
        }
        if (mRetrofitCallList == null) {
            mRetrofitCallList = new ArrayList<>();
        }
        //移除已经失效了的Retrofit回调
        if (mRetrofitCallList.size() > 0) {
            Iterator<Call> iterator = mRetrofitCallList.iterator();
            while (iterator.hasNext()) {
                Call c = iterator.next();
                if (c != null && c.isCanceled()) {
                    iterator.remove();
                }
            }
        }
        if (!mRetrofitCallList.contains(call)) {
            mRetrofitCallList.add(call);
        }
    }

    /**
     * 从Retrofit回调集合中移除回调
     *
     * @param call Retrofit回调
     */
    protected void removeRetrofitCall(Call call) {
        if (call == null || mRetrofitCallList == null || mRetrofitCallList.size() == 0) {
            return;
        }
        if (!call.isCanceled()) {
            call.cancel();
        }
        mRetrofitCallList.remove(call);
    }

    /**
     * 检测Retrofit的网络响应是否为空
     *
     * @param response 网络响应
     * @return 如果网络响应为空返回true，否则返回false
     */
    protected boolean checkRetrofitResponseIsNull(Response response) {
        if (response == null || response.body() == null) {
            onRetrofitResponseFailed(new IllegalStateException("服务器响应异常，请重试！"),
                    R.string.error_service_response);
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
