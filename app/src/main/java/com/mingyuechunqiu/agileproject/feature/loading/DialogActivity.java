package com.mingyuechunqiu.agileproject.feature.loading;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.framework.ui.IActivityInflateLayoutViewCreator;
import com.mingyuechunqiu.agile.ui.activity.BaseActivity;
import com.mingyuechunqiu.agileproject.R;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/3/11
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class DialogActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatButton btnAdd = findViewById(R.id.btn_add_loading);
        AppCompatButton btnHide = findViewById(R.id.btn_hide_loading);
        AppCompatButton btnShow = findViewById(R.id.btn_show_loading);
        AppCompatButton btnDismiss = findViewById(R.id.btn_dismiss_loading);
        AppCompatButton btnTest = findViewById(R.id.btn_loading_test);
        btnAdd.setOnClickListener(this);
        btnHide.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        btnDismiss.setOnClickListener(this);
        btnTest.setOnClickListener(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_loading_container, new DialogFragmentTest()).commit();
    }

    @NonNull
    @Override
    protected IActivityInflateLayoutViewCreator generateInflateLayoutViewCreator() {
        return new IActivityInflateLayoutViewCreator.ActivityInflateLayoutViewCreatorAdapter() {
            @Override
            public int getInflateLayoutId() {
                return R.layout.activity_loading_dialog_test;
            }

            @Nullable
            @Override
            public View getInflateLayoutView() {
                return null;
            }
        };
    }

    @Override
    protected void release() {

    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_add_loading) {
            getStatusViewManager().showStatusView(StatusViewConstants.StatusViewType.TYPE_LOADING, findViewById(R.id.fl_loading_container), null);
        } else if (id == R.id.btn_hide_loading || id == R.id.btn_dismiss_loading) {
            dismissStatusView();
        } else if (id == R.id.btn_show_loading) {
            showLoadingStatusView(null, true);
        } else if (id == R.id.btn_loading_test) {
            showToast("能点击到");
        }
    }
}
