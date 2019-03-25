package com.mingyuechunqiu.agilemvpframeproject.feature.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mingyuechunqiu.agilemvpframe.feature.loading.data.Constants;
import com.mingyuechunqiu.agilemvpframe.feature.loading.data.LoadingDialogFragmentOption;
import com.mingyuechunqiu.agilemvpframe.ui.fragment.BaseFragment;
import com.mingyuechunqiu.agilemvpframeproject.R;

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
        LoadingDialogFragmentOption option = new LoadingDialogFragmentOption.Builder()
                .setDialogWidth(200)
                .setDialogHeight(600)
                .setCancelWithOutside(true)
                .setOnLoadingOptionListener(new LoadingDialogFragmentOption.OnLoadingOptionListener() {
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
                showLoadingDialog(new LoadingDialogFragmentOption.Builder()
                        .setDialogWidth(500)
                        .setDialogHeight(400)
                        .setCancelWithOutside(true)
                        .setOnLoadingOptionListener(new LoadingDialogFragmentOption.OnLoadingOptionListener() {
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
                        .setThemeType(Constants.ThemeType.DARK_THEME).build());
                break;
            case R.id.btn_fragment_main_hide:
                dismissLoadingDialog();
                break;
        }
    }
}
