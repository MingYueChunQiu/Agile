package com.mingyuechunqiu.agileproject.feature.function;

import com.mingyuechunqiu.agile.base.model.BaseAbstractDataModel;
import com.mingyuechunqiu.agile.base.model.dao.remote.BaseAbstractRetrofitDao;
import com.mingyuechunqiu.agile.base.model.framework.callback.remote.DaoRetrofitCallback;
import com.mingyuechunqiu.agile.base.presenter.BaseAbstractDataPresenter;
import com.mingyuechunqiu.agile.base.view.IBaseDataView;

/**
 * <pre>
 *      Project:    Agile
 *      Github :    https://github.com/MingYueChunQiu
 *      Author:     XiYuJie
 *      Email:      xiyujie@jinying.com
 *      Time:       2020/1/15 14:57
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
interface FunctionContract {

    interface View extends IBaseDataView {

        void doSomeTest();
    }

    abstract class Dao<C extends DaoRetrofitCallback> extends BaseAbstractRetrofitDao<C> {
    }

    abstract class Model extends BaseAbstractDataModel {
    }

    abstract class Presenter<V extends View, M extends Model> extends BaseAbstractDataPresenter<V, M> {

    }
}
