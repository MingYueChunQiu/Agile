package com.mingyuechunqiu.agile.base.model.repository.operation.remote;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.base.model.repository.operation.BaseAbstractRepositoryOperation;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 所有远程调用操作抽象父基类
 *              实现IBaseRemoteRepositoryOperation
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractRemoteRepositoryOperation<T> extends BaseAbstractRepositoryOperation<T> implements IBaseRemoteRepositoryOperationAbility {

    public BaseAbstractRemoteRepositoryOperation(@NonNull T work) {
        super(work);
    }
}
