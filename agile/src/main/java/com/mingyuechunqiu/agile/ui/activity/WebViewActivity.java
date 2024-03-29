package com.mingyuechunqiu.agile.ui.activity;

import static com.mingyuechunqiu.agile.constants.AgileCommonConstants.BUNDLE_NAVIGATION_TITLE;
import static com.mingyuechunqiu.agile.constants.AgileKeyPrefixConstants.KEY_BUNDLE;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_DESKTOP_MODE;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_IS_SET_LEFT_ICON_SIZE;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_LEFT_ICON_HEIGHT;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_LEFT_ICON_WIDTH;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_NAVIGATION_BG_COLOR;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_SHOW_BACK_DIALOG;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_TITLE_COLOR;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_TITLE_TEXT_SIZE;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_TITLE_VISIBLE;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_WATCH_VIDEO;
import static com.mingyuechunqiu.agile.ui.activity.WebViewActivity.Constants.BUNDLE_WEB_URL;
import static com.mingyuechunqiu.agile.util.NetworkUtils.NetworkTypeConstants.NET_TYPE_WIFI;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.mingyuechunqiu.agile.R;
import com.mingyuechunqiu.agile.base.presenter.BaseAbstractDataPresenter;
import com.mingyuechunqiu.agile.base.view.IBaseDataView;
import com.mingyuechunqiu.agile.feature.helper.ui.widget.ToolbarHelper;
import com.mingyuechunqiu.agile.framework.ui.IActivityInflateLayoutViewCreator;
import com.mingyuechunqiu.agile.receiver.NetworkConnectedTypeReceiver;
import com.mingyuechunqiu.agile.util.NetworkUtils;

import org.jetbrains.annotations.NotNull;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/07/03
 *     desc   : 网页浏览界面
 *              继承自BaseToolbarPresenterActivity
 *     version: 1.0
 * </pre>
 */
public class WebViewActivity extends BaseToolbarPresenterActivity<IBaseDataView, BaseAbstractDataPresenter<IBaseDataView, ?>> {

    private static Drawable backDrawable;
    private ProgressBar pbProgress;
    private WebView wvWeb;

    private boolean isSelectedMobileNet;//标记是否选择了使用移动网络
    private NetworkConnectedTypeReceiver mReceiver;
    private Bundle mBundle;//配置信息

    @Override
    protected void release() {
        isSelectedMobileNet = false;
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
        //释放资源
        if (wvWeb != null) {
            wvWeb.loadDataWithBaseURL(null, "",
                    "text/html", "utf-8", null);
            ((ViewGroup) wvWeb.getParent()).removeAllViews();
            wvWeb.stopLoading();
            wvWeb.clearHistory();
            wvWeb.clearView();
            wvWeb.destroy();
            wvWeb = null;
        }
        if (mBundle != null) {
            mBundle.clear();
            mBundle = null;
        }
        backDrawable = null;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        FrameLayout container = findViewById(R.id.fl_navigation_container);
        View view = getLayoutInflater().inflate(R.layout.agile_fragment_web_view, container, false);
        AppCompatTextView tvToolbarTitle = findViewById(R.id.tv_navigation_title);
        AppCompatImageView ivBack = findViewById(R.id.iv_navigation_left_icon);
        ivBack.setVisibility(View.VISIBLE);
        if (backDrawable != null) {
            ivBack.setImageDrawable(backDrawable);
        }
        ivBack.setOnClickListener(v -> finish());
        mBundle = getIntent().getExtras();
        if (mBundle != null && mBundle.getBoolean(BUNDLE_TITLE_VISIBLE, false)) {
            tvToolbarTitle.setVisibility(View.VISIBLE);
            String title = mBundle.getString(BUNDLE_NAVIGATION_TITLE);
            if (!TextUtils.isEmpty(title)) {
                tvToolbarTitle.setText(title);
            }
            int titleColor = mBundle.getInt(BUNDLE_TITLE_COLOR, Color.WHITE);
            tvToolbarTitle.setTextColor(titleColor);
            int titleTextSize = mBundle.getInt(BUNDLE_TITLE_TEXT_SIZE, 20);
            tvToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSize);
        }
        if (mBundle != null) {
            if (mBundle.getBoolean(BUNDLE_IS_SET_LEFT_ICON_SIZE)) {
                int width = mBundle.getInt(BUNDLE_LEFT_ICON_WIDTH, Toolbar.LayoutParams.WRAP_CONTENT);
                int height = mBundle.getInt(BUNDLE_LEFT_ICON_HEIGHT, Toolbar.LayoutParams.WRAP_CONTENT);
                ivBack.setLayoutParams(new Toolbar.LayoutParams(width, height));
            }
        }
        container.addView(view);
        wvWeb = view.findViewById(R.id.wv_agile_web);
        pbProgress = view.findViewById(R.id.pb_agile_web);
        initWebView();

