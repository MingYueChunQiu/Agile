package com.mingyuechunqiu.agile.base.presenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.bridge.Request;
import com.mingyuechunqiu.agile.base.bridge.call.Call;
import com.mingyuechunqiu.agile.base.model.IBaseModel;
import com.mingyuechunqiu.agile.base.view.IBaseView;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *      Email:      xiyujieit@163.com
 *      Time:       2023/2/19 10:12
 *      Desc:       不依赖于数据层的孤立的展示者层，适用于纯粹视图逻辑模块
 *                  继承自BaseAbstractPresenter
 *      Version:    1.0
 * </pre>
 */
public abstract class BaseIsolatedPresenter extends BaseAbstractPresenter<IBaseView, IBaseModel> {

    @Nullable
    @Override
    public IBaseModel initModel() {
        return null;
    }

    @Override
    protected <I extends Request.IParamsInfo, T> boolean dispatchCallWithModel(@NonNull Call<I, T> call) {
        return false;
    }
}
