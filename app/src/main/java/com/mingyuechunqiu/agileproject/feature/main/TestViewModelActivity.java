package com.mingyuechunqiu.agileproject.feature.main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.mingyuechunqiu.agile.base.viewmodel.IBaseViewModel;
import com.mingyuechunqiu.agile.framework.ui.IActivityInflateLayoutViewCreator;
import com.mingyuechunqiu.agile.ui.activity.BaseDataViewModelActivity;
import com.mingyuechunqiu.agileproject.R;

import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2022/3/6 11:02 上午
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
public class TestViewModelActivity extends BaseDataViewModelActivity {

    private TestViewModel mTestViewModel;

    @NonNull
    @Override
    protected IActivityInflateLayoutViewCreator generateInflateLayoutViewCreator() {
        return new IActivityInflateLayoutViewCreator.ActivityInflateLayoutViewCreatorAdapter() {

            @Override
            public int getInflateLayoutId() {
                return R.layout.activity_test_viewmodel;
            }
        };
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        findViewById(R.id.btn_test_viewmodel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTestViewModel.testToast();
            }
        });
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void release() {

    }

    @NonNull
    @Override
    protected List<IBaseViewModel<?>> initBusinessViewModels() {
        mTestViewModel = new ViewModelProvider(this).get(TestViewModel.class);
        return Collections.singletonList(mTestViewModel);
    }
}
