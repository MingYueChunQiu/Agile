package com.mingyuechunqiu.agile.base.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
public interface IViewAttachPresenter<P> {

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
     * 初始化Presenter
     *
     * @return 返回设置好的Presenter
     */
    @Nullable
    P initPresenter();
}
