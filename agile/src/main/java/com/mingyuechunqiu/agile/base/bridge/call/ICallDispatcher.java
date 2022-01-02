package com.mingyuechunqiu.agile.base.bridge.call;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.base.bridge.Request;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/15 7:09 下午
 *      Desc:       调用分发者接口
 *      Version:    1.0
 * </pre>
 */
public interface ICallDispatcher {

    /**
     * 分发调用
     *
     * @param call 调用对象
     * @param <I>  请求泛型类型
     * @param <T>  响应泛型类型
     * @return 成功执行请求返回true，否则返回false
     */
    <I extends Request.IParamsInfo, T> boolean dispatchCall(@NonNull Call<I, T> call);
}
