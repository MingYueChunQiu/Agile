package com.mingyuechunqiu.agilemvpframe.base.model;

import com.mingyuechunqiu.agilemvpframe.R;
import com.mingyuechunqiu.agilemvpframe.base.framework.IBaseListener;
import com.mingyuechunqiu.agilemvpframe.base.model.part.IModelPart;
import com.mingyuechunqiu.agilemvpframe.data.bean.BaseInfo;

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
public abstract class BaseAbstractModel<I extends IBaseListener> implements IBaseModel {

    protected final String TAG = getClass().getSimpleName();//日志标签
    protected final String TAG_FAILURE = getClass().getSimpleName() + " failure";//打印错误日志标签

    protected I mListener;
    protected List<IModelPart> mModelPartList;

    public BaseAbstractModel(I listener) {
        mListener = listener;
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
                    part.destroy();
                }
            }
            mModelPartList.clear();
            mModelPartList = null;
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
            mListener.onFailure(R.string.error_set_net_params);
            return;
        }
        getRequest(info);
    }

    /**
     * 添加模型层part单元
     *
     * @param part part单元模块
     */
    protected void addModelPart(IModelPart part) {
        if (part == null) {
            return;
        }
        if (mModelPartList == null) {
            mModelPartList = new ArrayList<>();
        }
        mModelPartList.add(part);
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
