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
        initializeModelParts();
    }

    @Override
    public void initRepositories() {
        initializeRepositories();
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
    public <I extends Request.IParamsInfo, T> boolean dispatchCall(@NonNull Call<I, T> call) {
        if (dispatchCallWithCustom(call)) {
            return true;
        }
        return dispatchCallInternal(call);
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
     * @param <I>  请求泛型类型
     * @param <T>  响应泛型类型
     * @return 请求已处理返回true，否则返回false
     */
    protected <I extends Request.IParamsInfo, T> boolean dispatchCallWithCustom(@NonNull Call<I, T> call) {
        return false;
    }

    /**
     * 根据仓库映射分发调用
     *
     * @param map  仓库映射
     * @param call 调用对象
     * @param <I>  请求泛型类型
     * @param <T>  响应泛型类型
     * @return 请求已处理返回true，否则返回false
     */
    private <I extends Request.IParamsInfo, T> boolean dispatchCallWithRepositoryMap(@NonNull Map<IBaseRepository, Set<String>> map, @NonNull Call<I, T> call) {
        for (Map.Entry<IBaseRepository, Set<String>> entry : map.entrySet()) {
            if (entry.getValue().contains(call.getRequest().getRequestTag())) {
                entry.getKey().dispatchCall(call);
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
     * 内部分发调用逻辑
     *
     * @param call 调用对象
     * @param <I>  请求泛型类型
     * @param <T>  响应泛型类型
     * @return 请求已处理返回true，否则返回false
     */
    private <I extends Request.IParamsInfo, T> boolean dispatchCallInternal(@NonNull Call<I, T> call) {
        if (dispatchCallWithRepositoryMap(getModelPartRepositoryMap(), call)) {
            return true;
        }
        return dispatchCallWithRepositoryMap(getRepositoryMap(), call);
    }

    /**
     * 供子类初始化ModelPart
     */
    protected abstract void initializeModelParts();

    /**
     * 供子类初始化Repository
     */
    protected abstract void initializeRepositories();

    /**
     * 销毁资源
     */
    protected abstract void release();
}
