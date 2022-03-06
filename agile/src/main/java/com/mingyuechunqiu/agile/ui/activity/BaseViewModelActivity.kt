package com.mingyuechunqiu.agile.ui.activity

import android.os.Bundle
import com.mingyuechunqiu.agile.base.viewmodel.IBaseViewModel
import com.mingyuechunqiu.agile.base.viewmodel.IViewModelOwner
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider

/**
 * <pre>
 *     author : MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/16
 *     desc   : 所有具有ViewModel层功能的基类
 *              继承自BaseFullImmerseScreenActivity
 *     version: 1.0
 * </pre>
 */
abstract class BaseViewModelActivity : BaseFullImmerseScreenActivity(), IViewModelOwner {

    private val mTag = javaClass.simpleName
    private val mBusinessViewModelList: MutableList<IBaseViewModel<*>> = ArrayList()

    override fun initOnData(savedInstanceState: Bundle?) {
        initOnBusinessViewModels()
        super.initOnData(savedInstanceState)
    }

    override fun addBusinessViewModel(viewModel: IBaseViewModel<*>) {
        LogManagerProvider.i(mTag, "addViewModel")
        mBusinessViewModelList.add(viewModel)
    }

    override fun removeBusinessViewModel(viewModel: IBaseViewModel<*>) {
        LogManagerProvider.i(mTag, "removeViewModel")
        mBusinessViewModelList.remove(viewModel)
    }

    override fun getBusinessViewModelList(): List<IBaseViewModel<*>> {
        return mBusinessViewModelList
    }

    /**
     * 初始化业务逻辑模型（允许子类重写做额外操作，但重写时，必须调用super.initBusinessViewModels()。否则可能影响框架其他功能正常使用）
     */
    protected fun initOnBusinessViewModels() {
        LogManagerProvider.i(mTag, "initOnBusinessViewModels")
        mBusinessViewModelList.apply {
            addAll(initBusinessViewModels())
            forEach {
                initBusinessViewModelConfiguration(it)
            }
        }
    }

    /**
     * 初始化Agile库业务ViewModel相关配置
     *
     * @param viewModel 业务模型
     */
    private fun initBusinessViewModelConfiguration(viewModel: IBaseViewModel<*>) {
        LogManagerProvider.i(mTag, "initBusinessViewModels")
        viewModel.apply {
            attachView(this@BaseViewModelActivity)
            lifecycle.addObserver(this)
            registerAgileResourceObserver(this)
        }
    }

    /**
     * 注册与Agile库资源相关的观察者
     *
     * @param viewModel 业务模型
     */
    private fun registerAgileResourceObserver(viewModel: IBaseViewModel<*>) {
        LogManagerProvider.i(mTag, "initBusinessViewModels")
        viewModel.apply {
            getPopHintState().observe(this@BaseViewModelActivity) {
                when (it) {
                    is IBaseViewModel.PopHintState.MsgResIdToast -> this@BaseViewModelActivity.showToast(
                        it.msgResId
                    )
                    is IBaseViewModel.PopHintState.MsgToast -> this@BaseViewModelActivity.showToast(
                        it.msg
                    )
                    is IBaseViewModel.PopHintState.ErrorInfoToast -> this@BaseViewModelActivity.showToast(
                        it.info
                    )
                    is IBaseViewModel.PopHintState.ConfigToast -> this@BaseViewModelActivity.showToast(
                        it.config
                    )
                }
            }
            getStatusViewState().observe(this@BaseViewModelActivity) {
                when (it) {
                    is IBaseViewModel.StatusViewState.ShowContainerIdLoading -> this@BaseViewModelActivity.showLoadingStatusView(
                        it.containerId
                    )
                    is IBaseViewModel.StatusViewState.ShowHintLoading -> this@BaseViewModelActivity.showLoadingStatusView(
                        it.hint,
                        it.cancelable
                    )
                    is IBaseViewModel.StatusViewState.Dismiss -> this@BaseViewModelActivity.dismissStatusView()
                }
            }
        }
    }

    /**
     * 供子类重写，初始化业务模型列表
     *
     * @return 返回业务模型列表
     */
    protected abstract fun initBusinessViewModels(): List<IBaseViewModel<*>>
}