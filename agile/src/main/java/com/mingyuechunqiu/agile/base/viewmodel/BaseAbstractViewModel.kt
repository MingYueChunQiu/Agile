package com.mingyuechunqiu.agile.base.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mingyuechunqiu.agile.base.model.IBaseModel
import com.mingyuechunqiu.agile.base.presenter.engine.IBaseEngine
import com.mingyuechunqiu.agile.data.bean.ErrorInfo
import com.mingyuechunqiu.agile.feature.helper.ui.hint.ToastHelper
import com.mingyuechunqiu.agile.frame.Agile

abstract class BaseAbstractViewModel<M : IBaseModel> : IBaseViewModel {

    private val mModel: M? = null
    private var mEngineList: MutableList<IBaseEngine>? = null

    private val _popHintState: MutableLiveData<ToastHelper.ToastConfig> = MutableLiveData()
    val popHintState: LiveData<ToastHelper.ToastConfig> = _popHintState

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

    override fun showToast(msgResId: Int) {
        _popHintState.value = ToastHelper.ToastConfig.Builder().apply {
            this.msgResId = msgResId
        }
            .build()
    }

    override fun showToast(msg: String?) {
        _popHintState.value = ToastHelper.ToastConfig.Builder().apply {
            this.msg = msg
        }
            .build()
    }

    override fun showToast(config: ToastHelper.ToastConfig) {
        _popHintState.value = config
    }

    override fun showToast(info: ErrorInfo) {
        _popHintState.value = ToastHelper.ToastConfig.Builder()
            .setMsg(info.errorMsg)
            .setMsgResId(info.errorMsgResId)
            .build()
    }

    protected fun release() = Unit
}