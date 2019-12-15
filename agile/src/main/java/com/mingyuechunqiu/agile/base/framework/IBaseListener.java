package com.mingyuechunqiu.agile.base.framework;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.data.bean.ErrorInfo;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/17
 *     desc   : 所有回调信息的父接口
 *     version: 1.0
 * </pre>
 */
public interface IBaseListener {

    /**
     * 发生错误时回调
     *
     * @param info 错误信息对象
     */
    void onFailure(@NonNull ErrorInfo info);

}
