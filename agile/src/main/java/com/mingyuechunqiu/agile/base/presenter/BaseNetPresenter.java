package com.mingyuechunqiu.agile.base.presenter;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.R;
import com.mingyuechunqiu.agile.frame.Agile;
import com.mingyuechunqiu.agile.base.model.BaseNetModel;
import com.mingyuechunqiu.agile.base.view.IBaseNetView;
import com.mingyuechunqiu.agile.data.bean.BaseParamsInfo;
import com.mingyuechunqiu.agile.util.NetworkUtils;
import com.mingyuechunqiu.agile.util.SharedPreferencesUtils;

import static com.mingyuechunqiu.agile.constants.UserConstants.PREF_USER_INFO;
import static com.mingyuechunqiu.agile.constants.UserConstants.TOKEN;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/11/15
 *     desc   : 所有带网络功能的P层的基类
 *              继承自BaseDialogPresenter
 *     version: 1.0
 * </pre>
 */
public abstract class BaseNetPresenter<V extends IBaseNetView, M extends BaseNetModel> extends BaseDialogPresenter<V, M> {

    /**
     * 带参数的网络请求
     *
     * @param info 网络请求参数对象
     */
    @Override
    public void setParamsInfo(@Nullable BaseParamsInfo info) {
        if (mModel == null) {
            throw new IllegalArgumentException("Model has not been set!");
        }
        //判断当前网络状况，是否继续进行网络业务操作
        if (judgeNetwork()) {
            if (info == null) {
                showToast(R.string.agile_error_set_net_params);
                return;
            }
            requestModel(info);
        } else {
            if (!checkViewRefIsNull()) {
                disconnectNet();
            }
        }
    }

    /**
     * 释放网络相关资源
     */
    public void releaseNetResources() {
        if (mModel == null) {
            return;
        }
        mModel.releaseNetResources();
    }

    /**
     * 检测网络请求
     *
     * @return 当界面被回收和网络中断时返回false，否则返回true
     */
    protected boolean judgeNetwork() {
        if (!checkViewRefIsNull()) {
            if (!NetworkUtils.checkNetState(mViewRef.get().getCurrentContext())) {
                mViewRef.get().showToast(R.string.agile_network_disconnected);
                return false;
            }
            return true;
        }
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

    @Override
    protected void requestModel(BaseParamsInfo info) {
        mModel.setParamsInfo(info);
    }

    /**
     * 当网络连接断开时回调
     */
    protected abstract void disconnectNet();
}
