package com.mingyuechunqiu.agile.base.model.repository.remote;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.bridge.Request;
import com.mingyuechunqiu.agile.base.bridge.call.Call;
import com.mingyuechunqiu.agile.constants.AgileCodeConstants;
import com.mingyuechunqiu.agile.data.bean.ErrorInfo;
import com.mingyuechunqiu.agile.feature.helper.ui.hint.ToastHelper;
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;

import org.jetbrains.annotations.NotNull;

/**
 * <pre>
 *     author : xyj
 *     Github : <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 网络Repository层抽象基类
 *              继承自BaseAbstractRemoteRepository
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractNetworkRepository extends BaseAbstractRemoteRepository {

    /**
     * 根据网络响应返回码，进行不同处理
     *
     * @param call     调用对象
     * @param code     网络响应返回码
     * @param errorMsg 网络请求错误信息
     * @param <I>      请求泛型类型
     * @param <T>      响应泛型类型
     * @return 返回true表示响应成功，否则返回false失败
     */
    protected <I extends Request.IParamsInfo, T> boolean preHandleNetworkResponseFailureWithCode(@NonNull Call<I, T> call, int code, @Nullable String errorMsg) {
        if (code == getNetworkSuccessCode()) {
            return true;
        }
        if (code == getTokenOverdueCode()) {
            handleOnTokenOverdue(call);
        } else if (code == getTokenInvalidCode()) {
            handleOnTokenInvalid(errorMsg);
        } else {
            handleOnNetworkResponseError(call, errorMsg);
        }
        return false;
    }

    /**
     * 处理网络响应失败事件
     *
     * @param call     调用
     * @param errorMsg 错误信息
     * @param <I>      请求泛型类型
     * @param <T>      响应泛型类型
     */
    protected <I extends Request.IParamsInfo, T> void handleOnNetworkResponseError(@NonNull Call<I, T> call, @Nullable String errorMsg) {
        handleOnNetworkResponseError(call, new ErrorInfo(TextUtils.isEmpty(errorMsg) ? "信息异常" : errorMsg));
    }

    /**
     * 处理网络响应失败事件
     *
     * @param call 调用
     * @param info 错误对象
     * @param <I>  请求泛型类型
     * @param <T>  响应泛型类型
     */
    protected <I extends Request.IParamsInfo, T> void handleOnNetworkResponseError(@NonNull @NotNull Call<I, T> call, @NonNull @NotNull ErrorInfo info) {
        LogManagerProvider.d(TAG_FAILURE, "网络响应错误");
        call.getCallback().onFailure(info);
    }

    /**
     * 获取网络成功请求编码
     *
     * @return 返回成功编码
     */
    protected int getNetworkSuccessCode() {
        return AgileCodeConstants.CODE_SUCCESS;
    }

    /**
     * 获取Token过期编码
     *
     * @return 返回过期编码
     */
    protected int getTokenOverdueCode() {
        return AgileCodeConstants.CODE_TOKEN_OVERDUE;
    }

    /**
     * 获取Token无效编码
     *
     * @return 返回无效编码
     */
    protected int getTokenInvalidCode() {
        return AgileCodeConstants.CODE_TOKEN_INVALID;
    }

    /**
     * 处理Token过期
     *
     * @param call 调用对象
     * @param <I>  请求泛型类型
     * @param <T>  响应泛型类型
     */
    protected <I extends Request.IParamsInfo, T> void handleOnTokenOverdue(@NonNull Call<I, T> call) {
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
     * @param <I>  请求泛型类型
     * @param <T>  响应泛型类型
     */
    protected <I extends Request.IParamsInfo, T> void callOnTokenOverdue(@NonNull Call<I, T> call) {
    }

    /**
     * 当token无效时回调
     */
    protected void callOnTokenInvalid() {
    }
}
