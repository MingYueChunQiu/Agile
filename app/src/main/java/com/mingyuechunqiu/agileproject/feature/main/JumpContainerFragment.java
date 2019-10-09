package com.mingyuechunqiu.agileproject.feature.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.agile_layout_frame, container, false);
        FragmentUtils.replaceFragment(getChildFragmentManager(), R.id.fl_agile_frame_container, new JumpFragment1());
        return view;
    }

    @Override
    public void onCall(@NonNull Fragment fragment, @Nullable Bundle bundle) {
        if (!popAddedPage()) {
            returnToPreviousPageWithActivity();
        }
    }
}
