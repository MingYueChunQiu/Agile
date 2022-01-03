package com.mingyuechunqiu.agile.base.bridge;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.constants.AgileCodeConstants;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2022/1/2 9:32 下午
 *      Desc:       调用响应类
 *      Version:    1.0
 * </pre>
 */
public final class Response<T> {

    private final int mCode;
    @NonNull
    private final T mData;

    private Response(int code, @NonNull T data) {
        mCode = code;
        mData = data;
    }

    public int getCode() {
        return mCode;
    }

    @NonNull
    public T getData() {
        return mData;
    }

    @NonNull
    public static <T> Response<T> success(@NonNull T data) {
        return new Response<>(AgileCodeConstants.CODE_SUCCESS, data);
    }

    @NonNull
    public static <T> Response<T> success(int code, @NonNull T data) {
        return new Response<>(code, data);
    }
}
