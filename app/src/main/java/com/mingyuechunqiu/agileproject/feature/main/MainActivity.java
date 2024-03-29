package com.mingyuechunqiu.agileproject.feature.main;

import static com.mingyuechunqiu.agile.constants.AgileCommonConstants.BUNDLE_NAVIGATION_TITLE;
import static com.mingyuechunqiu.agile.constants.AgileCommonConstants.BUNDLE_RETURN_TO_PREVIOUS_PAGE;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_NAVIGATION_BG_COLOR;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_SHOW_BACK_DIALOG;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_TITLE_COLOR;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_TITLE_TEXT_SIZE;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_TITLE_VISIBLE;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_WEB_URL;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.mingyuechunqiu.agile.feature.helper.ui.widget.ToolbarHelper;
import com.mingyuechunqiu.agile.feature.helper.ui.hint.ToastHelper;
import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.feature.statusview.function.IStatusViewManager;
import com.mingyuechunqiu.agile.feature.statusview.function.StatusViewManagerProvider;
import com.mingyuechunqiu.agile.feature.statusview.ui.view.IStatusView;
import com.mingyuechunqiu.agile.frame.AgileExitAppManager;
import com.mingyuechunqiu.agile.framework.ui.IActivityInflateLayoutViewCreator;
import com.mingyuechunqiu.agile.ui.activity.BaseToolbarPresenterActivity;
import com.mingyuechunqiu.agile.ui.activity.WebViewActivity;
import com.mingyuechunqiu.agile.feature.helper.ui.fragment.FragmentHelper;
import com.mingyuechunqiu.agile.util.StringUtils;
import com.mingyuechunqiu.agileproject.R;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

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
public class MainActivity extends BaseToolbarPresenterActivity<MainContract.View, MainContract.Presenter<MainContract.View, ?>>
        implements MainContract.View, View.OnClickListener {

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
    public void showToast(@NonNull ToastHelper.ToastConfig config) {
        super.showToast(config);
    }

    @Override
    public Context getCurrentContext() {
        return this;
    }

    @Override
    public void showLoadingStatusView(String hint, boolean cancelable) {
        super.showLoadingStatusView(hint, cancelable);
    }

    @Override
    protected void release() {
        Log.d("份", "销毁");
        AgileExitAppManager.getInstance().exit();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        setDarkStatusBar();
        getWindowInsetsHelper().setLightStatusBars();
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        AppCompatTextView tvToolbarTitle = findViewById(R.id.tv_navigation_title);
        AppCompatImageView ivBack = findViewById(R.id.iv_navigation_left_icon);
        ivBack.setVisibility(View.VISIBLE);
        tvToolbarTitle.setText(R.string.app_name);
        tvToolbarTitle.setVisibility(View.VISIBLE);
        FrameLayout clContainer = findViewById(R.id.fl_navigation_container);
        View view = getLayoutInflater().inflate(R.layout.activity_main, null, false);
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
        StatusViewOption option = new StatusViewOption.Builder()
//                .setLoadingBackground(new ColorDrawable(Color.RED))
//                .setText("放大")
//                .setDialogWidth(400)
//                .setDialogHeight(500)
                .setCancelWithOutside(true)
//                .setOnStatusViewDialogListener(new StatusViewOption.OnStatusViewDialogListener() {
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
//        showLoadingStatusView(option);

//        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.fl_navigation_container,
//                new MainFragment());
//        ShowDialogFragment fragment = new ShowDialogFragment();
//        fragment.show(getSupportFragmentManager(), ShowDialogFragment.class.getSimpleName());
        mSelectedFg = new StatusFragment();
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, mSelectedFg)
                .commit();
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        getTransferPageDataReceiverHelper().addTransferDataReceiverListener((dataOwner, data) -> {
            if (data == null) {
                return;
            }
            if (data.getBundle().getBoolean(BUNDLE_RETURN_TO_PREVIOUS_PAGE)) {
                FragmentHelper.removeFragments(getSupportFragmentManager(), true,
                        R.anim.agile_alpha_slide_in_left, R.anim.agile_alpha_slide_out_right, mSelectedFg);
            }
        });
    }

    @Override
    protected void onInitInflateToolbar(@Nullable Toolbar toolbar) {
        super.onInitInflateToolbar(toolbar);
        if (toolbar == null) {
            return;
        }
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MediaUtils.startPickVideo(MainActivity.this, 2);
            }
        });
    }

    @NonNull
    @Override
    protected ToolbarHelper.IToolbarInflateCreator generateToolbarInflateCreator() {
        return new ToolbarHelper.IToolbarInflateCreator.ToolbarInflateCreatorAdapter() {

            @Override
            public int getInflateToolbarResId() {
                return R.id.tb_navigation_bar;
            }

            @NonNull
            @Override
            public ToolbarHelper.ToolbarConfig initToolbarConfig() {
                return new ToolbarHelper.ToolbarConfig.Builder()
                        .setImmerse(true)
                        .setNavigationIconResId(R.drawable.agile_arrow_back_pressed)
                        .setTitle("分为非")
                        .build();
            }
        };
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
                StatusViewOption option = StatusViewManagerProvider.getGlobalStatusViewOptionByType(StatusViewConstants.StatusViewType.TYPE_LOADING);
                option.setOnStatusViewDialogListener(new StatusViewOption.OnStatusViewDialogListener() {
                    @Override
                    public boolean onClickKeyBack(@NonNull @NotNull IStatusView view) {
                        return false;
                    }

                    @Override
                    public void onDismissListener(@NonNull @NotNull IStatusView view) {
                        Toast.makeText(MainActivity.this, "关闭", Toast.LENGTH_SHORT).show();
                    }
                });
                option.setOnStatusViewButtonListener(new StatusViewOption.OnStatusViewButtonListener() {
                    @Override
                    public void onClickReload() {
                        Toast.makeText(MainActivity.this, "重点", Toast.LENGTH_SHORT).show();
                    }
                });
//                showLoadingStatusView("测试", false);
                getStatusViewManager().showStatusView(StatusViewConstants.StatusViewType.TYPE_LOADING, this, option);
//                showLoadingStatusView(R.id.fl_main_container);

//                getCurrentLoadingDialog().resetLoadingDialog();
//                getLoadingFragment().setLoadingBackground(new ColorDrawable(Color.RED));
////                getLoadingFragment().setContainerBackground(new ColorDrawable(Color.DKGRAY));
//                getLoadingFragment().setLoadingMessageColor(Color.BLUE);
//                getLoadingFragment().setLoadingMessage("O(∩_∩)O哈哈~");
//                showLoadingStatusView(null);
//                getCurrentLoadingDialog().setThemeType(StatusViewConstants.ThemeType.DARK_THEME);

//                showLoadingStatusView("蜂王浆", true);
//                getCurrentLoadingDialog().setOnStatusViewDialogListener(new StatusViewOption.OnStatusViewDialogListener() {
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
                dismissStatusView();
                break;
        }
    }

    @NonNull
    @Override
    public IStatusViewManager getStatusViewManager() {
        return super.getStatusViewManager();
    }

    @NonNull
    @Override
    protected IActivityInflateLayoutViewCreator generateInflateLayoutViewCreator() {
        return new IActivityInflateLayoutViewCreator.ActivityInflateLayoutViewCreatorAdapter() {
            @Override
            public int getInflateLayoutId() {
                return R.layout.agile_layout_navigation;
            }
        };
    }

    @Nullable
    @Override
    public MainContract.Presenter<MainContract.View, ?> initPresenter() {
        return null;
    }
}
