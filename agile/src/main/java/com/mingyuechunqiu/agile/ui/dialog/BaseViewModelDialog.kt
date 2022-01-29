package com.mingyuechunqiu.agile.ui.dialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import com.mingyuechunqiu.agile.base.viewmodel.IBaseViewModel

abstract class BaseViewModelDialog : BaseDialog {

    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    protected constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)

    private val mBusinessViewModelList: MutableList<IBaseViewModel<*>> = ArrayList()

    override fun initOnData(savedInstanceState: Bundle?) {
        initBusinessViewModels()
        super.initOnData(savedInstanceState)
    }

    /**
     * 初始化业务逻辑模型
     */
    protected fun initBusinessViewModels() {
        mBusinessViewModelList.apply {
            addAll(initializeBusinessViewModels())
            forEach {
                initBusinessViewModelConfiguration(it)
            }
        }
    }

    /**
     * 注册与Agile库资源相关的观察者
     */
    private fun initBusinessViewModelConfiguration(viewModel: IBaseViewModel<*>) {
        viewModel.apply {
            attachView(this@BaseViewModelDialog)
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
        viewModel.apply {
            getPopHintState().observe(getDialogLifecycleOwner()) {
                when (it) {
                    is IBaseViewModel.PopHintState.MsgResIdToast -> showToast(it.msgResId)
                    is IBaseViewModel.PopHintState.MsgToast -> showToast(it.msg)
                    is IBaseViewModel.PopHintState.ErrorInfoToast -> showToast(it.info)
                    is IBaseViewModel.PopHintState.ConfigToast -> showToast(it.config)
                }
            }
            getStatusViewState().observe(getDialogLifecycleOwner()) {
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