package com.mingyuechunqiu.agile.base.model.part;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.bridge.Request;
import com.mingyuechunqiu.agile.base.model.dao.IBaseDao;

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

    //Dao映射集合，一个Dao可以响应多个Request请求
    @Nullable
    private Map<IBaseDao<?>, Set<String>> mDaoMap;

    @NonNull
    @Override
    public Map<IBaseDao<?>, Set<String>> getDaoMap() {
        if (mDaoMap == null) {
            synchronized (this) {
                if (mDaoMap == null) {
                    mDaoMap = new ConcurrentHashMap<>();
                }
            }
        }
        return mDaoMap;
    }

    @Override
    public void releaseOnDetach() {
        release();
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

    /**
     * 添加Dao层单元
     *
     * @param dao dao单元
     * @return 如果添加成功返回true，否则返回false
     */
    @Override
    public boolean addDao(@Nullable IBaseDao<?> dao) {
        if (dao == null) {
            return false;
        }
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

    /**
     * 销毁资源
     */
    protected abstract void release();
}
