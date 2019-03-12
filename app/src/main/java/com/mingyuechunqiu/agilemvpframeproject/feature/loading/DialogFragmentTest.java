package com.mingyuechunqiu.agilemvpframeproject.feature.loading;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mingyuechunqiu.agilemvpframe.ui.fragment.BaseFragment;
import com.mingyuechunqiu.agilemvpframeproject.R;

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
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        addOrShowLoadingDialog(getChildFragmentManager(), R.id.fl_fragment_main_loading, null);
        return view;
    }
}
