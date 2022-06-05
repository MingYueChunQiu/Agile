package com.mingyuechunqiu.agile.ui.fragment;

import static com.mingyuechunqiu.agile.constants.AgileCommonConstants.NO_RESOURCE_ID;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import com.mingyuechunqiu.agile.base.model.BaseAbstractDataModel;
import com.mingyuechunqiu.agile.base.presenter.BaseAbstractDataPresenter;
import com.mingyuechunqiu.agile.base.view.IBaseDataView;
import com.mingyuechunqiu.agile.feature.helper.ui.widget.ToolbarHelper;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/10/19
 *     desc   : 所有MVP层工具条界面的基类
 *              继承自BaseDataPresenterFragment
 *     version: 1.0
 * </pre>
 */
public abstract class BaseToolbarPresenterFragment<V extends IBaseDataView, P extends BaseAbstractDataPresenter<V, ? extends BaseAbstractDataModel>> extends BaseDataPresenterFragment<V, P> {

    @Nullable
    private Toolbar tbBar;
    @Nullable
    private ToolbarHelper.IToolbarInflateCreator mToolbarInflateCreator = null;
    @Nullable
    private ToolbarHelper.ToolbarConfig mToolbarConfig;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInflateToolbar(view);
        ActionBar actionBar = null;
        FragmentActivity activity = getActivity();
        if (activity instanceof AppCompatActivity) {
            setHasOptionsMenu(true);
            ((AppCompatActivity) activity).setSupportActionBar(tbBar);
            actionBar = ((AppCompatActivity) activity).getSupportActionBar();
        }
        if (mToolbarInflateCreator != null) {
            mToolbarConfig = mToolbarInflateCreator.initToolbarConfig();
            ToolbarHelper.initToolbar(tbBar, actionBar, mToolbarConfig);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (mToolbarConfig == null) {
            return;
        }
        if (mToolbarConfig.isClearActivityMenu()) {
            menu.clear();
        }
        if (mToolbarConfig.getMenuResId() != NO_RESOURCE_ID) {
            inflater.inflate(mToolbarConfig.getMenuResId(), menu);
            ToolbarHelper.applyMenuColorFilter(menu, mToolbarConfig.getMenuColorFilterColor());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mToolbarConfig = null;
    }

    /**
     * 初始化Toolbar控件
     *
     * @param view 父容器
     */
    private void initInflateToolbar(@NonNull View view) {
        mToolbarInflateCreator = generateToolbarInflateCreator();
        int resId = mToolbarInflateCreator.getInflateToolbarResId();
        if (resId != 0) {
            tbBar = view.findViewById(resId);
        }
        if (tbBar == null) {
            tbBar = mToolbarInflateCreator.getInflateToolbar();
        }
        onInitInflateToolbar(tbBar);
    }

    /**
     * 在初始化Toolbar时回调
     *
     * @param toolbar 初始化后的Toolbar控件
     */
    protected void onInitInflateToolbar(@Nullable Toolbar toolbar) {
    }

    /**
     * 获取拥有的Toolbar
     *
     * @return 返回Toolbar控件
     */
    @Nullable
    protected Toolbar getOwnedToolbar() {
        return tbBar;
    }

    /**
     * 生成Toolbar布局创建者（供子类实现）
     *
     * @return 返回创建者对象，非空
     */
    @NonNull
    protected abstract ToolbarHelper.IToolbarInflateCreator generateToolbarInflateCreator();
}
