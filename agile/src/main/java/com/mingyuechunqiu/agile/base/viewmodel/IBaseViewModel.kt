package com.mingyuechunqiu.agile.base.viewmodel

import android.content.Context
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import com.mingyuechunqiu.agile.base.bridge.call.ICallExecutor
import com.mingyuechunqiu.agile.base.businessengine.IBusinessEngineOwner
import com.mingyuechunqiu.agile.base.model.IBaseModel
import com.mingyuechunqiu.agile.data.bean.ErrorInfo
import com.mingyuechunqiu.agile.feature.helper.ui.hint.IPopHintOwner
import com.mingyuechunqiu.agile.feature.helper.ui.hint.ToastHelper
import com.mingyuechunqiu.agile.feature.statusview.framework.IStatusViewProcessor

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2021/12/29
 *     desc   : 所有ViewModel层的父接口
 *              继承自ICallExecutor, IPopHintOwner, IStatusViewProcessor
 *     version: 1.0
 * </pre>
 */
interface IBaseViewModel<M : IBaseModel> : ICallExecutor, IBusinessEngineOwner, IPopHintOwner,
    IStatusViewProcessor {

    fun releaseOnDetach()

    fun getAppContext(): Context

    fun getPopHintState(): LiveData<PopHintState>

    fun getStatusViewState(): LiveData<StatusViewState>

    fun initModel(): M?

    fun getModel(): M?

    sealed interface PopHintState {

        data class MsgResIdToast(@StringRes val msgResId: Int) : PopHintState

        data class MsgToast(val msg: String?) : PopHintState

        data class ErrorInfoToast(val info: ErrorInfo) : PopHintState

        data class ConfigToast(val config: ToastHelper.ToastConfig) : PopHintState
    }

    sealed interface StatusViewState {

        data class ShowContainerIdLoading(@IdRes val containerId: Int) : StatusViewState

        data class ShowHintLoading(val hint: String?, val cancelable: Boolean) : StatusViewState

        object Dismiss : StatusViewState
    }
}