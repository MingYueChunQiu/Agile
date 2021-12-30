package com.mingyuechunqiu.agile.base.model;

import com.mingyuechunqiu.agile.base.model.framework.data.local.IModelLocalDataProcessor;
import com.mingyuechunqiu.agile.base.model.framework.data.remote.IModelNetworkDataProcessor;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/17
 *     desc   : 所有网络Model层的抽象基类
 *              继承自BaseAbstractModel
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractDataModel extends BaseAbstractModel implements IModelLocalDataProcessor, IModelNetworkDataProcessor {
}
