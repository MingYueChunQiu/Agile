package com.mingyuechunqiu.agileproject.feature.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewConfigure;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;
import com.mingyuechunqiu.agile.feature.statusview.function.StatusViewManagerProvider;
import com.mingyuechunqiu.agile.ui.activity.BaseToolbarPresenterActivity;
import com.mingyuechunqiu.agile.util.ToolbarUtils;
import com.mingyuechunqiu.agileproject.R;

/**
 * <pre>
 *      Project:    Agile
 *
 *      author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-10-10 23:55
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
public class ToolbarActivity extends BaseToolbarPresenterActivity {

    @Override
    protected int getInflateToolbarResId() {
        return R.id.tb_toolbar_test;
    }

    @Override
    protected ToolbarUtils.ToolbarConfigure initToolbarConfigure() {
        return new ToolbarUtils.ToolbarConfigure.Builder()
                .setImmerse(true)
                .setNavigationIconResId(R.drawable.agile_arrow_back_press)
                .setTitle("分为非")
//                .setEnableDisplayHomeAsUp(true)
                .setHideDisplayTitle(false)
                .setOnNavigationIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ToolbarActivity.this, MainActivity.class));
                    }
                })
                .setLogoResId(R.drawable.agile_arrow_back_pressed)
                .build();

    }

    @Override
    public Object initPresenter() {
        return null;
    }

    @Override
    protected void release() {

    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_toolbar_test);
//        StatusViewOption option = StatusViewManagerProvider.getGlobalStatusViewOptionByType(StatusViewConstants.StatusType.TYPE_ERROR);
//        option.setContainerElevation(ScreenUtils.getPxFromDp(getResources(), 2F));
//        option.setDialogAnimationResId(R.style.PopupAnimation);
//        showStatusView(StatusViewConstants.StatusType.TYPE_ERROR, getSupportFragmentManager(), option);

//        TestAppDialog dialog = new TestAppDialog(this);
//        dialog.setCancelable(false);
//        dialog.show();

//        showStatusView(StatusViewConstants.StatusType.TYPE_ERROR, getSupportFragmentManager(),
//                R.id.fl_toolbar_test_container, option);
        showLoadingStatusView("发的凤凰网", false);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_toolbar_test_container, new Test())
                .commitAllowingStateLoss();
    }

    @Override
    protected void onInitStatusViewManager(@NonNull IStatusViewManager manager) {
        super.onInitStatusViewManager(manager);
        StatusViewConfigure configure = new StatusViewConfigure.Builder().build();
        StatusViewOption option = StatusViewManagerProvider.getGlobalStatusViewOptionByType(StatusViewConstants.StatusType.TYPE_LOADING);
        configure.setLoadingOption(option);
        option.getContentOption().setText("废物范围蜂窝网");
//        option.setProgressOption(new StatusViewProgressOption.Builder()
//                .setProgressStyle(StatusViewConstants.ProgressStyle.STYLE_DAISY)
//                .setProgressSize((int) ScreenUtils.getPxFromDp(getResources(), 70F))
//                .setDaisyColor(Color.BLUE)
//                .build());
        manager.applyStatusViewConfigure(configure);
    }
}
