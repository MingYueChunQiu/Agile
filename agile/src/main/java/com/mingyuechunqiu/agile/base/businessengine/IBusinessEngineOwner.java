package com.mingyuechunqiu.agile.base.businessengine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/12/30 11:32 下午
 *      Desc:       业务引擎拥有者接口
 *      Version:    1.0
 * </pre>
 */
public interface IBusinessEngineOwner {

    /**
     * 添加模型层engine单元
     *
     * @param engine engine单元模块
     * @return 如果添加成功返回true，否则返回false
     */
    boolean addBusinessEngine(@NonNull IBaseBusinessEngine engine);

    /**
     * 删除指定的模型层engine单元
     *
     * @param engine engine单元模块
     * @return 如果删除成功返回true，否则返回false
     */
    boolean removeBusinessEngine(@Nullable IBaseBusinessEngine engine);

    @NonNull
    List<IBaseBusinessEngine> getBusinessEngineList();
}
