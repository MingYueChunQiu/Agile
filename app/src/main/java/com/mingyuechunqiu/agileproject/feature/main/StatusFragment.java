package com.mingyuechunqiu.agileproject.feature.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.mingyuechunqiu.agile.feature.helper.ui.hint.ToastHelper;
import com.mingyuechunqiu.agile.framework.ui.IFragmentInflateLayoutViewCreator;
import com.mingyuechunqiu.agile.ui.fragment.BaseFragment;
import com.mingyuechunqiu.agileproject.R;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/7/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class StatusFragment extends BaseFragment {

    @Nullable
    @Override
    protected IFragmentInflateLayoutViewCreator generateInflateLayoutViewCreator() {
        return new IFragmentInflateLayoutViewCreator.FragmentInflateLayoutViewCreatorAdapter() {
            @Override
            public int getInflateLayoutId() {
                return R.layout.fragment_status;
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
        AppCompatButton btn1 = view.findViewById(R.id.btn_status_1);
        AppCompatButton btn2 = view.findViewById(R.id.btn_status_2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setDarkStatusBar();
//                returnToPreviousPageWithActivity();
                getWindowInsetsHelper().setDarkStatusBars();
                ToastHelper.showToast("few" + getWindowInsetsHelper().isLightStatusBars() + " " + getWindowInsetsHelper().isStatusBarsVisible());
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setLightStatusBar();
//                getWindowInsetsHelper().showLightStatusBars(true);
                getWindowInsetsHelper().setLightNavigationBars();
            }
        });
    }

    @Override
    protected void initData(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}
