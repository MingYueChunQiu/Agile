package com.mingyuechunqiu.agileproject.base.model;

import com.mingyuechunqiu.agile.base.framework.IBaseListener;
import com.mingyuechunqiu.agile.base.model.BaseTokenNetModel;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/11/7
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseTokenNetModelImpl<I extends IBaseListener> extends BaseTokenNetModel<I> {
    public BaseTokenNetModelImpl(I listener) {
        super(listener);
    }

    @Override
    protected void callOnTokenOverdue() {

    }

    @Override
    protected void callOnTokenInvalid() {
        //当token失效时，打开登录界面

    }

    @Override
    protected void callOnTokenError() {
    }
}
