package com.mingyuechunqiu.agilemvpframeproject.feature.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.mingyuechunqiu.agilemvpframe.ui.activity.BaseToolbarPresenterActivity;
import com.mingyuechunqiu.agilemvpframe.util.ToolbarUtils;
import com.mingyuechunqiu.agilemvpframeproject.R;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/11/8
 *     desc   : 主界面
 *              继承自BaseToolbarPresenterActivity
 *     version: 1.0
 * </pre>
 */
public class MainActivity extends BaseToolbarPresenterActivity<MainContract.View<MainContract.Presenter>, MainContract.Presenter>
        implements MainContract.View<MainContract.Presenter> {

    @Override
    public void setPresenter(@NonNull MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showToast(String hint) {
        super.showToast(hint);
    }

    @Override
    public void showToast(int stringResourceId) {
        super.showToast(stringResourceId);
    }

    @Override
    public Context getCurrentContext() {
        return this;
    }

    @Override
    protected ToolbarUtils.ToolbarBean setToolbarBean() {
        return new ToolbarUtils.ToolbarBean.Builder()
                .setImmerse(true)
                .build();
    }

    @Override
    protected MainContract.Presenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    public void showLoadingDialog(String hint, boolean cancelable) {
        super.showLoadingDialog(hint, cancelable);
    }

    @Override
    public void disappearLoadingDialog() {
        super.disappearLoadingDialog();
    }

    @Override
    protected void release() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.layout_navigation);
        mToolbar = findViewById(R.id.tb_navigation_bar);
        actvToolbarTitle = findViewById(R.id.tv_navigation_title);
        AppCompatImageView acivBack = findViewById(R.id.iv_navigation_left_icon);
        acivBack.setVisibility(View.VISIBLE);
        actvToolbarTitle.setText(R.string.app_name);
        setSupportActionBar(mToolbar);
    }
}
