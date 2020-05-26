package com.mingyuechunqiu.agile.base.model;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.mingyuechunqiu.agile.R;
import com.mingyuechunqiu.agile.base.framework.IBaseListener;
import com.mingyuechunqiu.agile.base.model.dao.framework.callback.local.DaoLocalCallback;
import com.mingyuechunqiu.agile.base.model.dao.framework.callback.remote.DaoRetrofitCallback;
import com.mingyuechunqiu.agile.base.model.dao.framework.result.DaoResultHandler;
import com.mingyuechunqiu.agile.base.model.framework.local.IModelLocalData;
import com.mingyuechunqiu.agile.base.model.framework.remote.IModelRetrofitData;
import com.mingyuechunqiu.agile.constants.URLConstants;
import com.mingyuechunqiu.agile.data.bean.ErrorInfo;
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;
import com.mingyuechunqiu.agile.util.ToastUtils;

import java.util.Map;

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
public abstract class BaseAbstractDataModel<I extends IBaseListener> extends BaseAbstractModel<I> implements DaoLocalCallback<I>, DaoRetrofitCallback<I>,
        IModelLocalData, IModelRetrofitData {

    public BaseAbstractDataModel(@NonNull I listener) {
        super(listener);
    }

    @Override
    public void releaseOnDetach() {
        releaseNetworkResources();
        super.releaseOnDetach();
    }

    /**
     * 检测Retrofit的网络响应是否为空
     *
     * @param response 网络响应
     * @return 如果网络响应为空返回true，否则返回false
     */
    @Override
    public boolean checkRetrofitResponseIsNull(@Nullable Response response) {
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
     * @param t             抛出的异常
     * @param errorMsgResId 错误提示字符串资源ID
     */
    @Override
    public void onNetworkResponseFailed(@Nullable Throwable t, @StringRes int errorMsgResId) {
        onNetworkResponseFailed(t, new ErrorInfo(errorMsgResId));
    }

    @Override
    public void onNetworkResponseFailed(@Nullable Throwable t, @NonNull ErrorInfo info) {
        if (t != null) {
            LogManagerProvider.d(TAG_FAILURE, t.getMessage());
        }
        if (mListener != null) {
            mListener.onFailure(info);
        }
    }

    @Override
    public void onExecuteDaoResult(@NonNull DaoResultHandler<I> handler) {
        handler.handleDaoResult(mListener);
    }

    /**
     * 根据网络响应返回码，进行不同处理
     *
     * @param code     网络响应返回码
     * @param errorMsg 网络请求错误信息
     * @return 返回true表示响应成功，否则返回false失败
     */
    @Override
    public boolean handleNetworkResponseCode(int code, @Nullable String errorMsg) {
        if (code == getNetworkSuccessCode()) {
            return true;
        }
        if (code == getTokenOverdueCode()) {
            handleOnTokenOverdue();
        } else if (code == getTokenInvalidCode()) {
            handleOnTokenInvalid(errorMsg);
        } else {
            handleOnNetworkResponseError(code, errorMsg);
        }
        return false;
    }

    /**
     * 释放网络相关资源
     */
    @Override
    public void releaseNetworkResources() {
    }

    /**
     * 获取网络成功请求编码
     *
     * @return 返回成功编码
     */
    protected int getNetworkSuccessCode() {
        return URLConstants.CODE_SUCCESS;
    }

    /**
     * 获取Token过期编码
     *
     * @return 返回过期编码
     */
    protected int getTokenOverdueCode() {
        return URLConstants.CODE_TOKEN_OVERDUE;
    }

    /**
     * 获取Token无效编码
     *
     * @return 返回无效编码
     */
    protected int getTokenInvalidCode() {
        return URLConstants.CODE_TOKEN_INVALID;
    }

    /**
     * 处理Token过期
     */
    protected void handleOnTokenOverdue() {
        callOnTokenOverdue();
    }

    /**
     * 处理Token无效
     */
    protected void handleOnTokenInvalid(@Nullable String errorMsg) {
        ToastUtils.showToast(errorMsg);
        callOnTokenInvalid();
    }

    /**
     * 处理网络响应发生未知异常
     *
     * @param code     错误码
     * @param errorMsg 错误信息
     */
    protected void handleOnNetworkResponseError(int code, @Nullable String errorMsg) {
        callOnNetworkResponseError(code, errorMsg);
        onNetworkResponseFailed(new IllegalArgumentException("网络响应错误"), new ErrorInfo(TextUtils.isEmpty(errorMsg) ? "信息异常" : errorMsg));
    }

    /**
     * 当token过期时进行回调
     */
    protected void callOnTokenOverdue() {
    }

    /**
     * 当token无效时回调
     */
    protected void callOnTokenInvalid() {
    }

    /**
     * 当网络响应发生未知异常时回调
     *
     * @param code     错误码
     * @param errorMsg 错误信息
     */
    protected void callOnNetworkResponseError(int code, @Nullable String errorMsg) {
    }

    /**
     * 当请求失败后，由子类重写调用进行再次请求
     *
     * @param paramMap 请求参数集合
     */
    protected void redoRequest(@NonNull Map<String, String> paramMap) {
    }
}
