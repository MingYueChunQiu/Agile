package com.mingyuechunqiu.agileproject.feature.main;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.helper.ui.hint.ToastHelper;
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewConfigure;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewProgressOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;
import com.mingyuechunqiu.agile.feature.statusview.function.StatusViewManagerProvider;
import com.mingyuechunqiu.agile.framework.ui.IFragmentInflateLayoutViewCreator;
import com.mingyuechunqiu.agile.ui.fragment.BasePresenterFragment;
import com.mingyuechunqiu.agileproject.R;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/5/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class TestFragment extends BasePresenterFragment<MainContract.View, MainContract.Presenter<MainContract.View, ?>>
        implements MainContract.View {

    @Override
    public void showLoadingStatusView(@Nullable String hint, boolean cancelable) {
        super.showLoadingStatusView(hint, cancelable);
    }

    @Override
    protected void releaseOnDestroyView() {

    }

    @Override
    protected void releaseOnDestroy() {

    }

    @Override
    protected void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {
        showLoadingStatusView("发的凤凰网", false);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                ToastHelper.showToast("范围分为非ewfwefew");
                return true;
            }
        });
//        showLoadingStatusView(R.id.fl_fragment_main_loading);
        TestDialogFragment fragment = new TestDialogFragment();
        fragment.show(getChildFragmentManager(), fragment.getPageTag());
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        LogManagerProvider.d("fewew", getPageTag());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("份", "背包");
//        setLightStatusBar();
        getWindowInsetsHelper().setDarkStatusBars();
    }

    @Override
    public void showToast(@NonNull ToastHelper.ToastConfig config) {

    }

    @Override
    public Context getCurrentContext() {
        return getContext();
    }

    @NonNull
    @Override
    public IStatusViewManager getStatusViewManager() {
        return super.getStatusViewManager();
    }

    @Override
    protected void onInitStatusViewManager(@NonNull IStatusViewManager manager) {
        super.onInitStatusViewManager(manager);
        StatusViewConfigure configure = new StatusViewConfigure.Builder().build();
        StatusViewOption option = StatusViewManagerProvider.getGlobalStatusViewOptionByType(StatusViewConstants.StatusViewType.TYPE_LOADING);
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

    @Nullable
    @Override
    protected IFragmentInflateLayoutViewCreator generateInflateLayoutViewCreator() {
        return new IFragmentInflateLayoutViewCreator.FragmentInflateLayoutViewCreatorAdapter() {
            @Override
            public int getInflateLayoutId() {
                return R.layout.fragment_main;
            }
        };
    }

    @Nullable
    @Override
    public MainContract.Presenter<MainContract.View, ?> initPresenter() {
        return new MainPresenter();
    }
}
