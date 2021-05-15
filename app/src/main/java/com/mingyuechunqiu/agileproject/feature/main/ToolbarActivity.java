package com.mingyuechunqiu.agileproject.feature.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.presenter.BaseAbstractDataPresenter;
import com.mingyuechunqiu.agile.base.view.IBaseDataView;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewConfigure;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewProgressOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;
import com.mingyuechunqiu.agile.feature.statusview.function.StatusViewManagerProvider;
import com.mingyuechunqiu.agile.framework.ui.IActivityInflateLayoutViewCreator;
import com.mingyuechunqiu.agile.ui.activity.BaseToolbarPresenterActivity;
import com.mingyuechunqiu.agile.feature.helper.ToolbarHelper;
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
public class ToolbarActivity extends BaseToolbarPresenterActivity<IBaseDataView, BaseAbstractDataPresenter<IBaseDataView, ?>> {

    @Override
    protected int getInflateToolbarResId() {
        return R.id.tb_toolbar_test;
    }

    @Override
    protected ToolbarHelper.ToolbarConfigure initToolbarConfigure() {
        return new ToolbarHelper.ToolbarConfigure.Builder()
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
    protected void release() {

    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
//        StatusViewOption option = StatusViewManagerProvider.getGlobalStatusViewOptionByType(StatusViewConstants.StatusType.TYPE_ERROR);
//        option.setContainerElevation(ScreenUtils.getPxFromDp(getResources(), 2F));
//        option.setDialogAnimationResId(R.style.PopupAnimation);
//        showStatusView(StatusViewConstants.StatusType.TYPE_ERROR, getSupportFragmentManager(), option);

//        TestAppDialog dialog = new TestAppDialog(this);
//        dialog.setCancelable(false);
//        dialog.show();

//        showStatusView(StatusViewConstants.StatusType.TYPE_ERROR, getSupportFragmentManager(),
//                R.id.fl_toolbar_test_container, option);
//        showLoadingStatusView("发的凤凰网", false);
//        Observable.timer(5000, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnComplete(new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        dismissStatusView();
//                        showLoadingStatusView("发的凤凰网", false);
//                    }
//                })
//                .subscribe();
//        showLoadingStatusView(R.id.fl_toolbar_test_container);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_toolbar_test_container, new TestFragment())
                .commitAllowingStateLoss();

    }

    @Override
    protected void onInitStatusViewManager(@NonNull IStatusViewManager manager) {
        super.onInitStatusViewManager(manager);
        StatusViewConfigure configure = new StatusViewConfigure.Builder().build();
        StatusViewOption option = StatusViewManagerProvider.getGlobalStatusViewOptionByType(StatusViewConstants.StatusType.TYPE_LOADING);
        configure.setLoadingOption(option);
        StatusViewOption.Builder builder = option.getBuilder();
        builder.getContentOption().setText("废物范围蜂窝网");
        builder.setShowContentText(false)
                .setDialogDimAmount(0.5F)
                .setContainerBackground(new ColorDrawable(Color.TRANSPARENT))
                .setProgressOption(new StatusViewProgressOption.Builder()
                        .setProgressStyle(StatusViewConstants.ProgressStyle.STYLE_DAISY)
//                .setProgressSize((int) ScreenUtils.getPxFromDp(getResources(), 70F))
//                .setDaisyColor(Color.BLUE)
                        .build());
        manager.applyStatusViewConfigure(configure);
    }

    @NonNull
    @Override
    protected IActivityInflateLayoutViewCreator generateInflateLayoutViewCreator() {
        return new IActivityInflateLayoutViewCreator.ActivityInflateLayoutViewCreatorAdapter() {
            @Override
            public int getInflateLayoutId() {
                return R.layout.activity_toolbar_test;
            }

            @Nullable
            @Override
            public View getInflateLayoutView() {
                return null;
            }
        };
    }

    @Nullable
    @Override
    public BaseAbstractDataPresenter<IBaseDataView, ?> initPresenter() {
        return null;
    }
}
