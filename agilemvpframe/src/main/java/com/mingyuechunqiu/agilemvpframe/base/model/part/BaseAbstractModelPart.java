package com.mingyuechunqiu.agilemvpframe.base.model.part;

import com.mingyuechunqiu.agilemvpframe.base.dao.IDao;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/3/14
 *     desc   : 所有ModelPart的抽象父类
 *              继承自IModelPart
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractModelPart implements IModelPart {

    protected List<IDao> mDaoList;

    @Override
    public void release() {
        destroy();
        if (mDaoList != null) {
            for (IDao dao : mDaoList) {
                if (dao != null) {
                    dao.release();
                }
            }
            mDaoList.clear();
            mDaoList = null;
        }
    }

    /**
     * 添加Dao层单元
     *
     * @param dao dao单元
     * @return 如果添加成功返回true，否则返回false
     */
    protected boolean addDao(IDao dao) {
        if (dao == null) {
            return false;
        }
        if (mDaoList == null) {
            mDaoList = new ArrayList<>();
        }
        return mDaoList.add(dao);
    }

    /**
     * 删除dao单元
     *
     * @param dao dao单元
     * @return 如果删除成功返回true，否则返回false
     */
    protected boolean removeDao(IDao dao) {
        if (dao == null || mDaoList == null) {
            return false;
        }
        return mDaoList.remove(dao);
    }

    /**
     * 销毁资源
     */
    protected abstract void destroy();
}
