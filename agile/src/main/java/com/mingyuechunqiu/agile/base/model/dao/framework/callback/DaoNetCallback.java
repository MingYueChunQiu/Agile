package com.mingyuechunqiu.agile.base.model.dao.framework.callback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.mingyuechunqiu.agile.base.framework.IBaseListener;
import com.mingyuechunqiu.agile.data.bean.ErrorInfo;

/**
 * <pre>
 *      Project:    Agile
 *      Github :    https://github.com/MingYueChunQiu
 *      Author:     XiYuJie
 *      Email:      xiyujie@jinying.com
 *      Time:       2020/1/15 13:53
 *      Desc:       网络功能Dao层回调接口
 *                  继承自DaoCallback
 *      Version:    1.0
 * </pre>
 */
public interface DaoNetCallback<I extends IBaseListener> extends DaoCallback<I> {

    /**
     * 根据网络响应返回码，进行不同处理
     *
     * @param code     网络响应返回码
     * @param errorMsg 网络请求错误信息
     * @return 返回true表示响应成功，否则返回false失败
     */
    boolean handleNetworkResponseCode(int code, @Nullable String errorMsg);

    /**
     * 处理Retrofit网络响应失败事件
     *
     * @param t                抛出的异常
     * @param errorStringResId 错误提示字符串资源ID
     */
    void onNetworkResponseFailed(@Nullable Throwable t, @StringRes int errorStringResId);

    /**
     * 处理网络响应失败
     *
     * @param t    抛出的异常
     * @param info 错误信息对象
     */
    void onNetworkResponseFailed(@Nullable Throwable t, @NonNull ErrorInfo info);
}
