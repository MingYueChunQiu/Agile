package com.mingyuechunqiu.agile.base.model.repository.remote;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.bridge.Callback;
import com.mingyuechunqiu.agile.base.bridge.call.Call;
import com.mingyuechunqiu.agile.base.model.framework.callback.remote.DaoNetworkCallback;
import com.mingyuechunqiu.agile.constants.AgileURLConstants;
import com.mingyuechunqiu.agile.data.bean.ErrorInfo;
import com.mingyuechunqiu.agile.feature.helper.ui.hint.ToastHelper;
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;

import org.jetbrains.annotations.NotNull;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 网络Repository层抽象基类
 *              继承自BaseAbstractRemoteRepository
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractNetworkRepository<C extends DaoNetworkCallback> extends BaseAbstractRemoteRepository<C> {

    /**
     * 根据网络响应返回码，进行不同处理
     *
     * @param code     网络响应返回码
     * @param errorMsg 网络请求错误信息
     * @return 返回true表示响应成功，否则返回false失败
     */
    protected <T> boolean preHandleNetworkResponseFailureWithCode(@NonNull Call<T> call, int code, @Nullable String errorMsg) {
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
    protected <T> void handleOnNetworkResponseError(@NonNull Callback<T> callback, @Nullable String errorMsg) {
        handleOnNetworkResponseError(callback, new ErrorInfo(TextUtils.isEmpty(errorMsg) ? "信息异常" : errorMsg));
    }

    /**
     * 处理网络响应失败事件
     *
     * @param callback 请求回调
     * @param info     错误对象
     */
    protected <T> void handleOnNetworkResponseError(@NonNull @NotNull Callback<T> callback, @NonNull @NotNull ErrorInfo info) {
        LogManagerProvider.d(TAG_FAILURE, "网络响应错误");
        callback.onFailure(info);
    }

    /**
     * 获取网络成功请求编码
     *
     * @return 返回成功编码
     */
    protected int getNetworkSuccessCode() {
        return AgileURLConstants.CODE_SUCCESS;
    }

    /**
     * 获取Token过期编码
     *
     * @return 返回过期编码
     */
    protected int getTokenOverdueCode() {
        return AgileURLConstants.CODE_TOKEN_OVERDUE;
    }

    /**
     * 获取Token无效编码
     *
     * @return 返回无效编码
     */
    protected int getTokenInvalidCode() {
        return AgileURLConstants.CODE_TOKEN_INVALID;
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
