package com.mingyuechunqiu.agile.base.model;

import com.mingyuechunqiu.agile.base.bridge.call.ICallExecutor;
import com.mingyuechunqiu.agile.base.model.repository.IRepositoryOwner;
import com.mingyuechunqiu.agile.base.model.modelpart.IModelPartOwner;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/16
 *     desc   : 所有业务模型的父接口
 *     version: 1.0
 * </pre>
 */
public interface IBaseModel extends IModelPartOwner, IRepositoryOwner, ICallExecutor {

    void callOnStart();

    void callOnResume();

    void callOnPause();

    void callOnStop();

    /**
     * 释放资源
     */
    void releaseOnDetach();

    /**
     * 初始化ModelPart资源
     */
    void initModelParts();

    /**
     * 初始化Repository资源
     */
    void initRepositories();

}
