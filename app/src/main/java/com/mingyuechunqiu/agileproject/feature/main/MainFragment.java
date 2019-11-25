package com.mingyuechunqiu.agileproject.feature.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.ui.fragment.BaseFragment;
import com.mingyuechunqiu.agileproject.R;

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

    @Override
    protected void releaseOnDestroyView() {

    }

    @Override
    protected void releaseOnDestroy() {

    }

    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        AppCompatButton btnShow = view.findViewById(R.id.btn_fragment_main_show);
        AppCompatButton btnHide = view.findViewById(R.id.btn_fragment_main_hide);
        btnShow.setOnClickListener(this);
        btnHide.setOnClickListener(this);
        StatusViewOption option = new StatusViewOption.Builder()
                .setDialogWidth(200)
                .setDialogHeight(600)
                .setCancelWithOutside(true)
                .setOnStatusViewDialogListener(new StatusViewOption.OnStatusViewDialogListener() {
                    @Override
                    public boolean onClickKeyBack(DialogInterface dialog) {
//                        dismissLoadingDialog();
                        return true;
                    }

                    @Override
                    public void onDismissListener(DialogFragment dialogFragment) {
                        showToast("温热");
                    }
                })
                .build();
        showLoadingDialog(option);
//        LoadingDialogFragment loadingDialogFragment = LoadingDialogFragment.newInstance();
//        loadingDialogFragment.setCancelable(false);
//        loadingDialogFragment.setDialogSize(200, 400);
//        loadingDialogFragment.show(getFragmentManager(), LoadingDialogFragment.class.getSimpleName());
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fragment_main_show:
//                showLoadingDialog("被我", false);
                showLoadingDialog(new StatusViewOption.Builder()
                        .setDialogWidth(500)
                        .setDialogHeight(400)
                        .setCancelWithOutside(true)
                        .setOnStatusViewDialogListener(new StatusViewOption.OnStatusViewDialogListener() {
                            @Override
                            public boolean onClickKeyBack(DialogInterface dialog) {
//                                dismissLoadingDialog();
                                showToast("分为");
                                return false;
                            }

                            @Override
                            public void onDismissListener(DialogFragment dialogFragment) {
                                showToast("VB我vwe");
                            }
                        })
                        .setThemeType(StatusViewConstants.ThemeType.DARK_THEME).build());
                break;
            case R.id.btn_fragment_main_hide:
                dismissLoadingDialog();
                break;
        }
    }
}
