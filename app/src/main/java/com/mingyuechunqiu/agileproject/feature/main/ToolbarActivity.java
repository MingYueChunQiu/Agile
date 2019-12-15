package com.mingyuechunqiu.agileproject.feature.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.ui.activity.BaseToolbarPresenterActivity;
import com.mingyuechunqiu.agile.util.ToolbarUtils;
import com.mingyuechunqiu.agileproject.R;

/**
 * <pre>
 *      Project:    Agile
 *
 *      author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-10-10 23:55
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
public class ToolbarActivity extends BaseToolbarPresenterActivity {

    @Override
    protected ToolbarUtils.ToolbarConfigure setToolbarConfigure() {
        return new ToolbarUtils.ToolbarConfigure.Builder()
                .setImmerse(true)
                .setNavigationIconResId(R.drawable.agile_arrow_back_press)
                .setTitle("分为非")
//                .setEnableDisplayHomeAsUp(true)
                .setHideDisplayTitle(false)
                .setOnIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ToolbarActivity.this, MainActivity.class));
                    }
                })
                .setLogoResId(R.drawable.agile_arrow_back_pressed)
                .build();
    }

    @Override
    public Object initPresenter() {
        return null;
    }

    @Override
    protected void release() {

    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_toolbar_test);
        tbBar = findViewById(R.id.tb_toolbar_test);
        showStatusView(StatusViewConstants.StatusType.TYPE_ERROR, getSupportFragmentManager(),
                R.id.fl_toolbar_test_container, null);
    }
}
