package com.mingyuechunqiu.agile.base.model;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.bridge.Request;
import com.mingyuechunqiu.agile.base.bridge.call.Call;
import com.mingyuechunqiu.agile.base.model.framework.callback.local.DaoLocalCallback;
import com.mingyuechunqiu.agile.base.model.framework.callback.remote.DaoRetrofitCallback;
import com.mingyuechunqiu.agile.base.model.framework.data.local.IModelLocalDataProcessor;
import com.mingyuechunqiu.agile.base.model.framework.data.remote.IModelNetworkDataProcessor;
import com.mingyuechunqiu.agile.constants.URLConstants;
import com.mingyuechunqiu.agile.data.bean.ErrorInfo;
import com.mingyuechunqiu.agile.feature.helper.ui.hint.ToastHelper;
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;

import org.jetbrains.annotations.NotNull;

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
public abstract class BaseAbstractDataModel extends BaseAbstractModel implements IModelLocalDataProcessor, IModelNetworkDataProcessor, DaoLocalCallback, DaoRetrofitCallback {

    /**
     * 根据网络响应返回码，进行不同处理
     *
     * @param code     网络响应返回码
     * @param errorMsg 网络请求错误信息
     * @return 返回true表示响应成功，否则返回false失败
     */
    @Override
    public <T> boolean preHandleNetworkResponseFailureWithCode(@NonNull Call<T> call, int code, @Nullable String errorMsg) {
        if (code == getNetworkSuccessCode()) {
            return true;
        }
        if (code == getTokenOverdueCode()) {
            handleOnTokenOverdue(call);
        } else if (code == getTokenInvalidCode()) {
            handleOnTokenInvalid(errorMsg);
        } else {
            handleOnNetworkResponseError(call.getCallback(), errorMsg);
        }
        return false;
    }

    /**
     * 处理网络响应失败事件
     *
     * @param callback 请求回调
     * @param errorMsg 错误信息
     */
    @Override
    public <T> void handleOnNetworkResponseError(@NonNull Request.Callback<T> callback, @Nullable String errorMsg) {
        handleOnNetworkResponseError(callback, new ErrorInfo(TextUtils.isEmpty(errorMsg) ? "信息异常" : errorMsg));
    }

    /**
     * 处理网络响应失败事件
     *
     * @param callback 请求回调
     * @param info     错误对象
     */
    @Override
    public <T> void handleOnNetworkResponseError(@NonNull @NotNull Request.Callback<T> callback, @NonNull @NotNull ErrorInfo info) {
        LogManagerProvider.d(TAG_FAILURE, "网络响应错误");
        callback.onFailure(info);
    }

    @Override
    public <T> boolean checkRetrofitResponseIsNull(@Nullable Response<T> response) {
        return response == null || response.body() == null;
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
     *
     * @param call 调用对象
     * @param <T>  响应对象类型
     */
    protected <T> void handleOnTokenOverdue(@NonNull Call<T> call) {
        callOnTokenOverdue(call);
    }

    /**
     * 处理Token无效
     */
    protected void handleOnTokenInvalid(@Nullable String errorMsg) {
        ToastHelper.showToast(errorMsg);
        callOnTokenInvalid();
    }

    /**
     * 当token过期时进行回调
     *
     * @param call 调用对象
     * @param <T>  响应对象类型
     */
    protected <T> void callOnTokenOverdue(@NonNull Call<T> call) {
    }

    /**
     * 当token无效时回调
     */
    protected void callOnTokenInvalid() {
    }
}
