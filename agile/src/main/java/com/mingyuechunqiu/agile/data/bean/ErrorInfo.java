package com.mingyuechunqiu.agile.data.bean;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

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

    private Builder mBuilder;

    public ErrorInfo(@NonNull String errorMsg) {
        this(new Builder().setErrorMsg(errorMsg));
    }

    public ErrorInfo(@StringRes int errorMsgResId) {
        this(new Builder().setErrorMsgResId(errorMsgResId));
    }

    public ErrorInfo(@NonNull Builder builder) {
        mBuilder = builder;
    }

    public int getRequestType() {
        return mBuilder.requestType;
    }

    public void setRequestType(int requestType) {
        mBuilder.requestType = requestType;
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

        private int requestType;//请求类型（默认-1）
        private String errorMsg;//错误信息
        private @StringRes
        int errorMsgResId;//错误信息资源ID

        public Builder(@NonNull String errorMsg) {
            this();
            setErrorMsg(errorMsg);
        }

        public Builder(@StringRes int errorMsgResId) {
            this();
            setErrorMsgResId(errorMsgResId);
        }

        private Builder() {
            requestType = -1;
        }

        public ErrorInfo build() {
            return new ErrorInfo(this);
        }

        public int getRequestType() {
            return requestType;
        }

        public Builder setRequestType(int requestType) {
            this.requestType = requestType;
            return this;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public Builder setErrorMsg(@NonNull String errorMsg) {
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
