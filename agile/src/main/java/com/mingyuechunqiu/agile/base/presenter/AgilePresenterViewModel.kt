package com.mingyuechunqiu.agile.base.presenter

import androidx.lifecycle.ViewModel
import com.mingyuechunqiu.agile.base.model.BaseAbstractModel
import com.mingyuechunqiu.agile.base.view.IBaseView

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *      Email:      xiyujieit@163.com
 *      Time:       2022/1/12 9:24 下午
 *      Desc:       用于储存Presenter的容器，确保Present的生命周期可以跟随界面变化
 *                  继承自ViewModel
 *      Version:    1.0
 * </pre>
 */
internal class AgilePresenterViewModel<P : IBasePresenter<out IBaseView, out BaseAbstractModel>> :
    ViewModel() {

    var presenter: P? = null
}