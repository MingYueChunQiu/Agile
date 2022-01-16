package com.mingyuechunqiu.agileproject.feature.main;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.framework.ui.IDialogInflateLayoutViewCreator;
import com.mingyuechunqiu.agile.framework.ui.WindowHandler;
import com.mingyuechunqiu.agile.ui.dialog.BaseDialog;
import com.mingyuechunqiu.agileproject.R;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020/4/7 9:50 PM
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
public class TestAppDialog extends BaseDialog {

    public TestAppDialog(Context context) {
        super(context);
    }

    public TestAppDialog(Context context, int theme) {
        super(context, theme);
    }

    protected TestAppDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @NonNull
    @Override
    protected IDialogInflateLayoutViewCreator generateInflateLayoutViewCreator() {
        return new IDialogInflateLayoutViewCreator.DialogInflateLayoutViewCreatorAdapter() {
            @Override
            public int getInflateLayoutId() {
                return R.layout.fragment_status;
            }

            @Nullable
            @Override
            public View getInflateLayoutView() {
                return null;
            }
        };
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setDialogWindow(new WindowHandler() {
            @Override
            public void onHandle(@NonNull Window window) {
                window.setWindowAnimations(R.style.AnimationPop);
            }
        });
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void release() {

    }
}
