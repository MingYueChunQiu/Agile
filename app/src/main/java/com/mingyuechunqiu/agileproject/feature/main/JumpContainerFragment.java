package com.mingyuechunqiu.agileproject.feature.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.framework.ui.IFragmentInflateLayoutViewCreator;
import com.mingyuechunqiu.agile.ui.fragment.BaseFragment;
import com.mingyuechunqiu.agile.feature.helper.ui.fragment.FragmentHelper;
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
public class JumpContainerFragment extends BaseFragment {

    @Nullable
    @Override
    protected IFragmentInflateLayoutViewCreator generateInflateLayoutViewCreator() {
        return new IFragmentInflateLayoutViewCreator() {
            @Override
            public int getInflateLayoutId() {
                return R.layout.agile_layout_frame;
            }

            @Nullable
            @Override
            public View getInflateLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
                return null;
            }
        };
    }

    @Override
    protected void releaseOnDestroyView() {

    }

    @Override
    protected void releaseOnDestroy() {

    }

    @Override
    protected void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FragmentHelper.replaceFragment(getChildFragmentManager(), R.id.fl_agile_frame_container, new JumpFragment1());
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        getTransferPageDataReceiverHelper().addTransferDataReceiverListener((dataOwner, data) -> {
            if (!popAddedPage()) {
                getTransferPageDataDispatcherHelper().returnToPreviousPageWithActivity(null);
            }
        });
    }
}