        if (!NetworkUtils.checkNetworkIsConnected()) {
            showToast(R.string.agile_network_disconnected);
            return;
        }
        //如果是为了观看视频，要判断一下是否是4G网络
        if (mBundle != null && mBundle.getBoolean(BUNDLE_WATCH_VIDEO, false)) {
            NetworkUtils.checkNetworkType(this, new NetworkUtils.OnCheckNetworkTypeListener() {
                @Override
                public void onDisconnectNetwork() {

                }

                @Override
                public void onConnectNetwork(int networkType) {
                    if (networkType == NET_TYPE_WIFI) {
                        registerNetworkTypeReceiver();
                        startLoadingWeb();
                    }
                }
            }, new NetworkUtils.OnSelectConnectInMobileListener() {
                @Override
                public void onConfirmConnectedInMobile() {
                    isSelectedMobileNet = true;
                    registerNetworkTypeReceiver();
                    startLoadingWeb();
                }

                @Override
                public void onCancelConnectedInMobile() {
                    finish();
                }
            });
        } else {
            startLoadingWeb();
        }
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        wvWeb.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        wvWeb.onPause();
    }

    @NonNull
    @Override
    protected IActivityInflateLayoutViewCreator generateInflateLayoutViewCreator() {
        return new IActivityInflateLayoutViewCreator.ActivityInflateLayoutViewCreatorAdapter() {
            @Override
            public int getInflateLayoutId() {
                return R.layout.agile_layout_navigation;
            }

            @Nullable
            @Override
            public View getInflateLayoutView() {
                return null;
            }
        };
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mBundle != null && mBundle.getBoolean(BUNDLE_SHOW_BACK_DIALOG, false)) {
                new AlertDialog.Builder(this)
                        .setMessage(R.string.agile_prompt_exit)
                        .setPositiveButton(R.string.agile_confirm, (dialog, which) -> finish())
                        .setNegativeButton(R.string.agile_cancel, null)
                        .create().show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onInitInflateToolbar(@Nullable Toolbar toolbar) {
        super.onInitInflateToolbar(toolbar);
        if (toolbar == null || mBundle == null) {
            return;
        }
        int toolbarBgColor = mBundle.getInt(BUNDLE_NAVIGATION_BG_COLOR,
                getResources().getColor(android.R.color.darker_gray));
        toolbar.setBackgroundColor(toolbarBgColor);
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
                        .build();
            }
        };
    }

    @Override
    protected void setTransparentStatusAndControlNavigationLayout() {
        //设置隐藏底部导航栏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 设置返回图标，在打开界面前调用
     *
     * @param drawable 图片
     */
    public static void setBackDrawable(Drawable drawable) {
        backDrawable = drawable;
    }

    /**
     * 初始化WebView相关属性
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings webSettings = wvWeb.getSettings();
        //下面两句话必须有，才能播放
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        if (mBundle != null && mBundle.getBoolean(BUNDLE_DESKTOP_MODE, false)) {
            webSettings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 " +
                    "(KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36");
        }
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setUseWideViewPort(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        wvWeb.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    pbProgress.setVisibility(View.GONE);
                } else {
                    pbProgress.setVisibility(View.VISIBLE);
                    pbProgress.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        wvWeb.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    /**
     * 注册网络连接类型改变广播
     */
    private void registerNetworkTypeReceiver() {
        mReceiver = new NetworkConnectedTypeReceiver(isMobile -> {
            if (isMobile) {
                if (isSelectedMobileNet) {
                    showToast(R.string.agile_prompt_use_mobile_network);
                } else {
                    new AlertDialog.Builder(WebViewActivity.this)
                            .setCancelable(false)
                            .setMessage(R.string.agile_prompt_query_mobile_network)
                            .setPositiveButton(R.string.agile_confirm, (dialog, which) -> isSelectedMobileNet = true)
                            .setNegativeButton(R.string.agile_cancel, (dialog, which) -> finish()).create().show();
                }
            }
        });
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, intentFilter);
    }

    /**
     * 开始加载网页
     */
    private void startLoadingWeb() {
        if (mBundle == null) {
            return;
        }
        String url = getIntent().getStringExtra(BUNDLE_WEB_URL);
        if (!TextUtils.isEmpty(url)) {
            wvWeb.loadUrl(url);
        }
    }

    @Override
    public void setPresenter(@NonNull @NotNull BaseAbstractDataPresenter<IBaseDataView, ?> presenter) {

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public BaseAbstractDataPresenter<IBaseDataView, ?> initPresenter() {
        return null;
    }

    /**
     * 常量类
     */
    public static class Constants {
        public static final String BUNDLE_TITLE_COLOR = KEY_BUNDLE + "title_color";
        public static final String BUNDLE_TITLE_TEXT_SIZE = KEY_BUNDLE + "title_text_size";
        public static final String BUNDLE_TITLE_VISIBLE = KEY_BUNDLE + "title_visible";
        public static final String BUNDLE_NAVIGATION_BG_COLOR = KEY_BUNDLE + "navigation_bg_color";
        public static final String BUNDLE_IS_SET_LEFT_ICON_SIZE = KEY_BUNDLE + "is_set_left_icon_size";
        //左边按钮的宽和高大小
        public static final String BUNDLE_LEFT_ICON_WIDTH = KEY_BUNDLE + "navigation_icon_width";
        public static final String BUNDLE_LEFT_ICON_HEIGHT = KEY_BUNDLE + "navigation_icon_height";
        //传递给本界面的网页地址
        public static final String BUNDLE_WEB_URL = KEY_BUNDLE + "web_url";
        //是否已桌面形式浏览网页
        public static final String BUNDLE_DESKTOP_MODE = KEY_BUNDLE + "desktop_mode";
        //是否是打开网页观看视频
        public static final String BUNDLE_WATCH_VIDEO = KEY_BUNDLE + "watch_video";
        //是否退出时显示退出对话框
        public static final String BUNDLE_SHOW_BACK_DIALOG = KEY_BUNDLE + "show_back_dialog";
    }
}
