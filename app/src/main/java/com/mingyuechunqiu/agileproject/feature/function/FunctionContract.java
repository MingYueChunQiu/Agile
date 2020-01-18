package com.mingyuechunqiu.agileproject.feature.function;

import com.mingyuechunqiu.agile.base.framework.IBaseListener;
import com.mingyuechunqiu.agile.base.model.BaseAbstractDataModel;
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

    interface View<P extends Presenter<?, ?>> extends IBaseDataView<P> {

        void doSomeTest();
    }

    interface Listener extends IBaseListener {

        void onGetTest();
    }

    abstract class Model<I extends Listener> extends BaseAbstractDataModel<I> {

        Model(I listener) {
            super(listener);
        }
    }

    abstract class Presenter<V extends View<?>, M extends Model<?>> extends BaseAbstractDataPresenter<V, M> {

    }
}
