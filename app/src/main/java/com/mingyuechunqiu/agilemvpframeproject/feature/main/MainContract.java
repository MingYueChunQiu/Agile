package com.mingyuechunqiu.agilemvpframeproject.feature.main;

import com.mingyuechunqiu.agilemvpframe.base.framework.IBaseListener;
import com.mingyuechunqiu.agilemvpframe.base.presenter.BaseDialogPresenter;
import com.mingyuechunqiu.agilemvpframe.base.view.BaseDialogView;
import com.mingyuechunqiu.agilemvpframeproject.base.model.BaseTokenNetModelImpl;

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

    interface View<P extends Presenter> extends BaseDialogView<P> {
    }

    interface Listener extends IBaseListener {
    }

    abstract class Model<I extends Listener> extends BaseTokenNetModelImpl<I> {
        public Model(I listener) {
            super(listener);
        }
    }

    abstract class Presenter<V extends View, M extends Model> extends BaseDialogPresenter<V, M> {
    }

}
