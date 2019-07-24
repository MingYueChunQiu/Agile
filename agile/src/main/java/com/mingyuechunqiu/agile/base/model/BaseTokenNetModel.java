package com.mingyuechunqiu.agile.base.model;

import android.content.Context;

import com.mingyuechunqiu.agile.base.framework.IBaseListener;
import com.mingyuechunqiu.agile.constants.URLConstants;
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

    public BaseTokenNetModel(I listener) {
        super(listener);
    }

    @Override
    public void release() {
        super.release();
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
    public boolean handleNetworkResponseCode(int code, String errorMsg) {
        if (code == URLConstants.CODE_SUCCESS) {
            return true;
        }
        if (code == URLConstants.CODE_TOKEN_OVERDUE) {
            callOnTokenOverdue();
        } else if (code == URLConstants.CODE_TOKEN_INVALID) {
            ToastUtils.showToast(errorMsg);
            callOnTokenInvalid();
        } else {
            LogManagerProvider.d(TAG, errorMsg);
            callOnResponseError(code, errorMsg);
            mListener.onFailure(errorMsg);
        }
        return false;
    }

    /**
     * 设置上下文
     */
    public void setContextRef(Context context) {
        mContextRef = new WeakReference<>(context);
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
    protected abstract void callOnResponseError(int code, String errorMsg);

    /**
     * 当token失效重新获取后，由子类重写调用进行再次网络请求
     *
     * @param paramMap 网络请求参数集合
     */
    protected abstract void reRequest(Map<String, String> paramMap);

}
