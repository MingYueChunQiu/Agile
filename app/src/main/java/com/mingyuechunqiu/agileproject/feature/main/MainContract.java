package com.mingyuechunqiu.agileproject.feature.main;

import com.mingyuechunqiu.agile.base.model.BaseAbstractDataModel;
import com.mingyuechunqiu.agile.base.model.repository.remote.BaseAbstractRetrofitRepository;
import com.mingyuechunqiu.agile.base.model.framework.callback.remote.DaoRetrofitCallback;
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

    interface View extends IBaseDataView {
    }

    abstract class Repository<C extends DaoRetrofitCallback> extends BaseAbstractRetrofitRepository<C> {
    }

    abstract class Model extends BaseAbstractDataModel {
    }

    abstract class Presenter<V extends View, M extends Model> extends BaseAbstractDataPresenter<V, M> {
    }

}
