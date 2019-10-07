package com.mingyuechunqiu.agileproject.feature.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.mingyuechunqiu.agile.feature.loading.data.Constants;
import com.mingyuechunqiu.agile.feature.loading.data.LoadingDialogFragmentOption;
import com.mingyuechunqiu.agile.feature.loading.provider.LoadingDfgProviderable;
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;
import com.mingyuechunqiu.agile.ui.activity.BaseToolbarPresenterActivity;
import com.mingyuechunqiu.agile.ui.activity.WebViewActivity;
import com.mingyuechunqiu.agile.ui.fragment.BaseFragment;
import com.mingyuechunqiu.agile.util.ExitApplicationManager;
import com.mingyuechunqiu.agile.util.FragmentUtils;
import com.mingyuechunqiu.agile.util.StringUtils;
import com.mingyuechunqiu.agile.util.ToolbarUtils;
import com.mingyuechunqiu.agileproject.R;

import java.lang.ref.WeakReference;

import static com.mingyuechunqiu.agile.constants.CommonConstants.BUNDLE_NAVIGATION_TITLE;
import static com.mingyuechunqiu.agile.constants.CommonConstants.BUNDLE_RETURN_TO_PREVIOUS_PAGE;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_NAVIGATION_BG_COLOR;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_SHOW_BACK_DIALOG;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_TITLE_COLOR;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_TITLE_TEXT_SIZE;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_TITLE_VISIBLE;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_WEB_URL;

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
        implements MainContract.View<MainContract.Presenter>, View.OnClickListener, BaseFragment.Callback {

    private Fragment mSelectedFg;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
//            MediaUtils.VideoInfo info = MediaUtils.queryVideoInfo(getContentResolver(), data.getData());
//            Log.d("份", (info == null) + "" + info.getSize() + " " + info.getDuration());
//            if (info.getSize() > 1 * 1024 * 1024L){
//                showToast("视频不能大于");
//            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

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
    public boolean aloneView() {
        return super.aloneView();
    }

    @Override
    public MainContract.Presenter initPresenter() {
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
    public void showLoadingDialog(int containerId, LoadingDialogFragmentOption option) {
        super.showLoadingDialog(getSupportFragmentManager(), containerId, option);
    }

    @Override
    public void showLoadingDialog(@Nullable LoadingDialogFragmentOption option) {
        super.showLoadingDialog(option);
    }

    @Override
    public LoadingDfgProviderable getLoadingDialog() {
        return super.getCurrentLoadingDialog();
    }

    @Override
    protected ToolbarUtils.ToolbarBean setToolbarBean() {
        return new ToolbarUtils.ToolbarBean.Builder()
                .setImmerse(true)
                .build();
    }

    @Override
    protected void release() {
        Log.d("份", "销毁");
        ExitApplicationManager.exit();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        setDarkStatusBar();
        setLightStatusBar();
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.agile_layout_navigation);
        tbBar = findViewById(R.id.tb_navigation_bar);
        tbBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MediaUtils.startPickVideo(MainActivity.this, 2);
            }
        });
        AppCompatTextView tvToolbarTitle = findViewById(R.id.tv_navigation_title);
        AppCompatImageView ivBack = findViewById(R.id.iv_navigation_left_icon);
        ivBack.setVisibility(View.VISIBLE);
        tvToolbarTitle.setText(R.string.app_name);
        tvToolbarTitle.setVisibility(View.VISIBLE);
        setSupportActionBar(tbBar);
        FrameLayout clContainer = findViewById(R.id.fl_navigation_container);
        View view = getLayoutInflater().inflate(R.layout.activity_main, null);
        clContainer.addView(view);
        AppCompatTextView actvUrl = view.findViewById(R.id.tv_url);
        actvUrl.setText(StringUtils.createColorUrlSpan("我已阅读并同意《云海螺用户注册协议》",
                "《云海螺用户注册协议》", "http://www.ehailuo.com", Color.RED, new StringUtils.OnClickUrlLinkListener() {
                    @Override
                    public void onClickUrlLink(@NonNull String source, @NonNull String urlText, @NonNull String url) {
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
//        showLoadingDialog(option);

//        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.fl_navigation_container,
//                new MainFragment());
//        ShowDialogFragment fragment = new ShowDialogFragment();
//        fragment.show(getSupportFragmentManager(), ShowDialogFragment.class.getSimpleName());
        mSelectedFg = new StatusFragment();
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, mSelectedFg)
                .commit();
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
                    public void onClickUrlLink(@NonNull String s, @NonNull String s1, @NonNull String s2) {
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
//                getCurrentLoadingDialog().resetLoadingDialog();
//                getLoadingFragment().setLoadingBackground(new ColorDrawable(Color.RED));
////                getLoadingFragment().setContainerBackground(new ColorDrawable(Color.DKGRAY));
//                getLoadingFragment().setLoadingMessageColor(Color.BLUE);
//                getLoadingFragment().setLoadingMessage("O(∩_∩)O哈哈~");
//                showLoadingDialog(null);
//                getCurrentLoadingDialog().setThemeType(Constants.ThemeType.DARK_THEME);

//                showLoadingDialog("蜂王浆", true);
//                getCurrentLoadingDialog().setOnLoadingOptionListener(new LoadingDialogFragmentOption.OnLoadingOptionListener() {
//                    @Override
//                    public boolean onClickKeyBack(DialogInterface dialog) {
//                        showToast("单位");
//                        return false;
//                    }
//
//                    @Override
//                    public void onDismissListener(DialogFragment dialogFragment) {
//
//                    }
//                });

//                SmartMediaPicker.builder(this)
//                        //最大图片选择数目 如果不需要图片 将数目设置为0
//                        .withMaxImageSelectable(5)
//                        //最大视频选择数目 如果不需要视频 将数目设置为0
//                        .withMaxVideoSelectable(1)
//                        //图片选择器是否显示数字
//                        .withCountable(true)
//                        //最大视频长度
//                        .withMaxVideoLength(15 * 1000)
//                        //最大视频文件大小 单位MB
//                        .withMaxVideoSize(10)
//                        //最大图片高度 默认1920
//                        .withMaxHeight(1920)
//                        //最大图片宽度 默认1920
//                        .withMaxWidth(1920)
//                        //最大图片大小 单位MB
//                        .withMaxImageSize(5)
//                        //设置图片加载引擎
//                        .withImageEngine(new Glide4Engine())
//                        //前置摄像头拍摄是否镜像翻转图像 默认为true 与微信一致的话为false
//                        .withIsMirror(false)
//                        //弹出类别，默认弹出底部选择栏，也可以选择单独跳转
//                        .withMediaPickerType(MediaPickerEnum.BOTH)
//                        .build()
//                        .show();
                break;
            case R.id.btn_main_hide:
                dismissLoadingDialog();
                break;
        }
    }

    @Override
    public void onCall(Fragment fragment, Bundle bundle) {
        if (bundle == null) {
            return;
        }
        if (bundle.getBoolean(BUNDLE_RETURN_TO_PREVIOUS_PAGE)) {
            FragmentUtils.removeFragments(getSupportFragmentManager(), true,
                    R.anim.agile_alpha_slide_in_left, R.anim.agile_alpha_slide_out_right, mSelectedFg);
        }
    }
}
