package com.mingyuechunqiu.agile.feature.loading.function;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mingyuechunqiu.agile.feature.loading.data.LoadingDialogFragmentOption;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2019/1/23
 *     desc   : 加载Fragment实际操作代理类
 *              实现LoadingFragmentable
 *     version: 1.0
 * </pre>
 */
class LoadingDfgDelegate implements LoadingDfgFunctionable {

    private final List<Runnable> mActions = new ArrayList<>();

    private WeakReference<Dialog> dialogRef;
    private WeakReference<View> vLoadingContainerRef;
    private WeakReference<ProgressBar> pbLoadingRef;
    private WeakReference<TextView> tvTextRef;

    private LoadingDialogFragmentOption mOption;
    private Handler mHandler;

    /**
     * 初始化参数
     *
     * @param dialog           对话框实例
     * @param loadingContainer 加载容器
     * @param progressBar      进度控件
     * @param textView         加载文本控件
     */
    void initialize(Dialog dialog, View loadingContainer, ProgressBar progressBar, TextView textView) {
        dialogRef = new WeakReference<>(dialog);
        vLoadingContainerRef = new WeakReference<>(loadingContainer);
        pbLoadingRef = new WeakReference<>(progressBar);
        tvTextRef = new WeakReference<>(textView);
    }

    @Override
    public void release() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        mActions.clear();
        mOption = null;
        dialogRef = null;
        vLoadingContainerRef = null;
        pbLoadingRef = null;
        tvTextRef = null;
    }

    @Override
    public void setCanCancelWithOutside(final boolean canCancelWithOutside) {
        checkOrCreateOption();
        mOption.setCancelWithOutside(canCancelWithOutside);
        post(new Runnable() {
            @Override
            public void run() {
                if (dialogRef.get() == null) {
                    return;
                }
                dialogRef.get().setCancelable(canCancelWithOutside);
            }
        });
    }

    @Override
    public void setDialogSize(final int width, final int height) {
        if (width == 0 && height == 0) {
            return;
        }
        checkOrCreateOption();
        mOption.setDialogWidth(width);
        mOption.setDialogHeight(height);
        post(new Runnable() {
            @Override
            public void run() {
                if (dialogRef.get() == null) {
                    return;
                }
                Window window = dialogRef.get().getWindow();
                if (window != null) {
                    window.setLayout(width > 0 ? width : 0, height > 0 ? height : 0);
                }
            }
        });
    }

    @Override
    public void setLoadingBackground(final Drawable drawable) {
        if (drawable == null) {
            return;
        }
        checkOrCreateOption();
        mOption.setLoadingBackground(drawable);
        post(new Runnable() {
            @Override
            public void run() {
                if (vLoadingContainerRef.get() == null) {
                    return;
                }
                vLoadingContainerRef.get().setBackground(drawable);
            }
        });
    }

    @Override
    public void setIndeterminateProgressDrawable(final Drawable drawable) {
        if (drawable == null) {
            return;
        }
        checkOrCreateOption();
        mOption.setIndeterminateDrawable(drawable);
        post(new Runnable() {
            @Override
            public void run() {
                if (pbLoadingRef.get() == null || !pbLoadingRef.get().isIndeterminate()) {
                    return;
                }
                pbLoadingRef.get().setIndeterminateDrawable(drawable);
            }
        });
    }

    @Override
    public void setShowLoadingMessage(final boolean showLoadingMessage) {
        post(new Runnable() {
            @Override
            public void run() {
                if (tvTextRef.get() == null) {
                    return;
                }
                if (showLoadingMessage) {
                    tvTextRef.get().setVisibility(View.VISIBLE);
                } else {
                    tvTextRef.get().setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void setLoadingMessageBackground(final Drawable drawable) {
        if (drawable == null) {
            return;
        }
        checkOrCreateOption();
        mOption.setTextBackground(drawable);
        post(new Runnable() {
            @Override
            public void run() {
                if (tvTextRef.get() == null) {
                    return;
                }
                tvTextRef.get().setBackground(drawable);
                showLoadingTextVisible();
            }
        });
    }

    @Override
    public void setLoadingMessage(@Nullable final String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        checkOrCreateOption();
        mOption.setText(msg);
        post(new Runnable() {
            @Override
            public void run() {
                if (tvTextRef.get() == null) {
                    return;
                }
                tvTextRef.get().setText(msg);
                showLoadingTextVisible();
            }
        });
    }

    @Override
    public void setLoadingMessageColor(@ColorInt final int color) {
        if (color == 0) {
            return;
        }
        checkOrCreateOption();
        mOption.setTextColor(color);
        post(new Runnable() {
            @Override
            public void run() {
                if (tvTextRef.get() == null) {
                    return;
                }
                tvTextRef.get().setTextColor(color);
                showLoadingTextVisible();
            }
        });
    }

    @Override
    public void setLoadingMessageTextAppearance(@StyleRes final int textAppearance) {
        if (textAppearance == 0) {
            return;
        }
        checkOrCreateOption();
        mOption.setTextAppearance(textAppearance);
        post(new Runnable() {
            @Override
            public void run() {
                if (tvTextRef.get() == null) {
                    return;
                }
                tvTextRef.get().setTextAppearance(tvTextRef.get().getContext(), textAppearance);
                showLoadingTextVisible();
            }
        });
    }

    @Override
    public void setOnLoadingOptionListener(LoadingDialogFragmentOption.OnLoadingOptionListener listener) {
        checkOrCreateOption();
        mOption.setOnLoadingOptionListener(listener);
    }

    @Override
    public void setLoadingFragmentOption(LoadingDialogFragmentOption option) {
        if (option == null) {
            return;
        }
        mOption = option;
        setCanCancelWithOutside(option.isCancelWithOutside());
        setDialogSize(option.getDialogWidth(), option.getDialogHeight());
        setLoadingBackground(option.getLoadingBackground());
        setIndeterminateProgressDrawable(option.getIndeterminateDrawable());
        setShowLoadingMessage(option.isShowLoadingText());
        setLoadingMessageBackground(option.getTextBackground());
        setLoadingMessage(option.getText());
        setLoadingMessageColor(option.getTextColor());
        setLoadingMessageTextAppearance(option.getTextAppearance());
    }

    @NonNull
    @Override
    public LoadingDialogFragmentOption getLoadingFragmentOption() {
        checkOrCreateOption();
        return mOption;
    }

    /**
     * 执行所有设置动作
     */
    void executeActions() {
        //防止多并发
        synchronized (mActions) {
            if (mHandler == null) {
                mHandler = new Handler();
            }
            for (Runnable r : mActions) {
                mHandler.post(r);
            }
            mActions.clear();
        }
    }

    /**
     * 检查配置信息对象是否存在，若不存在则创建
     */
    private void checkOrCreateOption() {
        if (mOption == null) {
            mOption = new LoadingDialogFragmentOption();
        }
    }

    /**
     * 显示加载文本
     */
    private void showLoadingTextVisible() {
        if (tvTextRef.get().getVisibility() != View.VISIBLE) {
            tvTextRef.get().setVisibility(View.VISIBLE);
        }
    }

    /**
     * 发起一个动作
     *
     * @param runnable 动作执行体
     */
    private void post(@NonNull Runnable runnable) {
        if (mHandler == null) {
            mActions.add(runnable);
        } else {
            mHandler.post(runnable);
        }
    }
}
