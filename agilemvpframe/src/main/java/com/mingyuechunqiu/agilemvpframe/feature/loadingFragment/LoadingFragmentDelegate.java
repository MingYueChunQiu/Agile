package com.mingyuechunqiu.agilemvpframe.feature.loadingFragment;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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
class LoadingFragmentDelegate implements LoadingFragmentable {

    private final List<Runnable> mActions = new ArrayList<>();

    private WeakReference<View> vContainerRef;
    private WeakReference<View> vLoadingContainerRef;
    private WeakReference<ProgressBar> pbLoadingRef;
    private WeakReference<TextView> tvTextRef;

    private LoadingFragmentOption mOption;
    private Handler mHandler;

    /**
     * 初始化参数
     *
     * @param container        父容器
     * @param loadingContainer 加载容器
     * @param progressBar      进度控件
     * @param textView         加载文本控件
     */
    void initialize(View container, View loadingContainer, ProgressBar progressBar, TextView textView) {
        vContainerRef = new WeakReference<>(container);
        vLoadingContainerRef = new WeakReference<>(loadingContainer);
        pbLoadingRef = new WeakReference<>(progressBar);
        tvTextRef = new WeakReference<>(textView);
        mHandler = new Handler();
        executeActions();
    }

    @Override
    public void release() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        mActions.clear();
        mOption = null;
        vContainerRef = null;
        vLoadingContainerRef = null;
        pbLoadingRef = null;
        tvTextRef = null;
    }

    @Override
    public void setCanTouchOutside(final boolean canTouchOutside) {
        checkOptionOrCreate();
        mOption.setCanTouchOutside(canTouchOutside);
        post(new Runnable() {
            @Override
            public void run() {
                if (vContainerRef.get() == null) {
                    return;
                }
                vContainerRef.get().setClickable(!canTouchOutside);
            }
        });
    }

    @Override
    public void setContainerBackground(final Drawable drawable) {
        if (drawable == null) {
            return;
        }
        checkOptionOrCreate();
        mOption.setContainerBackground(drawable);
        post(new Runnable() {
            @Override
            public void run() {
                if (vContainerRef.get() == null) {
                    return;
                }
                vContainerRef.get().setBackground(drawable);
            }
        });
    }

    @Override
    public void setLoadingBackground(final Drawable drawable) {
        if (drawable == null) {
            return;
        }
        checkOptionOrCreate();
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
        checkOptionOrCreate();
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
        checkOptionOrCreate();
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
        checkOptionOrCreate();
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
        checkOptionOrCreate();
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
        checkOptionOrCreate();
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
    public void setLoadingFragmentOption(LoadingFragmentOption option) {
        if (option == null) {
            return;
        }
        mOption = option;
        setCanTouchOutside(option.isCanTouchOutside());
        setContainerBackground(option.getContainerBackground());
        setLoadingBackground(option.getLoadingBackground());
        setIndeterminateProgressDrawable(option.getIndeterminateDrawable());
        setShowLoadingMessage(option.isShowLoadingText());
        setLoadingMessageBackground(option.getTextBackground());
        setLoadingMessage(option.getText());
        setLoadingMessageColor(option.getTextColor());
        setLoadingMessageTextAppearance(option.getTextAppearance());
    }

    @Override
    public LoadingFragmentOption getLoadingFragmentOption() {
        return mOption;
    }

    /**
     * 检查配置信息对象是否存在，若不存在则创建
     */
    private void checkOptionOrCreate() {
        if (mOption == null) {
            mOption = new LoadingFragmentOption();
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

    /**
     * 执行所有设置动作
     */
    private void executeActions() {
        //防止多并发
        synchronized (mActions) {
            for (Runnable r : mActions) {
                mHandler.post(r);
            }
            mActions.clear();
        }
    }
}
