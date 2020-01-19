package com.mingyuechunqiu.agileproject.feature.main;

import com.mingyuechunqiu.agile.base.framework.IBaseListener;
import com.mingyuechunqiu.agile.base.model.BaseAbstractDataModel;
import com.mingyuechunqiu.agile.base.model.dao.framework.callback.remote.DaoRetrofitCallback;
import com.mingyuechunqiu.agile.base.model.dao.remote.BaseAbstractRetrofitDao;
import com.mingyuechunqiu.agile.base.presenter.BaseAbstractDataPresenter;
import com.mingyuechunqiu.agile.base.view.IBaseDataView;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/11/8
 *     desc   : 主界面相关契约类，约定相互能实现调用的api
 *     version: 1.0
 * </pre>
 */
interface MainContract {

    interface View<P extends Presenter<?, ?>> extends IBaseDataView<P> {
    }

    interface Listener extends IBaseListener {
    }

    abstract class Dao<C extends DaoRetrofitCallback<Listener>> extends BaseAbstractRetrofitDao<C>{

    }

    abstract class Model<I extends Listener> extends BaseAbstractDataModel<I> {
        public Model(I listener) {
            super(listener);
        }
    }

    abstract class Presenter<V extends View<?>, M extends Model<?>> extends BaseAbstractDataPresenter<V, M> {
    }

}
