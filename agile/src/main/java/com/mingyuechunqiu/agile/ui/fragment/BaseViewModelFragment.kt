package com.mingyuechunqiu.agile.ui.fragment

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.mingyuechunqiu.agile.base.viewmodel.IBaseViewModel
import com.mingyuechunqiu.agile.base.viewmodel.IViewModelOwner
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider
import com.mingyuechunqiu.agile.frame.lifecycle.AgileLifecycle

/**
 * <pre>
 *     author : MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/16
 *     desc   : 所有具有ViewModel层功能的基类
 *              继承自BaseFragment
 *     version: 1.0
 * </pre>
 */
abstract class BaseViewModelFragment : BaseFragment(), IViewModelOwner {

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
     * 初始化Agile库业务ViewModel相关配置
     *
     * @param viewModel 业务模型
     */
    private fun initBusinessViewModelConfiguration(viewModel: IBaseViewModel<*>) {
        LogManagerProvider.i(mTag, "initBusinessViewModelConfiguration")
        viewModel.apply {
            attachView(this@BaseViewModelFragment)
            val lifecycleOwner = when (getLifecycleType()) {
                AgileLifecycle.LifecycleType.COMPONENT -> this@BaseViewModelFragment
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
        LogManagerProvider.i(mTag, "registerAgileResourceObserver")
        viewModel.apply {
            lifecycleOwner.lifecycle.addObserver(this)
            getPopHintState().observe(lifecycleOwner) {
                when (it) {
                    is IBaseViewModel.PopHintState.MsgResIdToast -> this@BaseViewModelFragment.showToast(
                        it.msgResId
                    )
                    is IBaseViewModel.PopHintState.MsgToast -> this@BaseViewModelFragment.showToast(
                        it.msg
                    )
                    is IBaseViewModel.PopHintState.ErrorInfoToast -> this@BaseViewModelFragment.showToast(
                        it.info
                    )
                    is IBaseViewModel.PopHintState.ConfigToast -> this@BaseViewModelFragment.showToast(
                        it.config
                    )
                }
            }
            getStatusViewState().observe(lifecycleOwner) {
                when (it) {
                    is IBaseViewModel.StatusViewState.ShowContainerIdLoading -> this@BaseViewModelFragment.showLoadingStatusView(
                        it.containerId
                    )
                    is IBaseViewModel.StatusViewState.ShowHintLoading -> this@BaseViewModelFragment.showLoadingStatusView(
                        it.hint,
                        it.cancelable
                    )
                    is IBaseViewModel.StatusViewState.Dismiss -> this@BaseViewModelFragment.dismissStatusView()
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