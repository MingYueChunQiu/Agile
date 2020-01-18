package com.mingyuechunqiu.agile.ui.activity;

import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.mingyuechunqiu.agile.base.presenter.BaseAbstractDataPresenter;
import com.mingyuechunqiu.agile.base.view.IBaseDataView;
import com.mingyuechunqiu.agile.util.ToolbarUtils;

import static com.mingyuechunqiu.agile.constants.CommonConstants.NO_RESOURCE_ID;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/21
 *     desc   : 所有工具条界面的基类
 *              继承自BasePresenterActivity
 *     version: 1.0
 * </pre>
 */
public abstract class BaseToolbarPresenterActivity<V extends IBaseDataView<P>, P extends BaseAbstractDataPresenter> extends BaseNetPresenterActivity<V, P> {

    protected Toolbar tbBar;
    private ToolbarUtils.ToolbarConfigure mToolbarConfigure;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setSupportActionBar(tbBar);
        mToolbarConfigure = setToolbarConfigure();
        ToolbarUtils.initToolbar(tbBar, getSupportActionBar(), mToolbarConfigure);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mToolbarConfigure != null && mToolbarConfigure.getMenuResId() != NO_RESOURCE_ID) {
            getMenuInflater().inflate(mToolbarConfigure.getMenuResId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 供子类覆写的创建ToolbarBean方法，并放回创建好的ToolbarBean
     *
     * @return 返回创建好的ToolbarBean
     */
    protected abstract ToolbarUtils.ToolbarConfigure setToolbarConfigure();
}
