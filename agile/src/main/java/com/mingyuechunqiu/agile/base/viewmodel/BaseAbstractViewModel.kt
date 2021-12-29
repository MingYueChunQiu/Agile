package com.mingyuechunqiu.agile.base.viewmodel

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mingyuechunqiu.agile.base.model.IBaseModel
import com.mingyuechunqiu.agile.base.presenter.engine.IBaseEngine
import com.mingyuechunqiu.agile.data.bean.ErrorInfo
import com.mingyuechunqiu.agile.feature.helper.ui.hint.ToastHelper
import com.mingyuechunqiu.agile.frame.Agile

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2021/12/29
 *     desc   : 所有ViewModel层的抽象基类
 *              继承自IBaseViewModel
 *     version: 1.0
 * </pre>
 */
abstract class BaseAbstractViewModel<M : IBaseModel> : IBaseViewModel {

    protected val model: M? = null
    private var mEngineList: MutableList<IBaseEngine>? = null

    private val _popHintState: MutableLiveData<PopHintState> = MutableLiveData()
    val popHintState: LiveData<PopHintState> = _popHintState

    private val _statusViewState: MutableLiveData<StatusViewState> = MutableLiveData()
    val statusViewState: LiveData<StatusViewState> = _statusViewState

    override fun releaseOnDetach() {
        release()
        mEngineList?.let {
            for (engine in it) {
                engine.release()
            }
            it.clear()
            mEngineList = null
        }
    }

    override fun getAppContext(): Context {
        return Agile.getAppContext()
    }

    override fun showToast(@StringRes msgResId: Int) {
        _popHintState.value = PopHintState.MsgResIdToast(msgResId)
    }

    override fun showToast(msg: String?) {
        _popHintState.value = PopHintState.MsgToast(msg)
    }

    override fun showToast(info: ErrorInfo) {
        _popHintState.value = PopHintState.ErrorInfoToast(info)
    }

    override fun showToast(config: ToastHelper.ToastConfig) {
        _popHintState.value = PopHintState.ConfigToast(config)
    }

    override fun showLoadingStatusView(@LayoutRes containerId: Int) {
        _statusViewState.value = StatusViewState.ShowContainerIdLoading(containerId)
    }

    override fun showLoadingStatusView(hint: String?, cancelable: Boolean) {
        _statusViewState.value = StatusViewState.ShowHintLoading(hint, cancelable)
    }

    override fun dismissStatusView() {
        _statusViewState.value = StatusViewState.Dismiss
    }

    protected fun release() = Unit

    sealed interface PopHintState {

        data class MsgResIdToast(@StringRes val msgResId: Int) : PopHintState

        data class MsgToast(val msg: String?) : PopHintState

        data class ErrorInfoToast(val info: ErrorInfo) : PopHintState

        data class ConfigToast(val config: ToastHelper.ToastConfig) : PopHintState
    }

    sealed interface StatusViewState {

        data class ShowContainerIdLoading(@LayoutRes val containerId: Int) : StatusViewState

        data class ShowHintLoading(val hint: String?, val cancelable: Boolean) : StatusViewState

        object Dismiss : StatusViewState
    }
}