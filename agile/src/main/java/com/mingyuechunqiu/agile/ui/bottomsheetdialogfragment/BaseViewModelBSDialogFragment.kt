package com.mingyuechunqiu.agile.ui.bottomsheetdialogfragment

import android.os.Bundle
import android.view.View
import com.mingyuechunqiu.agile.base.viewmodel.IBaseViewModel
import com.mingyuechunqiu.agile.frame.lifecycle.AgileLifecycle

/**
 * <pre>
 *     author : MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/16
 *     desc   : 所有具有ViewModel层功能的基类
 *              继承自BaseBSDialogFragment
 *     version: 1.0
 * </pre>
 */
abstract class BaseViewModelBSDialogFragment : BaseBSDialogFragment() {

    private val mBusinessViewModelList: MutableList<IBaseViewModel<*>> = ArrayList()

    override fun initOnData(view: View, savedInstanceState: Bundle?) {
        initBusinessViewModels()
        super.initOnData(view, savedInstanceState)
    }

    /**
     * 初始化业务逻辑模型
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
            val lifecycleOwner = when (getLifecycleType()) {
                AgileLifecycle.LifecycleType.COMPONENT -> this@BaseViewModelBSDialogFragment
                AgileLifecycle.LifecycleType.VIEW -> viewLifecycleOwner
            }
            lifecycleOwner.lifecycle.addObserver(this)
            getPopHintState().observe(lifecycleOwner) {
                when (it) {
                    is IBaseViewModel.PopHintState.MsgResIdToast -> showToast(it.msgResId)
                    is IBaseViewModel.PopHintState.MsgToast -> showToast(it.msg)
                    is IBaseViewModel.PopHintState.ErrorInfoToast -> showToast(it.info)
                    is IBaseViewModel.PopHintState.ConfigToast -> showToast(it.config)
                }
            }
            getStatusViewState().observe(lifecycleOwner) {
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