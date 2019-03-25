package com.mingyuechunqiu.agilemvpframeproject.feature.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.FrameLayout;

import com.mingyuechunqiu.agilemvpframe.feature.loading.data.Constants;
import com.mingyuechunqiu.agilemvpframe.feature.loading.provider.LoadingDfgProviderable;
import com.mingyuechunqiu.agilemvpframe.feature.loading.data.LoadingDialogFragmentOption;
import com.mingyuechunqiu.agilemvpframe.feature.logmanager.LogManagerProvider;
import com.mingyuechunqiu.agilemvpframe.ui.activity.BaseToolbarPresenterActivity;
import com.mingyuechunqiu.agilemvpframe.ui.activity.WebViewActivity;
import com.mingyuechunqiu.agilemvpframe.util.StringUtils;
import com.mingyuechunqiu.agilemvpframe.util.ToolbarUtils;
import com.mingyuechunqiu.agilemvpframeproject.R;

import java.lang.ref.WeakReference;

import static com.mingyuechunqiu.agilemvpframe.constants.CommonConstants.BUNDLE_NAVIGATION_TITLE;
import static com.mingyuechunqiu.agilemvpframe.ui.activity.WebViewActivity.Constants.BUNDLE_NAVIGATION_BG_COLOR;
import static com.mingyuechunqiu.agilemvpframe.ui.activity.WebViewActivity.Constants.BUNDLE_SHOW_BACK_DIALOG;
import static com.mingyuechunqiu.agilemvpframe.ui.activity.WebViewActivity.Constants.BUNDLE_TITLE_COLOR;
import static com.mingyuechunqiu.agilemvpframe.ui.activity.WebViewActivity.Constants.BUNDLE_TITLE_TEXT_SIZE;
import static com.mingyuechunqiu.agilemvpframe.ui.activity.WebViewActivity.Constants.BUNDLE_TITLE_VISIBLE;
import static com.mingyuechunqiu.agilemvpframe.ui.activity.WebViewActivity.Constants.BUNDLE_WEB_URL;

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
        implements MainContract.View<MainContract.Presenter>, View.OnClickListener {

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
    public void dismissLoadingDialog() {
        super.dismissLoadingDialog();
    }

    @Override
    public void addOrShowLoadingDialog(int containerId, LoadingDialogFragmentOption option) {
        super.addOrShowLoadingDialog(getSupportFragmentManager(), containerId, option);
    }

    @Override
    public void hideLoadingDialog() {
        super.hideLoadingDialog(getSupportFragmentManager());
    }

    @Override
    public void showLoadingDialog(@Nullable LoadingDialogFragmentOption option) {
        super.showLoadingDialog(option);
    }

    @NonNull
    @Override
    public LoadingDfgProviderable getLoadingDialog() {
        return super.getLoadingDialog();
    }

    @Override
    protected void release() {

    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.agile_layout_navigation);
        mToolbar = findViewById(R.id.tb_navigation_bar);
        actvToolbarTitle = findViewById(R.id.tv_navigation_title);
        AppCompatImageView acivBack = findViewById(R.id.iv_navigation_left_icon);
        acivBack.setVisibility(View.VISIBLE);
        actvToolbarTitle.setText(R.string.app_name);
        actvToolbarTitle.setVisibility(View.VISIBLE);
        setSupportActionBar(mToolbar);
        FrameLayout clContainer = findViewById(R.id.fl_navigation_container);
        View view = getLayoutInflater().inflate(R.layout.activity_main, null);
        clContainer.addView(view);
        AppCompatTextView actvUrl = view.findViewById(R.id.tv_url);
        actvUrl.setText(StringUtils.createColorUrlSpan("我已阅读并同意《云海螺用户注册协议》",
                "《云海螺用户注册协议》", "http://www.ehailuo.com", Color.RED, new StringUtils.OnClickUrlLinkListener() {
                    @Override
                    public void onClickUrlLink(String source, String urlText, String url) {
                        showToast("点击了链接" + url);
                    }
                }));
        actvUrl.setMovementMethod(LinkMovementMethod.getInstance());
        showCompact(actvUrl);
        AppCompatButton btnWeb = findViewById(R.id.btn_web);
        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra(BUNDLE_WEB_URL, "http://www.ehailuo.com");
                startActivity(intent);
            }
        });
        AppCompatButton btnShow = view.findViewById(R.id.btn_main_show);
        AppCompatButton btnHide = view.findViewById(R.id.btn_main_hide);
        btnShow.setOnClickListener(this);
        btnHide.setOnClickListener(this);
        LoadingDialogFragmentOption option = new LoadingDialogFragmentOption.Builder()
