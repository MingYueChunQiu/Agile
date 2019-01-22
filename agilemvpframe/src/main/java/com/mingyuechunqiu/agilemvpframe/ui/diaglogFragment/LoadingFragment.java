package com.mingyuechunqiu.agilemvpframe.ui.diaglogFragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mingyuechunqiu.agilemvpframe.R;

import static com.mingyuechunqiu.agilemvpframe.constants.KeyPrefixConstants.KEY_BUNDLE;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2019/1/22
 *     desc   : 用于加载显示的Fragment
 *              继承自Fragment
 *     version: 1.0
 * </pre>
 */
public class LoadingFragment extends DialogFragment {

    //是否可以触摸外围区域
    public static final String BUNDLE_AGILE_CAN_TOUCH_OUTSIDE = KEY_BUNDLE + "agile_can_touch_outside";
    //是否显示加载文本
    public static final String BUNDLE_AGILE_SHOW_FRAGMENT_LOADING = KEY_BUNDLE + "agile_show_fragment_text";
    //加载文本
    public static final String BUNDLE_AGILE_FRAGMENT_LOADING_TEXT = KEY_BUNDLE + "agile_fragment_loading_text";

    private LinearLayoutCompat llContainer;
    private ProgressBar pbLoading;
    private AppCompatTextView tvText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_loading, container, false);
        llContainer = view.findViewById(R.id.ll_agile_fg_loading_container);
        pbLoading = view.findViewById(R.id.pb_agile_fg_loading_progress);
        tvText = view.findViewById(R.id.tv_agile_fg_loading_text);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            init();
        }
    }

    /**
     * 设置加载背景
     *
     * @param drawable 背景图像对象
     */
    public void setLoadingBackground(final Drawable drawable) {
        if (drawable == null || llContainer == null) {
            return;
        }
        llContainer.post(new Runnable() {
            @Override
            public void run() {
                llContainer.setBackground(drawable);
            }
        });
    }

    /**
     * 设置无进度加载图像
     *
     * @param drawable 加载图像对象
     */
    public void setIndeterminateProgressDrawable(final Drawable drawable) {
        if (drawable == null || pbLoading == null || !pbLoading.isIndeterminate()) {
            return;
        }
        pbLoading.post(new Runnable() {
            @Override
            public void run() {
                pbLoading.setIndeterminateDrawable(drawable);
            }
        });
    }

    /**
     * 设置并显示加载文本信息
     *
     * @param msg 加载文本
     */
    public void setLoadingMessage(@Nullable final String msg) {
        if (TextUtils.isEmpty(msg) || tvText == null) {
            return;
        }
        //进行show和hide方法切换时，会先进队列执行onHiddenChanged，所以要讲绘制指令放进队列中
        tvText.post(new Runnable() {
            @Override
            public void run() {
                tvText.setText(msg);
                tvText.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 显示加载文本信息
     */
    public void showLoadingMessage() {
        if (tvText == null) {
            return;
        }
        tvText.post(new Runnable() {
            @Override
            public void run() {
                tvText.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 隐藏加载文本
     */
    public void hideLoadingMessage() {
        if (tvText == null) {
            return;
        }
        tvText.post(new Runnable() {
            @Override
            public void run() {
                tvText.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 初始化设置
     */
    private void init() {
        setCanTouchOutside();
        showLoadingText();
    }

    /**
     * 设置是否可以触摸外层区域
     */
    private void setCanTouchOutside() {
        if (getView() != null) {
            if (getArguments() != null && getArguments().getBoolean(BUNDLE_AGILE_CAN_TOUCH_OUTSIDE)) {
                getView().setClickable(false);
            } else {
                getView().setClickable(true);
            }
        }
    }

    /**
     * 显示加载文本
     */
    private void showLoadingText() {
        if (tvText == null) {
            return;
        }
        if (getArguments() != null && getArguments().getBoolean(BUNDLE_AGILE_SHOW_FRAGMENT_LOADING)) {
            String text = getArguments().getString(BUNDLE_AGILE_FRAGMENT_LOADING_TEXT);
            if (!TextUtils.isEmpty(text)) {
                tvText.setText(text);
            }
            tvText.setVisibility(View.VISIBLE);
        } else {
            tvText.setVisibility(View.GONE);
        }
    }
}
