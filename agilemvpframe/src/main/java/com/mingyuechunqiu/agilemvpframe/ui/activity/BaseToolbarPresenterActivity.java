package com.mingyuechunqiu.agilemvpframe.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.mingyuechunqiu.agilemvpframe.base.presenter.BaseNetPresenter;
import com.mingyuechunqiu.agilemvpframe.base.view.BaseNetView;
import com.mingyuechunqiu.agilemvpframe.util.ToolbarUtils;

import static com.mingyuechunqiu.agilemvpframe.constants.CommonConstants.NO_RESOURCE_ID;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/21
 *     desc   : 所有工具条界面的父类
 *              继承自BasePresenterActivity
 *     version: 1.0
 * </pre>
 */
public abstract class BaseToolbarPresenterActivity<V extends BaseNetView<P>, P extends BaseNetPresenter> extends BasePresenterActivity<V, P> {

    protected Toolbar mToolbar;
    protected AppCompatTextView actvToolbarTitle;//工具栏标题
    private ToolbarUtils.ToolbarBean mToolbarBean;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToolbarBean = setToolbarBean();
        ToolbarUtils.initToolbar(mToolbar, getSupportActionBar(), mToolbarBean);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mToolbarBean != null && mToolbarBean.getMenuResId() != NO_RESOURCE_ID) {
            getMenuInflater().inflate(mToolbarBean.getMenuResId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 供子类覆写的创建ToolbarBean方法，并放回创建好的ToolbarBean
     *
     * @return 返回创建好的ToolbarBean
     */
    protected abstract ToolbarUtils.ToolbarBean setToolbarBean();
}
