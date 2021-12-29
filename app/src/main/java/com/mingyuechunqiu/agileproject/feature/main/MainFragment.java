package com.mingyuechunqiu.agileproject.feature.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.feature.statusview.ui.view.IStatusView;
import com.mingyuechunqiu.agile.framework.ui.IFragmentInflateLayoutViewCreator;
import com.mingyuechunqiu.agile.ui.fragment.BaseFragment;
import com.mingyuechunqiu.agileproject.R;

import org.jetbrains.annotations.NotNull;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2019/1/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {

    @NonNull
    @Override
    protected IFragmentInflateLayoutViewCreator generateInflateLayoutViewCreator() {
        return new IFragmentInflateLayoutViewCreator.FragmentInflateLayoutViewCreatorAdapter() {
            @Override
            public int getInflateLayoutId() {
                return R.layout.fragment_main;
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
        AppCompatButton btnShow = view.findViewById(R.id.btn_fragment_main_show);
        AppCompatButton btnHide = view.findViewById(R.id.btn_fragment_main_hide);
        btnShow.setOnClickListener(this);
        btnHide.setOnClickListener(this);
        StatusViewOption option = new StatusViewOption.Builder()
                .setCancelWithOutside(true)
                .setOnStatusViewDialogListener(new StatusViewOption.OnStatusViewDialogListener() {
                    @Override
                    public boolean onClickKeyBack(@NonNull @NotNull IStatusView view) {
                        return false;
                    }

                    @Override
                    public void onDismissListener(@NonNull @NotNull IStatusView view) {
                        showToast("温热");
                    }
                })
                .build();
        getStatusViewManager().showStatusView(StatusViewConstants.StatusViewType.TYPE_LOADING, this, option);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fragment_main_show:
//                showLoadingStatusView("被我", false);
                getStatusViewManager().showStatusView(StatusViewConstants.StatusViewType.TYPE_LOADING, this, new StatusViewOption.Builder()
                        .setCancelWithOutside(true)
                        .setOnStatusViewDialogListener(new StatusViewOption.OnStatusViewDialogListener() {
                            @Override
                            public boolean onClickKeyBack(@NonNull @NotNull IStatusView view) {
                                showToast("分为");
                                return false;
                            }

                            @Override
                            public void onDismissListener(@NonNull @NotNull IStatusView view) {
                                showToast("VB我vwe");
                            }
                        }).build());
                break;
            case R.id.btn_fragment_main_hide:
                dismissStatusView();
                break;
        }
    }
}
