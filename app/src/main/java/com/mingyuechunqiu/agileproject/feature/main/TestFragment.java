package com.mingyuechunqiu.agileproject.feature.main;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewConfigure;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewProgressOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;
import com.mingyuechunqiu.agile.feature.statusview.function.StatusViewManagerProvider;
import com.mingyuechunqiu.agile.ui.fragment.BaseStatusViewPresenterFragment;
import com.mingyuechunqiu.agile.util.ToastUtils;
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
public class TestFragment extends BaseStatusViewPresenterFragment<MainContract.View<MainContract.Presenter<?, ?>>, MainContract.Presenter<?, ?>>
        implements MainContract.View<MainContract.Presenter<?, ?>> {

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
//        showLoadingStatusView(R.id.fl_fragment_main_loading);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("份", "背包");
//        setLightStatusBar();
        setDarkStatusBar();
    }

    @Override
    public void setPresenter(@NonNull MainContract.Presenter<?, ?> presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showToast(@NonNull ToastUtils.ToastConfig config) {

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
    public void dismissStatusView() {
        super.dismissStatusView();
    }

    @Nullable
    @Override
    public MainContract.Presenter<?, ?> initPresenter() {
        return new MainPresenter();
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

    @Nullable
    @Override
    protected IInflateLayoutViewCreator generateInflateLayoutViewCreator() {
        return new IInflateLayoutViewCreator.InflateLayoutViewCreatorAdapter() {
            @Override
            public int getInflateLayoutId() {
                return R.layout.fragment_main;
            }
        };
    }
}
