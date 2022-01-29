package com.mingyuechunqiu.agile.ui.diaglogfragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
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
abstract class BaseViewModelDialogFragment : BaseDialogFragment() {

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
        viewModel.apply {
            attachView(this@BaseViewModelDialogFragment)
            val lifecycleOwner = when (getLifecycleType()) {
                AgileLifecycle.LifecycleType.COMPONENT -> this@BaseViewModelDialogFragment
                AgileLifecycle.LifecycleType.VIEW -> viewLifecycleOwner
            }
            lifecycleOwner.lifecycle.addObserver(this)
            registerAgileResourceObserver(lifecycleOwner, this)
        }
    }

    /**
     * 注册与Agile库资源相关的观察者
     *
     * @param lifecycleOwner 生命周期拥有者
     * @param viewModel 业务模型
     */
    private fun registerAgileResourceObserver(
        lifecycleOwner: LifecycleOwner,
        viewModel: IBaseViewModel<*>
    ) {
        viewModel.apply {
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