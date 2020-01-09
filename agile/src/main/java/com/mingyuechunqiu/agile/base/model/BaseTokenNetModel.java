package com.mingyuechunqiu.agile.base.model;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.framework.IBaseListener;
import com.mingyuechunqiu.agile.constants.URLConstants;
import com.mingyuechunqiu.agile.data.bean.ErrorInfo;
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;
import com.mingyuechunqiu.agile.util.ToastUtils;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/22
 *     desc   : 所有需要使用token的网络业务模型类的基类
 *              继承自BaseNetModel
 *     version: 1.0
 * </pre>
 */
public abstract class BaseTokenNetModel<I extends IBaseListener> extends BaseNetModel<I> {

    protected WeakReference<Context> mContextRef;

    public BaseTokenNetModel(@NonNull I listener) {
        super(listener);
    }

    @Override
    public void releaseByPresenter() {
        super.releaseByPresenter();
        if (mContextRef != null) {
            mContextRef.clear();
            mContextRef = null;
        }
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
            handleOnResponseError(code, errorMsg);
        }
        return false;
    }

    /**
     * 设置上下文
     *
     * @param context 上下文
     */
    public void setContextRef(@Nullable Context context) {
        if (context == null) {
            return;
        }
        mContextRef = new WeakReference<>(context);
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
    protected void handleOnResponseError(int code, @Nullable String errorMsg) {
        LogManagerProvider.d(TAG, errorMsg);
        callOnResponseError(code, errorMsg);
        if (mListener != null) {
            mListener.onFailure(new ErrorInfo(TextUtils.isEmpty(errorMsg) ? "信息异常" : errorMsg));
        }
    }

    /**
     * 当token过期时进行回调
     */
    protected abstract void callOnTokenOverdue();

    /**
     * 当token无效时回调
     */
    protected abstract void callOnTokenInvalid();

    /**
     * 当网络响应发生未知异常时回调
     *
     * @param code     错误码
     * @param errorMsg 错误信息
     */
    protected abstract void callOnResponseError(int code, @Nullable String errorMsg);

    /**
     * 当token失效重新获取后，由子类重写调用进行再次网络请求
     *
     * @param paramMap 网络请求参数集合
     */
    protected abstract void redoRequest(@NonNull Map<String, String> paramMap);

}
