package com.mingyuechunqiu.agile.base.viewmodel

import android.content.Context
import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mingyuechunqiu.agile.base.businessengine.IBaseBusinessEngine
import com.mingyuechunqiu.agile.base.model.IBaseModel
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
    private var mBusinessEngineList: MutableList<IBaseBusinessEngine>? = null

    private val _popHintState: MutableLiveData<IBaseViewModel.PopHintState> = MutableLiveData()

    private val _statusViewState: MutableLiveData<IBaseViewModel.StatusViewState> =
        MutableLiveData()

    override fun releaseOnDetach() {
        release()
        mBusinessEngineList?.let {
            for (engine in it) {
                engine.release()
            }
            it.clear()
            mBusinessEngineList = null
        }
    }

    override fun getAppContext(): Context {
        return Agile.getAppContext()
    }

    override fun getPopHintState(): LiveData<IBaseViewModel.PopHintState> {
        return _popHintState
    }

    override fun getStatusViewState(): LiveData<IBaseViewModel.StatusViewState> {
        return _statusViewState
    }

    override fun showToast(@StringRes msgResId: Int) {
        _popHintState.value = IBaseViewModel.PopHintState.MsgResIdToast(msgResId)
    }

    override fun showToast(msg: String?) {
        _popHintState.value = IBaseViewModel.PopHintState.MsgToast(msg)
    }

    override fun showToast(info: ErrorInfo) {
        _popHintState.value = IBaseViewModel.PopHintState.ErrorInfoToast(info)
    }

    override fun showToast(config: ToastHelper.ToastConfig) {
        _popHintState.value = IBaseViewModel.PopHintState.ConfigToast(config)
    }

    override fun showLoadingStatusView(@LayoutRes containerId: Int) {
        _statusViewState.value = IBaseViewModel.StatusViewState.ShowContainerIdLoading(containerId)
    }

    override fun showLoadingStatusView(hint: String?, cancelable: Boolean) {
        _statusViewState.value = IBaseViewModel.StatusViewState.ShowHintLoading(hint, cancelable)
    }

    override fun dismissStatusView() {
        _statusViewState.value = IBaseViewModel.StatusViewState.Dismiss
    }

    fun getResources(): Resources {
        return getAppContext().resources
    }

    @Throws(NotFoundException::class)
    fun getString(resId: Int): String {
        return getResources().getString(resId)
    }

    @Throws(NotFoundException::class)
    open fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String {
        return getResources().getString(resId, formatArgs)
    }

    fun getColor(resId: Int): Int {
        return ContextCompat.getColor(getAppContext(), resId)
    }

    protected fun release() = Unit
}