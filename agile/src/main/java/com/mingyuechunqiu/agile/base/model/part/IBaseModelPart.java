package com.mingyuechunqiu.agile.base.model.part;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.base.model.repository.IBaseRepository;
import com.mingyuechunqiu.agile.base.model.repository.IRepositoryOwner;

import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/3/4
 *     desc   : 所有Model层中Part单元接口
 *              继承自IDaoOwner
 *     version: 1.0
 * </pre>
 */
public interface IBaseModelPart extends IRepositoryOwner {

    @NonNull
    Map<IBaseRepository<?>, Set<String>> getRepositoryMap();

    /**
     * 释放资源
     */
    void releaseOnDetach();
}
