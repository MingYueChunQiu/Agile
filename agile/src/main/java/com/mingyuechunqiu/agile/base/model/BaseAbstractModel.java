package com.mingyuechunqiu.agile.base.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.bridge.Request;
import com.mingyuechunqiu.agile.base.bridge.call.Call;
import com.mingyuechunqiu.agile.base.model.dao.IBaseDao;
import com.mingyuechunqiu.agile.base.model.part.IBaseModelPart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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

    public BaseAbstractModel() {
        initModelPart();
        initDao();
    }

    @Nullable
    private List<IBaseModelPart> mModelPartList;
    //Dao映射集合，一个Dao可以响应多个Request请求
    @Nullable
    private Map<IBaseDao<?>, Set<String>> mDaoMap;

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
        if (part == null || mModelPartList == null) {
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
     * 添加Dao层单元
     *
     * @param dao dao单元
     * @return 如果添加成功返回true，否则返回false
     */
    @Override
    public boolean addDao(@NonNull IBaseDao<?> dao) {
        List<String> requestTags = new ArrayList<>();
        requestTags.add(Request.DEFAULT_KEY_REQUEST_TAG);
        return addDao(dao, requestTags);
    }

    /**
     * 添加Dao层单元（同步方法）
     *
     * @param dao dao单元
     * @return 如果添加成功返回true，否则返回false
     */
    @Override
    public synchronized boolean addDao(@NonNull IBaseDao<?> dao, @NonNull List<String> requestTags) {
        Set<String> originalRequestTags = getDaoMap().get(dao);
        if (originalRequestTags == null) {
            originalRequestTags = new HashSet<>();
        }
        originalRequestTags.addAll(requestTags);
        return getDaoMap().put(dao, originalRequestTags) != null;
    }

    /**
     * 删除dao单元
     *
     * @param dao dao单元
     * @return 如果删除成功返回true，否则返回false
     */
    @Override
    public boolean removeDao(@Nullable IBaseDao<?> dao) {
        if (dao == null || mDaoMap == null) {
            return false;
        }
        return getDaoMap().remove(dao) != null;
    }

    @NonNull
    @Override
    public List<IBaseDao<?>> getDaoList() {
        return new ArrayList<>(getDaoMap().keySet());
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
        if (mDaoMap != null) {
            for (IBaseDao<?> dao : mDaoMap.keySet()) {
                if (dao != null) {
                    dao.releaseOnDetach();
                }
            }
            mDaoMap.clear();
            mDaoMap = null;
        }
    }

    private <T> boolean executeCallWithDaoMap(@NonNull Map<IBaseDao<?>, Set<String>> map, @NonNull Call<T> call) {
        for (Map.Entry<IBaseDao<?>, Set<String>> entry : map.entrySet()) {
            if (entry.getValue().contains(call.getRequest().getRequestTag())) {
                entry.getKey().executeCall(call);
                return true;
            }
        }
        return false;
    }

    @NonNull
    private Map<IBaseDao<?>, Set<String>> getModelPartDaoMap() {
        Map<IBaseDao<?>, Set<String>> daoMap = new HashMap<>();
        if (mModelPartList == null) {
            return daoMap;
        }
        for (IBaseModelPart part : mModelPartList) {
            daoMap.putAll(part.getDaoMap());
        }
        return daoMap;
    }

    @NonNull
    private Map<IBaseDao<?>, Set<String>> getDaoMap() {
        if (mDaoMap == null) {
            synchronized (this) {
                if (mDaoMap == null) {
                    mDaoMap = new ConcurrentHashMap<>();
                }
            }
        }
        return mDaoMap;
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

    /**
     * 内部执行调用逻辑
     *
     * @param call 调用对象
     * @param <T>  响应类型
     * @return 请求已处理返回true，否则返回false
     */
    private <T> boolean executeCallInternal(@NonNull Call<T> call) {
        if (executeCallWithDaoMap(getModelPartDaoMap(), call)) {
            return true;
        }
        if (mDaoMap == null) {
            return false;
        }
        return executeCallWithDaoMap(getDaoMap(), call);
    }

    public void initModelPart() {
    }

    public void initDao() {
    }

    /**
     * 销毁资源
     */
    protected abstract void release();
}
