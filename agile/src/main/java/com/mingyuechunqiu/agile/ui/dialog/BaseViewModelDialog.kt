package com.mingyuechunqiu.agile.ui.dialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import com.mingyuechunqiu.agile.base.viewmodel.IBaseViewModel
import com.mingyuechunqiu.agile.base.viewmodel.IViewModelOwner
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider

abstract class BaseViewModelDialog : BaseDialog, IViewModelOwner {

    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    protected constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)

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
     * 初始化业务逻辑模型
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
     * 注册与Agile库资源相关的观察者
     */
    private fun initBusinessViewModelConfiguration(viewModel: IBaseViewModel<*>) {
        LogManagerProvider.i(mTag, "initBusinessViewModelConfiguration")
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
        LogManagerProvider.i(mTag, "registerAgileResourceObserver")
        viewModel.apply {
            getPopHintState().observe(getDialogLifecycleOwner()) {
                when (it) {
                    is IBaseViewModel.PopHintState.MsgResIdToast -> this@BaseViewModelDialog.showToast(
                        it.msgResId
                    )
                    is IBaseViewModel.PopHintState.MsgToast -> this@BaseViewModelDialog.showToast(it.msg)
                    is IBaseViewModel.PopHintState.ErrorInfoToast -> this@BaseViewModelDialog.showToast(
                        it.info
                    )
                    is IBaseViewModel.PopHintState.ConfigToast -> this@BaseViewModelDialog.showToast(
                        it.config
                    )
                }
            }
            getStatusViewState().observe(getDialogLifecycleOwner()) {
                when (it) {
                    is IBaseViewModel.StatusViewState.ShowContainerIdLoading -> this@BaseViewModelDialog.showLoadingStatusView(
                        it.containerId
                    )
                    is IBaseViewModel.StatusViewState.ShowHintLoading -> this@BaseViewModelDialog.showLoadingStatusView(
                        it.hint,
                        it.cancelable
                    )
                    is IBaseViewModel.StatusViewState.Dismiss -> this@BaseViewModelDialog.dismissStatusView()
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