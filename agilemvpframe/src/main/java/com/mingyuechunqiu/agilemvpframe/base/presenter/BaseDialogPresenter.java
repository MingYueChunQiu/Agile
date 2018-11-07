package com.mingyuechunqiu.agilemvpframe.base.presenter;

import com.mingyuechunqiu.agilemvpframe.base.model.BaseNetModel;
import com.mingyuechunqiu.agilemvpframe.base.view.BaseDialogView;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/12
 *     desc   : 所有带对话框功能的Presenter的基类
 *     version: 1.0
 * </pre>
 */
public abstract class BaseDialogPresenter<V extends BaseDialogView, M extends BaseNetModel> extends BasePresenter<V, M> {

    /**
     * 显示提示信息并关闭加载对话框
     *
     * @param hint 提示文本
     */
    protected void showToastAndDisappear(String hint) {
        if (mViewRef.get() != null) {
            mViewRef.get().showToast(hint);
            mViewRef.get().disappearLoadingDialog();
        }
    }

    /**
     * 显示提示信息并关闭加载对话框
     *
     * @param stringResourceId 提示文本资源ID
     */
    protected void showToastAndDisappear(int stringResourceId) {
        if (mViewRef.get() != null) {
            mViewRef.get().showToast(stringResourceId);
            mViewRef.get().disappearLoadingDialog();
        }
    }

}
