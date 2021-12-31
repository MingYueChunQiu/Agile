package com.mingyuechunqiu.agile.base.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.bridge.Request;
import com.mingyuechunqiu.agile.base.bridge.call.Call;
import com.mingyuechunqiu.agile.base.model.modelpart.IBaseModelPart;
import com.mingyuechunqiu.agile.base.model.repository.IBaseRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/11/19
 *     desc   : 所有Model层的抽象基类
 *              实现IBaseModel
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractModel implements IBaseModel {

    protected final String TAG_FAILURE = getClass().getSimpleName() + " failure";//打印错误日志标签

    @Nullable
    private List<IBaseModelPart> mModelPartList = null;
    //Repository映射集合，一个Repository可以响应多个Request请求
    @Nullable
    private Map<IBaseRepository, Set<String>> mRepositoryMap = null;

    public BaseAbstractModel() {
        initModelParts();
        initRepositories();
    }

    @Override
    public void callOnStart() {
    }

    @Override
    public void callOnPause() {
    }

    @Override
    public void callOnResume() {
    }

    @Override
    public void callOnStop() {
    }

    @Override
    public void initModelParts() {
    }

    @Override
    public void initRepositories() {
    }

    /**
     * 添加模型层part单元（同步方法）
     *
     * @param part part单元模块
     * @return 如果添加成功返回true，否则返回false
     */
    @Override
    public synchronized boolean addModelPart(@NonNull IBaseModelPart part) {
        return getModelPartList().add(part);
    }

    /**
     * 删除指定的模型层part单元
     *
     * @param part part单元模块
     * @return 如果删除成功返回true，否则返回false
     */
    @Override
    public boolean removeModelPart(@Nullable IBaseModelPart part) {
        if (part == null) {
            return false;
        }
        return getModelPartList().remove(part);
    }

    @NonNull
    @Override
    public List<IBaseModelPart> getModelPartList() {
        if (mModelPartList == null) {
            synchronized (this) {
                if (mModelPartList == null) {
                    mModelPartList = new ArrayList<>();
                }
            }
        }
        return mModelPartList;
    }

    /**
     * 添加Repository层单元
     *
     * @param repository Repository单元
     * @return 如果添加成功返回true，否则返回false
     */
    @Override
    public boolean addRepository(@NonNull IBaseRepository repository) {
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
        if (repository == null) {
            return false;
        }
        return getRepositoryMap().remove(repository) != null;
    }

    @NonNull
    @Override
    public List<IBaseRepository> getRepositoryList() {
        return new ArrayList<>(getRepositoryMap().keySet());
    }

    @Override
    public <T> boolean executeCall(@NonNull Call<T> call) {
        if (executeCallWithCustom(call)) {
            return true;
        }
        return executeCallInternal(call);
    }

    /**
     * 释放资源方法
     */
    @Override
    public void releaseOnDetach() {
        release();
        if (mModelPartList != null) {
            for (IBaseModelPart part : mModelPartList) {
                if (part != null) {
                    part.releaseOnDetach();
                }
            }
            mModelPartList.clear();
            mModelPartList = null;
        }
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
     * 用户自定义执行调用逻辑
     *
     * @param call 调用对象
     * @param <T>  响应对象类型
     * @return 请求已处理返回true，否则返回false
     */
    protected <T> boolean executeCallWithCustom(@NonNull Call<T> call) {
        return false;
    }

    private <T> boolean executeCallWithRepositoryMap(@NonNull Map<IBaseRepository, Set<String>> map, @NonNull Call<T> call) {
        for (Map.Entry<IBaseRepository, Set<String>> entry : map.entrySet()) {
            if (entry.getValue().contains(call.getRequest().getRequestTag())) {
                entry.getKey().executeCall(call);
                return true;
            }
        }
        return false;
    }

    @NonNull
    private Map<IBaseRepository, Set<String>> getModelPartRepositoryMap() {
        Map<IBaseRepository, Set<String>> repositoryMap = new HashMap<>();
        if (mModelPartList != null) {
            for (IBaseModelPart part : mModelPartList) {
                repositoryMap.putAll(part.getRepositoryMap());
            }
        }
        return repositoryMap;
    }

    @NonNull
    private Map<IBaseRepository, Set<String>> getRepositoryMap() {
        if (mRepositoryMap == null) {
            synchronized (this) {
                if (mRepositoryMap == null) {
                    mRepositoryMap = new HashMap<>();
                }
            }
        }
        return mRepositoryMap;
    }

    /**
     * 内部执行调用逻辑
     *
     * @param call 调用对象
     * @param <T>  响应类型
     * @return 请求已处理返回true，否则返回false
     */
    private <T> boolean executeCallInternal(@NonNull Call<T> call) {
        if (executeCallWithRepositoryMap(getModelPartRepositoryMap(), call)) {
            return true;
        }
        return executeCallWithRepositoryMap(getRepositoryMap(), call);
    }

    /**
     * 销毁资源
     */
    protected abstract void release();
}
