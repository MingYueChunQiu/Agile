package com.mingyuechunqiu.agile.base.bridge.call;

import androidx.annotation.NonNull;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/15 7:09 下午
 *      Desc:       调用执行者接口
 *      Version:    1.0
 * </pre>
 */
public interface ICallExecutor {

    /**
     * 执行一次调用
     *
     * @param call 调用对象
     * @param <T>  响应数据类型
     * @return 执行请求返回true，否则返回false
     */
    <T> boolean executeCall(@NonNull Call<T> call);
}
