package com.mingyuechunqiu.agile.base.bridge.call;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.base.bridge.Callback;
import com.mingyuechunqiu.agile.base.bridge.Request;
import com.mingyuechunqiu.agile.base.model.repository.operation.IBaseRepositoryOperation;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2022/1/2 5:15 下午
 *      Desc:       调用执行者接口
 *      Version:    1.0
 * </pre>
 */
public interface ICallExecutor {

    /**
     * 执行一次调用
     *
     * @param call 调用对象
     * @param <I>  请求泛型类型
     * @param <T>  响应泛型类型
     * @param <R>  用于转换的请求泛型类型
     * @param <C>  用户转换的响应泛型类型
     * @param <O>  操作泛型类型
     */
    <I extends Request.IParamsInfo, T, R extends Request.IParamsInfo, C, O> void executeCall(@NonNull Call<I, T> call, @NonNull ExecuteCallCallback<R, C, O> callback);

    interface ExecuteCallCallback<I extends Request.IParamsInfo, T, O> {

        IBaseRepositoryOperation<O> executeCall(@NonNull Request<I> request, @NonNull Callback<T> callback);
    }
}
