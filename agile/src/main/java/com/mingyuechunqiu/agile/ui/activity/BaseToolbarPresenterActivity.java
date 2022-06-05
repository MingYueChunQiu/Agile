package com.mingyuechunqiu.agile.ui.activity;

import static com.mingyuechunqiu.agile.constants.AgileCommonConstants.NO_RESOURCE_ID;

import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.mingyuechunqiu.agile.base.model.BaseAbstractDataModel;
import com.mingyuechunqiu.agile.base.presenter.BaseAbstractDataPresenter;
import com.mingyuechunqiu.agile.base.view.IBaseDataView;
import com.mingyuechunqiu.agile.feature.helper.ui.widget.ToolbarHelper;

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
public abstract class BaseToolbarPresenterActivity<V extends IBaseDataView, P extends BaseAbstractDataPresenter<V, ? extends BaseAbstractDataModel>> extends BaseDataPresenterActivity<V, P> {

    @Nullable
    private Toolbar tbBar;
    @Nullable
    private ToolbarHelper.IToolbarInflateCreator mToolbarInflateCreator = null;
    @Nullable
    private ToolbarHelper.ToolbarConfig mToolbarConfig;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initInflateToolbar();
        setSupportActionBar(tbBar);
        if (mToolbarInflateCreator != null) {
            mToolbarConfig = mToolbarInflateCreator.initToolbarConfig();
            ToolbarHelper.initToolbar(tbBar, getSupportActionBar(), mToolbarConfig);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mToolbarConfig != null && mToolbarConfig.getMenuResId() != NO_RESOURCE_ID) {
            getMenuInflater().inflate(mToolbarConfig.getMenuResId(), menu);
            ToolbarHelper.applyMenuColorFilter(menu, mToolbarConfig.getMenuColorFilterColor());
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 初始化Toolbar控件
     */
    private void initInflateToolbar() {
        mToolbarInflateCreator = generateToolbarInflateCreator();
        int resId = mToolbarInflateCreator.getInflateToolbarResId();
        if (resId != 0) {
            tbBar = findViewById(resId);
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
