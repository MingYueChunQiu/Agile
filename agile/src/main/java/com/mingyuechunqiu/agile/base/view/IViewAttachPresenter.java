package com.mingyuechunqiu.agile.base.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.model.IBaseModel;
import com.mingyuechunqiu.agile.base.presenter.IBasePresenter;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/6/19
 *     desc   : View层关联Presenter层标准接口
 *     version: 1.0
 * </pre>
 */
public interface IViewAttachPresenter<P extends IBasePresenter<? extends IBaseView, ? extends IBaseModel>> {

    /**
     * 关联添加Present
     */
    void attachPresenter();

    /**
     * 绑定Presenter
     *
     * @param presenter 外部传入的Presenter
     */
    void bindPresenter(@NonNull P presenter);

    /**
     * 设置Presenter
     *
     * @param presenter 需要设置的Presenter
     */
    void setPresenter(@NonNull P presenter);

    /**
     * 初始化Presenter
     *
     * @return 返回设置好的Presenter
     */
    @Nullable
    P initPresenter();

    /**
     * 获取Presenter
     *
     * @return 返回Presenter，如果为空返回null
     */
    @Nullable
    P getPresenter();
}
