package com.mingyuechunqiu.agile.base.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.bridge.Request;
import com.mingyuechunqiu.agile.base.bridge.call.Call;
import com.mingyuechunqiu.agile.base.model.modelpart.IBaseModelPart;
import com.mingyuechunqiu.agile.base.model.repository.IBaseRepository;
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;

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

    private final String TAG = getClass().getSimpleName();//打印日志标签

    @Nullable
    private List<IBaseModelPart> mModelPartList = null;
    //Repository映射集合，一个Repository可以响应多个Request请求
    @Nullable
    private Map<IBaseRepository, Set<String>> mRepositoryMap = null;

    public BaseAbstractModel() {
        initOnModelParts();
        initOnRepositories();
    }

    @Override
    public void callOnStart() {
        LogManagerProvider.i(TAG, "callOnStart");
        onStart();
    }

    @Override
    public void callOnPause() {
        LogManagerProvider.i(TAG, "callOnPause");
        onPause();
    }

    @Override
    public void callOnResume() {
        LogManagerProvider.i(TAG, "callOnResume");
        onResume();
    }

    @Override
    public void callOnStop() {
        LogManagerProvider.i(TAG, "callOnStop");
        onStop();
    }

    /**
     * 添加模型层part单元（同步方法）
     *
     * @param part part单元模块
     * @return 如果添加成功返回true，否则返回false
     */
    @Override
    public synchronized boolean addModelPart(@NonNull IBaseModelPart part) {
        LogManagerProvider.i(TAG, "addModelPart");
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
        LogManagerProvider.i(TAG, "removeModelPart");
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
        LogManagerProvider.i(TAG, "addRepository");
        List<String> requestTags = new ArrayList<>();
        requestTags.add(Request.Tag.TAG_DEFAULT_REQUEST);
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
        LogManagerProvider.i(TAG, "addRepository requestTags");
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
        LogManagerProvider.i(TAG, "removeRepository");
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
        LogManagerProvider.i(TAG, "dispatchCall");
        if (dispatchCallWithCustom(call)) {
            return true;
        }
        boolean result = dispatchCallInternal(call);
        if (!result) {
            LogManagerProvider.e(TAG, "dispatchCall method found no matched repository to handle call!");
        }
        return result;
    }

    /**
     * 释放资源方法
     */
    @Override
    public void releaseOnDetach() {
        LogManagerProvider.i(TAG, "releaseOnDetach");
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

    protected void initOnModelParts() {
        LogManagerProvider.i(TAG, "initOnModelParts");
        initModelParts();
    }

    protected void initOnRepositories() {
        LogManagerProvider.i(TAG, "initOnRepositories");
        initRepositories();
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
        LogManagerProvider.i(TAG, "dispatchCallWithCustom");
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
        LogManagerProvider.i(TAG, "dispatchCallWithRepositoryMap");
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
        LogManagerProvider.i(TAG, "dispatchCallInternal");
        if (dispatchCallWithRepositoryMap(getModelPartRepositoryMap(), call)) {
            return true;
        }
        return dispatchCallWithRepositoryMap(getRepositoryMap(), call);
    }

    protected void onStart() {
    }

    protected void onResume() {
    }

    protected void onPause() {
    }

    protected void onStop() {
    }

    /**
     * 销毁资源
     */
    protected abstract void release();
}
