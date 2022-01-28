package com.mingyuechunqiu.agile.base.viewmodel

import android.content.Context
import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.mingyuechunqiu.agile.base.businessengine.IBaseBusinessEngine
import com.mingyuechunqiu.agile.base.model.IBaseModel
import com.mingyuechunqiu.agile.data.bean.ErrorInfo
import com.mingyuechunqiu.agile.feature.helper.ui.hint.ToastHelper
import com.mingyuechunqiu.agile.feature.helper.ui.hint.ToastHelper.ToastConfig
import com.mingyuechunqiu.agile.frame.Agile
import java.util.*

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
abstract class BaseAbstractViewModel<M : IBaseModel> : ViewModel(), IBaseViewModel<M> {

    private val mModel: M? = null
    private var mBusinessEngineList: MutableList<IBaseBusinessEngine>? = null

    private val _popHintState: MutableLiveData<IBaseViewModel.PopHintState> = MutableLiveData()

    private val _statusViewState: MutableLiveData<IBaseViewModel.StatusViewState> =
        MutableLiveData()

    private val _observableData: MutableLiveData<IBaseViewModel.ObservableData<*>> =
        MutableLiveData()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> callOnStart()
            Lifecycle.Event.ON_RESUME -> callOnResume()
            Lifecycle.Event.ON_PAUSE -> callOnPause()
            Lifecycle.Event.ON_STOP -> callOnStop()
            Lifecycle.Event.ON_DESTROY -> releaseOnDetach()
            else -> Unit
        }
    }

    override fun callOnStart() {
        onStart()
        mModel?.callOnStart()
    }

    override fun callOnResume() {
        onResume()
        mModel?.callOnResume()
    }

    override fun callOnPause() {
        onPause()
        mModel?.callOnPause()
    }

    override fun callOnStop() {
        onStop()
        mModel?.callOnStop()
    }

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

    override fun getModel(): M? {
        return mModel
    }

    override fun initBusinessEngines() {
        initializeBusinessEngines()
    }

    /**
     * 添加模型层engine单元
     *
     * @param engine engine单元模块
     * @return 如果添加成功返回true，否则返回false
     */
    override fun addBusinessEngine(engine: IBaseBusinessEngine): Boolean {
        return mBusinessEngineList?.add(engine) ?: kotlin.run {
            val list = ArrayList<IBaseBusinessEngine>()
            mBusinessEngineList = list
            list.add(engine)
        }
    }

    /**
     * 删除指定的模型层engine单元
     *
     * @param engine engine单元模块
     * @return 如果删除成功返回true，否则返回false
     */
    override fun removeBusinessEngine(engine: IBaseBusinessEngine?): Boolean {
        return if (engine == null) {
            false
        } else {
            mBusinessEngineList?.remove(engine) ?: false
        }
    }

    override fun getBusinessEngineList(): List<IBaseBusinessEngine?> {
        return mBusinessEngineList ?: ArrayList()
    }

    override fun getPopHintState(): LiveData<IBaseViewModel.PopHintState> {
        return _popHintState
    }

    override fun getStatusViewState(): LiveData<IBaseViewModel.StatusViewState> {
        return _statusViewState
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> getObservableData(): LiveData<IBaseViewModel.ObservableData<T>> {
        return _observableData as LiveData<IBaseViewModel.ObservableData<T>>
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

    /**
     * 显示信息并关闭加载对话框
     *
     * @param msg 文本
     */
    protected fun showToastAndDismissStatusView(msg: String?) {
        showToastAndDismissStatusView(
            ToastConfig.Builder()
                .setMsg(msg)
                .build()
        )
    }

    /**
     * 显示信息并关闭加载对话框
     *
     * @param msgResId 文本资源ID
     */
    protected fun showToastAndDismissStatusView(@StringRes msgResId: Int) {
        showToastAndDismissStatusView(
            ToastConfig.Builder()
                .setMsgResId(msgResId)
                .build()
        )
    }

    /**
     * 显示信息并关闭加载对话框
     *
     * @param info 错误信息对象
     */
    protected fun showToastAndDismissStatusView(info: ErrorInfo) {
        showToastAndDismissStatusView(
            ToastConfig.Builder()
                .setMsg(info.errorMsg)
                .setMsgResId(info.errorMsgResId)
                .build()
        )
    }

    /**
     * 显示信息并关闭加载对话框
     *
     * @param config 配置信息对象
     */
    protected fun showToastAndDismissStatusView(config: ToastConfig) {
        showToast(config)
        dismissStatusView()
    }

    protected open fun onStart() {}

    protected open fun onResume() {}

    protected open fun onPause() {}

    protected open fun onStop() {}

    /**
     * 供子类重写进行业务引擎初始化
     */
    protected abstract fun initializeBusinessEngines()

    protected abstract fun release()
}