//                .setLoadingBackground(new ColorDrawable(Color.RED))
//                .setText("放大")
                .setThemeType(Constants.ThemeType.DARK_THEME)
//                .setDialogWidth(400)
//                .setDialogHeight(500)
                .setCancelWithOutside(true)
//                .setOnLoadingOptionListener(new LoadingDialogFragmentOption.OnLoadingOptionListener() {
//                    @Override
//                    public void onClickKeyBack(DialogInterface dialog) {
//                        showToast("哈哈");
////                        finish();
//                        dismissLoadingDialog();
//                    }
//
//                    @Override
//                    public void onDismissListener(DialogFragment dialogFragment) {
//                        showToast("任务分为");
//                    }
//                })
                .build();
        showLoadingDialog(option);
//        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.fl_navigation_container,
//                new MainFragment());
    }

    /**
     * 显示公司协议
     *
     * @param v 文本控件
     */
    public void showCompact(AppCompatTextView v) {
        if (v == null) {
            return;
        }
        final WeakReference<Context> ref = new WeakReference<>(v.getContext());
        v.setText(StringUtils.createColorUrlSpan("我已阅读并同意《云海螺用户注册协议》",
                "《云海螺用户注册协议》", "http://www.ehailuo.com",
                v.getResources().getColor(android.R.color.holo_red_dark), new StringUtils.OnClickUrlLinkListener() {
                    @Override
                    public void onClickUrlLink(String s, String s1, String s2) {
                        LogManagerProvider.d("放大", s2 + " " + (ref.get() == null));
                        if (ref.get() != null) {
                            Intent intent = new Intent(ref.get(), WebViewActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(BUNDLE_WEB_URL, s2);
                            bundle.putInt(BUNDLE_NAVIGATION_BG_COLOR, Color.GREEN);
                            bundle.putInt(BUNDLE_TITLE_COLOR, Color.RED);
                            bundle.putString(BUNDLE_NAVIGATION_TITLE, "辅导费");
                            bundle.putBoolean(BUNDLE_TITLE_VISIBLE, true);
                            bundle.putInt(BUNDLE_TITLE_TEXT_SIZE, 12);
                            bundle.putBoolean(BUNDLE_SHOW_BACK_DIALOG, false);
                            intent.putExtras(bundle);
                            WebViewActivity.setBackDrawable(getResources().getDrawable(R.drawable.agile_arrow_back_press));
                            ref.get().startActivity(intent);
                        }
                    }
                }));
        v.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_show:
//                getLoadingDialog().resetLoadingDialog();
//                getLoadingFragment().setLoadingBackground(new ColorDrawable(Color.RED));
////                getLoadingFragment().setContainerBackground(new ColorDrawable(Color.DKGRAY));
//                getLoadingFragment().setLoadingMessageColor(Color.BLUE);
//                getLoadingFragment().setLoadingMessage("O(∩_∩)O哈哈~");
//                showLoadingDialog(null);
//                getLoadingDialog().setThemeType(Constants.ThemeType.DARK_THEME);

                showLoadingDialog("蜂王浆", true);
                getLoadingDialog().setOnLoadingOptionListener(new LoadingDialogFragmentOption.OnLoadingOptionListener() {
                    @Override
                    public boolean onClickKeyBack(DialogInterface dialog) {
                        showToast("单位");
                        return false;
                    }

                    @Override
                    public void onDismissListener(DialogFragment dialogFragment) {

                    }
                });
                break;
            case R.id.btn_main_hide:
                dismissLoadingDialog();
                break;
        }
    }
}
