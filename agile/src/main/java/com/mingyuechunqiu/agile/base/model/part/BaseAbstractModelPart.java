package com.mingyuechunqiu.agile.base.model.part;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.bridge.Request;
import com.mingyuechunqiu.agile.base.model.repository.IBaseRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/3/14
 *     desc   : 所有ModelPart的抽象父类
 *              继承自IBaseModelPart
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractModelPart implements IBaseModelPart {

    //Repository映射集合，一个Repository可以响应多个Request请求
    @Nullable
    private Map<IBaseRepository, Set<String>> mRepositoryMap;

    @NonNull
    @Override
    public Map<IBaseRepository, Set<String>> getRepositoryMap() {
        if (mRepositoryMap == null) {
            synchronized (this) {
                if (mRepositoryMap == null) {
                    mRepositoryMap = new ConcurrentHashMap<>();
                }
            }
        }
        return mRepositoryMap;
    }

    @Override
    public void releaseOnDetach() {
        release();
        if (mRepositoryMap != null) {
            for (IBaseRepository repository : mRepositoryMap.keySet()) {
                if (repository != null) {
                    repository.releaseOnDetach();
                }
            }
            mRepositoryMap.clear();
            mRepositoryMap = null;
        }
    }

    /**
     * 添加Repository层单元
     *
     * @param repository Repository单元
     * @return 如果添加成功返回true，否则返回false
     */
    @Override
    public boolean addRepository(@Nullable IBaseRepository repository) {
        if (repository == null) {
            return false;
        }
        List<String> requestTags = new ArrayList<>();
        requestTags.add(Request.DEFAULT_KEY_REQUEST_TAG);
        return addRepository(repository, requestTags);
    }

    /**
     * 添加Repository层单元（同步方法）
     *
     * @param repository Repository单元
     * @return 如果添加成功返回true，否则返回false
     */
    @Override
    public synchronized boolean addRepository(@NonNull IBaseRepository repository, @NonNull List<String> requestTags) {
        Set<String> originalRequestTags = getRepositoryMap().get(repository);
        if (originalRequestTags == null) {
            originalRequestTags = new HashSet<>();
        }
        originalRequestTags.addAll(requestTags);
        return getRepositoryMap().put(repository, originalRequestTags) != null;
    }

    /**
     * 删除Repository单元
     *
     * @param repository Repository单元
     * @return 如果删除成功返回true，否则返回false
     */
    @Override
    public boolean removeRepository(@Nullable IBaseRepository repository) {
        if (repository == null || mRepositoryMap == null) {
            return false;
        }
        return getRepositoryMap().remove(repository) != null;
    }

    /**
     * 销毁资源
     */
    protected abstract void release();
}
