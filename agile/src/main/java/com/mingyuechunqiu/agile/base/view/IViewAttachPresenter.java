package com.mingyuechunqiu.agile.base.view;

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
     * 是否单独使用View，不绑定Presenter（默认返回false）
     *
     * @return 返回true表示只用View不绑定Presenter，否则返回false
     */
    boolean aloneView();

    /**
     * 关联添加Present
     */
    void attachPresenter();

    /**
     * 绑定Presenter
     *
     * @param outside   Presenter是否是从外部传入
     * @param presenter 外部传入的Presenter
     */
    void bindPresenter(boolean outside, P presenter);

    /**
     * 初始化Presenter
     *
     * @return 返回设置好的Presenter
     */
    P initPresenter();
}
