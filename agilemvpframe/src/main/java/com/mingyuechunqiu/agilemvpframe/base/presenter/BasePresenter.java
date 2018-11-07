package com.mingyuechunqiu.agilemvpframe.base.presenter;

import android.text.TextUtils;

import com.mingyuechunqiu.agilemvpframe.agile.AgileMVPFrame;
import com.mingyuechunqiu.agilemvpframe.R;
import com.mingyuechunqiu.agilemvpframe.base.model.BaseNetModel;
import com.mingyuechunqiu.agilemvpframe.base.model.BaseTokenNetModel;
import com.mingyuechunqiu.agilemvpframe.base.view.BaseView;
import com.mingyuechunqiu.agilemvpframe.data.bean.BaseInfo;
import com.mingyuechunqiu.agilemvpframe.util.NetworkUtils;
import com.mingyuechunqiu.agilemvpframe.util.SharedPreferencesUtils;

import java.lang.ref.WeakReference;

import static com.mingyuechunqiu.agilemvpframe.constants.UserConstants.PREF_USER_INFO;
import static com.mingyuechunqiu.agilemvpframe.constants.UserConstants.TOKEN;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/06/14
 *     desc   : 所有Presenter的基类
 *     version: 1.0
 * </pre>
 */
public abstract class BasePresenter<V extends BaseView, M extends BaseNetModel> {

    protected WeakReference<V> mViewRef;
    protected M mModel;

    public void attachView(V view) {
        mViewRef = new WeakReference<>(view);
        mModel = initModel();
        if (mViewRef.get() != null && mViewRef.get().getCurrentContext() != null) {
            if (mModel instanceof BaseTokenNetModel) {
                ((BaseTokenNetModel) mModel).setContextRef(mViewRef.get().getCurrentContext());
            }
        }
    }

    public void detachView() {
        if (mModel != null) {
            mModel.release();
            mModel = null;
        }
        release();
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    /**
     * 检测网络请求
     *
     * @return 当界面被回收和网络中断时返回false，否则返回true
     */
    protected boolean judgeNetwork() {
        if (mViewRef.get() != null) {
            if (!NetworkUtils.checkNetState(mViewRef.get().getCurrentContext())) {
                mViewRef.get().showToast(R.string.network_disconnected);
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 进行网络请求
     */
    protected void getRequest() {
        getRequest(null);
    }

    /**
     * 带参数的网络请求
     *
     * @param info 网络请求参数对象
     * @param <B>  网络请求参数类型
     */
    protected <B extends BaseInfo> void getRequest(B info) {
        //判断当前网络状况，是否继续进行网络业务操作
        if (judgeNetwork()) {
            requestModel(info);
        } else {
            if (mViewRef.get() != null) {
                disconnectNet();
            }
        }
    }

    /**
     * 获取token
     *
     * @return 存储的token值
     */
    protected String getToken() {
        if (mViewRef.get() == null) {
            return null;
        }
        String token = SharedPreferencesUtils.getString(
                AgileMVPFrame.getAppContext(), PREF_USER_INFO, TOKEN, null);
        if (TextUtils.isEmpty(token)) {
            showToast(R.string.error_set_net_params);
            return null;
        } else {
            return token;
        }
    }

    protected void showToast(String hint) {
        if (mViewRef.get() != null) {
            mViewRef.get().showToast(hint);
        }
    }

    protected void showToast(int stringResourceId) {
        if (mViewRef.get() != null) {
            mViewRef.get().showToast(stringResourceId);
        }
    }

    public abstract void start();

    protected abstract M initModel();

    /**
     * 由子类重写，调用model进行网络请求操作
     *
     * @param info 网络请求参数对象
     * @param <B>  网络请求参数类型
     */
    protected abstract <B extends BaseInfo> void requestModel(B info);

    /**
     * 释放资源
     */
    protected abstract void release();

    protected abstract void disconnectNet();
}
