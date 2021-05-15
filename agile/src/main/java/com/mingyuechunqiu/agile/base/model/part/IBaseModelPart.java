package com.mingyuechunqiu.agile.base.model.part;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.base.model.dao.IBaseDao;
import com.mingyuechunqiu.agile.base.model.dao.IDaoOwner;

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
public interface IBaseModelPart extends IDaoOwner {

    @NonNull
    Map<IBaseDao<?>, Set<String>> getDaoMap();

    /**
     * 释放资源
     */
    void releaseOnDetach();
}
