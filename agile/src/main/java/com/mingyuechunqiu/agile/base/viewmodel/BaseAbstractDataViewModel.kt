package com.mingyuechunqiu.agile.base.viewmodel

import android.text.TextUtils
import com.mingyuechunqiu.agile.R
import com.mingyuechunqiu.agile.base.bridge.Request
import com.mingyuechunqiu.agile.base.bridge.call.Call
import com.mingyuechunqiu.agile.base.model.BaseAbstractDataModel
import com.mingyuechunqiu.agile.constants.AgileUserConstants
import com.mingyuechunqiu.agile.frame.Agile
import com.mingyuechunqiu.agile.util.NetworkUtils
import com.mingyuechunqiu.agile.util.SharedPreferencesUtils

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2021/12/29
 *     desc   : 所有带数据处理功能的ViewModel层的基类
 *              继承自BaseAbstractViewModel
 *     version: 1.0
 * </pre>
 */
abstract class BaseAbstractDataViewModel<M : BaseAbstractDataModel> : BaseAbstractViewModel<M>() {

    override fun <I : Request.IParamsInfo, T : Any> dispatchCall(call: Call<I, T>): Boolean {
        requireNotNull(getModel()) { "Model has not been set!" }
        if (call.getRequest().requestCategory === Request.RequestCategory.CATEGORY_NETWORK) {
            //判断当前网络状况，是否继续进行网络业务操作
            if (!checkNetworkIsConnected()) {
                disconnectNetwork()
                return false
            }
        }
        return dispatchCallWithModel(call)
    }

    protected open fun <I : Request.IParamsInfo, T : Any> dispatchCallWithModel(call: Call<I, T>): Boolean {
        return getModel()?.dispatchCall(call) ?: false
    }

    /**
     * 检测网络是否连接
     *
     * @return 当网络连接正常时返回true，否则返回false
     */
    protected open fun checkNetworkIsConnected(): Boolean {
        if (NetworkUtils.checkNetworkIsConnected()) {
            return true
        }
        showToast(R.string.agile_network_disconnected)
        return false
    }

    /**
     * 获取token
     *
     * @return 存储的token值
     */
    protected open fun getToken(): String? {
        val token = SharedPreferencesUtils.getString(
            Agile.getAppContext(), AgileUserConstants.PREF_USER_INFO, AgileUserConstants.TOKEN, null
        )
        return if (TextUtils.isEmpty(token)) {
            showToast(R.string.agile_error_set_net_params)
            null
        } else {
            token
        }
    }

    /**
     * 当网络连接断开时回调
     */
    protected abstract fun disconnectNetwork()
}