package com.mingyuechunqiu.agile.ui.activity;

import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.mingyuechunqiu.agile.base.presenter.BaseAbstractDataPresenter;
import com.mingyuechunqiu.agile.base.view.IBaseDataView;
import com.mingyuechunqiu.agile.feature.helper.ToolbarHelper;

import static com.mingyuechunqiu.agile.constants.CommonConstants.NO_RESOURCE_ID;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/21
 *     desc   : 所有工具条界面的基类
 *              继承自BaseDataPresenterActivity
 *     version: 1.0
 * </pre>
 */
public abstract class BaseToolbarPresenterActivity<V extends IBaseDataView<P>, P extends BaseAbstractDataPresenter<?, ?>> extends BaseDataPresenterActivity<V, P> {

    @Nullable
    private Toolbar tbBar;
    @Nullable
    private ToolbarHelper.ToolbarConfigure mToolbarConfigure;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initInflateToolbar();
        setSupportActionBar(tbBar);
        mToolbarConfigure = initToolbarConfigure();
        ToolbarHelper.initToolbar(tbBar, getSupportActionBar(), mToolbarConfigure);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mToolbarConfigure != null && mToolbarConfigure.getMenuResId() != NO_RESOURCE_ID) {
            getMenuInflater().inflate(mToolbarConfigure.getMenuResId(), menu);
            ToolbarHelper.applyMenuColorFilter(menu, mToolbarConfigure.getMenuColorFilterColor());
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 初始化Toolbar控件
     */
    private void initInflateToolbar() {
        int resId = getInflateToolbarResId();
        if (resId != 0) {
            tbBar = findViewById(resId);
        }
        if (tbBar == null) {
            tbBar = getInflateToolbar();
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
     * 获取填充的Toolbar（在getInflateToolbarResId返回为0时，会被调用）
     *
     * @return 返回Toolbar控件
     */
    @Nullable
    protected Toolbar getInflateToolbar() {
        return null;
    }

    /**
     * 获取填充的Toolbar控件的资源ID
     *
     * @return 返回资源ID
     */
    @IdRes
    protected abstract int getInflateToolbarResId();

    /**
     * 供子类覆写的创建ToolbarBean方法，并放回创建好的ToolbarBean
     *
     * @return 返回创建好的ToolbarBean
     */
    @Nullable
    protected abstract ToolbarHelper.ToolbarConfigure initToolbarConfigure();
}
