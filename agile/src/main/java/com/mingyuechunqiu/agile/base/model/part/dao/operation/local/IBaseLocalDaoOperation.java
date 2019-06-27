package com.mingyuechunqiu.agile.base.model.part.dao.operation.local;

import com.mingyuechunqiu.agile.base.model.part.dao.operation.IBaseDaoOperation;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/26
 *     desc   : 本地操作父接口
 *              继承自IBaseDaoOperation
 *     version: 1.0
 * </pre>
 */
public interface IBaseLocalDaoOperation extends IBaseDaoOperation {

    /**
     * 操作是否是无效的
     *
     * @return 如果无效返回true，否则返回false
     */
    boolean isInvalid();

    /**
     * 清理资源
     */
    void clear();
}
