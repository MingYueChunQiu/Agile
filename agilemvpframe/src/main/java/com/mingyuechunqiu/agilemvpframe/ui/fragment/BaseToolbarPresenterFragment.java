package com.mingyuechunqiu.agilemvpframe.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mingyuechunqiu.agilemvpframe.base.presenter.BaseNetPresenter;
import com.mingyuechunqiu.agilemvpframe.base.view.BaseNetView;
import com.mingyuechunqiu.agilemvpframe.util.ToolbarUtils;

import static com.mingyuechunqiu.agilemvpframe.util.ToolbarUtils.NO_RESOURCE_ID;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/10/19
 *     desc   : 所有工具条界面的额父类
 *              继承自BasePresenterFragment
 *     version: 1.0
 * </pre>
 */
public abstract class BaseToolbarPresenterFragment<V extends BaseNetView<P>, P extends BaseNetPresenter> extends BasePresenterFragment<V, P> {

    protected Toolbar mToolbar;
    protected AppCompatTextView actvToolbarTitle;//工具栏标题
    protected ToolbarUtils.ToolbarBean mToolbarBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ActionBar actionBar = null;
        if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
            setHasOptionsMenu(true);
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        }
        mToolbarBean = setToolbarBean();
        ToolbarUtils.initToolbar(mToolbar, actionBar, mToolbarBean);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (mToolbarBean == null) {
            return;
        }
        if (mToolbarBean.isClearActivityMenu()) {
            menu.clear();
        }
        if (mToolbarBean.getMenuResId() != NO_RESOURCE_ID) {
            inflater.inflate(mToolbarBean.getMenuResId(), menu);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mToolbarBean = null;
    }

    /**
     * 供子类覆写的创建ToolbarBean方法，并放回创建好的ToolbarBean
     *
     * @return 返回创建好的ToolbarBean
     */
    protected abstract ToolbarUtils.ToolbarBean setToolbarBean();
}
