package com.mingyuechunqiu.agile.base.model.repository.operation.local;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.base.model.repository.operation.BaseAbstractRepositoryOperation;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 本地操作抽象基类
 *              实现IBaseLocalRepositoryOperation
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractLocalRepositoryOperation<T> extends BaseAbstractRepositoryOperation<T> implements IBaseLocalRepositoryOperationAbility {

    public BaseAbstractLocalRepositoryOperation(@NonNull T work) {
        super(work);
    }
}
