package com.mingyuechunqiu.agileproject.feature.main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mingyuechunqiu.agile.ui.fragment.BaseFragment;
import com.mingyuechunqiu.agile.util.FragmentUtils;
import com.mingyuechunqiu.agileproject.R;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/8/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class JumpContainerFragment extends BaseFragment implements BaseFragment.Callback {

    @Override
    protected void releaseOnDestroyView() {

    }

    @Override
    protected void releaseOnDestroy() {

    }

    @Override
    protected int getInflateLayoutId() {
        return R.layout.agile_layout_frame;
    }

    @Override
    protected void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FragmentUtils.replaceFragment(getChildFragmentManager(), R.id.fl_agile_frame_container, new JumpFragment1());
    }

    @Override
    public void onCall(@NonNull Fragment fragment, @Nullable Bundle bundle) {
        if (!popAddedPage()) {
            returnToPreviousPageWithActivity();
        }
    }
}
