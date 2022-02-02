package com.mingyuechunqiu.agile.base.viewmodel

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/12/30 11:14 下午
 *      Desc:       ViewModel拥有者接口
 *      Version:    1.0
 * </pre>
 */
interface IViewModelOwner {

    /**
     * 添加模型层engine单元
     *
     * @param viewModel ViewModel单元模块
     * @return 如果添加成功返回true，否则返回false
     */
    fun addBusinessViewModel(viewModel: IBaseViewModel<*>)

    fun removeBusinessViewModel(viewModel: IBaseViewModel<*>)

    fun getBusinessViewModelList(): List<IBaseViewModel<*>>
}