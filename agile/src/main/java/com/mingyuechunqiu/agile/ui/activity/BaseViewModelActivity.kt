package com.mingyuechunqiu.agile.ui.activity

import android.os.Bundle
import com.mingyuechunqiu.agile.base.viewmodel.IBaseViewModel

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
abstract class BaseViewModelActivity : BaseFullImmerseScreenActivity() {

    private val mBusinessViewModelList: MutableList<IBaseViewModel<*>> = ArrayList()

    override fun initOnData(savedInstanceState: Bundle?) {
        initBusinessViewModels()
        super.initOnData(savedInstanceState)
    }

    /**
     * 初始化业务逻辑模型（允许子类重写做额外操作，但重写时，必须调用super.initBusinessViewModels()。否则可能影响框架其他功能正常使用）
     */
    protected fun initBusinessViewModels() {
        mBusinessViewModelList.apply {
            addAll(initializeBusinessViewModels())
            forEach {
                registerAgileResourceObserver(it)
            }
        }
    }

    /**
     * 注册与Agile库资源相关的观察者
     */
    private fun registerAgileResourceObserver(viewModel: IBaseViewModel<*>) {
        viewModel.apply {
            lifecycle.addObserver(this)
            getPopHintState().observe(this@BaseViewModelActivity) {
                when (it) {
                    is IBaseViewModel.PopHintState.MsgResIdToast -> showToast(it.msgResId)
                    is IBaseViewModel.PopHintState.MsgToast -> showToast(it.msg)
                    is IBaseViewModel.PopHintState.ErrorInfoToast -> showToast(it.info)
                    is IBaseViewModel.PopHintState.ConfigToast -> showToast(it.config)
                }
            }
            getStatusViewState().observe(this@BaseViewModelActivity) {
                when (it) {
                    is IBaseViewModel.StatusViewState.ShowContainerIdLoading -> showLoadingStatusView(
                        it.containerId
                    )
                    is IBaseViewModel.StatusViewState.ShowHintLoading -> showLoadingStatusView(
                        it.hint,
                        it.cancelable
                    )
                    is IBaseViewModel.StatusViewState.Dismiss -> dismissStatusView()
                }
            }
        }
    }

    protected abstract fun initializeBusinessViewModels(): List<IBaseViewModel<*>>
}