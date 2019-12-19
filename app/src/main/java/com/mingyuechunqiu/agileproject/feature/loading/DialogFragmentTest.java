package com.mingyuechunqiu.agileproject.feature.loading;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.ui.fragment.BaseFragment;
import com.mingyuechunqiu.agileproject.R;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/3/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class DialogFragmentTest extends BaseFragment {

    @Override
    protected void releaseOnDestroyView() {

    }

    @Override
    protected void releaseOnDestroy() {

    }

    @Override
    protected int getInflateLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {
        showStatusView(StatusViewConstants.StatusType.TYPE_LOADING, getChildFragmentManager(), R.id.fl_fragment_main_loading, null);
    }
}
