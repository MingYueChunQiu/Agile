package com.mingyuechunqiu.agilemvpframe.ui.activity;

import android.content.DialogInterface;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
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

import com.mingyuechunqiu.agilemvpframe.R;
import com.mingyuechunqiu.agilemvpframe.base.presenter.BaseDialogPresenter;
import com.mingyuechunqiu.agilemvpframe.receiver.NetworkConnectedTypeReceiver;
import com.mingyuechunqiu.agilemvpframe.util.NetworkUtils;
import com.mingyuechunqiu.agilemvpframe.util.ToolbarUtils;

import static com.mingyuechunqiu.agilemvpframe.constants.CommonConstants.BUNDLE_NAVIGATION_TITLE;
import static com.mingyuechunqiu.agilemvpframe.constants.KeyPrefixConstants.KEY_BUNDLE;
import static com.mingyuechunqiu.agilemvpframe.util.ToolbarUtils.NO_RESOURCE_ID;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/07/03
 *     desc   : 网页浏览界面
 *              继承自BaseFullImmerseScreenActivity
 *     version: 1.0
 * </pre>
 */
public class WebViewActivity extends BaseToolbarPresenterActivity {

    //传递给本界面的网页地址
    public static final String BUNDLE_WEB_URL = KEY_BUNDLE + "web_url";
    //是否已桌面形式浏览网页
    public static final String BUNDLE_DESKTOP_MODE = KEY_BUNDLE + "desktop_mode";
    //是否是打开网页观看视频
    public static final String BUNDLE_WATCH_VIDEO = KEY_BUNDLE + "watch_video";

    private ProgressBar pbProgress;
    private WebView wvWeb;

    private boolean isSelectedMobileNet;//标记是否选择了使用移动网络
    private NetworkConnectedTypeReceiver mReceiver;

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
            wvWeb.clearHistory();
            ((ViewGroup) wvWeb.getParent()).removeAllViews();
            wvWeb.destroy();
            wvWeb = null;
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.layout_navigation);
        FrameLayout container = findViewById(R.id.fl_navigation_container);
        View view = getLayoutInflater().inflate(R.layout.fragment_web_view, container, false);
        mToolbar = findViewById(R.id.tb_navigation_bar);
        actvToolbarTitle = findViewById(R.id.tv_navigation_title);
        AppCompatImageView acivBack = findViewById(R.id.iv_navigation_left_icon);
        acivBack.setVisibility(View.VISIBLE);
        acivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(mToolbar);
        String title = getIntent().getStringExtra(BUNDLE_NAVIGATION_TITLE);
        if (!TextUtils.isEmpty(title)) {
            actvToolbarTitle.setText(title);
        }
        container.addView(view);
        wvWeb = view.findViewById(R.id.wv_web);
        pbProgress = view.findViewById(R.id.pb_web);
        initWebView();

        if (!NetworkUtils.checkNetState(this)) {
            showToast(R.string.network_disconnected);
            return;
        }
        //如果是为了观看视频，要判断一下是否是4G网络
        if (getIntent().getBooleanExtra(BUNDLE_WATCH_VIDEO, false)) {
            NetworkUtils.checkNetworkType(this, new NetworkUtils.OnCheckNetworkTypeListener() {
                @Override
                public void onConnectedInMobile() {
                }

                @Override
                public void onConnectedInWifi() {
                    registerNetworkTypeReceiver();
                    startLoadingWeb();
                }

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
    protected void onResume() {
        super.onResume();
        wvWeb.onResume();
    }

    @Override
    protected BaseDialogPresenter initPresenter() {
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wvWeb.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.prompt_exit)
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNegativeButton(R.string.cancel, null)
                    .create().show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected ToolbarUtils.ToolbarBean setToolbarBean() {
        return new ToolbarUtils.ToolbarBean.Builder()
                .setImmerse(true)
                .build();
    }

    @Override
    protected void attachPresenter() {
        //由于本界面不需要使用present，所以重写父类方法，不进行present相关设置
    }

    @Override
    protected void setStatusAndNavigationTransparent() {
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
     * 初始化WebView相关属性
     */
    private void initWebView() {
        WebSettings webSettings = wvWeb.getSettings();
        //下面两句话必须有，才能播放
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        if (getIntent().getBooleanExtra(BUNDLE_DESKTOP_MODE, false)) {
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
        mReceiver = new NetworkConnectedTypeReceiver();
        mReceiver.setOnNetworkTypeChangedListener(new NetworkConnectedTypeReceiver.OnNetworkTypeChangedListener() {
            @Override
            public void onNetworkTypeChanged(boolean isMobile) {
                if (isMobile) {
                    if (isSelectedMobileNet) {
                        showToast(R.string.prompt_use_mobile_network);
                    } else {
                        new AlertDialog.Builder(WebViewActivity.this)
                                .setCancelable(false)
                                .setMessage(R.string.prompt_query_mobile_network)
                                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        isSelectedMobileNet = true;
                                    }
                                })
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).create().show();
                    }
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
        String url = getIntent().getStringExtra(BUNDLE_WEB_URL);
        if (!TextUtils.isEmpty(url)) {
            wvWeb.loadUrl(url);
        }
    }
}
