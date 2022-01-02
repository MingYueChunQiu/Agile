package com.mingyuechunqiu.agile.base.presenter;

import static com.mingyuechunqiu.agile.constants.AgileUserConstants.PREF_USER_INFO;
import static com.mingyuechunqiu.agile.constants.AgileUserConstants.TOKEN;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.mingyuechunqiu.agile.R;
import com.mingyuechunqiu.agile.base.bridge.Request;
import com.mingyuechunqiu.agile.base.bridge.call.Call;
import com.mingyuechunqiu.agile.base.model.BaseAbstractDataModel;
import com.mingyuechunqiu.agile.base.view.IBaseDataView;
import com.mingyuechunqiu.agile.frame.Agile;
import com.mingyuechunqiu.agile.util.NetworkUtils;
import com.mingyuechunqiu.agile.util.SharedPreferencesUtils;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/11/15
 *     desc   : 所有带数据处理功能的P层的基类
 *              继承自BaseAbstractStatusViewPresenter
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractDataPresenter<V extends IBaseDataView, M extends BaseAbstractDataModel> extends BaseAbstractPresenter<V, M> {

    @Override
    public <I extends Request.IParamsInfo, T> boolean dispatchCall(@NonNull Call<I, T> call) {
        if (getModel() == null) {
            throw new IllegalArgumentException("Model has not been set!");
        }
        if (call.getRequest().getRequestCategory() == Request.RequestCategory.CATEGORY_NETWORK) {
            //判断当前网络状况，是否继续进行网络业务操作
            if (!checkNetworkIsConnected()) {
                if (!checkViewRefIsNull()) {
                    disconnectNetwork();
                }
                return false;
            }
        }
        return dispatchCallWithModel(call);
    }

    @Override
    protected <I extends Request.IParamsInfo, T> boolean dispatchCallWithModel(@NonNull Call<I, T> call) {
        M model = getModel();
        return model != null && model.dispatchCall(call);
    }

    /**
     * 检测网络是否连接
     *
     * @return 当网络连接正常时返回true，否则返回false
     */
    protected boolean checkNetworkIsConnected() {
        if (NetworkUtils.checkNetworkIsConnected()) {
            return true;
        }
        showToast(R.string.agile_network_disconnected);
        return false;
    }

    /**
     * 获取token
     *
     * @return 存储的token值
     */
    protected String getToken() {
        String token = SharedPreferencesUtils.getString(
                Agile.getAppContext(), PREF_USER_INFO, TOKEN, null);
        if (TextUtils.isEmpty(token)) {
            showToast(R.string.agile_error_set_net_params);
            return null;
        } else {
            return token;
        }
    }

    /**
     * 当网络连接断开时回调
     */
    protected abstract void disconnectNetwork();
}
