package com.mingyuechunqiu.agile.base.model;

import com.mingyuechunqiu.agile.R;
import com.mingyuechunqiu.agile.base.framework.IBaseListener;
import com.mingyuechunqiu.agile.base.model.part.dao.IDao;
import com.mingyuechunqiu.agile.base.model.part.IModelPart;
import com.mingyuechunqiu.agile.data.bean.BaseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/11/19
 *     desc   : 所有Model层的抽象基类
 *              实现BaseModel
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractModel<I extends IBaseListener> implements IBaseModel<I> {

    protected final String TAG = getClass().getSimpleName();//日志标签
    protected final String TAG_FAILURE = getClass().getSimpleName() + " failure";//打印错误日志标签

    protected I mListener;
    protected List<IModelPart> mModelPartList;
    protected List<IDao> mDaoList;

    public BaseAbstractModel(I listener) {
        attachListener(listener);
    }

    @Override
    public void attachListener(I listener) {
        mListener = listener;
        onAttachListener(listener);
    }

    @Override
    public void start() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    /**
     * 释放资源方法
     */
    @Override
    public void release() {
        destroy();
        mListener = null;
        if (mModelPartList != null) {
            for (IModelPart part : mModelPartList) {
                if (part != null) {
                    part.release();
                }
            }
            mModelPartList.clear();
            mModelPartList = null;
        }
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
     * 设置网络请求参数对象，进行网络请求
     *
     * @param info 网络请求参数对象
     */
    public void setParamsInfo(BaseInfo info) {
        if (mListener == null) {
            throw new IllegalArgumentException("Listener has not been set!");
        }
        if (info == null) {
            mListener.onFailure(R.string.agile_error_set_net_params);
            return;
        }
        getRequest(info);
    }

    /**
     * 添加模型层part单元
     *
     * @param part part单元模块
     * @return 如果添加成功返回true，否则返回false
     */
    protected boolean addModelPart(IModelPart part) {
        if (part == null) {
            return false;
        }
        if (mModelPartList == null) {
            mModelPartList = new ArrayList<>();
        }
        return mModelPartList.add(part);
    }

    /**
     * 删除指定的模型层part单元
     *
     * @param part part单元模块
     * @return 如果删除成功返回true，否则返回false
     */
    protected boolean removeModelPart(IModelPart part) {
        if (part == null || mModelPartList == null) {
            return false;
        }
        return mModelPartList.remove(part);
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
     * 当和监听器关联时回调
     *
     * @param listener 监听器
     */
    protected void onAttachListener(I listener) {
    }

    /**
     * 由子类重写进行网络请求
     *
     * @param info 网络请求参数对象
     */
    protected abstract void getRequest(BaseInfo info);

    /**
     * 销毁资源
     */
    protected abstract void destroy();
}
