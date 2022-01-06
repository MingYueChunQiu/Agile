package com.mingyuechunqiu.agile.data.bean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.mingyuechunqiu.agile.base.bridge.Request;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-12-14 11:37
 *      Desc:       错误信息类
 *      Version:    1.0
 * </pre>
 */
public class ErrorInfo {

    private final Builder mBuilder;

    public ErrorInfo(@Nullable String errorMsg) {
        this(new Builder().setErrorMsg(errorMsg));
    }

    public ErrorInfo(@StringRes int errorMsgResId) {
        this(new Builder().setErrorMsgResId(errorMsgResId));
    }

    public ErrorInfo(@NonNull Builder builder) {
        mBuilder = builder;
    }

    @NonNull
    public Request.RequestCategory getRequestType() {
        return mBuilder.requestCategory;
    }

    public void setRequestType(@NonNull Request.RequestCategory requestType) {
        mBuilder.requestCategory = requestType;
    }

    @NonNull
    public String getRequestTag() {
        return mBuilder.requestTag;
    }

    public void setRequestTag(@NonNull String requestTag) {
        mBuilder.requestTag = requestTag;
    }

    public String getErrorMsg() {
        return mBuilder.errorMsg;
    }

    public void setErrorMsg(@NonNull String errorMsg) {
        mBuilder.errorMsg = errorMsg;
    }

    public int getErrorMsgResId() {
        return mBuilder.errorMsgResId;
    }

    public void setErrorMsgResId(@StringRes int errorMsgResId) {
        mBuilder.errorMsgResId = errorMsgResId;
    }

    /**
     * 链式调用
     */
    public static class Builder {

        @NonNull
        private Request.RequestCategory requestCategory = Request.RequestCategory.CATEGORY_NOT_SET;//请求类型（默认-1）
        @NonNull
        private String requestTag = Request.Tag.TAG_NOT_SET;//请求标记
        private String errorMsg;//错误信息
        private @StringRes
        int errorMsgResId;//错误信息资源ID

        public Builder(@Nullable String errorMsg) {
            this();
            setErrorMsg(errorMsg);
        }

        public Builder(@StringRes int errorMsgResId) {
            this();
            setErrorMsgResId(errorMsgResId);
        }

        private Builder() {
        }

        public ErrorInfo build() {
            return new ErrorInfo(this);
        }

        @NonNull
        public Request.RequestCategory getRequestCategory() {
            return requestCategory;
        }

        public Builder setRequestCategory(@NonNull Request.RequestCategory requestCategory) {
            this.requestCategory = requestCategory;
            return this;
        }

        @NonNull
        public String getRequestTag() {
            return requestTag;
        }

        public Builder setRequestTag(@NonNull String requestTag) {
            this.requestTag = requestTag;
            return this;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public Builder setErrorMsg(@Nullable String errorMsg) {
            this.errorMsg = errorMsg;
            return this;
        }

        public int getErrorMsgResId() {
            return errorMsgResId;
        }

        public Builder setErrorMsgResId(int errorMsgResId) {
            this.errorMsgResId = errorMsgResId;
            return this;
        }
    }
}
